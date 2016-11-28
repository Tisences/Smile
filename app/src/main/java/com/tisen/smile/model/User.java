package com.tisen.smile.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by tisen on 2016/9/29.
 */
public class User extends BmobUser {

    private BmobFile avatar;
    private Bitmap bitmap;
    private boolean sex;
    private List<String> myWriteId;
    private List<String> myCollectId;
    private List<String> myCommentId;
    private List<String> likeJokeId;
    private List<String> dislikeJokeId;
    private String mySettingId;


    @Override
    public String toString() {
        return "User{" +
                "avatar=" + avatar +
                ", bitmap=" + bitmap +
                ", sex=" + sex +
                ", myWriteId=" + myWriteId +
                ", myCollectId=" + myCollectId +
                ", myCommentId=" + myCommentId +
                ", likeJokeId=" + likeJokeId +
                ", dislikeJokeId=" + dislikeJokeId +
                ", mySettingId='" + mySettingId + '\'' +
                "} " + super.toString();
    }

    public BmobFile getAvatar() {
        return avatar;
    }

    public void setAvatar(BmobFile avatar) {
        this.avatar = avatar;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public List<String> getMyWriteId() {
        if(myWriteId==null)
            myWriteId = new ArrayList<>();
        return myWriteId;
    }

    public void setMyWriteId(List<String> myWriteId) {
        this.myWriteId = myWriteId;
    }

    public List<String> getMyCollectId() {
        if (myCollectId==null)
            myCollectId = new ArrayList<>();
        return myCollectId;
    }

    public void setMyCollectId(List<String> myCollectId) {
        this.myCollectId = myCollectId;
    }

    public List<String> getMyCommentId() {
        if(myCommentId==null)
            myCommentId = new ArrayList<>();
        return myCommentId;
    }

    public void setMyCommentId(List<String> myCommentId) {
        this.myCommentId = myCommentId;
    }

    public List<String> getLikeJokeId() {
        if(likeJokeId==null)
            likeJokeId = new ArrayList<>();
        return likeJokeId;
    }

    public void setLikeJokeId(List<String> likeJokeId) {
        this.likeJokeId = likeJokeId;
    }

    public List<String> getDislikeJokeId() {
        if(dislikeJokeId==null)
            dislikeJokeId = new ArrayList<>();
        return dislikeJokeId;
    }

    public void setDislikeJokeId(List<String> dislikeJokeId) {
        this.dislikeJokeId = dislikeJokeId;
    }

    public String getMySettingId() {
        return mySettingId;
    }

    public void setMySettingId(String mySettingId) {
        this.mySettingId = mySettingId;
    }
}
