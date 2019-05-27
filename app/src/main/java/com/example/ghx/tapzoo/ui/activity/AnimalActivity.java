package com.example.ghx.tapzoo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.bumptech.glide.Glide;
import com.example.ghx.tapzoo.R;
import com.example.ghx.tapzoo.base.BaseActivity;
import com.example.ghx.tapzoo.bean.Animal;
import com.example.ghx.tapzoo.utils.ShareUtils;
import com.example.ghx.tapzoo.utils.ShowToast;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ghx on 2019/5/25.
 * 动物详情界面
 */

public class AnimalActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "AnimalActivity";

    private static final String ANIMAL_ID = "animal_id";
    private String animal_id;

    private Toolbar mToolbar;

    private ImageView iv_play;
    private CircleImageView iv_animal;
    private TextView tv_name;
    private TextView tv_description;

    private String pic;
    private String name;
    private String description = "";

    private int isSpeak = 0;
    private boolean isInitSuccess = false;
    private SpeechSynthesizer mTts; // TTS

    private ImageView iv_like;
    private ImageView iv_bookmark;

    private boolean isLiked = false;
    private boolean isBookmarked = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal);

        animal_id = getIntent().getStringExtra(ANIMAL_ID);

        initView();

        isLike();
        isBookmark();
    }

    private void initView() {

        mToolbar = (Toolbar) findViewById(R.id.mToolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                release();
                ShareUtils.putInt(AnimalActivity.this, "isSpeak", 0);
                finish();
            }
        });

        iv_play = findViewById(R.id.iv_play);
        iv_animal = findViewById(R.id.iv_animal);
        tv_name = findViewById(R.id.tv_name);
        tv_description = findViewById(R.id.tv_description);

        AVQuery<Animal> query = AVObject.getQuery(Animal.class);
        query.whereEqualTo("objectId", animal_id);
        query.findInBackground(new FindCallback<Animal>() {
            @Override
            public void done(List<Animal> list, AVException avException) {
                if (list != null) {
                    if (list.size() != 0) {
                        pic = list.get(0).getAnimalpic();
                        name = list.get(0).getAnimalname();
                        description = list.get(0).getAnimaldesc();
                        Glide.with(AnimalActivity.this).load(pic).into(iv_animal);
                        tv_name.setText(name);
                        tv_description.setText("    " + description);
                    }
                }
            }
        });

        iv_play.setOnClickListener(this);

        iv_like = findViewById(R.id.iv_like);
        iv_bookmark = findViewById(R.id.iv_bookmark);
        iv_like.setOnClickListener(this);
        iv_bookmark.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_play:
                //0：第一次播放 1：正在播放 2：正在暂停
                isSpeak = ShareUtils.getInt(this, "isSpeak", 0);
                if (isSpeak == 0) {
                    iv_play.setImageResource(R.drawable.ic_pause);
                    ShareUtils.putInt(AnimalActivity.this, "isSpeak", 1);
                    speak(description);
                } else if (isSpeak == 1) {
                    iv_play.setImageResource(R.drawable.ic_play);
                    ShareUtils.putInt(AnimalActivity.this, "isSpeak", 2);
                    pause();
                } else if (isSpeak == 2) {
                    iv_play.setImageResource(R.drawable.ic_pause);
                    ShareUtils.putInt(AnimalActivity.this, "isSpeak", 1);
                    resume();
                }
                break;
            case R.id.iv_like:
                //如果已经喜欢，点击则为取消喜欢
                if (isLiked) {
                    isLiked = false;
                    iv_like.setImageResource(R.drawable.ic_like);
                    //如果已经喜欢，则喜欢列表肯定不为空，判断当前列表是否包含该相册，然后移出列表
                    if (mCurrentUser.getLikeAnimal().contains(animal_id)) {
                        mCurrentUser.getLikeAnimal().remove(animal_id);
                        mCurrentUser.setLikeAnimal(mCurrentUser.getLikeAnimal());
                        mCurrentUser.saveInBackground();
                        ShowToast.showShortToast(AnimalActivity.this, "您已将该动物移出喜欢列表！");
                    }
                } else {
                    //如果未喜欢，点击则为喜欢
                    isLiked = true;
                    iv_like.setImageResource(R.drawable.ic_liked);
                    //先判断喜欢列表是否为空，如果喜欢的列表不为空，判断列表是否包含该相册，然后添加
                    if (mCurrentUser.getLikeAnimal() != null) {
                        if (!mCurrentUser.getLikeAnimal().contains(animal_id)) {
                            mCurrentUser.getLikeAnimal().add(animal_id);
                            mCurrentUser.setLikeAnimal(mCurrentUser.getLikeAnimal());
                            mCurrentUser.saveInBackground();
                            ShowToast.showShortToast(AnimalActivity.this, "您已将该动物标记为喜欢！");
                        }
                    } else {
                        //如果喜欢的列表为空，新建一个列表，添加该相册并赋值
                        List<String> newList = new ArrayList<>();
                        newList.add(animal_id);
                        mCurrentUser.setLikeAnimal(newList);
                        mCurrentUser.saveInBackground();
                        ShowToast.showShortToast(AnimalActivity.this, "您已将该动物标记为喜欢！");
                    }
                }
                break;
            case R.id.iv_bookmark:
                //如果已经收藏，点击则为取消收藏
                if (isBookmarked) {
                    isBookmarked = false;
                    iv_bookmark.setImageResource(R.drawable.ic_bookmark);
                    //如果已经收藏，则收藏列表肯定不为空，判断是否包含该相册，然后取消收藏
                    if (mCurrentUser.getBookmarkAnimal().contains(animal_id)) {
                        mCurrentUser.getBookmarkAnimal().remove(animal_id);
                        mCurrentUser.setBookmarkAnimal(mCurrentUser.getBookmarkAnimal());
                        mCurrentUser.saveInBackground();
                        ShowToast.showShortToast(AnimalActivity.this, "您已将该动物移出收藏列表！");
                    }

                } else {
                    //如果未收藏，点击则为收藏
                    isBookmarked = true;
                    iv_bookmark.setImageResource(R.drawable.ic_bookmarked);
                    //先判断收藏列表是否为空，如果收藏列表不为空，判断是否包含该相册，然后添加
                    if (mCurrentUser.getBookmarkAnimal() != null) {
                        if (!mCurrentUser.getBookmarkAnimal().contains(animal_id)) {
                            mCurrentUser.getBookmarkAnimal().add(animal_id);
                            mCurrentUser.setBookmarkAnimal(mCurrentUser.getBookmarkAnimal());
                            mCurrentUser.saveInBackground();
                            ShowToast.showShortToast(AnimalActivity.this, "您已成功收藏该动物！");
                        }
                    } else {
                        //如果收藏列表为空，新建一个列表，添加该相册，并赋值
                        List<String> newList = new ArrayList<>();
                        newList.add(animal_id);
                        mCurrentUser.setBookmarkAnimal(newList);
                        mCurrentUser.saveInBackground();
                        ShowToast.showShortToast(AnimalActivity.this, "您已成功收藏该动物！");
                    }
                }
                break;
        }
    }

    //判断当前动物是否被当前用户喜欢
    private void isLike() {
        if (mCurrentUser.getLikeAnimal() != null) {
            if (mCurrentUser.getLikeAnimal().contains(animal_id)) {
                isLiked = true;
                iv_like.setImageResource(R.drawable.ic_liked);
            } else {
                isLiked = false;
                iv_like.setImageResource(R.drawable.ic_like);
            }
        }
    }

    //判断当前动物是否被当前用户收藏
    private void isBookmark() {
        if (mCurrentUser.getBookmarkAnimal() != null) {
            if (mCurrentUser.getBookmarkAnimal().contains(animal_id)) {
                isBookmarked = true;
                iv_bookmark.setImageResource(R.drawable.ic_bookmarked);
            } else {
                isBookmarked = false;
                iv_bookmark.setImageResource(R.drawable.ic_bookmark);
            }
        }
    }


    private void initTTS() {
        // 创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener
        mTts = SpeechSynthesizer.createSynthesizer(this, null);
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 设置在线云端
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 设置发音人
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
        // 设置发音语速
        mTts.setParameter(SpeechConstant.SPEED, "50");
        // 设置音调
        mTts.setParameter(SpeechConstant.PITCH, "50");
        // 设置合成音量
        mTts.setParameter(SpeechConstant.VOLUME, "100");
        // 设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");
/*        // 设置音频保存路径，需要申请WRITE_EXTERNAL_STORAGE权限，如不需保存注释该行代码
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH,"./sdcard/iflytek.pcm");*/

        isInitSuccess = true;
    }


    //合成监听器
    private SynthesizerListener mSynListener = new SynthesizerListener() {

        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError speechError) {
            if (speechError == null) {
                ShareUtils.putInt(AnimalActivity.this, "isSpeak", 0);
                iv_play.setImageResource(R.drawable.ic_play);
            }
        }

        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
        }

        //开始播放
        public void onSpeakBegin() {
        }

        //暂停播放
        public void onSpeakPaused() {
        }

        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        //恢复播放回调接口
        public void onSpeakResumed() {
        }

        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
        }
    };

    //开始合成
    public void speak(String text) {
        if (isInitSuccess) {
            if (mTts.isSpeaking()) {
                stop();
            }
            mTts.startSpeaking(text, mSynListener);
        } else {
            initTTS();
            mTts.startSpeaking(text, mSynListener);
        }
    }

    public void pause() {
        mTts.pauseSpeaking();
    }

    public void resume() {
        mTts.resumeSpeaking();
    }

    public void release() {
        if (null != mTts) {
            mTts.stopSpeaking();
            mTts.destroy();  //退出时释放
        }
    }

    public void stop() {
        mTts.stopSpeaking();
    }
}
