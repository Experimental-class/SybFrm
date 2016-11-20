package com.example.tyhj.sybfrm.fragement.categoriesfragement;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tyhj.sybfrm.Adpter.EssayAdpter;
import com.example.tyhj.sybfrm.Adpter.SimpleAdapter;
import com.example.tyhj.sybfrm.R;
import com.example.tyhj.sybfrm.info.Essay;
import com.example.tyhj.sybfrm.savaInfo.MyFunction;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import custom.OnVerticalScrollListener;
import custom.ShowButton;

public class Categories_2 extends Fragment {
    View view;
    RecyclerView rcyvLastedEssay;
    private EssayAdpter mAdapter;
    private List<Essay> mDatas;
    boolean ifFinish;
    SwipeRefreshLayout swipeRefreshLayout;

    public Categories_2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_categories_2, null);
        rcyvLastedEssay= (RecyclerView) view.findViewById(R.id.rcyvLastedEssay);
        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        recycleView();
        getEssay();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rcyvLastedEssay.setOnScrollListener(new OnVerticalScrollListener() {
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

        //刷新
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_dark,
                android.R.color.holo_green_light);// 设置刷新动画的颜色
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(ifFinish) {
                    swipeRefreshLayout.setRefreshing(true);// 开始刷新
                    // 执行刷新后需要完成的操作
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String[] str = MyFunction.getEssayId(1);
                            if (str != null) {
                                for (int i = 0; i < str.length; i++) {
                                    try {
                                        Essay essay = new Essay(null, null, null, null, null, null, null, true, true, null, str[i], null);
                                        if (!mDatas.contains(essay)) {
                                            mDatas.add(0, MyFunction.getEssay(str[i]));
                                            handler.sendEmptyMessage(2);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            handler.sendEmptyMessage(3);
                        }
                    }).start();
                }else {
                    Snackbar.make(swipeRefreshLayout,"数据加载中,请稍后再试",Snackbar.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void recycleView() {
        mDatas=new ArrayList<Essay>();
        mAdapter=new EssayAdpter(getActivity(),mDatas);
        rcyvLastedEssay.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rcyvLastedEssay.setLayoutManager(linearLayoutManager);
        rcyvLastedEssay.setItemAnimator(new DefaultItemAnimator());
    }

    ShowButton showButton;
    public void setShowme(ShowButton showButton){
        this.showButton=showButton;
    }

    //获取文章
    private void getEssay() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] str= MyFunction.getEssayId(1);
                if(str!=null) {
                    for (int i = 0; i < str.length; i++) {
                        try {
                            mDatas.add(MyFunction.getEssay(str[i]));
                            handler.sendEmptyMessage(1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }else {
                    Log.e("失败","为什么");
                }
                ifFinish=true;
            }
        }).start();
    }


    android.os.Handler handler=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    mAdapter.notifyItemInserted(mDatas.size());
                    break;
                case 2:
                    mAdapter.notifyItemInserted(0);
                    rcyvLastedEssay.scrollToPosition(0);
                    break;
                case 3:
                    swipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };
}
