package com.tisen.smile.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Switch;

import com.tisen.smile.R;
import com.tisen.smile.utils.HttpHelper;
import com.tisen.smile.utils.RequestResult;

import java.io.File;
import java.net.URI;

/**
 * Created by tisen on 2016/10/8.
 */
public class ChooseImageDialog extends Dialog implements View.OnClickListener{

    private static final int CAMERA_CODE = 1;
    private static final int GALLERY_CODE = 2;
    private static final int CROP_CODE = 3;


    private View cameraLayout;
    private View galleryLayout;
    private View cancelLayout;
    private Context context;
    private Activity activity;


    public static final String tempImagePath = Environment.getExternalStorageDirectory()+File.separator+"tempImage.png";

    public ChooseImageDialog(Activity activity) {
        super(activity, R.style.ActionSheetDialogStyle);
        this.context = activity;
        this.activity = activity;
        View view = LayoutInflater.from(context).inflate(R.layout.choose_image_view,null);
        cameraLayout = view.findViewById(R.id.camera_layout);
        galleryLayout = view.findViewById(R.id.gallery_layout);
        cancelLayout = view.findViewById(R.id.cancel_layout);
        cameraLayout.setOnClickListener(this);
        galleryLayout.setOnClickListener(this);
        cancelLayout.setOnClickListener(this);
        setContentView(view);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
    }

    private void chooseFromCamera(){
        if(HttpHelper.file.exists()){
            HttpHelper.file.delete();
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(HttpHelper.file));
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
        activity.startActivityForResult(intent, RequestResult.CAMERA_REQUEST_CODE);
    }
    private void chooseFromGallery(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        activity.startActivityForResult(intent,RequestResult.GALLERY_REQUEST_CODE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.camera_layout:chooseFromCamera();break;
            case R.id.gallery_layout:chooseFromGallery();break;
            case R.id.cancel_layout:break;
            default:break;
        }
        dismiss();
    }
}
