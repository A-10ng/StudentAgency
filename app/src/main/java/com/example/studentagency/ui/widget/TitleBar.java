package com.example.studentagency.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.studentagency.R;
import com.example.studentagency.ui.activity.BaseActivity;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/10/15
 * desc:
 */
public class TitleBar extends RelativeLayout {

    private boolean iv_return_visibility;
    private String title_name;
    private TextView tv_title;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //自定义属性值的获取
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        iv_return_visibility = typedArray.getBoolean(R.styleable.TitleBar_iv_return_visibility, true);
        title_name = typedArray.getString(R.styleable.TitleBar_title_name);
        typedArray.recycle();

        init(context);
    }

    private void init(final Context context) {
        LayoutInflater.from(context).inflate(R.layout.titlebar, this);

        //根据用户的选择来设置返回键的可见性
        ImageView iv_return = findViewById(R.id.iv_return);
        iv_return.setVisibility(iv_return_visibility ? VISIBLE : GONE);

        //根据用户的选择来设置标题
        tv_title = findViewById(R.id.tv_title);
        if (TextUtils.isEmpty(title_name)) {
            tv_title.setText("代办");
        } else {
            tv_title.setText(title_name);
        }

        iv_return.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });
    }

    public void setTitle_name(String title_name) {
        tv_title.setText(title_name);
    }
}
