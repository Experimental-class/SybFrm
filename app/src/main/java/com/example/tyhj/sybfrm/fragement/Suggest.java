package com.example.tyhj.sybfrm.fragement;

import android.content.Context;
import android.net.Uri;
import android.os.*;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.tyhj.sybfrm.Adpter.SimpleAdapter;
import com.example.tyhj.sybfrm.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import custom.CircleRefreshLayout;
import custom.OnVerticalScrollListener;
import custom.ScrollAwareFABBehavior;

public class Suggest extends Fragment {
    private static final android.view.animation.Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    View view;
    ImageView ivCategories;
    LinearLayout llseek;
    AppBarLayout app_bar;
    private SimpleAdapter mAdapter;
    private List<String> mDatas;
    RecyclerView rvToDoList;
    CircleRefreshLayout crl_Refresh;
    public Suggest() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_suggest,container,false);
        initView();
        recycleView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvToDoList.setOnScrollListener(new OnVerticalScrollListener() {

            @Override
            public void onScrolledDown() {
                if(app_bar.getVisibility()==View.VISIBLE){

                }
                    //animateOut(app_bar);
            }

            @Override
            public void onScrolledUp() {
                if(app_bar.getVisibility()==View.INVISIBLE){

                }
                    //animateIn(app_bar);
            }

        });
        crl_Refresh.setOnRefreshListener(new CircleRefreshLayout.OnCircleRefreshListener() {
            @Override
            public void completeRefresh() {

            }

            @Override
            public void refreshing() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            handler.sendEmptyMessage(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    private void recycleView() {
        mDatas=new ArrayList<String>();
        for(int i='A';i<='z';i++){
            mDatas.add(""+(char)i);
        }
        mAdapter=new SimpleAdapter(getActivity(),mDatas);
        rvToDoList.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rvToDoList.setLayoutManager(linearLayoutManager);
        rvToDoList.setItemAnimator(new DefaultItemAnimator());
    }

    void initView(){
        ivCategories= (ImageView) view.findViewById(R.id.ivCategories);
        rvToDoList= (RecyclerView) view.findViewById(R.id.rvToDoList);
        llseek= (LinearLayout) view.findViewById(R.id.llseek);
        app_bar= (AppBarLayout) view.findViewById(R.id.app_bar);
        crl_Refresh= (CircleRefreshLayout) view.findViewById(R.id.crl_Refresh);
    }



    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    crl_Refresh.finishRefreshing();
                    break;
            }
        }
    };


    private void animateOut(final AppBarLayout button) {
        ViewCompat.animate(button).translationY(-500).setDuration(800)
                .setInterpolator(INTERPOLATOR).withLayer()
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {

                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        view.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(View view) {

                    }
                }).start();
    }

    private void animateIn(AppBarLayout button) {
        button.setVisibility(View.VISIBLE);
        ViewCompat.animate(button).translationY(0)
                .setInterpolator(INTERPOLATOR).withLayer().setListener(null)
                .start();
    }
}
