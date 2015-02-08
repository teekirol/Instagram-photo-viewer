package codepath.com.instagramphotoviewer;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Post {

    public String username;
    public String avatarUrl;
    public String createdTime;
    public String caption;
    public String photoUrl;
    public int numLikes;
    public String locationName;

    private static final int WEEKS_IN_A_YEAR = 52;
    private static final int WEEKS_IN_A_MONTH = 4;
    private static final String WEEKS_SUFFIX = "w";
    private static final String DAYS_SUFFIX = "d";
    private static final String HOURS_SUFFIX = "h";
    private static final String MINUTES_SUFFIX = "m";
    private static final String SECONDS_SUFFIX = "s";


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

            JSONObject location = object.getJSONObject("location");
            this.locationName = location.getString("name");

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
        try {
            // The time from Instagram is seconds from the epoch, while DateTime expects milliseconds
            DateTime d = new DateTime(Long.parseLong(createdTime)*1000);
            Period p = new Period(d, DateTime.now());
            if(p.getYears() > 0) {
                return WEEKS_IN_A_YEAR*p.getYears() + WEEKS_SUFFIX;
            } else if(p.getMonths() > 0) {
                return WEEKS_IN_A_MONTH*p.getMonths() + WEEKS_SUFFIX;
            } else if(p.toStandardWeeks().getWeeks() > 0) {
                return p.toStandardWeeks().getWeeks() + WEEKS_SUFFIX;
            } else if(p.toStandardDays().getDays() > 0) {
                return p.toStandardDays().getDays() + DAYS_SUFFIX;
            } else if(p.toStandardHours().getHours() > 0) {
                return p.toStandardHours().getHours() + HOURS_SUFFIX;
            } else if(p.toStandardMinutes().getMinutes() > 0) {
                return p.toStandardMinutes().getMinutes() + MINUTES_SUFFIX;
            } else {
                return p.toStandardSeconds().getSeconds() + SECONDS_SUFFIX;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "";
        }
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

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

}
