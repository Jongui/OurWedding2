package joaogd53.com.br.ourweddingapp.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import joaogd53.com.br.ourweddingapp.application.OurWeddingApp;

/**
 * Created by root on 30/09/16.
 */
public class Quota {

    private static final String dateFormatString = "yyyyMMdd";
    private HoneyMoonGift honeyMoon;
    private Guest guest;
    private double qtdQuota;
    private double vlrQuota;
    private Date date;

    private Quota() {

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public HoneyMoonGift getHoneyMoon() {
        return honeyMoon;
    }

    public void setHoneyMoon(HoneyMoonGift honeyMoon) {
        this.honeyMoon = honeyMoon;
    }

    public double getQtdQuota() {
        return qtdQuota;
    }

    public void setQtdQuota(double qtdQuota) {
        this.qtdQuota = qtdQuota;
    }

    public double getVlrQuota() {
        return vlrQuota;
    }

    public void setVlrQuota(double vlrQuota) {
        this.vlrQuota = vlrQuota;
    }

    public static class QuotaBuilder {
        public static Quota buildFromJson(JSONObject jsonObject) throws JSONException {
            Quota quota = new Quota();
            SimpleDateFormat sdf = new SimpleDateFormat(Quota.dateFormatString);
            try {
                quota.date = sdf.parse(jsonObject.getString("date"));
            } catch (ParseException e) {
                quota.date = new Date();
                e.printStackTrace();
            }
            quota.guest = OurWeddingApp.getInstance().getUser();
            quota.honeyMoon = HoneyMoonGift.HoneyMoonGiftBuilder.buildFromJson(jsonObject.getJSONObject("idHoneyMoonGift"));
            quota.qtdQuota = jsonObject.getDouble("qtdQuota");
            quota.vlrQuota = jsonObject.getDouble("vlrQuota");
            return quota;
        }

        public static Quota buildQuota(HoneyMoonGift gift, Double value, Date date) {
            Quota ret = new Quota();
            ret.vlrQuota = value;
            ret.honeyMoon = gift;
            ret.guest = OurWeddingApp.getInstance().getUser();
            ret.date = date;
            double quotaValue = gift.getTotalValue() / gift.getQuota();
            ret.qtdQuota = ret.vlrQuota / quotaValue;
            return ret;
        }
    }
}
