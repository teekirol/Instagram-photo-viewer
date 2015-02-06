package codepath.com.instagramphotoviewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class PostAdapter extends ArrayAdapter<Post> {

    public PostAdapter(Context c, ArrayList<Post> posts) {
        super(c, 0, posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Post post = getItem(position);
        // TODO Use ViewHolder pattern
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.post_item, parent, false);
        }
        ImageView avatar = (ImageView) convertView.findViewById(R.id.ivAvatar);
        TextView username = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView postAge = (TextView) convertView.findViewById(R.id.tvPostAge);
        ImageView photo = (ImageView) convertView.findViewById(R.id.ivPhoto);
        TextView numLikes = (TextView) convertView.findViewById(R.id.tvLikes);

        Picasso.with(getContext()).load(post.getAvatarUrl()).into(avatar);
        username.setText(post.getUsername());
        postAge.setText(post.getCreatedTime()); // TODO formatting
        Picasso.with(getContext()).load(post.getPhotoUrl()).into(photo);
        numLikes.setText(post.getNumLikes() + " likes");

        return convertView;
    }

}