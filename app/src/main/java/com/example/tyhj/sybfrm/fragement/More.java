package com.example.tyhj.sybfrm.fragement;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tyhj.sybfrm.Adpter.SimpleAdapter;
import com.example.tyhj.sybfrm.R;
import com.example.tyhj.sybfrm.YourInfo;

import java.util.ArrayList;
import java.util.List;

public class More extends Fragment {
    FloatingActionButton fab;
    View view;

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
        initViews();
        return view;
    }

    void initViews() {
        fab= (FloatingActionButton) view.findViewById(R.id.fab);
        CollapsingToolbarLayout toolbarLayout= (CollapsingToolbarLayout) view.findViewById(R.id.maincollapsing);
        //设置名字
        toolbarLayout.setTitle("Title");
    }
}
