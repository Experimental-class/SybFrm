package com.example.tyhj.sybfrm.fragement.categoriesfragement;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tyhj.sybfrm.Adpter.EssayAdpter;
import com.example.tyhj.sybfrm.Adpter.SimpleAdapter;
import com.example.tyhj.sybfrm.R;
import com.example.tyhj.sybfrm.info.Essay;
import com.example.tyhj.sybfrm.savaInfo.MyFunction;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.UiThread;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import custom.OnVerticalScrollListener;
import custom.ShowButton;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class Categories_1 extends Fragment {
    ShowButton showButton;
    View view;
    RecyclerView rcyvPopularEssay;
    private EssayAdpter mAdapter;
    private List<Essay> mDatas;
    SwipeRefreshLayout swipeRefreshLayout;
    boolean ifFinish;
    String[] essaysId;
    int loading_position;
    static int ESSAYCONUTLIMIT=10;
    int count=ESSAYCONUTLIMIT;
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
        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        recycleView();
        getEssay(1,1);
        return view;
    }


    //获取文章id
    private void getEssayId(){
        essaysId= MyFunction.getEssayId(0);
    }

    //获取文章
    private void getEssay(final int type, final int location) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(count==ESSAYCONUTLIMIT||type==2)
                    getEssayId();
                ifFinish=false;
                if(essaysId!=null) {
                    for (int i = count-ESSAYCONUTLIMIT; i < essaysId.length; i++) {
                        try {
                            Essay essay=MyFunction.getEssay(essaysId[i]);
                            if(essay!=null&&!mDatas.contains(essay)) {
                                if(location!=0)
                                    mDatas.add(essay);
                                else
                                    mDatas.add(0,essay);
                                handler.sendEmptyMessage(type);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(i>=count){
                            count=count+ESSAYCONUTLIMIT;
                            break;
                        }
                        if(i==(essaysId.length-1))
                            count=i;
                    }
                }else {
                    //Log.e("失败","为什么");
                }
                ifFinish=true;
            }
        }).start();
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

                            getEssay(2,0);
                            handler.sendEmptyMessage(3);
                        }
                    }).start();
                }else {
                    Snackbar.make(swipeRefreshLayout,"数据加载中,请稍后再试",Snackbar.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        rcyvPopularEssay.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                int totalItemCount = layoutManager.getItemCount();

                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                if(ifFinish&&lastVisibleItem==totalItemCount-1){
                    ifFinish=false;
                    //Toast.makeText(getActivity(),"最后一个",Toast.LENGTH_SHORT).show();
                    mDatas.add(null);
                    mAdapter.notifyItemInserted(mDatas.size()-1);
                    loading_position=mDatas.size()-1;
                    getEssay(1,1);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(800);
                                mDatas.remove(loading_position);
                                handler.sendEmptyMessage(4);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
    }

    private void recycleView() {
        mDatas=new ArrayList<Essay>();
        mAdapter=new EssayAdpter(getActivity(),mDatas);
        rcyvPopularEssay.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rcyvPopularEssay.setLayoutManager(linearLayoutManager);
        rcyvPopularEssay.setItemAnimator(new DefaultItemAnimator());
    }

    public void setShowme(ShowButton showButton){
        this.showButton=showButton;
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
                    rcyvPopularEssay.scrollToPosition(0);
                    break;
                case 3:
                    swipeRefreshLayout.setRefreshing(false);
                    break;
                case 4:
                    mAdapter.notifyItemRemoved(loading_position);
                    break;
            }
        }
    };
}
