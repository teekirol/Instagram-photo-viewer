package codepath.com.instagramphotoviewer;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

    private static class ViewHolder {
        ImageView avatar;
        TextView username;
        TextView postAge;
        TextView locationName;
        ImageView photo;
        TextView numLikes;
        TextView captionText;
        ImageView pin;
        Button commentsBtn;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Post post = getItem(position);

        ViewHolder viewHolder;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.post_item, parent, false);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.ivAvatar);
            viewHolder.username = (TextView) convertView.findViewById(R.id.tvUsername);
            viewHolder.postAge = (TextView) convertView.findViewById(R.id.tvPostAge);
            viewHolder.locationName = (TextView) convertView.findViewById(R.id.tvLocationName);
            viewHolder.photo = (ImageView) convertView.findViewById(R.id.ivPhoto);
            viewHolder.numLikes = (TextView) convertView.findViewById(R.id.tvLikes);
            viewHolder.captionText = (TextView) convertView.findViewById(R.id.tvCaptionText);
            viewHolder.pin = (ImageView) convertView.findViewById(R.id.ivPin);
            viewHolder.commentsBtn = (Button) convertView.findViewById(R.id.btnComments);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Transformation transformation = new RoundedTransformationBuilder()
            .oval(true)
            .build();

        Picasso.with(getContext()).load(post.getAvatarUrl()).fit().transform(transformation).into(viewHolder.avatar);

        viewHolder.username.setText(post.getUsername());
        viewHolder.postAge.setText(post.getCreatedTime());
        viewHolder.locationName.setText(post.getLocationName());
        Picasso.with(getContext()).load(post.getPhotoUrl()).into(viewHolder.photo);
        viewHolder.captionText.setText(Html.fromHtml("<b><font color='#1c5380'>" + post.getUsername() + "</font></b> " + post.getCaption()));
        viewHolder.numLikes.setText(post.getNumLikes() + " likes");

        if(post.getLocationName() == null) {
            viewHolder.pin.setVisibility(View.GONE);
            viewHolder.locationName.setVisibility(View.GONE);
        } else {
            viewHolder.pin.setVisibility(View.VISIBLE);
            viewHolder.locationName.setVisibility(View.VISIBLE);
        }

        if(post.getNumComments() > 5) {
            viewHolder.commentsBtn.setText("view all " + post.getNumCommentsFormatted() + " comments");
            viewHolder.commentsBtn.setVisibility(View.VISIBLE);
        } else {
            viewHolder.commentsBtn.setVisibility(View.GONE);
        }

        return convertView;
    }

}