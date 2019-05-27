package com.example.ghx.tapzoo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.ghx.tapzoo.R;
import com.example.ghx.tapzoo.adapter.AnimalAdapter;
import com.example.ghx.tapzoo.base.BaseActivity;
import com.example.ghx.tapzoo.bean.Animal;
import com.example.ghx.tapzoo.utils.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ghx on 2019/5/25.
 * 识别的列表
 */

public class RecognitionActivity extends BaseActivity {

    private Toolbar mToolbar;

    private RecyclerView rv_recognition;
    private LinearLayoutManager mLayoutManager;
    private AnimalAdapter mAdapter;
    private List<Animal> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognition);

        initView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        refreshView();
    }

    private void initView() {

        mToolbar = (Toolbar) findViewById(R.id.mToolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rv_recognition = findViewById(R.id.rv_recognition);
        mLayoutManager = new LinearLayoutManager(this);
        rv_recognition.setLayoutManager(mLayoutManager);

        setAnimalAdapter();

    }

    private void refreshView() {
        mList.clear();
        setAnimalAdapter();
    }

    private void setAnimalAdapter() {

        AVQuery<Animal> query = AVObject.getQuery(Animal.class);
        query.whereEqualTo(Config.UserId, mCurrentUser.getObjectId());
        query.findInBackground(new FindCallback<Animal>() {
            @Override
            public void done(List<Animal> list, AVException avException) {
                if (list != null) {
                    if (list.size() != 0) {
                        for (int i = 0; i < list.size(); i++) {
                            mList.add(list.get(i));
                            mAdapter = new AnimalAdapter(RecognitionActivity.this, mList);
                            rv_recognition.setAdapter(mAdapter);
                        }
                    } else {
                        rv_recognition.removeAllViews();
                    }
                }
            }
        });
    }
}
