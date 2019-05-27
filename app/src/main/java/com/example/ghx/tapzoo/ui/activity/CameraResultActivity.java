package com.example.ghx.tapzoo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.example.ghx.tapzoo.R;
import com.example.ghx.tapzoo.base.BaseActivity;
import com.example.ghx.tapzoo.bean.Animal;
import com.example.ghx.tapzoo.bean.AnimalJson;
import com.example.ghx.tapzoo.utils.FileUtils;
import com.example.ghx.tapzoo.utils.ShareUtils;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ghx on 2019/4/1.
 * 识别结果
 */

public class CameraResultActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "CameraResultActivity";

    private Toolbar mToolbar;
    private ImageView iv_play;
    private CircleImageView iv_animal;
    private TextView tv_animal;
    private TextView tv_description;

    private String description = "";

    private int isSpeak = 0;
    private boolean isInitSuccess = false;
    private SpeechSynthesizer mTts; // TTS

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_result);

        initView();
    }

    private void initView() {

        mToolbar = findViewById(R.id.mToolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                release();
                ShareUtils.putInt(CameraResultActivity.this, "isSpeak", 0);
                finish();
            }
        });

        iv_play = findViewById(R.id.iv_play);
        iv_animal = findViewById(R.id.iv_animal);
        tv_animal = findViewById(R.id.tv_animal);
        tv_description = findViewById(R.id.tv_description);

        iv_play.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        try {
            JSONObject object = new JSONObject(bundle.getString("json"));
            JSONArray jsonArray = object.getJSONArray("result");
            JSONObject object1 = (JSONObject) jsonArray.get(0);
            String object2 = object1.getString("baike_info");
            String[] strings = object2.split("\"");
            Log.i(TAG, "strings.length = " + strings.length);
            if (strings.length > 5) {
                description = strings[11];
            }
            AnimalJson animalJson = new AnimalJson(object1.getString("name"),
                    bundle.getString("str"),
                    description);
            Glide.with(this).load(animalJson.getPicture_url()).into(iv_animal);
            tv_animal.setText(animalJson.getAnimalname());
            tv_description.setText("    " + animalJson.getDescription());

            //创建animal实例并上传数据库
            final Animal animal = new Animal();
            animal.setUserId(mCurrentUser.getObjectId());
            animal.setAnimalname(animalJson.getAnimalname());
            animal.setAnimaldesc(animalJson.getDescription());
            String path = animalJson.getPicture_url();
            try {
                final AVFile file = AVFile.withAbsoluteLocalPath(FileUtils.getFileName(path), path);
                file.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        animal.setAnimalpic(file.getUrl());
                        animal.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    Log.i(TAG, "上传成功！");
                                }
                            }
                        });
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        initTTS();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_play:
                //0：第一次播放 1：正在播放 2：正在暂停
                isSpeak = ShareUtils.getInt(this, "isSpeak", 0);
                if (isSpeak == 0) {
                    iv_play.setImageResource(R.drawable.ic_pause);
                    ShareUtils.putInt(CameraResultActivity.this, "isSpeak", 1);
                    speak(description);
                } else if (isSpeak == 1) {
                    iv_play.setImageResource(R.drawable.ic_play);
                    ShareUtils.putInt(CameraResultActivity.this, "isSpeak", 2);
                    pause();
                } else if (isSpeak == 2) {
                    iv_play.setImageResource(R.drawable.ic_pause);
                    ShareUtils.putInt(CameraResultActivity.this, "isSpeak", 1);
                    resume();
                }
                break;
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
                ShareUtils.putInt(CameraResultActivity.this, "isSpeak", 0);
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




