package codepath.com.instagramphotoviewer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.jar.JarException;

public class Post {

    public String username;
    public String avatarUrl;
    public String createdTime;
    public String caption;
    public String photoUrl;
    public int numLikes;

    public Post(JSONObject object) {
        try {

            JSONObject user = object.getJSONObject("user");
            this.username = user.getString("username");
            this.avatarUrl = user.getString("profile_picture");

            JSONObject caption = object.getJSONObject("caption");
            this.createdTime = caption.getString("created_time");
            this.caption = caption.getString("text");

            JSONObject images = object.getJSONObject("images");
            this.photoUrl = images.getJSONObject("standard_resolution").getString("url");

            JSONObject likes = object.getJSONObject("likes");
            this.numLikes = likes.getInt("count");

        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Post> fromJson(JSONArray array) {
        ArrayList<Post> posts = new ArrayList<Post>(array.length());
        for (int i = 0; i < array.length(); i++) {
            try {
                posts.add(new Post(array.getJSONObject(i)));
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        return posts;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getNumLikes() {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(numLikes);
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

}
