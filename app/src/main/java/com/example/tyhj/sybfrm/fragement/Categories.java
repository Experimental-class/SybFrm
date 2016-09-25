package com.example.tyhj.sybfrm.fragement;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tyhj.sybfrm.Adpter.FragmentTableAdapter;
import com.example.tyhj.sybfrm.R;
import com.example.tyhj.sybfrm.fragement.categoriesfragement.Categories_1;
import com.example.tyhj.sybfrm.fragement.categoriesfragement.Categories_2;
import com.example.tyhj.sybfrm.fragement.categoriesfragement.Categories_3;
import com.example.tyhj.sybfrm.fragement.categoriesfragement.Categories_4;


public class Categories extends Fragment {
    View view;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Fragment[] fragments;
    private String[] titles;
    public Categories() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragments= new Fragment[]{new Categories_1(), new Categories_2(), new Categories_3(), new Categories_4()};
        titles=new String[]{"推荐","圆桌","热门","收藏"};
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_categories,null);
        initViews();

        return view;
    }

    private void initViews() {
        mTabLayout= (TabLayout) view.findViewById(R.id.tabCategories);
        mViewPager= (ViewPager) view.findViewById(R.id.vpCategories);



        mViewPager.setAdapter(new FragmentTableAdapter(getFragmentManager(),getActivity(),titles,fragments));
        mViewPager.setOffscreenPageLimit(4);


        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }
}
