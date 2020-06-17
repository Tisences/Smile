package com.tisen.smile.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.tisen.smile.R;
import com.tisen.smile.model.User;
import com.tisen.smile.utils.Classification;
import com.tisen.smile.utils.HttpHelper;
import com.tisen.smile.utils.RequestResult;
import com.tisen.smile.dialog.ChooseImageDialog;
import com.tisen.smile.model.Joke;
import com.tisen.smile.utils.UserInfo;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by tisen on 2016/10/7.
 */
public class WriteJokeActivity extends ContentActivity implements View.OnClickListener {


    private static String tipString = "/160";

    private EditText content;
    private ImageView image;
    private ImageView delete;
    private TextView tip;

    private ProgressBar progress;

    private ChooseImageDialog chooseImageDialog;

    private User user;
    private Joke joke;
    private BmobFile bmobFile;
    private Bitmap bitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        initTop("投稿");
        user = User.getCurrentUser(User.class);
        joke = new Joke();
        buttonImage.setImageResource(R.drawable.submit);
        buttonImage.setVisibility(View.INVISIBLE);
        buttonImage.setOnClickListener(this);


        content = (EditText) findViewById(R.id.write_content);
        image = (ImageView) findViewById(R.id.write_image);
        delete = (ImageView) findViewById(R.id.image_delete);
        tip = (TextView) findViewById(R.id.content_tip);
        progress = (ProgressBar) findViewById(R.id.write_upload_progress);
        progress.setVisibility(View.INVISIBLE);
        delete.setVisibility(View.INVISIBLE);
        image.setOnClickListener(this);
        delete.setOnClickListener(this);
        content.addTextChangedListener(contentChange);
        chooseImageDialog = new ChooseImageDialog(this);
    }

    private TextWatcher contentChange = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            int num = s.length();
            tip.setText(num + "");
            if (num < 5 || num > 160) {
                tip.setTextColor(getResources().getColor(R.color.orangered));
                buttonImage.setVisibility(View.INVISIBLE);
            } else {
                tip.setTextColor(getResources().getColor(R.color.lightsteelblue));
                buttonImage.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.write_image:
                chooseImageDialog.show();
                break;
            case R.id.image_delete:
                deleteImage();
                break;
            case R.id.activity_button_image:
                jokeUpload.sendEmptyMessage(1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RequestResult.CAMERA_REQUEST_CODE:
                bitmap = BitmapFactory.decodeFile(HttpHelper.file.getPath());
                bmobFile = new BmobFile(HttpHelper.file);
                imageUpload.sendEmptyMessage(1);
                break;
            case RequestResult.GALLERY_REQUEST_CODE:
                if (data == null) return;
                else {
                    Uri uri = data.getData();
                    if (uri != null) {
                        File file = new File(uri.getPath());
                        bitmap = BitmapFactory.decodeFile(uri.getPath());
                        bmobFile = new BmobFile(file);
                        imageUpload.sendEmptyMessage(1);
                    }
                }
                break;
            default:
                break;
        }

    }

    private Handler jokeUpload = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            buttonImage.setVisibility(View.INVISIBLE);
            joke.setClassification(Classification.DEFAULT);
            joke.setAuthorName(user.getUsername());
            joke.setAuthorAvatar(user.getAvatar().getFileUrl());
            joke.setContent(content.getText().toString());
            joke.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    progress.setVisibility(View.INVISIBLE);
                    buttonImage.setVisibility(View.INVISIBLE);
                    if (e == null) {
                        Toast.makeText(WriteJokeActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                        UserInfo.myWriteId.add(s);
                        finish();
                    } else {
                        Toast.makeText(WriteJokeActivity.this, "上传失败，请稍后重试", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    };

    private Handler imageUpload = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            image.setImageResource(R.drawable.loading);
            progress.setVisibility(View.VISIBLE);
            bmobFile.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        image.setImageBitmap(bitmap);
                        joke.setImage(bmobFile);
                        delete.setVisibility(View.VISIBLE);
                        Toast.makeText(WriteJokeActivity.this, "图片上传成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(WriteJokeActivity.this, "图片上传失败", Toast.LENGTH_SHORT).show();
                    }
                    progress.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onProgress(Integer value) {
                    progress.setProgress(value);
                }
            });
        }
    };

    private void deleteImage() {
        joke.setImage(null);
        image.setImageResource(R.drawable.image_default);
        delete.setVisibility(View.INVISIBLE);
    }
}
