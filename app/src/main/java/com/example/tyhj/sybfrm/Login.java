package com.example.tyhj.sybfrm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tyhj.sybfrm.info.UserInfo;

import com.example.tyhj.sybfrm.initlogin.ChangePassword_;
import com.example.tyhj.sybfrm.initlogin.Signup_;
import com.example.tyhj.sybfrm.savaInfo.MyInfo;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_login)
public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        String id=etUserId.getText().toString().trim();
        String password=etUserPassword.getText().toString().trim();
        Snackbar.make(btnLogin,"登陆中",Snackbar.LENGTH_INDEFINITE).show();
        check(id,password);
    }





    //登陆验证
    @Background
    void check(String id,String password){
        /**
         * 后台验证,保存信息
         */
        UserInfo userInfo=getUserInfo();
        saveUserInfo(userInfo);
        finishActivity();
    }
    //未完。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。
    private UserInfo getUserInfo() {
        return new UserInfo("id","password","url","name","email","signature");
    }

    @UiThread
    void finishActivity(){
        startActivity(new Intent(Login.this,Home_.class));
        this.finish();
    }

    private void saveUserInfo(UserInfo userInfo) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("saveLogin", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putString("number",userInfo.getId());
        editor.putString("password",userInfo.getPassword());
        editor.putString("name",userInfo.getName());
        editor.putString("signature",userInfo.getSignature());
        editor.putString("email",userInfo.getEmail());
        editor.putString("headImage",userInfo.getUrl());
        editor.putBoolean("canLogin", true);
        editor.commit();
        new MyInfo().setUserInfo(userInfo);
    }
}
