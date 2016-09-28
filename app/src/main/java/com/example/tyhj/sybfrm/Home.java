package com.example.tyhj.sybfrm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.tyhj.sybfrm.fragement.Categories;
import com.example.tyhj.sybfrm.fragement.Message;
import com.example.tyhj.sybfrm.fragement.More;
import com.example.tyhj.sybfrm.fragement.Popular;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import custom.MyViewPager;
import custom.ShowButton;

@EActivity(R.layout.activity_home)
    public class Home extends AppCompatActivity implements ShowButton {
    private int TAB_COUNT=4;
    Popular popular;
    Categories categories;
    Message message;
    More more;
    View viewBottombar;
    Animation animation_up,animation_down;
    boolean ifAnimation=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @ViewById
    ImageView btnHome1,btnHome2,btnHome3,btnHome4;

    @ViewById
    MyViewPager vpHome;

    @AfterViews
    void  afterview(){
        setInitColor();
        popular=new Popular();
        categories=new Categories(Home.this);
        animation_up= AnimationUtils.loadAnimation(this,R.anim.bottombarup);
        animation_down= AnimationUtils.loadAnimation(this,R.anim.bottombardown);
        animation_down.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                ifAnimation=false;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ifAnimation=true;
                viewBottombar.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        viewBottombar=findViewById(R.id.viewBottombar);
        vpHome.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        return popular;
                    case 1:
                        return categories;
                    case 2:
                        return message=new Message();
                    case 3:
                        return more=new More();
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
                setInitColor();
                switch (position){
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

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        popular.setShowme(this);
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

    @Override
    public void showMe(boolean showWhat) {
        if(ifAnimation&&showWhat&&viewBottombar.getVisibility()==View.VISIBLE){
            viewBottombar.startAnimation(animation_down);
        }else if(!showWhat&&viewBottombar.getVisibility()==View.GONE){
            viewBottombar.setVisibility(View.VISIBLE);
            viewBottombar.startAnimation(animation_up);
        }
    }
}
