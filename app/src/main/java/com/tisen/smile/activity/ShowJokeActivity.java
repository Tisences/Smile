package com.tisen.smile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tisen.smile.utils.HttpHelper;
import com.tisen.smile.R;
import com.tisen.smile.adapter.CommentAdapter;
import com.tisen.smile.model.Comment;
import com.tisen.smile.model.Joke;
import com.tisen.smile.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by tisen on 2016/10/4.
 */
public class ShowJokeActivity extends BaseActivity {

    private ImageView back;
    private ImageView report;
    private ImageView image;

    private ListView listView;
    private CommentAdapter adapter;

    private User user;
    private Joke joke;
    private List<Comment> comments;

    private ImageLoader loader;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showjoke);
        Bmob.initialize(HttpHelper.getConfig(this));
        loader = HttpHelper.getImageLoader(this);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        joke = (Joke) intent.getSerializableExtra("joke");

        listView = (ListView) findViewById(R.id.show_joke_comment_listView);
        back = (ImageView) findViewById(R.id.show_joke_top_back);
        back.setOnClickListener(listener);

        comments = new ArrayList<>();
        adapter = new CommentAdapter(comments, this, loader, user);


        listView.addHeaderView(initHeaderView(),null,false);
        listView.setHeaderDividersEnabled(true);
        listView.setAdapter(adapter);

        commentDown.sendEmptyMessage(1);
    }

    private View initHeaderView(){
        View view = LayoutInflater.from(this).inflate(R.layout.joke_view_1,null);
        TextView authorName = (TextView) view.findViewById(R.id.joke_author_name);
        ImageView authorImage = (ImageView) view.findViewById(R.id.joke_author_image);
        ImageView image = (ImageView) view.findViewById(R.id.joke_image);
        TextView content = (TextView) view.findViewById(R.id.joke_content);
        ImageView likeImage = (ImageView) view.findViewById(R.id.joke_like_image);
        TextView likeNum = (TextView) view.findViewById(R.id.joke_like_num);
        ImageView dislikeImage = (ImageView) view.findViewById(R.id.joke_dislike_image);
        TextView dislikeNum = (TextView) view.findViewById(R.id.joke_dislike_num);
        ImageView collect = (ImageView) view.findViewById(R.id.joke_collect);

        authorName.setText(joke.getAuthorName());
        loader.displayImage(joke.getAuthorAvatar(),authorImage);
        loader.displayImage(joke.getImage().getFileUrl(),image);

        content.setText(joke.getContent());
        likeNum.setText(joke.getLikeNum()+"");
        dislikeNum.setText(joke.getDislikeNum()+"");
        return view;
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.show_joke_top_back:finish();break;
            }
        }
    };

    private Handler commentDown = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            BmobQuery<Comment> query = new BmobQuery<>();
            query.addWhereEqualTo("jokeId", joke.getObjectId());
            query.findObjects(new FindListener<Comment>() {
                @Override
                public void done(List<Comment> list, BmobException e) {
                    if (e == null) {
                        if (!list.isEmpty()) {
                            Log.d("comments size is"+list.size(), Arrays.toString(list.toArray()));
                            comments = list;
                            adapter.reFresh(list);
                        } else {

                        }
                    } else {

                    }

                }
            });
        }
    };
}
