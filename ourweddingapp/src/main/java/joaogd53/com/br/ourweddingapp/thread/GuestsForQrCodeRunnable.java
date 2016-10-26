package joaogd53.com.br.ourweddingapp.thread;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import joaogd53.com.br.ourweddingapp.application.OurWeddingApp;
import joaogd53.com.br.ourweddingapp.model.Guest;

/**
 * Created by root on 26/09/16.
 */
public class GuestsForQrCodeRunnable extends AbstractConnection {

    private String qrCode;
    private List<Guest> guestList;

    public GuestsForQrCodeRunnable(Activity context, String qrCode) {
        super(context);
        this.qrCode = qrCode;
    }

    @Override
    public void writeOutput(URLConnection connection) throws IOException {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("locale", Locale.getDefault());
            jsonObject.put("qrCode", qrCode);
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
        guestList = new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((returnString = in.readLine()) != null) {
                ret = returnString;
            }
            jsonObject = new JSONObject(ret);
            this.returnCode = Integer.parseInt(jsonObject.get("status").toString());
            switch (this.returnCode) {
                case 0:
                    JSONArray guestsArray = jsonObject.getJSONArray("guests");
                    for (int i = 0; i < guestsArray.length(); i++) {
                        JSONObject guestJSON = guestsArray.getJSONObject(i);
                        Guest guest = Guest.GuestBuilder.buildGuestFromJson(guestJSON);
                        this.guestList.add(guest);
                    }
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        URLConnection connection = ConnectionFactory.getInstance()
                .connectionFactory(ConnectionFactory.READ_QR_CODE_SERVLET);
        try {
            this.writeOutput(connection);
            this.writeInput(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Guest> getGuestList() {
        return this.guestList;
    }

}
