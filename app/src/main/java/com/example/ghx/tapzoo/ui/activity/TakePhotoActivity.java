package com.example.ghx.tapzoo.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.cjt2325.cameralibrary.JCameraView;
import com.cjt2325.cameralibrary.listener.ClickListener;
import com.cjt2325.cameralibrary.listener.JCameraListener;
import com.example.ghx.tapzoo.R;
import com.example.ghx.tapzoo.base.BaseActivity;
import com.example.ghx.tapzoo.utils.Sample;
import com.example.ghx.tapzoo.utils.SaveBitmap;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by ghx on 2019/4/1.
 * 拍照
 */

public class TakePhotoActivity extends BaseActivity {

    private static final String TAG = "TakePhotoActivity";

    private JCameraView camera;

    private static JSONObject jsonObject;
    private static String str;


    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent(TakePhotoActivity.this, CameraResultActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("json", jsonObject.toString());
            bundle.putString("str", str);
            intent.putExtras(bundle);
            Log.i(TAG, "handleMessage: " + jsonObject.toString());
            //Toast.makeText(getContext(), "识别成功", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);

        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        camera.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        camera.onPause();
    }

    private void initView() {
        camera = findViewById(R.id.jcameraview);

        camera.setSaveVideoPath(Environment.getExternalStorageDirectory().getPath());
        camera.setFeatures(JCameraView.BUTTON_STATE_BOTH);
        camera.setJCameraLisenter(new JCameraListener() {
            @Override
            public void captureSuccess(final Bitmap bitmap) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HashMap<String, String> options = new HashMap<String, String>();
                        options.put("top_num", "1");
                        options.put("filter_threshold", "0.7");
                        options.put("baike_num", "1");
                        str = SaveBitmap.saveImageToGallery(TakePhotoActivity.this, bitmap);
                        JSONObject res = Sample.client.animalDetect(str, options);
                        Log.i(TAG, "run: " + res.toString());
                        jsonObject = res;
                        Message message = Message.obtain();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                }).start();
            }

            @Override
            public void recordSuccess(String url, Bitmap firstFrame) {
            }

        });
        //左边按钮点击事件
        camera.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                TakePhotoActivity.this.finish();
            }
        });
        camera.setRightClickListener(new ClickListener() {
            @Override
            public void onClick() {
            }
        });
    }
}
