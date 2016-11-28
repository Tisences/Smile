package com.tisen.smile.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by tisen on 2016/9/29.
 */
public class HttpHelper {

    public static String APPLICATION_ID = "d0898f76f1d68bbf539525cfd2398f86";
    public static String IMAGE_FILEPATH = "/Android/data/com.tisen.smile";
    public static String AVATAR_FILE_NAME = "/avatar.png";
    public static String TEMP_FILE_NAME = "/cache/temp.png";
    public static String SHAREDPREFERENCES = "smile";
    public static File file = new File(Environment.getExternalStorageDirectory()+IMAGE_FILEPATH+TEMP_FILE_NAME);

    public static ImageLoader getImageLoader(Context context) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory().cacheOnDisc().build();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(options).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory().discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(configuration);
        return imageLoader;
    }

    public static BmobConfig getConfig(Context context) {
        BmobConfig config = new BmobConfig.Builder(context).
                setApplicationId(HttpHelper.APPLICATION_ID).
                setConnectTimeout(4).build();
        return config;
    }

    public static File setUserAvatar(Bitmap bitmap) {
        File tmpDir = new File(Environment.getExternalStorageDirectory()+IMAGE_FILEPATH);
        if(!tmpDir.exists()){
            tmpDir.mkdir();
        }

        File img = new File(tmpDir.getAbsolutePath()+AVATAR_FILE_NAME);
        if(img.exists()){
            img.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(img);
            bitmap.compress(Bitmap.CompressFormat.PNG,50,out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;

    }

    public static File saveTempImage(Bitmap bitmap){
        File tmpDir = new File(Environment.getExternalStorageDirectory()+IMAGE_FILEPATH);
        if(!tmpDir.exists()){
            tmpDir.mkdir();
        }

        File img = new File(tmpDir.getAbsolutePath()+TEMP_FILE_NAME);
        if(img.exists()){
            img.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(img);
            bitmap.compress(Bitmap.CompressFormat.PNG,90,out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }


    public static void saveAvatar(Bitmap bitmap,UploadFileListener listener){
        File img = setUserAvatar(bitmap);
        BmobFile bmobFile = new BmobFile(img);
        bmobFile.uploadblock(listener);
    }

    public static Bitmap getUserAvatar() {
        File tmpDir = new File(Environment.getExternalStorageDirectory()+IMAGE_FILEPATH);
        String path = tmpDir.getAbsolutePath()+AVATAR_FILE_NAME;
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        return bitmap;
    }

    public static boolean getShared(Context context,String key){
        SharedPreferences sharedPreferences =context.getSharedPreferences(SHAREDPREFERENCES,Context.MODE_PRIVATE);
        boolean b=sharedPreferences.getBoolean(key,false);
        return b;
    }
    public static void setShared(Context context,String key,boolean b){
        SharedPreferences sharedPreferences =context.getSharedPreferences(SHAREDPREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,b);
        editor.commit();
    }
}
