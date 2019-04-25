package com.example.ziv.zhujiandemo.Activities;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ziv.zhujiandemo.Adapters.KitchenImgPagerAdapter;
import com.example.ziv.zhujiandemo.Adapters.KitchenPagerAdapter;
import com.example.ziv.zhujiandemo.Fragments.AfterCookFragment;
import com.example.ziv.zhujiandemo.Fragments.BeforeCookFragment;
import com.example.ziv.zhujiandemo.Fragments.InCookFragment;
import com.example.ziv.zhujiandemo.Models.Recipe;
import com.example.ziv.zhujiandemo.R;
import com.example.ziv.zhujiandemo.Tools.SpUtils;

import java.util.List;

/**
 * 我的厨房Activity
 * 此Activity下分为三个Fragment，分别对应下厨前中后三种状态
 * BeforeCookFragment,InCookFragment,AfterCookFragment
 */

public class KitchenActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private ViewPager sliderImgPager;
    private Button cancelBtn;
    private Button backHomeBtn;
    private TextView emptyWarnTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout_id);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbarlayout_id);

        //更新厨房页轮播图
        sliderImgPager = (ViewPager) findViewById(R.id.kitchen_slider_pager);
        cancelBtn = (Button) findViewById(R.id.cancel_button);
        backHomeBtn = (Button) findViewById(R.id.back_home_button);
        emptyWarnTV = (TextView) findViewById(R.id.empty_warn_text);
        List<Recipe> kitchenRecipes = SpUtils.getObject(getApplicationContext(), Recipe.class);
        if (kitchenRecipes.size() == 0) {
            emptyWarnTV.setVisibility(View.VISIBLE);
        }
        final KitchenImgPagerAdapter kipAdapter = new KitchenImgPagerAdapter(this, kitchenRecipes);
        sliderImgPager.setAdapter(kipAdapter);


        backHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent flushIntent = new Intent(KitchenActivity.this, Home.class);
                startActivity(flushIntent);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Recipe> recipes = SpUtils.getObject(KitchenActivity.this, Recipe.class);
                if (recipes.size() != 0) {
                    Recipe delrecipe = recipes.get(sliderImgPager.getCurrentItem());
                    recipes.remove(delrecipe);
                    SpUtils.putObject(KitchenActivity.this, recipes);
                    kipAdapter.changeAdapterList(recipes);
                    kipAdapter.notifyDataSetChanged();
                    Toast.makeText(KitchenActivity.this, "已将" + delrecipe.getTitle() + "移出我的厨房", Toast.LENGTH_LONG).show();
                    sliderImgPager.setAdapter(kipAdapter);
                } else {
                    emptyWarnTV.setVisibility(View.VISIBLE);
                }
                //通知fragment更新
                Intent flushIntent = new Intent(KitchenActivity.this, KitchenActivity.class);
                startActivity(flushIntent);
            }
        });


        //更新厨房页三大板块
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        KitchenPagerAdapter adapter = new KitchenPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BeforeCookFragment(), "下厨前");
        adapter.addFragment(new InCookFragment(), "下厨中");
        adapter.addFragment(new AfterCookFragment(), "下厨后");
        viewPager.setAdapter(adapter);


        tabLayout.setupWithViewPager(sliderImgPager);
        tabLayout.setupWithViewPager(viewPager);

    }
}
