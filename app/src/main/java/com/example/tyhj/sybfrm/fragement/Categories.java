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

import custom.ShowButton;


public class Categories extends Fragment {
    ShowButton showButton;
    View view;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Fragment[] fragments;
    private String[] titles;
    public Categories(ShowButton showButton) {
        this.showButton=showButton;
    }
    Categories_1 categories1;
    Categories_2 categories2;
    Categories_3 categories3;
    Categories_4 categories4;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragments= new Fragment[]{categories1=new Categories_1(), categories2=new Categories_2(), categories3=new Categories_3(), categories4=new Categories_4()};
        titles=new String[]{"热门","最新","喜爱","收藏"};
        categories1.setShowme(showButton);
        categories2.setShowme(showButton);
        categories3.setShowme(showButton);
        categories4.setShowme(showButton);
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
        mViewPager.setOffscreenPageLimit(titles.length);
        mViewPager.setCurrentItem(0);

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }
}
