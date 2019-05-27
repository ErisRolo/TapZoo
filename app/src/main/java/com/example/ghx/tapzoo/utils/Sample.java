package com.example.ghx.tapzoo.utils;

import com.baidu.aip.imageclassify.AipImageClassify;

/**
 * Created by ghx on 2019/4/1.
 */

public class Sample {

    //设置APPID/AK/SK
    public static final String APP_ID = "15908925";
    public static final String API_KEY = "hqeDctSXbEK0CY8AX4wKQxV3";
    public static final String SECRET_KEY = "ZC1h3cA6eFaSotaK5Vouz38j8mLffKBv";

    private static final String TAG = "Sample";

    public static final AipImageClassify client = new AipImageClassify(APP_ID,API_KEY,SECRET_KEY);
}
