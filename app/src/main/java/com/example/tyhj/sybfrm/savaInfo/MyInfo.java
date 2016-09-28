package com.example.tyhj.sybfrm.savaInfo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.os.Handler;
import android.widget.Toast;

import com.example.tyhj.sybfrm.R;
import com.example.tyhj.sybfrm.info.UserInfo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Tyhj on 2016/9/23.
 */

public class MyInfo {

    private static int IMAGE_SIZE=2000;

    public static UserInfo userInfo;

    public static UserInfo getUserInfo() {
        return userInfo;
    }

    public static void setUserInfo(UserInfo userInfo) {
        MyInfo.userInfo = userInfo;
    }



    //是否有网络
    public static boolean isIntenet(Context context){
        ConnectivityManager con=(ConnectivityManager)context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        if(wifi||internet){
            return true;
        }else {
            MyInfo.Toast(context,context.getString(R.string.nointernet));
            return false;
        }
    }

    //是否有WIFI
    public static boolean isWifi(Context context){
        ConnectivityManager con=(ConnectivityManager)context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        return con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
    }
    //Toast
    public static void Toast(Context context,String str){
        Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
    }

    public static void saveBitmapFile(Bitmap bm, String name, Context context) {
        if(bm==null)
            return;
        File f1 = new File(Environment.getExternalStorageDirectory()+context.getString(R.string.savaphotopath));
        if(!f1.exists()){
            f1.mkdirs();
        }
        File imageFile = new File(Environment.getExternalStorageDirectory()+context.getString(R.string.savaphotopath),name);
        if(imageFile.exists()){
            return;
        }
        int options = 80;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
        while (baos.toByteArray().length / 1024 > IMAGE_SIZE) {
            baos.reset();
            options -= 10;
            bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        try {
            FileOutputStream fos = new FileOutputStream(imageFile);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
