package codepath.com.instagramphotoviewer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Comment {

    private String avatarUrl;
    private String username;
    private String text;
    private Date timestamp;

    public Date getTimestamp() {
        return timestamp;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Comment(JSONObject object) {
        try {
            JSONObject user = object.getJSONObject("from");
            this.avatarUrl = user.getString("profile_picture");
            this.username = user.getString("username");
            this.text = object.getString("text");
            this.timestamp = new Date(Long.parseLong(object.getString("created_time"))*1000);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Comment> fromJson(JSONArray array) {
        ArrayList<Comment> comments = new ArrayList<Comment>(array.length());
        for(int i = 0; i < array.length(); i++) {
            try {
                comments.add(new Comment(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return comments;
    }
}
