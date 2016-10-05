package com.example.tyhj.sybfrm.initlogin;

import android.support.annotation.UiThread;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.tyhj.sybfrm.R;

import org.androidannotations.annotations.Background;
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

    @Background
    void signup(){
        snackBar();
        String name=etSignupName.getText().toString().trim();
        String id=etSignupId.getText().toString().trim();
        String password=etSignupPassword.getText().toString().trim();
        /*
        *
        * 进行注册
        *
        */
        finishActivity();
    }

    @UiThread
    void snackBar(){
        Snackbar.make(btnSignup,"注册成功",Snackbar.LENGTH_INDEFINITE).show();
    }

    @UiThread
    void finishActivity(){
        this.finishActivity();
    }
}
