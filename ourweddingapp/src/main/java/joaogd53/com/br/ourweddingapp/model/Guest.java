package joaogd53.com.br.ourweddingapp.model;

import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Guest {

    private int idGuest;
    private String personName;
    private String personEmail;
    private Uri personPhoto;
    private int status;
    private int age;
    private String code;
    private static HashMap<Integer, Guest> guestHashMap;

    static {
        guestHashMap = new HashMap<>();
    }

    private Guest() {
        this.personEmail = "";
        this.personName = "";
    }

    public static HashMap<Integer, Guest> getAllInstances() {
        return guestHashMap;
    }

    public static Guest getInstance(int idGuest) {
        Guest guest = guestHashMap.get(idGuest);
        if (guest == null) {
            guest = new Guest();
            guest.idGuest = idGuest;
            guestHashMap.put(idGuest, guest);
        }
        return guestHashMap.get(idGuest);
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }

    public int getIdGuest() {
        return idGuest;
    }

    public void setIdGuest(int idGuest) {
        this.idGuest = idGuest;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Uri getPersonPhoto() {
        return personPhoto;
    }

    public void setPersonPhoto(Uri personPhoto) {
        this.personPhoto = personPhoto;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCode() {
        return this.code;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject ret = new JSONObject();
        ret.put("age", this.age);
        ret.put("email", this.personEmail);
        ret.put("name", this.personName);
        ret.put("photo", this.personPhoto);
        ret.put("status", this.status);
        ret.put("idGuest", this.idGuest);
        ret.put("code", this.code);
        return ret;
    }

    public static class GuestBuilder {
        public static Guest buildUser(String personName, String personEmail, Uri personPhoto) {
            Guest user = new Guest();
            user.personEmail = personEmail;
            user.personName = personName;
            if (user.personName == null) {
                user.personName = "";
            }
            user.personPhoto = personPhoto;
            return user;
        }

        public static Guest buildGuestFromJson(JSONObject jsonObject) throws JSONException {
            int idGuest = jsonObject.getInt("idGuest");
            Guest ret = Guest.getAllInstances().get(idGuest);
            if (ret == null) {
                ret = new Guest();
                Guest.guestHashMap.put(idGuest, ret);
            }
            ret.idGuest = idGuest;
            ret.personName = jsonObject.getString("name");
            try {
                ret.personEmail = jsonObject.getString("email");
            } catch (Exception ex) {
                ret.personEmail = "";
            }
            ret.status = jsonObject.getInt("status");
            try {
                ret.personPhoto = Uri.parse(jsonObject.getString("photo"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            ret.code = jsonObject.getString("code");
            ret.age = jsonObject.getInt("age");
            return ret;
        }

        public static Guest buildGuest() {
            return new Guest();
        }
    }

}
