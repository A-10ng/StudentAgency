package com.example.studentagency.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.example.studentagency.R;

import androidx.core.app.ActivityCompat;
import chuangyuan.ycj.videolibrary.video.ExoUserPlayer;
import chuangyuan.ycj.videolibrary.widget.VideoPlayerView;

public class VideoPlayerActivity extends BaseActivity {

    private static final String TAG = "VideoPlayerActivity";
    String[] test;
    private ExoUserPlayer exoPlayerManager;
    private VideoPlayerView videoPlayerView;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        String url = getIntent().getStringExtra("path");

        videoPlayerView = findViewById(R.id.exo_play_context_id);
        exoPlayerManager = new ExoUserPlayer(this, videoPlayerView);

        //设置视频标题
        videoPlayerView.setTitle("视频标题");
        exoPlayerManager.setPlayUri(url);
        exoPlayerManager.startPlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        exoPlayerManager.onDestroy();

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        exoPlayerManager.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        exoPlayerManager.onResume();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //  exoPlayerManager.onConfigurationChanged(newConfig);//横竖屏切换
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (exoPlayerManager.onBackPressed()) {
            ActivityCompat.finishAfterTransition(this);
            exoPlayerManager.onDestroy();
        }
    }
}