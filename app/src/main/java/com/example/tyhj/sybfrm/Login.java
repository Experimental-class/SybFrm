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
import com.example.tyhj.sybfrm.savaInfo.MyFunction;

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
        if(MyFunction.canLog(this)){
            if(MyFunction.isIntenet(this)){
                MyFunction.saveUserInfo(this);
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
        MyFunction.saveUserInfo(Login.this);
        finishActivity();
    }

    @UiThread
    void finishActivity(){
        startActivity(new Intent(Login.this,Home_.class));
        this.finish();
    }


}
