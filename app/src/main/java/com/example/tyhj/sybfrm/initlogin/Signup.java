package com.example.tyhj.sybfrm.initlogin;

import android.support.annotation.UiThread;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tyhj.sybfrm.Login;
import com.example.tyhj.sybfrm.R;
import com.example.tyhj.sybfrm.info.UserInfo;
import com.example.tyhj.sybfrm.savaInfo.MyFunction;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.tyhj.sybfrm.savaInfo.MyFunction.getStringFromInputStream;

@EActivity(R.layout.activity_signup)
public class Signup extends AppCompatActivity {
String msg=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @ViewById
    EditText etSignupName,etSignupId,etSignupPassword;
    @ViewById
    Button btnSignup;
    @Click(R.id.btnSignup)
    void btnSignup(){
        if(!MyFunction.isIntenet(this))
            return;
        snackBar("注册中",Snackbar.LENGTH_INDEFINITE);
        signup();
    }


    @Background
    void signup(){
        String name=etSignupName.getText().toString().trim();
        String id=etSignupId.getText().toString().trim();
        String password=etSignupPassword.getText().toString().trim();
        if(!checkEmail(id)){
            snackBar("请输入正确的邮箱地址",Snackbar.LENGTH_SHORT);
            return;
        }
        doPost(name,id,password);
    }

    @UiThread
    void snackBar(String string,int time){
        Snackbar.make(btnSignup,string,time).show();
    }

    @UiThread
    void finishActivity(){
        this.finish();
    }

    //邮箱正则验证
    public static boolean checkEmail(String email){
        boolean flag = false;
        try{
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        }catch(Exception e){
            flag = false;
        }
        return flag;
    }
    //连接服务器并注册
    @Background
    public void doPost(String name ,String email,String password){

        try {
            HttpURLConnection conn = null;
            String url = getString(R.string.url)+"/sign_up";
            URL mURL = new URL(url);
            conn = (HttpURLConnection) mURL.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(10000);
            conn.setDoOutput(true);
            String data =  "u_name=" + name + "&u_email=" + email+"&u_psw="+password;
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();
            int responseCode = conn.getResponseCode();// 调用此方法就不必再使用conn.connect()方
            if (responseCode == 200) {
                InputStream is = conn.getInputStream();
                String state = getStringFromInputStream(is);
                JSONObject jsonObject=new JSONObject(state);
                if(jsonObject.getInt("code")==1){
                    snackBar("注册成功",Snackbar.LENGTH_SHORT);
                    finishActivity();
                }else {
                    snackBar(jsonObject.getString("codeState"),Snackbar.LENGTH_SHORT);
                }
            } else {
                Log.i("TAG", "访问失败" + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
