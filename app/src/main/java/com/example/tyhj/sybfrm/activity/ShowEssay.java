package com.example.tyhj.sybfrm.activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tyhj.sybfrm.R;
import com.example.tyhj.sybfrm.savaInfo.MyFunction;
import com.zzhoujay.richtext.RichText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;

import custom.mohuView.BlurredView;

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
    @ViewById
    BlurredView BlurredView;
    @ViewById
    Toolbar maintoolbar;
    @ViewById
    ImageView iv_userHeadImage;


    @Click(R.id.maintoolbar)
    void back(){
        this.finish();
    }

    @AfterViews
    void afterViews(){
        BlurredView.setBlurredLevel(93);
        maincollapsing.setTitle("关于你妹啊");
        iv_userHeadImage.setClipToOutline(true);
        iv_userHeadImage.setOutlineProvider(MyFunction.getOutline(true,10,0));
        try {
           RichText.fromMarkdown(MyFunction.readToBuffer(R.raw.markdown,ShowEssay.this)).into(tvEssayBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
