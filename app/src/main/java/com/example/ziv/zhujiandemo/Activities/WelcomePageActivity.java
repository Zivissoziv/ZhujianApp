package com.example.ziv.zhujiandemo.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.ziv.zhujiandemo.R;

/**
 * 欢迎页Activity，
 * 进入程序后（后台完全退出时），会进入欢迎页，欢迎页2秒后跳转至登陆/注册页
 */

public class WelcomePageActivity extends AppCompatActivity {

    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        iv = (ImageView) findViewById(R.id.welcomeImg);
        //设置动画
        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.welcome_page_transition);
        iv.setAnimation(myanim);
        final Intent i = new Intent(this, LaunchActivity.class);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();

    }
}
