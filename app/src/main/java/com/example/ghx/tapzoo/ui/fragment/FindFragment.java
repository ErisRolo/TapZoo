package com.example.ghx.tapzoo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.ghx.tapzoo.R;
import com.example.ghx.tapzoo.adapter.FindAdapter;
import com.example.ghx.tapzoo.base.BaseFragment;
import com.example.ghx.tapzoo.bean.Animal;
import com.example.ghx.tapzoo.utils.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ghx on 2019/3/30.
 * 发现
 */

public class FindFragment extends BaseFragment {

    private RecyclerView rv_find;
    private GridLayoutManager mLayoutManager;
    private FindAdapter mAdapter;
    private List<Animal> mList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, null);
        findView(view);
        return view;
    }


    private void findView(View view) {

        rv_find = view.findViewById(R.id.rv_find);
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        rv_find.setLayoutManager(mLayoutManager);

        setFindAdapter();
    }

    private void setFindAdapter() {

        AVQuery<Animal> query = AVObject.getQuery(Animal.class);
        query.whereEqualTo(Config.UserId, "edu");
        query.findInBackground(new FindCallback<Animal>() {
            @Override
            public void done(List<Animal> list, AVException avException) {
                if (list != null) {
                    if (list.size() != 0) {
                        for (int i = 0; i < list.size(); i++) {
                            mList.add(list.get(i));
                            mAdapter = new FindAdapter(getContext(), mList);
                            rv_find.setAdapter(mAdapter);
                        }
                    } else {
                        rv_find.removeAllViews();
                    }
                }
            }
        });
    }
}
