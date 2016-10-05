package com.example.tyhj.sybfrm.fragement.categoriesfragement;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tyhj.sybfrm.Adpter.EssayAdpter;
import com.example.tyhj.sybfrm.Adpter.SimpleAdapter;
import com.example.tyhj.sybfrm.R;
import com.example.tyhj.sybfrm.info.Essay;

import java.util.ArrayList;
import java.util.List;

import custom.OnVerticalScrollListener;
import custom.ShowButton;

public class Categories_1 extends Fragment {
    ShowButton showButton;
    View view;
    RecyclerView rcyvPopularEssay;
    private EssayAdpter mAdapter;
    private List<Essay> mDatas;
    String testUserUrl="http://ac-fgtnb2h8.clouddn.com/21d88c8102759c96ecdf.jpg";
    String testEssayUrl="http://tupian.enterdesk.com/2014/lxy/2014/04/24/2/6.jpg";
    String testEssayUrl2="http://photo.enterdesk.com/2011-7-17/enterdesk.com-D7E968D1602DAED1B19229CF1BD3C5B1.jpg";
    public Categories_1() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_categories_1, null);
        rcyvPopularEssay= (RecyclerView) view.findViewById(R.id.rcyvPopularEssay);
        recycleView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rcyvPopularEssay.setOnScrollListener(new OnVerticalScrollListener() {
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
        mDatas=new ArrayList<Essay>();
        mDatas.add(new Essay(testUserUrl,"Tyhj",testEssayUrl,"关于你妹啊",getString(R.string.large_text),"15","16","17","2016","id"));
        mDatas.add(new Essay(testUserUrl,"Tyhj",testEssayUrl2,"关于你妹啊",getString(R.string.large_text),"15","16","17","2016","id"));
        mDatas.add(new Essay(testUserUrl,"Tyhj","http://img1.imgtn.bdimg.com/it/u=1792538780,2798164743&fm=21&gp=0.jpg","关于你妹啊",getString(R.string.large_text),"15","16","17","2016","id"));
        mDatas.add(new Essay(testUserUrl,"Tyhj","http://pic.pp3.cn/uploads//201409/2014092008.jpg","关于你妹啊",getString(R.string.large_text),"15","16","17","2016","id"));
        mDatas.add(new Essay(testUserUrl,"Tyhj","http://pic.pp3.cn/uploads//201512/2015123007.jpg","关于你妹啊",getString(R.string.large_text),"15","16","17","2016","id"));
        mAdapter=new EssayAdpter(getActivity(),mDatas);
        rcyvPopularEssay.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rcyvPopularEssay.setLayoutManager(linearLayoutManager);
        rcyvPopularEssay.setItemAnimator(new DefaultItemAnimator());
    }

    public void setShowme(ShowButton showButton){
        this.showButton=showButton;
    }
}
