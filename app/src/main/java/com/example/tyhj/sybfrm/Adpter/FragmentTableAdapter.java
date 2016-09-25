package com.example.tyhj.sybfrm.Adpter;
 
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tyhj.sybfrm.fragement.categoriesfragement.Categories_1;
import com.example.tyhj.sybfrm.fragement.categoriesfragement.Categories_2;
import com.example.tyhj.sybfrm.fragement.categoriesfragement.Categories_3;
import com.example.tyhj.sybfrm.fragement.categoriesfragement.Categories_4;

/**
 * Created by Administrator on 2015/7/30.
 */
public class FragmentTableAdapter extends FragmentPagerAdapter {

    private int PAGE_COUNT;
    private String tabTitles[];
    private Context context;
    private Fragment[] fragments;
    public FragmentTableAdapter(FragmentManager fm, Context context,String[] title,Fragment[] fragments) {
        super(fm);
        this.context = context;
        this.tabTitles=title;
        this.fragments=fragments;
        PAGE_COUNT=title.length;
    }
 
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return fragments[0];
            case 1:
                return fragments[1];
            case 2:
                return fragments[2];
            case 3:
                return fragments[3];
            case 4:
                return fragments[4];
        }
        return null;
    }
 
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
 
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}