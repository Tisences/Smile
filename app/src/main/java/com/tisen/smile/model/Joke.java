package com.tisen.smile.model;


import com.tisen.smile.utils.FileType;

import java.io.Serializable;
import java.util.ArrayList;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by tisen on 2016/9/29.
 */
public class Joke extends BmobObject implements Serializable{
    private String authorName;              //作者用户名
    private String authorAvatar;            //作者头像地址
    private String content;                 //joke内容
    private BmobFile image;                 //joke图片
    private String accurateComment;         //神评论
    private int likeNum;                    //喜欢人数目
    private int dislikeNum;                 //不喜欢人数目
    private boolean report;                 //是否被举报
    private String classification;          //所属分类


    @Override
    public String toString() {
        return "Joke{" +
                "authorName='" + authorName + '\'' +
                ", authorAvatar='" + authorAvatar + '\'' +
                ", content='" + content + '\'' +
                ", image=" + image +
                ", accurateComment='" + accurateComment + '\'' +
                ", likeNum=" + likeNum +
                ", dislikeNum=" + dislikeNum +
                ", report=" + report +
                ", classification='" + classification + '\'' +
                "} " + super.toString();
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getDislikeNum() {
        return dislikeNum;
    }

    public void setDislikeNum(int dislikeNum) {
        this.dislikeNum = dislikeNum;
    }

    public String getAccurateComment() {
        return accurateComment;
    }

    public void setAccurateComment(String accurateComment) {
        this.accurateComment = accurateComment;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public String getClassification() {
        return classification;
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

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    public boolean isReport() {
        return report;
    }

    public void setReport(boolean report) {
        this.report = report;
    }
}
