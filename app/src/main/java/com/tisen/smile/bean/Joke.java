package com.tisen.smile.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

/**
 * Created by tisen on 2016/10/18.
 */
public class Joke extends SugarRecord implements Parcelable{
    private String title;
    private String content;


    public Joke(){

    }

    protected Joke(Parcel in) {
        title = in.readString();
        content = in.readString();
    }

    public static final Creator<Joke> CREATOR = new Creator<Joke>() {
        @Override
        public Joke createFromParcel(Parcel in) {
            return new Joke(in);
        }

        @Override
        public Joke[] newArray(int size) {
            return new Joke[size];
        }
    };

    @Override
    public String toString() {
        return "Joke{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                "} " + super.toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
    }
}
