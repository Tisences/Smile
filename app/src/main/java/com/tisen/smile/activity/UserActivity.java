package com.tisen.smile.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tisen.smile.dialog.ChooseImageDialog;
import com.tisen.smile.utils.CircleImageView;
import com.tisen.smile.utils.Helper;
import com.tisen.smile.utils.HttpHelper;
import com.tisen.smile.R;
import com.tisen.smile.utils.RequestResult;
import com.tisen.smile.model.User;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by tisen on 2016/10/5.
 */
public class UserActivity extends ContentActivity implements View.OnClickListener {

    public final static int CROP_CODE = 20;

    private CircleImageView userAvatar;
    private ImageView userAvatarBackground;
    private TextView userName;
    private ImageView userSex;
    private Button logoff;

    private User user;
    private ImageLoader loader;
    private ChooseImageDialog chooseImageDialog;

    private File file;
    private Bitmap bitmap;

    private boolean isUserAvatarDown;
    private String isUserAvatarDownKey = "isUserAvatarDown";
    private static final String fileName = "/avatar.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Intent intent = getIntent();
        loader = HttpHelper.getImageLoader(this);
        initTop("个人中心");

        user = User.getCurrentUser(User.class);

        userAvatar = (CircleImageView) findViewById(R.id.user_avatar);
        userAvatarBackground = (ImageView) findViewById(R.id.user_background);
        userName = (TextView) findViewById(R.id.user_name);
        userSex = (ImageView) findViewById(R.id.user_sex);
        logoff = (Button) findViewById(R.id.user_logoff);
        userAvatar.setOnClickListener(this);
        logoff.setOnClickListener(this);
        initUser(user);
        chooseImageDialog = new ChooseImageDialog(this);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case RequestResult.CAMERA_REQUEST_CODE:
                Uri uri = Uri.fromFile(HttpHelper.file);
                starImageZoom(uri);
                break;
            case RequestResult.GALLERY_REQUEST_CODE:
                if (intent == null) return;
                else {
                    Uri uri1 = intent.getData();
                    starImageZoom(uri1);
                }
                break;
            case RequestResult.CROP_REQUEST_CODE:
                if (intent == null) return;
                else {
                    Bundle bundle = intent.getExtras();
                    if (bundle != null) {
                        bitmap = bundle.getParcelable("data");
                        avatarUpLoad.sendEmptyMessage(1);
                    }
                }
                break;
        }
    }

    private void initUser(User user) {
        Bitmap userBitmap = null;
        userName.setText(user.getMobilePhoneNumber());
        userSex.setVisibility(View.VISIBLE);
        userSex.setImageResource(user.isSex() ? R.drawable.male : R.drawable.female);
        logoff.setVisibility(View.VISIBLE);
        userBitmap = HttpHelper.getUserAvatar();
        if (userBitmap == null) {
            userBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.user_default);
            Helper.blurBitmap(this, userAvatarBackground, userBitmap, 20);
            userAvatar.setImageResource(R.drawable.user_default);
        } else {
            Helper.blurBitmap(this, userAvatarBackground, userBitmap, 20);
            userAvatar.setImageBitmap(HttpHelper.getUserAvatar());
        }
    }

    private void userLogoff() {
        User.logOut();
        HttpHelper.setShared(this, "isUserAvatarDown", false);
        setResult(RequestResult.USER_LOGOUT_CODE, new Intent());
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_logoff:
                userLogoff();
                break;
            case R.id.user_avatar:
                userAvatarClick();
                break;
            default:
                break;
        }
    }

    private void userAvatarClick() {
        //更换头像
        chooseImageDialog.show();
    }

    private void starImageZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("return-data", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);

        intent.putExtra("outputFormat", "PNG");// 图片格式
        startActivityForResult(intent, RequestResult.CROP_REQUEST_CODE);
    }

    private Handler avatarUpLoad = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            final BmobFile file = new BmobFile(HttpHelper.setUserAvatar(bitmap));
            file.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        User user1 = new User();
                        user1.setAvatar(file);
                        user1.update(user.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(UserActivity.this, "头像更新成功", Toast.LENGTH_SHORT).show();
                                    userAvatar.setImageBitmap(bitmap);
                                    Helper.blurBitmap(UserActivity.this, userAvatarBackground, bitmap, 20);
                                    Intent intent = new Intent();
                                    setResult(RequestResult.USER_INFOCHANGE_CODE, intent);
                                    finish();
                                } else {
                                    Toast.makeText(UserActivity.this, "头像更新失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(UserActivity.this, "头像上传失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    };
}
