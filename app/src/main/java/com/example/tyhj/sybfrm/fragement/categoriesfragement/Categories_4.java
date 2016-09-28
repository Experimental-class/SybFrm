package com.example.tyhj.sybfrm.fragement.categoriesfragement;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tyhj.sybfrm.Adpter.SimpleAdapter;
import com.example.tyhj.sybfrm.R;

import java.util.ArrayList;
import java.util.List;

import custom.OnVerticalScrollListener;
import custom.ShowButton;


public class Categories_4 extends Fragment {
    View view;
    RecyclerView rcyvCollectEssay;
    private SimpleAdapter mAdapter;
    private List<String> mDatas;
    public Categories_4() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_categories_4, null);
        rcyvCollectEssay= (RecyclerView) view.findViewById(R.id.rcyvCollectEssay);
        recycleView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rcyvCollectEssay.setOnScrollListener(new OnVerticalScrollListener() {
            @Override
            public void onScrolledDown() {
                //向下划
                showButton.showMe(true);
            }

            @Override
            public void onScrolledUp() {
                showButton.showMe(false);
            }

            @Override
            public void onScrolledToTop() {
                showButton.showMe(false);
            }
        });
    }

    private void recycleView() {
        mDatas=new ArrayList<String>();
        for(int i='A';i<='z';i++){
            mDatas.add(""+(char)i);
        }
        mAdapter=new SimpleAdapter(getActivity(),mDatas);
        rcyvCollectEssay.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rcyvCollectEssay.setLayoutManager(linearLayoutManager);
        rcyvCollectEssay.setItemAnimator(new DefaultItemAnimator());
    }

    ShowButton showButton;
    public void setShowme(ShowButton showButton){
        this.showButton=showButton;
    }
}
