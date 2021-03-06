package joaogd53.com.br.ourweddingapp.thread;

import android.app.Activity;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.URLConnection;
import java.util.Locale;

import joaogd53.com.br.ourweddingapp.R;
import joaogd53.com.br.ourweddingapp.application.OurWeddingApp;
import joaogd53.com.br.ourweddingapp.model.Guest;

public class GetAllGuestsRunnable extends AbstractConnection {

    public GetAllGuestsRunnable(Activity context) {
        super(context);
    }

    @Override
    public void writeInput(URLConnection connection) throws IOException {
        JSONObject jsonObject;
        String returnString;
        String ret = "";
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((returnString = in.readLine()) != null) {
                ret = returnString;
            }
            jsonObject = new JSONObject(ret);
            this.returnCode = Integer.parseInt(jsonObject.get("status").toString());
            if (this.returnCode == 0) {
                JSONArray jsonArray = jsonObject.getJSONArray("guests");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject guestJson = jsonArray.getJSONObject(i);
                    Guest.GuestBuilder.buildGuestFromJson(guestJson);
                }
            } else {
                final String message = jsonObject.get("errorMessage").toString();
                this.context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void writeOutput(URLConnection connection) throws IOException {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("locale", Locale.getDefault());
            jsonObject.put("tokenId", OurWeddingApp.getInstance().getTokenId());
            jsonObject.put("email", OurWeddingApp.getInstance().getUser().getPersonEmail());
            jsonObject.put("idGuest", OurWeddingApp.getInstance().getUser().getIdGuest());
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(jsonObject.toString());
            out.close();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            URLConnection connection = ConnectionFactory.getInstance()
                    .connectionFactory(ConnectionFactory.GET_ALL_GUESTS_SERVLET);
            this.writeOutput(connection);
            this.writeInput(connection);
        } catch (ConnectException e) {
            final String message = context.getResources().getString(R.string.connectionRefused);
            this.context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                }
            });
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
