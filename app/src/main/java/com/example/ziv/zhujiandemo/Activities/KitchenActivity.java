package com.example.ziv.zhujiandemo.Activities;

import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;


import com.example.ziv.zhujiandemo.Adapters.KitchenPagerAdapter;
import com.example.ziv.zhujiandemo.Fragments.AfterCookFragment;
import com.example.ziv.zhujiandemo.Fragments.BeforeCookFragment;
import com.example.ziv.zhujiandemo.Fragments.InCookFragment;
import com.example.ziv.zhujiandemo.R;

/**
 * 我的厨房Activity
 * 此Activity下分为三个Fragment，分别对应下厨前中后三种状态
 * BeforeCookFragment,InCookFragment,AfterCookFragment
 */

public class KitchenActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);
        tabLayout = (TabLayout)findViewById(R.id.tab_layout_id);
        appBarLayout = (AppBarLayout)findViewById(R.id.appbarlayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        KitchenPagerAdapter adapter = new KitchenPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new BeforeCookFragment(),"下厨前");
        adapter.addFragment(new InCookFragment(),"下厨中");
        adapter.addFragment(new AfterCookFragment(),"下厨后");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
