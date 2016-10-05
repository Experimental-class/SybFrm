package com.example.tyhj.sybfrm.fragement;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.tyhj.sybfrm.Login;
import com.example.tyhj.sybfrm.Login_;
import com.example.tyhj.sybfrm.R;
import com.example.tyhj.sybfrm.SetUserInfo_;

import org.androidannotations.annotations.Click;

import static android.content.Context.MODE_PRIVATE;

public class More extends Fragment implements View.OnClickListener{
    FloatingActionButton fabSetInfo;
    View view;
    LinearLayout ll_logout;

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
        view= inflater.inflate(R.layout.fragment_more,null);
        ll_logout= (LinearLayout) view.findViewById(R.id.ll_logout);
        initViews();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ll_logout.setOnClickListener(this);
    }

    void initViews() {
        fabSetInfo= (FloatingActionButton) view.findViewById(R.id.fabSetInfo);
        CollapsingToolbarLayout toolbarLayout= (CollapsingToolbarLayout) view.findViewById(R.id.maincollapsing);
        //设置名字
        toolbarLayout.setTitle("Tyhj");

        fabSetInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SetUserInfo_.class));
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ll_logout:
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("saveLogin", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                editor.clear();
                editor.commit();
                startActivity(new Intent(getActivity(), Login_.class));
                getActivity().finish();
                break;
        }
    }
}
