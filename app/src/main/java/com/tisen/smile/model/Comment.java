package com.tisen.smile.model;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by tisen on 2016/9/29.
 */
public class Comment extends BmobObject {


    private String authorImage;
    private String authorName;
    private String jokeId;
    private String content;
    private int praiseNum;


    public String getJokeId() {
        return jokeId;
    }

    public void setJokeId(String jokeId) {
        this.jokeId = jokeId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorImage() {
        return authorImage;
    }

    public void setAuthorImage(String authorImage) {
        this.authorImage = authorImage;
    }

    public int getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(int praiseNum) {
        this.praiseNum = praiseNum;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "authorImage='" + authorImage + '\'' +
                ", authorName='" + authorName + '\'' +
                ", jokeId='" + jokeId + '\'' +
                ", content='" + content + '\'' +
                ", praiseNum=" + praiseNum +
                "} " + super.toString();
    }
}
