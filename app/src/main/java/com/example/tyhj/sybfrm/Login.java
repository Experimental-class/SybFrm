package com.example.tyhj.sybfrm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tyhj.sybfrm.info.UserInfo;

import com.example.tyhj.sybfrm.initlogin.ChangePassword_;
import com.example.tyhj.sybfrm.initlogin.Signup_;
import com.example.tyhj.sybfrm.savaInfo.MyFunction;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.example.tyhj.sybfrm.savaInfo.MyFunction.getStringFromInputStream;

@EActivity(R.layout.activity_login)
public class Login extends AppCompatActivity {
    String msg=null;
    UserInfo userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("saveLogin", MODE_PRIVATE);
        if(MyFunction.canLog(this)){
            if(MyFunction.isIntenet(this)){
                MyFunction.doPostToGetUserInfo(Login.this,sharedPreferences.getString("id",null));
            }else {
                MyFunction.getInitInformation(this);
            }
            startActivity(new Intent(this,Home_.class));
            this.finish();
        }
    }

    @ViewById
    TextView tvRegister,tvForgetPassword;
    @ViewById
    Button btnLogin;
    @ViewById
    EditText etUserId,etUserPassword;

    //注册
    @Click(R.id.tvRegister)
    void register(){
        startActivity(new Intent(Login.this, Signup_.class));
    }
    //忘记密码
    @Click(R.id.tvForgetPassword)
    void changePassword(){
        startActivity(new Intent(Login.this, ChangePassword_.class));
    }
    //登陆
    @Click(R.id.btnLogin)
    void login() {
        if(!MyFunction.isIntenet(this))
            return;
        String id=etUserId.getText().toString().trim();
        String password=etUserPassword.getText().toString().trim();
        if(id.equals("")&&password.equals("")){
            Toast.makeText(Login.this,"以游客身份登陆",Toast.LENGTH_SHORT).show();
            MyFunction.setUserInfo(new UserInfo("5233",getString(R.string.defaultHeadImage),"tour#5233","null",getString(R.string.intro),"0","null","null"));
            MyFunction.setIstour(true);
            finishActivity();
        }else {
            Snackbar.make(btnLogin, "登陆中", Snackbar.LENGTH_INDEFINITE).show();
            doPost(id, password);
        }
    }

    @UiThread(delay = 10)
    void finishActivity(){
        startActivity(new Intent(Login.this,Home_.class));
        this.finish();
    }

    @UiThread
    void snackBar(String str,int time){
        Snackbar.make(btnLogin,str,time).show();
    }
    //连接服务器并登陆
    @Background
    public void doPost(String name ,String password){
        try {
            HttpURLConnection conn = null;
            String url = "http://139.129.24.151:5000/sign_in";
            URL mURL = new URL(url);
            conn = (HttpURLConnection) mURL.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(10000);
            conn.setDoOutput(true);
            String data =  "u_loginname=" + name + "&u_psw=" + password;
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
                    snackBar("登陆成功",Snackbar.LENGTH_SHORT);
                    Log.i("TAG",state);
                    MyFunction.saveUserInfo(Login.this,new UserInfo(
                            jsonObject.getString("u_id"),
                            "url",
                            jsonObject.getString("u_name"),
                            jsonObject.getString("u_email"),
                            jsonObject.getString("u_intro"),
                            jsonObject.getString("u_reputation"),
                            jsonObject.getString("u_blog"),
                            jsonObject.getString("u_github")));
                    MyFunction.setIstour(false);
                    MyFunction.getUserInfo().setPassword(etUserPassword.getText().toString());
                    finishActivity();
                }else {
                    snackBar(jsonObject.getString("codeState"),Snackbar.LENGTH_SHORT);
                }
            } else {
                snackBar("后台很菜，服务器崩溃了，请稍后再试",Snackbar.LENGTH_SHORT);
                Log.i("TAG", "访问失败" + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
