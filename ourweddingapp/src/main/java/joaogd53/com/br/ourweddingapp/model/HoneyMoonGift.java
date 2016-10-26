package joaogd53.com.br.ourweddingapp.model;

import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by root on 13/09/16.
 */
public class HoneyMoonGift {
    private int idHoneyMoonGift;
    private String description;
    private double totalValue;
    private Uri internetAddress;
    private int quota;
    private String streetAddress;
    private Uri smallImage;
    private Uri largeImage;
    private static HashMap<Integer, HoneyMoonGift> giftsHashMap;

    static {
        giftsHashMap = new HashMap<>();
    }

    private HoneyMoonGift() {

    }

    public static HashMap<Integer, HoneyMoonGift> getAllInstances() {
        return giftsHashMap;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdHoneyMoonGift() {
        return idHoneyMoonGift;
    }

    public void setIdHoneyMoonGift(int idHoneyMoonGift) {
        this.idHoneyMoonGift = idHoneyMoonGift;
    }

    public Uri getInternetAddress() {
        return internetAddress;
    }

    public void setInternetAddress(Uri internetAddress) {
        this.internetAddress = internetAddress;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public Uri getLargeImage() {
        return largeImage;
    }

    public void setLargeImage(Uri largeImage) {
        this.largeImage = largeImage;
    }

    public Uri getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(Uri smallImage) {
        this.smallImage = smallImage;
    }

    public static class HoneyMoonGiftBuilder {
        public static HoneyMoonGift buildHoneyMoonGift() {
            HoneyMoonGift ret = new HoneyMoonGift();
            return ret;
        }

        public static HoneyMoonGift buildFromJson(JSONObject jsonObject) throws JSONException {
            int idGift = jsonObject.getInt("idHoneyMoonGift");
            HoneyMoonGift ret = HoneyMoonGift.giftsHashMap.get(idGift);
            if (ret == null) {
                ret = new HoneyMoonGift();
                ret.idHoneyMoonGift = idGift;
                HoneyMoonGift.giftsHashMap.put(idGift, ret);
            }
            ret.setIdHoneyMoonGift(idGift);
            ret.setDescription(jsonObject.getString("description"));
            ret.setTotalValue(jsonObject.getDouble("totalValue"));
            ret.setInternetAddress(Uri.parse(jsonObject.getString("internetAddress")));
            ret.setQuota(jsonObject.getInt("quota"));
            ret.setStreetAddress(jsonObject.getString("streetAddress"));
            ret.setSmallImage(Uri.parse(jsonObject.getString("smallImage")));
            ret.setLargeImage(Uri.parse(jsonObject.getString("largeImage")));
            return ret;
        }

        public static HoneyMoonGift buildFromId(int idHoneyMoonGift) {
            return HoneyMoonGift.giftsHashMap.get(idHoneyMoonGift);
        }
    }
}
