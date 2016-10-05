package com.example.tyhj.sybfrm.activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.tyhj.sybfrm.R;
import com.example.tyhj.sybfrm.savaInfo.MyFunction;
import com.zzhoujay.richtext.RichText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;

@EActivity(R.layout.activity_show_essay)
public class ShowEssay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @ViewById
    CollapsingToolbarLayout maincollapsing;
    @ViewById
    TextView tvEssayBody;


    @AfterViews
    void afterViews(){
        maincollapsing.setTitle("关于你妹啊");
        try {
           RichText.fromMarkdown(MyFunction.readToBuffer(R.raw.markdown,ShowEssay.this)).into(tvEssayBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
