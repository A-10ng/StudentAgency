package com.example.studentagency.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.studentagency.R;

public class PreviewImageActivity extends BaseActivity {

    private ImageView iv_picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_image);

        iv_picture = findViewById(R.id.iv_picture);

        Glide.with(this)
                .load(getIntent().getStringExtra("filePath"))
                .error(R.drawable.placeholder_pic)
                .into(iv_picture);
    }
}
