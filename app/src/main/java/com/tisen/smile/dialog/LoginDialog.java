package com.tisen.smile.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tisen.smile.R;
import com.tisen.smile.model.User;
import com.tisen.smile.utils.CircleImageView;
import com.tisen.smile.utils.HttpHelper;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by tisen on 2016/10/8.
 */
public class LoginDialog extends Dialog {

    public final static String MODEL = "model_1";

    public CircleImageView userAvatar;
    public EditText userPhone;
    public TextView getCode;
    public EditText code;
    public TextView login;
    private String uPhone;
    private String uCode;
    private Context context;
    private ImageLoader loader;


    public LoginDialog(Context context, ImageLoader loader) {
        super(context,R.style.ActionSheetDialogStyle);
        this.context = context;
        this.loader = loader;
        View view = LayoutInflater.from(context).inflate(R.layout.view_login, null);
        userAvatar = (CircleImageView) view.findViewById(R.id.login_user_avatar);
        userPhone = (EditText) view.findViewById(R.id.login_user_phone);
        getCode = (TextView) view.findViewById(R.id.login_get_code);
        code = (EditText) view.findViewById(R.id.login_code);
        login = (TextView) view.findViewById(R.id.login_login);

        //code.setVisibility(View.VISIBLE);


        userPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                uPhone = userPhone.getText().toString();
                if (uPhone.length() == 11) {
                    getCode.setEnabled(true);
                    avatarDown.sendEmptyMessage(1);
                } else {
                    userAvatar.setImageResource(R.drawable.user_default);
                    getCode.setEnabled(false);
                }
            }
        });

        code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                uCode = code.getText().toString();
                if (uCode.length() == 6) {
                    login.setVisibility(View.VISIBLE);
                } else login.setVisibility(View.INVISIBLE);
            }
        });

        setContentView(view);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
    }

    public void show() {
        getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobSMS.requestSMSCode(uPhone, MODEL, new QueryListener<Integer>() {
                    @Override
                    public void done(Integer integer, BmobException e) {
                        if (e == null) {
                            getCode.setVisibility(View.GONE);
                            code.setVisibility(View.VISIBLE);
                            code.setFocusable(true);
                            Toast.makeText(context, "验证码发送成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("requestSMSCode", e.getMessage() + " " + e.getErrorCode());
                            Toast.makeText(context, "验证码发送失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginHandler.sendEmptyMessage(1);
                Toast.makeText(context, "正在登录，请稍后...", Toast.LENGTH_SHORT).show();
            }
        });
        super.show();
    }

    private Handler loginHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d("signOrLogin", "Phone: " + uPhone + " , Code: " + uCode);
            User.signOrLoginByMobilePhone(uPhone, uCode, new LogInListener<User>() {
                @Override
                public void done(User user, BmobException e) {

                    user = User.getCurrentUser(User.class);
                    Log.d("error code", e.getMessage() + " " + e.getErrorCode());
                    if (user != null) {
                        Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
                        dismiss();
                    } else {
                        Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    };
    private Handler avatarDown = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            BmobQuery<User> query = new BmobQuery<>();
            query.addWhereEqualTo("username", uPhone);
            query.findObjects(new FindListener<User>() {
                @Override
                public void done(List<User> list, BmobException e) {
                    if (list != null && !list.isEmpty() && list.get(0).getAvatar() != null) {
                        loader.loadImage(list.get(0).getAvatar().getFileUrl(), new ImageLoadingListener() {
                            @Override
                            public void onLoadingStarted(String imageUri, View view) {

                            }

                            @Override
                            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                            }

                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                HttpHelper.setUserAvatar(loadedImage);
                                HttpHelper.setShared(context, "isUserAvatarDown", true);
                                userAvatar.setImageBitmap(loadedImage);
                            }

                            @Override
                            public void onLoadingCancelled(String imageUri, View view) {

                            }
                        });
                    } else {
                        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.user_default);
                        HttpHelper.setUserAvatar(bitmap);
                        HttpHelper.setShared(context, "isUserAvatarDown", true);
                        userAvatar.setImageBitmap(bitmap);
                    }
                }
            });

        }
    };
}
