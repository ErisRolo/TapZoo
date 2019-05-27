package com.example.ghx.tapzoo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.ghx.tapzoo.bean.User;

/**
 * Created by ghx on 2019/3/29.
 * Fragment基类
 */

public class BaseFragment extends Fragment {

    protected User mCurrentUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCurrentUser = User.getCurrentUser(User.class);
    }
}
