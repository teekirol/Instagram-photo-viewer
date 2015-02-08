package codepath.com.instagramphotoviewer;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.w3c.dom.Text;

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
        TextView locationName = (TextView) convertView.findViewById(R.id.tvLocationName);
        ImageView photo = (ImageView) convertView.findViewById(R.id.ivPhoto);
        TextView numLikes = (TextView) convertView.findViewById(R.id.tvLikes);
        TextView captionText = (TextView) convertView.findViewById(R.id.tvCaptionText);
        ImageView pin = (ImageView) convertView.findViewById(R.id.ivPin);

        Transformation transformation = new RoundedTransformationBuilder()
            .oval(true)
            .build();

        Picasso.with(getContext()).load(post.getAvatarUrl()).fit().transform(transformation).into(avatar);

        username.setText(post.getUsername());
        postAge.setText(post.getCreatedTime());
        locationName.setText(post.getLocationName());
        Picasso.with(getContext()).load(post.getPhotoUrl()).into(photo);
        captionText.setText(Html.fromHtml("<b><font color='#1c5380'>" + post.getUsername() + "</font></b> " + post.getCaption()));
        numLikes.setText(post.getNumLikes() + " likes");
        if(post.getLocationName() == null) {
            pin.setVisibility(View.INVISIBLE);
        }


        return convertView;
    }

}