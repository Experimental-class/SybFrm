package com.example.tyhj.sybfrm.activity;

import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tyhj.sybfrm.R;
import com.example.tyhj.sybfrm.info.Essay;
import com.example.tyhj.sybfrm.savaInfo.MyFunction;
import com.squareup.picasso.Picasso;
import com.zzhoujay.richtext.RichText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;

import custom.mohuView.BlurredView;

@EActivity(R.layout.activity_show_essay)
public class ShowEssay extends AppCompatActivity {

    Essay essay;

    Drawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        essay = (Essay) getIntent().getSerializableExtra("essay");
    }


    @ViewById
    CollapsingToolbarLayout maincollapsing;
    @ViewById
    TextView tvEssayBody, tv_name, tv_signature;
    @ViewById
    BlurredView BlurredView;
    @ViewById
    Toolbar maintoolbar;
    @ViewById
    ImageView iv_userHeadImage;


    @Click(R.id.maintoolbar)
    void back() {
        this.finish();
    }


    @UiThread
    void getBlurredImg() {
        if (drawable != null)
            BlurredView.setBlurredImg(drawable);
        else
            BlurredView.setBlurredImg(getDrawable(R.mipmap.essay_bg));
    }


    @Background
    void setBlurredImg() {
        drawable=MyFunction.loadImageFromNetwork(essay.getEssayImageUrl());
        getBlurredImg();
    }

    @AfterViews
    void afterViews() {
        BlurredView.setBlurredLevel(93);
        setBlurredImg();
        maincollapsing.setTitle(essay.getEssayTitle() + "");
        iv_userHeadImage.setClipToOutline(true);
        iv_userHeadImage.setOutlineProvider(MyFunction.getOutline(true, 10, 0));

        if (essay.getUserHeadImageUrl() != null)
            Picasso.with(this).load(essay.getUserHeadImageUrl()).error(R.mipmap.girl).into(iv_userHeadImage);
        else
            Picasso.with(this).load(R.mipmap.girl).into(iv_userHeadImage);
        tv_name.setText(essay.getUserName());
        //tv_signature.setText(essay.getU_id());

        try {
            RichText.fromMarkdown(essay.getEssayBody() + "").into(tvEssayBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
