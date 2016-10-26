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

/**
 * Created by root on 26/09/16.
 */
public class UpdateUserRunnable extends AbstractConnection {

    private Guest guest;

    public UpdateUserRunnable(Activity context, Guest guest) {
        super(context);
        this.guest = guest;
    }

    @Override
    public void writeOutput(URLConnection connection) throws IOException {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("locale", Locale.getDefault());
            jsonObject.put("tokenId", OurWeddingApp.getInstance().getTokenId());
            jsonObject.put("email", OurWeddingApp.getInstance().getUser().getPersonEmail());
            jsonObject.put("userEmail", this.guest.getPersonEmail());
            jsonObject.put("photo", this.guest.getPersonPhoto());
            jsonObject.put("idGuest", new Integer(this.guest.getIdGuest()).toString());
            jsonObject.put("userName", this.guest.getPersonName());
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
            switch (this.returnCode) {
                case 0:
                    this.guest = Guest.GuestBuilder.buildGuestFromJson(jsonObject);
                    OurWeddingApp.getInstance().setUser(this.guest);
                    break;
            }

        } catch (JSONException e) {

        }
    }

    @Override
    public void run() {
        URLConnection connection = ConnectionFactory.getInstance()
                .connectionFactory(ConnectionFactory.UPDATE_USER_SERVLET);
        try {
            this.writeOutput(connection);
            this.writeInput(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
