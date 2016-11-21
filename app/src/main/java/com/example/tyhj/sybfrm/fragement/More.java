package com.example.tyhj.sybfrm.fragement;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.tyhj.sybfrm.Login;
import com.example.tyhj.sybfrm.Login_;
import com.example.tyhj.sybfrm.R;
import com.example.tyhj.sybfrm.SetUserInfo_;
import com.example.tyhj.sybfrm.info.UserInfo;
import com.example.tyhj.sybfrm.savaInfo.MyFunction;
import com.example.tyhj.sybfrm.savaInfo.SharedData;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.json.JSONException;

import static android.content.Context.MODE_PRIVATE;

@EFragment(R.layout.fragment_more)
public class More extends Fragment implements View.OnClickListener {
    FloatingActionButton fabSetInfo;
    View view;
    LinearLayout ll_logout;
    ImageView iv_backdrop;
    CollapsingToolbarLayout toolbarLayout;

    public More() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_more, null);
        ll_logout = (LinearLayout) view.findViewById(R.id.ll_logout);
        iv_backdrop = (ImageView) view.findViewById(R.id.iv_backdrop);
        fabSetInfo = (FloatingActionButton) view.findViewById(R.id.fabSetInfo);
        toolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.maincollapsingx);

        initViews();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ll_logout.setOnClickListener(this);

        fabSetInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SetUserInfo_.class));
            }
        });
    }

    //渲染
    void initViews() {
        //设置名字
        toolbarLayout.setTitle(MyFunction.getUserInfo().getName() + "");

        //设置背景
        Picasso.with(getActivity())
                .load(MyFunction.getUserInfo().getUrl())
                .into(iv_backdrop);
        //Log.e("背景",MyFunction.getUserInfo().getUrl());


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_logout:
                new SharedData(getActivity()).logOut();
                startActivity(new Intent(getActivity(), Login_.class));
                getActivity().finish();
                break;
        }
    }

    //更新信息
    @UiThread
    void upDateInfo() {
        initViews();
    }

    //获取新信息
    @Background
    void getInfo() {
        if(!MyFunction.istour()){
            MyFunction.doPostToGetUserInfo(getActivity());
            upDateInfo();
        }
    }

    @Override
    public void onResume() {
        getInfo();
        super.onResume();
    }
}
