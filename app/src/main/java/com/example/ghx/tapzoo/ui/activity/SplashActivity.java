package com.example.ghx.tapzoo.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.avos.avoscloud.AVUser;
import com.example.ghx.tapzoo.MainActivity;
import com.example.ghx.tapzoo.R;
import com.example.ghx.tapzoo.base.BaseActivity;
import com.example.ghx.tapzoo.utils.ShareUtils;

/**
 * Created by ghx on 2019/3/30.
 * 闪屏页
 */

public class SplashActivity extends BaseActivity {

    //闪屏页延时
    private static final int HANDLER_SPLASH = 1001;
    //判断程序是否是第一次运行
    private static final String SHARE_IS_FIRST = "isFirst";

    private ImageView iv_splash;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_SPLASH:
                    //判断程序是否是第一次运行，第一次运行就跳转到引导页
                    if (isFirstRun()) {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    } else {
                        //判断当前用户是否为空，为空就跳转到登录页面让用户登录，如果不为空就跳转到首页
                        AVUser currentUser = AVUser.getCurrentUser();
                        if (currentUser != null) {
                            //跳转到首页
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        } else {
                            //缓存用户对象为空时，跳转登录界面
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        }
                    }
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }

    private void initView() {
        //延时3000ms
        handler.sendEmptyMessageDelayed(HANDLER_SPLASH, 3000);

        iv_splash = findViewById(R.id.iv_splash);

    }

    //判断程序是否第一次运行
    private boolean isFirstRun() {
        boolean isFirstRun = ShareUtils.getBoolean(this, SHARE_IS_FIRST, true);
        if (isFirstRun) {
            //标记我们已经启动过App
            ShareUtils.putBoolean(this, SHARE_IS_FIRST, false);
            return true;
        } else {
            return false;
        }
    }
}
