package com.example.ghx.tapzoo;

import android.app.Application;
import android.content.Context;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.example.ghx.tapzoo.bean.Animal;
import com.example.ghx.tapzoo.bean.User;
import com.example.ghx.tapzoo.utils.API;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import static com.example.ghx.tapzoo.utils.API.MSC_APP_ID;

/**
 * Created by ghx on 2019/3/29.
 * Application
 * 全局Context并初始化各类API
 */

public class MyApplication extends Application {

    private static Context mContext;
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();

        //子类化User
        AVUser.registerSubclass(User.class);
        AVUser.alwaysUseSubUserClass(User.class);

        //子类化Animal
        AVObject.registerSubclass(Animal.class);

        //初始化LeanCloud
        AVOSCloud.initialize(this, API.AVOS_APP_ID, API.AVOS_APP_KAY);

        //初始化科大讯飞API
        SpeechUtility.createUtility(this, SpeechConstant.APPID + MSC_APP_ID);

    }

    public static Context getContext() {
        return mContext;
    }

    public static MyApplication getInstance() {
        return instance;
    }

}
