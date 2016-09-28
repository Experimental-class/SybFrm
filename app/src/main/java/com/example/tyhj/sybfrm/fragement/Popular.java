package com.example.tyhj.sybfrm.fragement;

import android.content.Intent;
import android.os.*;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.tyhj.sybfrm.Adpter.SimpleAdapter;
import com.example.tyhj.sybfrm.Allquestions;
import com.example.tyhj.sybfrm.Allquestions_;
import com.example.tyhj.sybfrm.R;

import java.util.ArrayList;
import java.util.List;

import custom.CircleRefreshLayout;
import custom.OnVerticalScrollListener;
import custom.ShowButton;

public class Popular extends Fragment {
    private static final android.view.animation.Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    View view;
    ImageView ivCategories;
    LinearLayout llseek;
    AppBarLayout app_bar;
    private SimpleAdapter mAdapter;
    private List<String> mDatas;
    RecyclerView rvToDoList;
    CircleRefreshLayout crl_Refresh;
    public Popular() {
        // Required empty public constructor
    }
    ShowButton showButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_popular,container,false);
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

        ivCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Allquestions_.class));
            }
        });
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


    public void setShowme(ShowButton showButton){
        this.showButton=showButton;
    }
}
