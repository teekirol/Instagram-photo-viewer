package codepath.com.instagramphotoviewer;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.net.URI;
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

        avatar.setImageURI(Uri.parse(post.getAvatarUrl()));
        username.setText(post.getUsername());
        postAge.setText(post.getCreatedTime()); // TODO formatting
        photo.setImageURI(Uri.parse(post.getPhotoUrl()));
        numLikes.setText(post.getNumLikes());

        return convertView;
    }

}