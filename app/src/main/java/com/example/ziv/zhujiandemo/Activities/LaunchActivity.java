package com.example.ziv.zhujiandemo.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ziv.zhujiandemo.R;

/**
 * 此Activity为登陆/注册页面Activity
 * 账号密码暂时写死在代码中（后期后台开发时再完善）
 * 用SharedPreferences实现记住登陆的功能
 */
public class LaunchActivity extends BaseActivity {

    private EditText userEmail, userPassword, userPassword2;
    private ImageView launchImage;

    private Button regBtn;
    private Button logBtn;
    private Button changeRLBtn;
    private Button showRegLogBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //判断是否已登陆
        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        if (sp == null||sp.getBoolean("issignout",false)) {

            setContentView(R.layout.activity_launch);


            userEmail = findViewById(R.id.userEmail);
            userPassword = findViewById(R.id.userPassword);
            userPassword2 = findViewById(R.id.userPassword2);

            launchImage = findViewById(R.id.launchlogo);

            showRegLogBtn = findViewById(R.id.showRegLogButton);
            changeRLBtn = findViewById(R.id.regLogChangeButton);
            regBtn = findViewById(R.id.regButton);
            logBtn = findViewById(R.id.logButton);


            showRegLogBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showRegLogBtn.setVisibility(View.INVISIBLE);
                    showRegInfo();
                }
            });
        }else{
            Intent homeIntent = new Intent(getApplicationContext(), Home.class);
            startActivity(homeIntent);
            finish();
        }

    }

    private void showRegInfo() {
        launchImage.setVisibility(View.VISIBLE);
        userEmail.setVisibility(View.VISIBLE);
        userPassword.setVisibility(View.VISIBLE);
        userPassword2.setVisibility(View.VISIBLE);

        logBtn.setVisibility(View.INVISIBLE);
        regBtn.setVisibility(View.VISIBLE);

        changeRLBtn.setText("直接登陆");
        changeRLBtn.setVisibility(View.VISIBLE);

        changeRLBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogInfo();
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = userEmail.getText().toString();
                final String password = userPassword.getText().toString();
                final String password2 = userPassword2.getText().toString();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(password2)) {
                    showMassage("请确认是否输入所有信息");
                } else if (password.equals(password2)) {
                    Intent homeIntent = new Intent(getApplicationContext(), Home.class);
                    startActivity(homeIntent);
                    showMassage("注册成功");

                    //纪录登录状态
                    SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
                    sp.edit().putString("useremail", email)
                            .putString("userpassword", password)
                            .putBoolean("issignout",false)
                            .apply();

                } else {
                    showMassage("两次输入的密码不一致");
                }

            }
        });

    }

    private void showLogInfo() {
        launchImage.setVisibility(View.VISIBLE);
        userEmail.setVisibility(View.VISIBLE);
        userPassword.setVisibility(View.VISIBLE);
        userPassword2.setVisibility(View.INVISIBLE);

        logBtn.setVisibility(View.VISIBLE);
        regBtn.setVisibility(View.INVISIBLE);

        changeRLBtn.setText("尚未注册");
        changeRLBtn.setVisibility(View.VISIBLE);

        changeRLBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegInfo();
            }
        });


        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = userEmail.getText().toString();
                final String password = userPassword.getText().toString();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    showMassage("请确认是否输入所有信息");
                } else if (email.equals("ziv@qq.com") && password.equals("8888")) {
                    Intent homeIntent = new Intent(getApplicationContext(), Home.class);
                    startActivity(homeIntent);
                    showMassage("登陆成功");

                    //纪录登录状态
                    SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
                    sp.edit().putString("useremail", email)
                            .putString("userpassword", password)
                            .putBoolean("issignout",false)
                            .apply();

                } else {
                    showMassage("邮箱或密码不正确");
                }
            }
        });
    }

    private void showMassage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

}
