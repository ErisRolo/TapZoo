package com.example.ghx.tapzoo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.ghx.tapzoo.bean.User;

/**
 * Created by ghx on 2019/3/29.
 * Activity基类
 */

public class BaseActivity extends AppCompatActivity {

    protected User mCurrentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCurrentUser = User.getCurrentUser(User.class);
    }
}
