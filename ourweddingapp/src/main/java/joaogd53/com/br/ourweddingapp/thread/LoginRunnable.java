package joaogd53.com.br.ourweddingapp.thread;

import android.app.Activity;
import android.widget.Toast;

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

/**
 * Created by root on 24/09/16.
 */
public class LoginRunnable extends AbstractConnection {

    public LoginRunnable(Activity context) {
        super(context);
    }

    @Override
    public void run() {
        try {
            URLConnection connection = ConnectionFactory.getInstance()
                    .connectionFactory(ConnectionFactory.LOGIN_SERVLET);
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
            this.returnCode = 1;
            e.printStackTrace();
        } catch (Exception e) {
            this.returnCode = 1;
            e.printStackTrace();
        }
    }

    @Override
    public void writeOutput(URLConnection connection) throws IOException {
        JSONObject jsonObject = new JSONObject();
        Guest user = OurWeddingApp.getInstance().getUser();
        try {
            jsonObject.put("locale", Locale.getDefault());
            jsonObject.put("tokenId", OurWeddingApp.getInstance().getTokenId());
            jsonObject.put("email", user.getPersonEmail());
            jsonObject.put("idGuest", OurWeddingApp.getInstance().getUser().getIdGuest());
            jsonObject.put("name", user.getPersonName());
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(jsonObject.toString());
            out.close();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                Guest user = Guest.GuestBuilder.buildGuestFromJson(jsonObject);
                OurWeddingApp.getInstance().setUser(user);
            } else {
                final String message = jsonObject.get("errorMessage").toString();
//                this.context.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
//                    }
//                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
