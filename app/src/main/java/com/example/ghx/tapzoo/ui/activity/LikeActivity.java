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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ghx on 2019/5/25.
 * 喜欢的列表
 */

public class LikeActivity extends BaseActivity {

    private Toolbar mToolbar;

    private RecyclerView rv_like;
    private LinearLayoutManager mLayoutManager;
    private AnimalAdapter mAdapter;
    private List<Animal> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

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

        rv_like = findViewById(R.id.rv_like);
        mLayoutManager = new LinearLayoutManager(this);
        rv_like.setLayoutManager(mLayoutManager);

        setLikeAdapter();
    }

    private void refreshView() {
        mList.clear();
        setLikeAdapter();
    }

    private void setLikeAdapter() {
        if (mCurrentUser.getLikeAnimal() != null) {
            if (mCurrentUser.getLikeAnimal().size() != 0) {
                for (int i = 0; i < mCurrentUser.getLikeAnimal().size(); i++) {
                    AVQuery<Animal> query = AVObject.getQuery(Animal.class);
                    query.whereEqualTo("objectId", mCurrentUser.getLikeAnimal().get(i));
                    query.findInBackground(new FindCallback<Animal>() {
                        @Override
                        public void done(List<Animal> list, AVException avException) {
                            if (list != null) {
                                if (list.size() != 0) {
                                    for (int j = 0; j < list.size(); j++) {
                                        mList.add(list.get(j));
                                        mAdapter = new AnimalAdapter(LikeActivity.this, mList);
                                        rv_like.setAdapter(mAdapter);
                                    }
                                }
                            }
                        }
                    });
                }
            } else {
                rv_like.removeAllViews();
            }
        }
    }
}
