package com.example.tyhj.sybfrm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.tyhj.sybfrm.fragement.Categories;
import com.example.tyhj.sybfrm.fragement.Message;
import com.example.tyhj.sybfrm.fragement.More;
import com.example.tyhj.sybfrm.fragement.Suggest;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_home)
    public class Home extends AppCompatActivity {
    private int TAB_COUNT=4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @ViewById
    ImageView btnHome1,btnHome2,btnHome3,btnHome4;

    @ViewById
    ViewPager vpHome;

    @AfterViews
    void  afterview(){
        setInitColor();
        vpHome.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        return new Suggest();
                    case 1:
                        return new Categories();
                    case 2:
                        return new Message();
                    case 3:
                        return new More();
                }
                return null;
            }

            @Override
            public int getCount() {
                return TAB_COUNT;
            }
        });
        vpHome.setOffscreenPageLimit(4);
        btnHome1.setImageResource(R.drawable.ic_popular_chose);
        vpHome.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                setInitColor();
                switch (state){
                    case 0:
                        btnHome1.setImageResource(R.drawable.ic_popular_chose);
                        break;
                    case 1:
                        btnHome2.setImageResource(R.drawable.ic_tags_chose);
                        break;
                    case 2:
                        btnHome3.setImageResource(R.drawable.ic_msg_chose);
                        break;
                    case 3:
                        btnHome4.setImageResource(R.drawable.ic_menu_chose);
                        break;
                }
            }
        });
    }


    @Click
    void btnHome1(){
        vpHome.setCurrentItem(0,false);
        setInitColor();
        btnHome1.setImageResource(R.drawable.ic_popular_chose);
    }
    @Click
    void btnHome2(){
        vpHome.setCurrentItem(1,false);
        setInitColor();
        btnHome2.setImageResource(R.drawable.ic_tags_chose);
    }
    @Click
    void btnHome3(){
        vpHome.setCurrentItem(2,false);
        setInitColor();
        btnHome3.setImageResource(R.drawable.ic_msg_chose);
    }
    @Click
    void btnHome4(){
        vpHome.setCurrentItem(3,false);
        setInitColor();
        btnHome4.setImageResource(R.drawable.ic_menu_chose);
    }

    void setInitColor(){
        btnHome1.setImageResource(R.drawable.ic_popular);
        btnHome2.setImageResource(R.drawable.ic_tags);
        btnHome3.setImageResource(R.drawable.ic_msg);
        btnHome4.setImageResource(R.drawable.ic_menu);
    }

}
