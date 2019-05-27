package com.example.ghx.tapzoo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import com.example.ghx.tapzoo.base.BaseActivity;
import com.example.ghx.tapzoo.ui.fragment.FindFragment;
import com.example.ghx.tapzoo.ui.fragment.ExploreFragment;
import com.example.ghx.tapzoo.ui.fragment.MyFragment;
import com.example.ghx.tapzoo.ui.fragment.RecognizeFragment;
import com.example.ghx.tapzoo.utils.BottomNavigationViewHelper;
import com.example.ghx.tapzoo.utils.FragmentUtils;

/**
 * Created by ghx on 2019/3/29.
 * 主界面
 */

public class MainActivity extends BaseActivity {


    private BottomNavigationView bottom_layout;
    private FragmentManager fm;

    private RecognizeFragment mRecognizeFragment;
    private ExploreFragment mExploreFragment;
    private FindFragment mFindFragment;
    private MyFragment mMyFragment;

    public static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;

        fm = getSupportFragmentManager();

        if (savedInstanceState == null) {
            mRecognizeFragment = new RecognizeFragment();
            FragmentUtils.addFragment(fm, R.id.fragment_container, mRecognizeFragment);
            initView();
        } else {
            initView();
        }
    }

    @SuppressLint("NewApi")
    private void initView() {
        bottom_layout = findViewById(R.id.bottom_layout);
        BottomNavigationViewHelper.disableShiftMode(bottom_layout);
        bottom_layout.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.recognize:
                        mRecognizeFragment = new RecognizeFragment();
                        FragmentUtils.replaceFragment(fm, R.id.fragment_container, mRecognizeFragment);
                        break;
                    case R.id.map:
                        mExploreFragment = new ExploreFragment();
                        FragmentUtils.replaceFragment(fm, R.id.fragment_container, mExploreFragment);
                        break;
                    case R.id.find:
                        mFindFragment = new FindFragment();
                        FragmentUtils.replaceFragment(fm, R.id.fragment_container, mFindFragment);
                        break;
                    case R.id.my:
                        mMyFragment = new MyFragment();
                        FragmentUtils.replaceFragment(fm, R.id.fragment_container, mMyFragment);
                        break;
                }
                return true;
            }
        });
    }
}
