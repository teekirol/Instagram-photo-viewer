package codepath.com.instagramphotoviewer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CommentsActivity extends Activity {

    ListView listView;
    String mediaId;
    CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        mediaId = getIntent().getStringExtra("mediaId");
        listView = (ListView) findViewById(R.id.lvAllComments);
        ArrayList<Comment> comments = new ArrayList<Comment>();
        commentAdapter = new CommentAdapter(this, comments);
        listView.setAdapter(commentAdapter);
        fetchRecentComments();
    }

    private void fetchRecentComments() {
        InstagramRestClient.getRecentComments(mediaId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray data = response.getJSONArray("data");
                    Log.d("Number of comments", String.valueOf(data.length()));
                    ArrayList<Comment> comments = Comment.fromJson(data);
                    commentAdapter.addAll(comments);
                    commentAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comments, menu);
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
}
