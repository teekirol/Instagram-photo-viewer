package codepath.com.instagramphotoviewer;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.RoundedTransformationBuilder;
import com.ocpsoft.pretty.time.PrettyTime;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

public class CommentAdapter extends ArrayAdapter<Comment> {

    public CommentAdapter(Context c, ArrayList<Comment> comments) {
        super(c, 0, comments);
    }

    public static String linkTextStyle(String txt) {
        return "<b><font color='#1c5380'>" + txt + "</font></b>";
    }

    public static Spanned commentFormat(String author, String text) {
        return Html.fromHtml(linkTextStyle(author) + " " + text);
    }

    private static class ViewHolder {
        ImageView avatar;
        TextView text;
        TextView timestamp;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Comment comment = getItem(position);
        ViewHolder viewHolder;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment_item, parent, false);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.ivCommentAvatar);
            viewHolder.text = (TextView) convertView.findViewById(R.id.tvCommentText);
            viewHolder.timestamp = (TextView) convertView.findViewById(R.id.tvCommentAge);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Transformation transformation = new RoundedTransformationBuilder().oval(true).build();
        Picasso.with(getContext())
                .load(comment.getAvatarUrl()).fit().transform(transformation)
                .placeholder(R.drawable.user_placeholder)
                .into(viewHolder.avatar);
        viewHolder.text.setText(commentFormat(comment.getUsername(), comment.getText()));
        PrettyTime pt = new PrettyTime();
        viewHolder.timestamp.setText(pt.format(comment.getTimestamp()));
        return convertView;
    }
}
