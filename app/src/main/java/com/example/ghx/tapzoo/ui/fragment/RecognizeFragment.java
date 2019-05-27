package com.example.ghx.tapzoo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ghx.tapzoo.R;
import com.example.ghx.tapzoo.base.BaseFragment;
import com.example.ghx.tapzoo.ui.activity.MyUserActivity;
import com.example.ghx.tapzoo.ui.activity.TakePhotoActivity;

/**
 * Created by ghx on 2019/3/29.
 * 识别（首页）
 */

public class RecognizeFragment extends BaseFragment implements View.OnClickListener {

    private ImageView iv_recognize;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recognize, null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        iv_recognize = view.findViewById(R.id.iv_recognize);
        iv_recognize.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_recognize:
                startActivity(new Intent(getActivity(), TakePhotoActivity.class));
                break;
        }
    }

}
