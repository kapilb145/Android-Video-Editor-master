package com.marvhong.videoeditor.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import butterknife.BindView;
import com.cjt2325.cameralibrary.JCameraView;
import com.cjt2325.cameralibrary.listener.ClickListener;
import com.cjt2325.cameralibrary.listener.ErrorListener;
import com.cjt2325.cameralibrary.listener.JCameraListener;
import com.cjt2325.cameralibrary.listener.RecordStateListener;
import com.cjt2325.cameralibrary.util.FileUtil;
import com.marvhong.videoeditor.R;
import com.marvhong.videoeditor.base.BaseActivity;
import java.io.File;

/**
 * @author LLhon
 * @Project Android-Video-Editor
 * @Package com.marvhong.videoeditor
 * @Date 2018/8/22 10:54
 * @description Video shooting interface
 */


public class VideoCameraActivity extends BaseActivity {

    @BindView(R.id.jcameraview)
    JCameraView mJCameraView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_camera;
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void init() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void initView() {
        //Set video save path
        mJCameraView.setSaveVideoPath(
            Environment.getExternalStorageDirectory().getPath() + File.separator
                + "videoeditor" + File.separator + "small_video");
        mJCameraView.setMinDuration(3000); //Set the minimum recording duration
        mJCameraView.setDuration(10000); //Set the maximum recording duration
        mJCameraView.setFeatures(JCameraView.BUTTON_STATE_ONLY_RECORDER);
        mJCameraView.setTip("Long press to shoot , 3~10 seconds");
        mJCameraView.setRecordShortTip("\n" + "Recording time 3~10 seconds");
        mJCameraView.setMediaQuality(JCameraView.MEDIA_QUALITY_MIDDLE);
        mJCameraView.setErrorLisenter(new ErrorListener() {
            @Override
            public void onError() {
                //Error monitoring
                Log.d("CJT", "camera error");
                Intent intent = new Intent();
                setResult(103, intent);
                finish();
            }

            @Override
            public void AudioPermissionError() {
                Toast.makeText(VideoCameraActivity.this, "Can you give recording permission?", Toast.LENGTH_SHORT).show();
            }
        });
        //JCameraView monitor
        mJCameraView.setJCameraLisenter(new JCameraListener() {
            @Override
            public void captureSuccess(Bitmap bitmap) {
                //Get picture bitmap
//                Log.i("JCameraView", "bitmap = " + bitmap.getWidth());
                String path = FileUtil.saveBitmap("small_video", bitmap);
            }

            @Override
            public void recordSuccess(String url, Bitmap firstFrame) {
                //Get video path
                String path = FileUtil.saveBitmap("small_video", firstFrame);
                //url:/storage/emulated/0/haodiaoyu/small_video/video_1508930416375.mp4, Bitmap:/storage/emulated/0/haodiaoyu/small_video/picture_1508930429832.jpg
                Log.d("CJT", "url:" + url + ", firstFrame:" + path);

                TrimVideoActivity.startActivity(VideoCameraActivity.this, url);
                finish();
            }
        });
        mJCameraView.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        mJCameraView.setRightClickListener(new ClickListener() {
            @Override
            public void onClick() {
                Toast.makeText(VideoCameraActivity.this, "Right", Toast.LENGTH_SHORT).show();
            }
        });
        mJCameraView.setRecordStateListener(new RecordStateListener() {
            @Override
            public void recordStart() {

            }

            @Override
            public void recordEnd(long time) {
                Log.e("Recording status callba", "Recording time：" + time);
            }

            @Override
            public void recordCancel() {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //full-screen display
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mJCameraView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mJCameraView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
