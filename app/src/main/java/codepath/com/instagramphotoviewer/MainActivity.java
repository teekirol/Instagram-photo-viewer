package codepath.com.instagramphotoviewer;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    ListView listView;
    PostAdapter adapter;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPopularPhotos();
            }
        });

        listView = (ListView) findViewById(R.id.lvPosts);
        ArrayList<Post> posts = new ArrayList<Post>();
        adapter = new PostAdapter(this, posts);
        listView.setAdapter(adapter);

        fetchPopularPhotos();
    }

    private void fetchPopularPhotos() {
        // TODO Check if connected to the internet
        InstagramRestClient.getPopular(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers,
                                  org.json.JSONObject response) {
                try {
                    ArrayList<Post> posts = Post.fromJson(response.getJSONArray("data"));
                    System.out.println("Received " + posts.size() + " photos");
                    adapter.clear();
                    for(Post p: posts) {
                        adapter.add(p);
                    }
                    adapter.notifyDataSetChanged();
                } catch(JSONException e) {
                    e.printStackTrace();
                } finally {
                    swipeContainer.setRefreshing(false);
                }
            }
            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers,
                                  java.lang.Throwable throwable, org.json.JSONObject errorResponse) {
                System.out.println("GET failed with " + statusCode);
                System.out.println(errorResponse.toString());
                throwable.printStackTrace();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}