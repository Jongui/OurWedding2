package joaogd53.com.br.ourweddingapp.thread;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLConnection;
import java.util.Locale;

import joaogd53.com.br.ourweddingapp.application.OurWeddingApp;
import joaogd53.com.br.ourweddingapp.model.Guest;

public class GuestByIdRunnable extends AbstractConnection {
    private int idGuest;
    private Guest guest;

    public Guest getGuest() {
        return this.guest;
    }

    public GuestByIdRunnable(Activity context, int idGuest) {
        super(context);
        this.idGuest = idGuest;
    }

    @Override
    public void writeOutput(URLConnection connection) throws IOException {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("locale", Locale.getDefault());
            jsonObject.put("searchId", this.idGuest);
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
                this.guest = Guest.GuestBuilder.buildGuestFromJson(jsonObject.getJSONObject("guest"));
            } else {
                final String message = jsonObject.get("errorMessage").toString();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        URLConnection connection = ConnectionFactory.getInstance()
                .connectionFactory(ConnectionFactory.GUEST_BY_ID_SERVLET);
        try {
            this.writeOutput(connection);
            this.writeInput(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
