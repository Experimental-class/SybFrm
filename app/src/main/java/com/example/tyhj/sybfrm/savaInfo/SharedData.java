package com.example.tyhj.sybfrm.savaInfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.tyhj.sybfrm.info.UserInfo;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
public  class SharedData {
  
    private Context context;

    public SharedData(Context context) {
        this.context = context;
    }  

    public void saveUser(UserInfo userInfo) {
        SharedPreferences shared = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // 创建对象输出流，并封装字节流  
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            // 将对象写入字节流  
            oos.writeObject(userInfo);
            // 将字节流编码成base64的字符串  
            String oAuth_Base64 = new String(Base64.encodeBase64(baos
                    .toByteArray()));  
            SharedPreferences.Editor editor = shared.edit();
            editor.putString("user", oAuth_Base64);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();  
        }  
    }  
  
    public UserInfo getUser() {
        SharedPreferences shared = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        UserInfo userInfo = null;
        String productBase64 = shared.getString("user", null);
        if(productBase64==null) {
            return null;
        }
        // 读取字节  
        byte[] base64 = Base64.decodeBase64(productBase64.getBytes());
        // 封装到字节流  
        ByteArrayInputStream bais = new ByteArrayInputStream(base64);
        try {  
            // 再次封装  
            ObjectInputStream bis = new ObjectInputStream(bais);
            // 读取对象  
            userInfo = (UserInfo) bis.readObject();
        } catch (Exception e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }
        return userInfo;
    }

    public void logOut(){
        SharedPreferences shared = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.clear();
        editor.commit();
    }

}  