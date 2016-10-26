package joaogd53.com.br.ourweddingapp.thread;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Locale;

import joaogd53.com.br.ourweddingapp.R;
import joaogd53.com.br.ourweddingapp.application.OurWeddingApp;
import joaogd53.com.br.ourweddingapp.model.Quota;

/**
 * Created by root on 30/09/16.
 */
public class QuotaSaveRunnable extends AbstractConnection {
    private static final String dateFormatString = "yyyyMMdd";
    private Quota quota;

    public QuotaSaveRunnable(Context context, Quota quota) {
        super((Activity) context);
        this.quota = quota;
    }

    @Override
    public void writeOutput(URLConnection connection) throws IOException {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("locale", Locale.getDefault());
            jsonObject.put("tokenId", OurWeddingApp.getInstance().getTokenId());
            jsonObject.put("email", OurWeddingApp.getInstance().getUser().getPersonEmail());
            jsonObject.put("idGuest", OurWeddingApp.getInstance().getUser().getIdGuest());
            JSONObject jsonQuota = new JSONObject();
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormatString);
            jsonQuota.put("date", sdf.format(quota.getDate()));
            jsonQuota.put("idGuest", quota.getGuest().getIdGuest());
            jsonQuota.put("idHoneyMoonGift", quota.getHoneyMoon().getIdHoneyMoonGift());
            jsonQuota.put("qtdQuota", quota.getQtdQuota());
            jsonQuota.put("vlrQuota", quota.getVlrQuota());
            jsonObject.put("quota", jsonQuota);
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
                this.quota = Quota.QuotaBuilder.buildFromJson(jsonObject.getJSONObject("quota"));
            } else {
                String message = jsonObject.get("errorMessage").toString();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            URLConnection connection = ConnectionFactory.getInstance()
                    .connectionFactory(ConnectionFactory.QUOTA_SAVE_SERVLET);
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
