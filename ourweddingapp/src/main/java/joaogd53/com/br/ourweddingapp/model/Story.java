package joaogd53.com.br.ourweddingapp.model;

import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Story {
    private static final String dateFormatString = "yyyyMMdd";
    private int idStory;
    private String author;
    private String title;
    private String text;
    private String signature;
    private Date date;
    private Uri image;
    private int commentsCount;
    private List<StoryComment> comments;
    private static HashMap<Integer, Story> storyHashMap;
//    private static Story lastStory;

    private Story() {
        comments = new ArrayList<>();
    }

    static {
        storyHashMap = new HashMap<>();
    }
//    public static Story getLastStory() {
//        if (lastStory == null) {
//            lastStory = new Story();
//        }
//        return lastStory;
//    }

//    public static void setLastStory(Story story) {
//        lastStory = story;
//    }

    public static HashMap<Integer, Story> getAllInstance() {
        return storyHashMap;
    }

    public static Story getInstance(int idStory) {
        Story ret = storyHashMap.get(idStory);
        if (ret == null) {
            ret = new Story();
            ret.idStory = idStory;
            storyHashMap.put(idStory, ret);
        }
        return ret;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIdStory() {
        return idStory;
    }

    public void setIdStory(int idStory) {
        this.idStory = idStory;
    }

    public String getSignature() {
        return signature;
    }

    public String getText() {
        return text;
    }

    public Uri getImage() {
        return this.image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public int getCommentsCount() {
        return this.commentsCount;
    }

    public List<StoryComment> getComments() {
        return this.comments;
    }

    public void addComment(StoryComment comment) {
        boolean found = false;
        for (StoryComment c : this.comments) {
            if (c.getPosition() == comment.getPosition()) {
                found = true;
                c = comment;
                break;
            }
        }
        if (!found) this.comments.add(comment);

    }

    public static class StoryBuilder {
        public static Story buildFromJson(JSONObject jsonObject) throws JSONException {
            int idStory = jsonObject.getInt("idStory");
            Story story = Story.getInstance(idStory);
            SimpleDateFormat sdf = new SimpleDateFormat(Story.dateFormatString);
            story.author = jsonObject.getString("author");
            JSONObject file = jsonObject.getJSONObject("file");
            story.title = file.getString("title");
            story.text = file.getString("text");
            story.signature = file.getString("signature");
            story.idStory = jsonObject.getInt("idStory");
            try {
                story.date = sdf.parse(jsonObject.getString("date"));
            } catch (ParseException e) {
                story.date = new Date();
                e.printStackTrace();
            }
            story.image = Uri.parse(jsonObject.getString("image"));
            story.commentsCount = jsonObject.getInt("commentsCount");
            return story;
        }
    }
}
