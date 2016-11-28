package com.tisen.smile.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tisen.smile.R;
import com.tisen.smile.activity.ShowJokeActivity;
import com.tisen.smile.model.Comment;
import com.tisen.smile.model.Joke;
import com.tisen.smile.model.User;
import com.tisen.smile.utils.CollectImageView;
import com.tisen.smile.utils.UserInfo;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by tisen on 2016/9/29.
 */
public class JokeAdapter extends BaseAdapter {

    private Context context;
    private List<Joke> jokes;
    private ImageLoader loader;
    private User user;

    public JokeAdapter(Context context, List<Joke> jokes, ImageLoader loader, User user) {
        this.context = context;
        this.jokes = jokes;
        this.loader = loader;
        this.user = user;
    }
    public void reFresh(List<Joke>jokes){
        this.jokes = jokes;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.jokes.size();
    }

    @Override
    public Object getItem(int position) {
        return this.jokes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        JokeView view = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.joke_view,parent,false);
            view = new JokeView();
            view.authorImage = (ImageView) convertView.findViewById(R.id.joke_author_image);
            view.authorName = (TextView) convertView.findViewById(R.id.joke_author_name);
            view.content = (TextView) convertView.findViewById(R.id.joke_content);
            view.clickTip = (TextView) convertView.findViewById(R.id.joke_click_tip);
            view.likeImage = (ImageView) convertView.findViewById(R.id.joke_like_image);
            view.likeNum = (TextView) convertView.findViewById(R.id.joke_like_num);
            view.dislikeImage = (ImageView) convertView.findViewById(R.id.joke_dislike_image);
            view.dislikeNum = (TextView) convertView.findViewById(R.id.joke_dislike_num);
            view.collect = (CollectImageView) convertView.findViewById(R.id.joke_collect);
            view.image = (ImageView) convertView.findViewById(R.id.joke_image);

            convertView.setTag(view);
        } else {
            view = (JokeView) convertView.getTag();
        }
        final Joke joke = jokes.get(position);


        view.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, ShowJokeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",user);
                bundle.putSerializable("joke",joke);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        loader.displayImage(joke.getAuthorAvatar(), view.authorImage);
        view.authorName.setText(joke.getAuthorName());
        view.content.setText(joke.getContent());
        loader.displayImage(joke.getImage().getFileUrl(), view.image);

        boolean isUserLikeThisJoke = false;
        boolean isUserDislikeThisJoke = false;

        if (user == null) {
            isUserLikeThisJoke = false;
            isUserDislikeThisJoke = false;
        } else {
            isUserLikeThisJoke = UserInfo.myLikeId.contains(joke.getObjectId());
            isUserDislikeThisJoke = UserInfo.myDislikeId.contains(joke.getObjectId());
        }
        if(isUserLikeThisJoke||isUserDislikeThisJoke){
            view.isLikeSelect = true;
        }else {
            view.isLikeSelect = false;
        }
        view.likeImage.setImageResource(isUserLikeThisJoke ? R.drawable.like_select : R.drawable.like);
        view.dislikeImage.setImageResource(isUserDislikeThisJoke ? R.drawable.dislike_select : R.drawable.dislike);

        final JokeView finalView = view;
        view.likeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user==null){
                    Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
                }else{
                    if(finalView.isLikeSelect){
                        Toast.makeText(context, "你已选择过", Toast.LENGTH_SHORT).show();
                    }else {
                        finalView.isLikeSelect = true;
                        finalView.likeImage.setImageResource(R.drawable.like_select);
                        finalView.likeNumInc();
                        UserInfo.myLikeId.add(joke.getObjectId());
                        Message message = new Message();
                        message.obj = joke.getObjectId();
                        like.sendMessage(message);
                    }
                }
            }
        });
        view.dislikeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user==null){
                    Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
                }else{
                    if(finalView.isLikeSelect){
                        Toast.makeText(context, "你已选择过", Toast.LENGTH_SHORT).show();
                    }else {
                        finalView.isLikeSelect = true;
                        finalView.dislikeImage.setImageResource(R.drawable.dislike_select);
                        finalView.dislikeNumInc();
                        UserInfo.myDislikeId.add(joke.getObjectId());
                        Message message = new Message();
                        message.obj = joke.getObjectId();
                        dislike.sendMessage(message);
                    }
                }
            }
        });


        view.collect.setImageResource(R.drawable.collect_select,R.drawable.collect);
        view.collect.initState(joke.getObjectId());
        view.collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user==null){
                    Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
                }else
                ((CollectImageView)v).onClick();
            }
        });

        view.likeImage.setTag(view.likeNum);
        view.dislikeImage.setTag(view.dislikeNum);

        view.likeNum.setText(joke.getLikeNum()+"");
        view.dislikeNum.setText(joke.getDislikeNum()+"");


        return convertView;
    }


    private Handler like = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String jokeId = (String) msg.obj;
            Joke joke = new Joke();
            joke.increment("likeNum");
            joke.update(jokeId, new UpdateListener() {
                @Override
                public void done(BmobException e) {

                }
            });

        }
    };

    private Handler dislike = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String jokeId = (String) msg.obj;
            Joke joke = new Joke();
            joke.increment("dislikeNum");
            joke.update(jokeId, new UpdateListener() {
                @Override
                public void done(BmobException e) {

                }
            });
        }
    };

    class JokeView {
        ImageView authorImage;
        TextView authorName;
        TextView content;
        ImageView image;
        TextView clickTip;
        ImageView likeImage;
        TextView likeNum;
        ImageView dislikeImage;
        TextView dislikeNum;
        CollectImageView collect;
        boolean isLikeSelect = false;

        public void likeNumInc(){
            int i = Integer.parseInt(likeNum.getText().toString())+1;
            likeNum.setText(i+"");
        }
        public void dislikeNumInc(){
            int i = Integer.parseInt(dislikeNum.getText().toString())+1;
            dislikeNum.setText(i+"");
        }
    }
}
