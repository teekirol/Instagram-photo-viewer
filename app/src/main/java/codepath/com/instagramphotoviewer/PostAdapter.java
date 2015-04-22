package codepath.com.instagramphotoviewer;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.text.Html;
import android.text.Spanned;
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
        TextView comment1;
        TextView comment2;
    }

    private static String linkTextStyle(String txt) {
        return "<b><font color='#1c5380'>" + txt + "</font></b>";
    }

    private static Spanned commentFormat(String author, String text) {
        return Html.fromHtml(linkTextStyle(author) + " " + text);
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
            viewHolder.comment1 = (TextView) convertView.findViewById(R.id.comment1);
            viewHolder.comment2 = (TextView) convertView.findViewById(R.id.comment2);
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
        Picasso.with(getContext()).load(post.getPhotoUrl()).placeholder(R.drawable.placeholder).into(viewHolder.photo);
        // I don't know how I feel about this view stuff in here
        viewHolder.captionText.setText(commentFormat(post.getUsername(), post.getCaption()));
        viewHolder.numLikes.setText(post.getNumLikes() + " likes");

        if(post.getLocationName() == null || post.getLocationName().isEmpty()) {
            viewHolder.pin.setVisibility(View.GONE);
            viewHolder.locationName.setVisibility(View.GONE);
        } else {
            viewHolder.pin.setVisibility(View.VISIBLE);
            viewHolder.locationName.setVisibility(View.VISIBLE);
            viewHolder.locationName.setText(post.getLocationName());
        }

        if(post.getNumComments() > 2) {
            viewHolder.commentsBtn.setText("view all " + post.getNumCommentsFormatted() + " comments");
            viewHolder.commentsBtn.setVisibility(View.VISIBLE);
        } else {
            viewHolder.commentsBtn.setVisibility(View.GONE);
        }

        if(!post.getComment1Text().isEmpty()) {
            viewHolder.comment1.setText(commentFormat(post.getComment1Author(), post.getComment1Text()));
        } else {
            viewHolder.comment1.setVisibility(View.GONE);
        }

        if(!post.getComment2Text().isEmpty()) {
            viewHolder.comment2.setText(commentFormat(post.getComment2Author(), post.getComment2Text()));
        } else {
            viewHolder.comment2.setVisibility(View.GONE);
        }

        return convertView;
    }

}