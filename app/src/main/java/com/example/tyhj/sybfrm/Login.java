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
import com.example.tyhj.sybfrm.savaInfo.SharedData;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyFunction.setURL(getString(R.string.url));
        UserInfo userInfo=new SharedData(this).getUser();
        if(userInfo!=null){
            MyFunction.setIstour(false);
            MyFunction.setUserInfo(userInfo);
            if(MyFunction.isIntenet(this)){
                doPostToGetUserInfo();
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
            savaTourInfo();
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
        String data =  "u_loginname=" + name + "&u_psw=" + password;
        String reback=MyFunction.Login(data,password,Login.this);
        if(reback==null)
            finishActivity();
        else
            Snackbar.make(btnLogin,reback,Snackbar.LENGTH_SHORT).show();
    }

    @Background
    void savaTourInfo(){
        MyFunction.setUserInfo(new UserInfo("5233",MyFunction.getUserHeadImage("5233"),"tour#5233","null",getString(R.string.intro),"0","null","null"));
        finishActivity();
    }

    @Background
    void doPostToGetUserInfo(){
        MyFunction.doPostToGetUserInfo(Login.this);
    }
}
