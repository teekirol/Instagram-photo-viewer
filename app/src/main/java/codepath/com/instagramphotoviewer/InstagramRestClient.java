package codepath.com.instagramphotoviewer;

import org.json.*;
import com.loopj.android.http.*;

public class InstagramRestClient {

    private static final String BASE_URL = "https://api.instagram.com/v1/";
    private static final String CLIENT_ID = "22e95e3cb61040449f9dc23a7d197fe8";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void getPopular(AsyncHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams("client_id", CLIENT_ID);
        get("media/popular", params, responseHandler);
    }

    public static void getRecentComments(String mediaId, AsyncHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams("client_id", CLIENT_ID);
        get("media/" + mediaId + "/comments", params, responseHandler);
    }

    private static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

}
