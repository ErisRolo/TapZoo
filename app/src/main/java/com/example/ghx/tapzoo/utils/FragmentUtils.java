package com.example.ghx.tapzoo.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by ghx on 2019/3/29.
 * Fragment封装工具类
 */

public class FragmentUtils {

    public static void addFragment(FragmentManager mFragmentManager, int frameId,
                                   Fragment mFragment) {
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(frameId, mFragment);
        mFragmentTransaction.commit();
    }

    public static void replaceFragment(FragmentManager mFragmentManager, int frameId,
                                       Fragment mFragment) {
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(frameId, mFragment);
        mFragmentTransaction.commit();
    }
}
