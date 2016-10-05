package com.example.tyhj.sybfrm.savaInfo;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Outline;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Toast;

import com.example.tyhj.sybfrm.R;
import com.example.tyhj.sybfrm.info.UserInfo;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Tyhj on 2016/9/23.
 */

public class MyFunction {

    private static int IMAGE_SIZE=500;

    public static UserInfo userInfo;

    public static UserInfo getUserInfo() {
        return userInfo;
    }

    public static void setUserInfo(UserInfo userInfo) {
        MyFunction.userInfo = userInfo;
    }

    //是否有网络
    public static boolean isIntenet(Context context){
        ConnectivityManager con=(ConnectivityManager)context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        if(wifi||internet){
            return true;
        }else {
            MyFunction.Toast(context,context.getString(R.string.nointernet));
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
    //保存bitmap为File
    public static void saveBitmapFile(Bitmap bm, String name, Context context) {
        if(bm==null)
            return;
        File f1 = new File(Environment.getExternalStorageDirectory()+context.getString(R.string.savaphotopath));
        if(!f1.exists()){
            f1.mkdirs();
        }
        File imageFile = new File(Environment.getExternalStorageDirectory()+context.getString(R.string.savaphotopath),name);
        int options = 80;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
        while (baos.toByteArray().length / 1024 > IMAGE_SIZE&&options>=5) {
            baos.reset();
            options -=5;
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
    //轮廓
    public static ViewOutlineProvider getOutline(boolean b, final int x, final int y){
        if(b) {
            return  new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    final int margin = Math.min(view.getWidth(), view.getHeight()) / x;
                    //outline.setRoundRect(margin, margin, view.getWidth() - margin, view.getHeight() - margin, 20);
                    outline.setOval(margin, margin, view.getWidth() - margin, view.getHeight() - margin);
                }
            };
        }else {
            return new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    final int margin = Math.min(view.getWidth(), view.getHeight()) / x;
                    outline.setRoundRect(margin, margin, view.getWidth() - margin, view.getHeight() - margin, y);
                    //outline.setOval(margin, margin, view.getWidth() - margin, view.getHeight() - margin);
                }
            };
        }

    }
    //读取文本
    public static String readToBuffer(int filePath,Context context) throws IOException {
        StringBuffer buffer = new StringBuffer();
        final InputStream stream = context.getResources().openRawResource(filePath);
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        line = reader.readLine(); // 读取第一行
        while (line != null) { // 如果 line 为空说明读完了
            buffer.append(line); // 将读到的内容添加到 buffer 中
            buffer.append("\n"); // 添加换行符
            line = reader.readLine(); // 读取下一行
        }
        reader.close();
        stream.close();
        return buffer.toString();
    }
    //在线获取用户信息
    public static UserInfo getUserInfoOnline(){
        return new UserInfo("id","url","name","email","signature","reputation","blog","github");
    }
    //重新设置用户信息并保存用户信息到本地数据库和全局变量
    public static void saveUserInfo(Context context) {
        UserInfo userInfo=getUserInfoOnline();
        SharedPreferences sharedPreferences = context.getSharedPreferences("saveLogin", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putString("id",userInfo.getId());
        editor.putString("name",userInfo.getName());
        editor.putString("signature",userInfo.getSignature());
        editor.putString("email",userInfo.getEmail());
        editor.putString("headImage",userInfo.getUrl());
        editor.putString("reputation",userInfo.getReputation());
        editor.putString("blog",userInfo.getBlog());
        editor.putString("github",userInfo.getGithub());
        editor.putBoolean("canLogin", true);
        editor.commit();
        MyFunction.setUserInfo(userInfo);
    }
    //获取本地用户信息到全局变量
    public static void getInitInformation(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("saveLogin", MODE_PRIVATE);
        MyFunction.setUserInfo(new UserInfo(
                sharedPreferences.getString("id",null),
                sharedPreferences.getString("name",null),
                sharedPreferences.getString("signature",null),
                sharedPreferences.getString("email",null),
                sharedPreferences.getString("headImage",null),
                sharedPreferences.getString("blog",null),
                sharedPreferences.getString("github",null),
                sharedPreferences.getString("reputation",null)
        ));

    }
    //是否可以登录
    public static boolean canLog(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("saveLogin", MODE_PRIVATE);
        if(sharedPreferences.getBoolean("canLogin",false))
            return true;
        else
            return false;
    }
    //保存文章到本地
    public static void savaEssay(Context context,String title,String text){
        SharedPreferences sharedPreferences = context.getSharedPreferences("saveEssay", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putString("title",title);
        editor.putString("text",text);
        editor.commit();
    }
    //删除本地文章
    public static void deleteEssay(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("saveEssay", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.clear();
        editor.commit();
    }
    //获取本地文章
    public static String[] getEssay(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("saveEssay", MODE_PRIVATE);
        String title=sharedPreferences.getString("title",null);
        String text=sharedPreferences.getString("text",null);
        return new String[]{title,text};
    }
    //Uri转File
    public static String getFilePathFromContentUri(Uri uri, ContentResolver contentResolver) {
        String filePath;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};
        Cursor cursor = contentResolver.query(uri, filePathColumn, null, null, null);
//      也可用下面的方法拿到cursor
//      Cursor cursor = this.context.managedQuery(selectedVideoUri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }
    //文件复制
    public static void copyFile(File source, File dest) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }

    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height/ (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    //图片压缩
    public static void ImgCompress(String filePath,File newFile) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options,720,1280);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap bitmap= BitmapFactory.decodeFile(filePath, options);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(newFile);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
