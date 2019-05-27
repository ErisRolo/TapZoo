package com.example.ghx.tapzoo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.bumptech.glide.Glide;
import com.example.ghx.tapzoo.R;
import com.example.ghx.tapzoo.base.BaseFragment;
import com.example.ghx.tapzoo.bean.Animal;
import com.example.ghx.tapzoo.ui.activity.BookmarkActivity;
import com.example.ghx.tapzoo.ui.activity.LikeActivity;
import com.example.ghx.tapzoo.ui.activity.MyUserActivity;
import com.example.ghx.tapzoo.ui.activity.RecognitionActivity;
import com.example.ghx.tapzoo.utils.Config;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by ghx on 2019/3/30.
 * 个人中心
 */

public class MyFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout ll_profile;//编辑个人资料

    private CircleImageView iv_avatar;
    private TextView tv_nickname;
    private TextView tv_desc;

    private LinearLayout ll_recognition;
    private LinearLayout ll_like;
    private LinearLayout ll_bookmark;

    private TextView tv_recognition;
    private TextView tv_like;
    private TextView tv_bookmark;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, null);
        findView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        refreshView();
    }

    private void findView(View view) {

        ll_profile = view.findViewById(R.id.ll_profile);
        ll_profile.setOnClickListener(this);

        iv_avatar = view.findViewById(R.id.iv_avatar);
        tv_nickname = view.findViewById(R.id.tv_nickname);
        tv_desc = view.findViewById(R.id.tv_desc);

        ll_recognition = view.findViewById(R.id.ll_recognition);
        ll_like = view.findViewById(R.id.ll_like);
        ll_bookmark = view.findViewById(R.id.ll_bookmark);

        ll_recognition.setOnClickListener(this);
        ll_like.setOnClickListener(this);
        ll_bookmark.setOnClickListener(this);

        tv_recognition = view.findViewById(R.id.tv_recognition);
        tv_like = view.findViewById(R.id.tv_like);
        tv_bookmark = view.findViewById(R.id.tv_bookmark);

        refreshView();
    }

    private void refreshView() {

        if (mCurrentUser != null) {
            String url = mCurrentUser.getAvatar();
            if (url != null) {
                Glide.with(this).load(url).into(iv_avatar);
            }
            tv_nickname.setText(mCurrentUser.getNickname());
            tv_desc.setText(mCurrentUser.getDesc());

            AVQuery<Animal> queryRecognition = AVObject.getQuery(Animal.class);
            queryRecognition.whereEqualTo(Config.UserId, mCurrentUser.getObjectId());
            queryRecognition.findInBackground(new FindCallback<Animal>() {
                @Override
                public void done(List<Animal> list, AVException avException) {
                    if (list != null) {
                        if (list.size() != 0) {
                            tv_recognition.setText(String.valueOf(list.size()));
                        } else {
                            tv_recognition.setText("0");
                        }
                    }
                }
            });

            if (mCurrentUser.getLikeAnimal() != null) {
                if (mCurrentUser.getLikeAnimal().size() != 0) {
                    tv_like.setText(String.valueOf(mCurrentUser.getLikeAnimal().size()));
                }
            }

            if (mCurrentUser.getBookmarkAnimal() != null) {
                if (mCurrentUser.getBookmarkAnimal().size() != 0) {
                    tv_bookmark.setText(String.valueOf(mCurrentUser.getBookmarkAnimal().size()));
                }
            }

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_profile:
                startActivity(new Intent(getActivity(), MyUserActivity.class));
                break;
            case R.id.ll_recognition:
                startActivity(new Intent(getActivity(), RecognitionActivity.class));
                break;
            case R.id.ll_like:
                startActivity(new Intent(getActivity(), LikeActivity.class));
                break;
            case R.id.ll_bookmark:
                startActivity(new Intent(getActivity(), BookmarkActivity.class));
                break;
        }

    }
}
