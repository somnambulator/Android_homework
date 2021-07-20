package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Switch;
import android.widget.VideoView;

public class VideoDetailActivity extends AppCompatActivity {
    String mockUrl = "https://stream7.iqilu.com/10339/upload_transcode/202002/18/20200218114723HDu3hhxqIT.mp4";
    VideoView videoView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        videoView = findViewById(R.id.vv_detail);
        videoView.setVideoURI(Uri.parse(mockUrl));
        videoView.setMediaController(new MediaController(this));
        videoView.requestFocus();
        videoView.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}