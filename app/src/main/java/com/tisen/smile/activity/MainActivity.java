package com.tisen.smile.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tisen.smile.utils.CircleImageView;
import com.tisen.smile.utils.Helper;
import com.tisen.smile.utils.HttpHelper;
import com.tisen.smile.R;
import com.tisen.smile.dialog.LoginDialog;
import com.tisen.smile.utils.RequestResult;
import com.tisen.smile.adapter.ViewPagerAdapter;
import com.tisen.smile.fragment.FindFragment;
import com.tisen.smile.fragment.HotFragment;
import com.tisen.smile.fragment.SelectedFragment;
import com.tisen.smile.model.User;
import com.tisen.smile.utils.UserInfo;

import java.util.ArrayList;

import cn.bmob.v3.Bmob;

/**
 * Created by tisen on 2016/10/3.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {


    private DrawerLayout drawerLayout;
    private ImageView userAvatarBackground;
    private CircleImageView userAvatar;
    private View myWrite;
    private View myComment;
    private View myCollect;
    private View setting;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;


    private ImageView topUserImage;
    private ImageView topJokeAdd;


    private ImageLoader imageLoader;
    private boolean isUserAvatarDown;
    private String isUserAvatarDownKey = "isUserAvatarDown";
    private static final String fileName = "/avatar.png";

    private LoginDialog loginDialog;


    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        user = User.getCurrentUser(User.class);
        imageLoader = HttpHelper.getImageLoader(this);
        Bmob.initialize(HttpHelper.getConfig(this));
        isUserAvatarDown = HttpHelper.getShared(this, isUserAvatarDownKey);


        initViewPager(user);

        initMenuView(user);
        loginDialog = new LoginDialog(this, imageLoader);

        loginDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                User user = User.getCurrentUser(User.class);
                Message message = new Message();
                message.obj = user;
                userChange.sendMessage(message);
            }
        });

        drawerLayout.closeDrawer(Gravity.LEFT);

    }

    private void initMenuView(User user) {
        this.user = user;
        Bitmap userBitmap = null;
        if (user == null) {
            userBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.user_default);
            Helper.blurBitmap(this, userAvatarBackground, userBitmap, 20);
            userAvatar.setImageResource(R.drawable.user_default);
        } else {
            UserInfo.initInfo(this);
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
    }

    private Handler userChange = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            User user = (User) msg.obj;
            initMenuView(user);
            initViewPager(user);
        }
    };

    private void initViewPager(User user) {
        ArrayList<String> titles = new ArrayList<>();
        titles.add("精选");
        titles.add("热门");
        titles.add("发现");
        ArrayList<Fragment> fragments = new ArrayList<>();

        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);

        SelectedFragment selectedFragment = new SelectedFragment();
        selectedFragment.setArguments(bundle);
        fragments.add(selectedFragment);

        HotFragment hotFragment = new HotFragment();
        hotFragment.setArguments(bundle);
        fragments.add(hotFragment);

        FindFragment findFragment = new FindFragment();
        findFragment.setArguments(bundle);
        fragments.add(findFragment);

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), titles, this, fragments);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    private void findViews() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        userAvatarBackground = (ImageView) findViewById(R.id.user_avatar_background);
        userAvatar = (CircleImageView) findViewById(R.id.user_avatar);
        myWrite = findViewById(R.id.menu_my_write);
        myComment = findViewById(R.id.menu_my_comment);
        myCollect = findViewById(R.id.menu_my_collect);
        setting = findViewById(R.id.menu_setting);
        topUserImage = (ImageView) findViewById(R.id.top_user_image);
        topJokeAdd = (ImageView) findViewById(R.id.top_joke_add);

        topUserImage.setOnClickListener(this);
        topJokeAdd.setOnClickListener(this);
        userAvatar.setOnClickListener(this);
        myWrite.setOnClickListener(this);
        myComment.setOnClickListener(this);
        myCollect.setOnClickListener(this);
        setting.setOnClickListener(settingListener);

        setting.setVisibility(View.INVISIBLE);
        viewPager = (ViewPager) findViewById(R.id.main_viewpager);
        tabLayout = (TabLayout) findViewById(R.id.main_tab);
    }

    private void changeAvatar() {
        Bitmap userBitmap;
        if (user == null) {
            userBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.user_default);
            Helper.blurBitmap(this, userAvatarBackground, userBitmap, 20);
            userAvatar.setImageResource(R.drawable.user_default);
        } else {
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RequestResult.MAIN_REQUEST_CODE) {
            switch (resultCode) {
                case RequestResult.USER_LOGOUT_CODE:
                    Message message = new Message();
                    message.obj = null;
                    user = User.getCurrentUser(User.class);
                    userChange.sendMessage(message);
                    break;
                case RequestResult.USER_INFOCHANGE_CODE:
                    changeAvatar();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (user == null) {
            Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
            loginDialog.show();
        } else {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            switch (v.getId()) {
                case R.id.user_avatar:
                    intent.setClass(this, UserActivity.class);
                    bundle.putSerializable("user", user);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, RequestResult.MAIN_REQUEST_CODE);
                    break;
                case R.id.menu_my_write:
                    intent.setClass(this, MyWriteActivity.class);
                    startActivityForResult(intent, RequestResult.MAIN_REQUEST_CODE);
                    break;
                case R.id.menu_my_collect:
                    intent.setClass(this, MyCollectActivity.class);
                    startActivityForResult(intent, RequestResult.MAIN_REQUEST_CODE);
                    break;
                case R.id.menu_my_comment:
                    intent.setClass(this, MyCommentActivity.class);
                    startActivityForResult(intent, RequestResult.MAIN_REQUEST_CODE);
                    break;
                case R.id.top_user_image:
                    drawerLayout.openDrawer(Gravity.LEFT);
                    break;
                case R.id.top_joke_add:
                    intent.setClass(this, WriteJokeActivity.class);
                    bundle.putSerializable("user", user);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, RequestResult.MAIN_REQUEST_CODE);
                    break;
                default:
                    break;
            }
        }

    }

    private View.OnClickListener settingListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, SettingActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user);
            intent.putExtras(bundle);
            startActivityForResult(intent, RequestResult.MAIN_REQUEST_CODE);
        }
    };

    @Override
    public void finish() {
        UserInfo.saveInfo(this, UserInfo.MY_ALL_NAME);
        super.finish();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
