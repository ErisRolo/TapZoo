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
 * 收藏的列表
 */

public class BookmarkActivity extends BaseActivity {

    private Toolbar mToolbar;

    private RecyclerView rv_bookmark;
    private LinearLayoutManager mLayoutManager;
    private AnimalAdapter mAdapter;
    private List<Animal> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

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

        rv_bookmark = findViewById(R.id.rv_bookmark);
        mLayoutManager = new LinearLayoutManager(this);
        rv_bookmark.setLayoutManager(mLayoutManager);

        setBookmarkAdapter();
    }

    private void refreshView() {
        mList.clear();
        setBookmarkAdapter();
    }

    private void setBookmarkAdapter() {
        if (mCurrentUser.getBookmarkAnimal() != null) {
            if (mCurrentUser.getBookmarkAnimal().size() != 0) {
                for (int i = 0; i < mCurrentUser.getBookmarkAnimal().size(); i++) {
                    AVQuery<Animal> query = AVObject.getQuery(Animal.class);
                    query.whereEqualTo("objectId", mCurrentUser.getBookmarkAnimal().get(i));
                    query.findInBackground(new FindCallback<Animal>() {
                        @Override
                        public void done(List<Animal> list, AVException avException) {
                            if (list != null) {
                                if (list.size() != 0) {
                                    for (int j = 0; j < list.size(); j++) {
                                        mList.add(list.get(j));
                                        mAdapter = new AnimalAdapter(BookmarkActivity.this, mList);
                                        rv_bookmark.setAdapter(mAdapter);
                                    }
                                }
                            }
                        }
                    });
                }
            } else {
                rv_bookmark.removeAllViews();
            }
        }
    }
}
