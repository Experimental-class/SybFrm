package com.example.tyhj.sybfrm.fragement;

import android.content.Intent;
import android.os.*;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tyhj.sybfrm.Adpter.EssayAdpter;
import com.example.tyhj.sybfrm.Adpter.SimpleAdapter;
import com.example.tyhj.sybfrm.Allquestions;
import com.example.tyhj.sybfrm.Allquestions_;
import com.example.tyhj.sybfrm.R;
import com.example.tyhj.sybfrm.activity.WriteEssay;
import com.example.tyhj.sybfrm.activity.WriteEssay_;
import com.example.tyhj.sybfrm.info.Essay;
import com.example.tyhj.sybfrm.savaInfo.MyFunction;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import custom.CircleRefreshLayout;
import custom.OnVerticalScrollListener;
import custom.ShowButton;

public class Popular extends Fragment {
    private static final android.view.animation.Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    String testUserUrl="http://ac-fgtnb2h8.clouddn.com/21d88c8102759c96ecdf.jpg";
    String testEssayUrl="http://tupian.enterdesk.com/2014/lxy/2014/04/24/2/6.jpg";
    String testEssayUrl2="http://photo.enterdesk.com/2011-7-17/enterdesk.com-D7E968D1602DAED1B19229CF1BD3C5B1.jpg";
    View view;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageView ivCategories;
    LinearLayout llseek;
    AppBarLayout app_bar;
    private EssayAdpter mAdapter;
    private List<Essay> mDatas;
    RecyclerView rcyvEssay;
    FloatingActionButton fab_addEssay,fab_addQuestion;
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
        rcyvEssay.setOnScrollListener(new OnVerticalScrollListener() {

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
                swipeRefreshLayout.setRefreshing(true);// 开始刷新
                // 执行刷新后需要完成的操作
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        // 刷新完成
                        swipeRefreshLayout.setRefreshing(false);// 结束刷新
                    }
                }, 2000);// 刷新动画持续2秒
            }
        });

        fab_addEssay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyFunction.istour()){
                    Toast.makeText(getActivity(),"游客没有该权限",Toast.LENGTH_SHORT).show();
                    return;
                }

                startActivity(new Intent(getActivity(), WriteEssay_.class).putExtra("what",true));
            }
        });

        fab_addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyFunction.istour()){
                    Toast.makeText(getActivity(),"游客没有该权限",Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(new Intent(getActivity(), WriteEssay_.class).putExtra("what",false));
            }
        });
    }

    private void recycleView() {
        getEssayDate();
        mAdapter=new EssayAdpter(getActivity(),mDatas);
        rcyvEssay.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rcyvEssay.setLayoutManager(linearLayoutManager);
        rcyvEssay.setItemAnimator(new DefaultItemAnimator());
    }

    private void getEssayDate() {
        mDatas=new ArrayList<Essay>();
        mDatas.add(new Essay(testUserUrl,"Tyhj",testEssayUrl,"关于你妹啊",getString(R.string.large_text),"15","16","17","2016","id","id"));
        mDatas.add(new Essay(testUserUrl,"Tyhj",testEssayUrl2,"关于你妹啊",getString(R.string.large_text),"15","16","17","2016","id","id"));
        mDatas.add(new Essay(testUserUrl,"Tyhj","http://img1.imgtn.bdimg.com/it/u=1792538780,2798164743&fm=21&gp=0.jpg","关于你妹啊",getString(R.string.large_text),"15","16","17","2016","id","id"));
        mDatas.add(new Essay(testUserUrl,"Tyhj","http://pic.pp3.cn/uploads//201409/2014092008.jpg","关于你妹啊",getString(R.string.large_text),"15","16","17","2016","id","id"));
        mDatas.add(new Essay(testUserUrl,"Tyhj","http://pic.pp3.cn/uploads//201512/2015123007.jpg","关于你妹啊",getString(R.string.large_text),"15","16","17","2016","id","id"));
    }

    void initView(){
        fab_addQuestion= (FloatingActionButton) view.findViewById(R.id.fab_addQuestion);
        fab_addEssay= (FloatingActionButton) view.findViewById(R.id.fab_addEssay);
        ivCategories= (ImageView) view.findViewById(R.id.ivCategories);
        rcyvEssay= (RecyclerView) view.findViewById(R.id.rcyvEssay);
        llseek= (LinearLayout) view.findViewById(R.id.llseek);
        app_bar= (AppBarLayout) view.findViewById(R.id.app_bar);
        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
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

                    break;
            }
        }
    };


    public void setShowme(ShowButton showButton){
        this.showButton=showButton;
    }
}
