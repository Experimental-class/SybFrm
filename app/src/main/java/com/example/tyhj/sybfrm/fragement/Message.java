package com.example.tyhj.sybfrm.fragement;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tyhj.sybfrm.Adpter.FragmentTableAdapter;
import com.example.tyhj.sybfrm.R;
import com.example.tyhj.sybfrm.fragement.msgfragement.Msg_1;
import com.example.tyhj.sybfrm.fragement.msgfragement.Msg_2;
import com.example.tyhj.sybfrm.fragement.msgfragement.Msg_3;

import custom.MyViewPager;

public class Message extends Fragment {
    View view;
    ViewPager viewPager;
    TabLayout tabLayout;
    String[] title;
    Fragment[] fragements;
    public Message() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title=new String[]{"通知","消息","关注"};
        fragements=new Fragment[]{new Msg_1(),new Msg_2(),new Msg_3()};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_message,null);
        initViews();
        return view;
    }

    private void initViews() {
        viewPager= (ViewPager) view.findViewById(R.id.vpMsg);
        tabLayout= (TabLayout) view.findViewById(R.id.tabMsg);

        viewPager.setAdapter(new FragmentTableAdapter(getFragmentManager(),getActivity(),title,fragements));
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(3);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

}
