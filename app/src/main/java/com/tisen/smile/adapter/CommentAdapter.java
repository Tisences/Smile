package com.tisen.smile.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tisen.smile.R;
import com.tisen.smile.model.Comment;
import com.tisen.smile.model.User;

import java.util.List;

/**
 * Created by tisen on 2016/9/29.
 */
public class CommentAdapter extends BaseAdapter {

    private Context context;
    private List<Comment>comments;
    private ImageLoader loader;
    private User user;
    public CommentAdapter(List<Comment> comments, Context context, ImageLoader loader, User user) {
        this.comments = comments;
        this.context = context;
        this.loader = loader;
        this.user = user;
    }

    public void reFresh(List<Comment>comments){
        this.comments = comments;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CommentView view = null;
        Comment comment = comments.get(position);
        Log.d("comment"+position,comment.toString());
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_view,null);
            view = new CommentView();

            view.commentFlag = (ImageView) convertView.findViewById(R.id.comment_flag);
            view.authorImage = (ImageView) convertView.findViewById(R.id.author_image);
            view.authorName = (TextView) convertView.findViewById(R.id.author_name);
            view.praiseImage = (ImageView) convertView.findViewById(R.id.praise_image);
            view.praiseNum = (TextView) convertView.findViewById(R.id.praise_num);
            view.praise = convertView.findViewById(R.id.praise);
            view.content = (TextView) convertView.findViewById(R.id.comment_content);

            convertView.setTag(view);
        }{
            view = (CommentView) convertView.getTag();
        }
        loader.displayImage(comment.getAuthorImage(),view.authorImage);
        view.authorName.setText(comment.getAuthorName());
        view.praiseNum.setText(comment.getPraiseNum()+"");
        view.content.setText(comment.getContent());
        switch (position){
            case 0:view.commentFlag.setImageResource(R.drawable.accurate_comment);break;
            case 1:view.commentFlag.setImageResource(R.drawable.comment);break;
            default:view.commentFlag.setVisibility(View.GONE);
        }
        return convertView;
    }
    class CommentView{
        ImageView commentFlag;
        ImageView authorImage;
        TextView authorName;
        View praise;
        ImageView praiseImage;
        TextView praiseNum;
        TextView content;
    }
}
