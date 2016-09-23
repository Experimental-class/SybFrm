package com.example.tyhj.sybfrm.initlogin;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.tyhj.sybfrm.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_signup)
public class Signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @ViewById
    EditText etSignupName,etSignupId,etSignupPassword;
    @ViewById
    Button btnSignup;
    @Click(R.id.btnSignup)
    void signup(){
        String name=etSignupName.getText().toString().trim();
        String id=etSignupId.getText().toString().trim();
        String password=etSignupPassword.getText().toString().trim();
        /**
         * 发送数据到服务器进行注册
         *
         */
        Snackbar.make(btnSignup,"注册成功",Snackbar.LENGTH_SHORT).show();
    }
}
