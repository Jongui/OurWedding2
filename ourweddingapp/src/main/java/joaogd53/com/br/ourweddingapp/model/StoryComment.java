package joaogd53.com.br.ourweddingapp.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StoryComment {
    private static final String dateFormatString = "yyyyMMdd";
    private Story story;
    private int position;
    private Date date;
    private String text;
    private Guest guest;

    private StoryComment() {

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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject ret = new JSONObject();
        ret.put("idStory", this.story.getIdStory());
        ret.put("position", this.position);
        ret.put("text", this.text);
        ret.put("date", new SimpleDateFormat(StoryComment.dateFormatString).format(this.date));
        ret.put("idGuest", this.guest.getIdGuest());
        return ret;
    }

    public static class StoryCommentBuilder {
        public static StoryComment buildNewComment(Story story, int position, String text, Guest guest) {
            StoryComment ret = new StoryComment();
            ret.story = story;
            ret.position = position;
            ret.text = text;
            ret.date = new Date();
            ret.guest = guest;
            return ret;
        }

        public static StoryComment buildFromJson(JSONObject commentJson, Story story) throws JSONException {
            StoryComment ret = new StoryComment();
            SimpleDateFormat sdf = new SimpleDateFormat(StoryComment.dateFormatString);
            ret.story = story;
            ret.position = commentJson.getInt("position");
            ret.text = commentJson.getString("text");
            int idGuest = commentJson.getInt("idGuest");
            ret.guest = Guest.getInstance(idGuest);
            try {
                ret.date = sdf.parse(commentJson.getString("date"));
            } catch (ParseException e) {
                ret.date = new Date();
                e.printStackTrace();
            }
            return ret;
        }
    }
}
