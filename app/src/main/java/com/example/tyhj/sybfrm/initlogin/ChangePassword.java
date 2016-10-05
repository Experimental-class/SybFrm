package com.example.tyhj.sybfrm.initlogin;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.tyhj.sybfrm.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_change_password)
public class ChangePassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @ViewById
    Button btGetNewPassword;
    @ViewById
    EditText etEmailToChange;

    @Click(R.id.btGetNewPassword)
    void getNewPassword(){
        String newPassword=etEmailToChange.getText().toString().trim();
        /**
         * 将密码发送给服务器
         */
        Snackbar.make(btGetNewPassword,"修改成功",Snackbar.LENGTH_SHORT).show();
    }
}
