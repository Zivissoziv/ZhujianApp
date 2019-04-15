package com.example.ziv.zhujiandemo.Activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ziv.zhujiandemo.Adapters.SliderPagerAdapter;
import com.example.ziv.zhujiandemo.Models.Recipe;
import com.example.ziv.zhujiandemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试首页Activity，无效
 */

public class HomeActivity extends BaseActivity{

    private List<Recipe> lstSlides ;
    private ViewPager sliderpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


    }
}
