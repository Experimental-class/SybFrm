package com.example.tyhj.sybfrm;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tyhj.sybfrm.Adpter.FragmentTableAdapter;
import com.example.tyhj.sybfrm.fragement.Popular;
import com.example.tyhj.sybfrm.fragement.questions.Eyes;
import com.example.tyhj.sybfrm.fragement.questions.MyQs;
import com.example.tyhj.sybfrm.fragement.questions.NewQs;
import com.example.tyhj.sybfrm.fragement.questions.NoAnswerQs;
import com.example.tyhj.sybfrm.fragement.questions.PopularQs;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_allquestions)
public class Allquestions extends AppCompatActivity {
    String[] title;
    Fragment[] fragements;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title=new String[]{"最新","热门","关注","我的提问","未回答"};
        fragements=new Fragment[]{new PopularQs(),new NewQs(),new Eyes(),new MyQs(),new NoAnswerQs()};
    }

    @ViewById
    ViewPager vpAllquestions;

    @ViewById
    TabLayout tabAllquestions;

    @AfterViews
    void afterViews(){
        vpAllquestions.setAdapter(new FragmentTableAdapter(getSupportFragmentManager(),this,title,fragements));
        vpAllquestions.setOffscreenPageLimit(title.length);
        vpAllquestions.setCurrentItem(0);

        tabAllquestions.setupWithViewPager(vpAllquestions);
        tabAllquestions.setTabMode(TabLayout.MODE_SCROLLABLE);
    }
}
