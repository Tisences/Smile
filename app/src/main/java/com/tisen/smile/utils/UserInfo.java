package com.tisen.smile.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tisen.smile.model.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tisen on 2016/10/9.
 */
public class UserInfo {
    public static List<String> myWriteId = new ArrayList<>();
    public static List<String> myCollectId = new ArrayList<>();
    public static List<String> myCommentId = new ArrayList<>();
    public static List<String> myLikeId = new ArrayList<>();
    public static List<String> myDislikeId = new ArrayList<>();


    public final static String MY_WRITE_NAME = "myWrite";
    public final static String MY_COLLECT_NAME = "myCollect";
    public final static String MY_COMMENT_NAME = "myComment";
    public final static String MY_LIKE_NAME = "myLike";
    public final static String MY_DISLIKE_NAME = "myDislike";
    public final static String MY_ALL_NAME = "all";

    public static void initInfo(Context context) {
        myWriteId = getList(context, MY_WRITE_NAME);
        myCollectId = getList(context, MY_COLLECT_NAME);
        myCommentId = getList(context, MY_COMMENT_NAME);
        myLikeId = getList(context,MY_LIKE_NAME);
        myDislikeId = getList(context,MY_DISLIKE_NAME);
    }

    public static void saveInfo(Context context, String flag) {
        switch (flag) {
            case MY_WRITE_NAME:
                setList(context, MY_WRITE_NAME, myWriteId);
                break;
            case MY_COLLECT_NAME:
                setList(context, MY_COLLECT_NAME, myCollectId);
                break;
            case MY_COMMENT_NAME:
                setList(context, MY_COMMENT_NAME, myCommentId);
                break;
            case MY_LIKE_NAME:
                setList(context, MY_LIKE_NAME, myLikeId);
                break;
            case MY_DISLIKE_NAME:
                setList(context, MY_DISLIKE_NAME, myDislikeId);
                break;
            case MY_ALL_NAME:
                setList(context, MY_WRITE_NAME, myWriteId);
                setList(context, MY_COLLECT_NAME, myCollectId);
                setList(context, MY_COMMENT_NAME, myCommentId);
                setList(context, MY_LIKE_NAME, myLikeId);
                setList(context, MY_DISLIKE_NAME, myDislikeId);
                break;
            default:break;
        }
    }

    public static List<String> getList(Context context, String groupName) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("smile", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(groupName, null);
        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<String>>() {
            }.getType();
            List<String> lists = new ArrayList<>();
            lists = gson.fromJson(json, type);
            return lists;
        }
        return new ArrayList<String>();

    }

    public static void setList(Context context, String groupName, List<String> list) {
        SharedPreferences.Editor editor = context.getSharedPreferences("smile", Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        Log.d("json "+groupName+":", json);
        editor.putString(groupName, json);
        editor.commit();
    }
    public static void downList(User user){
        if(user.getMyWriteId()!=null)myWriteId = user.getMyWriteId();
        if(user.getMyCollectId()!=null)myWriteId = user.getMyCollectId();

        if(user.getMyCommentId()!=null)myWriteId = user.getMyCommentId();
        if(user.getLikeJokeId()!=null)myWriteId = user.getLikeJokeId();
        if(user.getDislikeJokeId()!=null)myWriteId = user.getDislikeJokeId();

    }
}
