package com.example.studentagency.ui.widget;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.studentagency.R;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/04
 * desc:
 */
public class ChoosePicPopupWindow extends PopupWindow implements View.OnClickListener {

    private Button btn_takePhoto,btn_pickPhoto,btn_cancel;
    private View rootView;
    private ClickItemListener clickItemListener;

    public void setClickItemListener(ClickItemListener clickItemListener) {
        this.clickItemListener = clickItemListener;
    }

    public ChoosePicPopupWindow(Context context) {
        super(context);
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_choose_pic_popupwindow,null);

        initViews();

        //设置布局视图
        setContentView(rootView);

        //设置宽高
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        //设置可点击
        setFocusable(true);

        //设置窗体动画效果
        setAnimationStyle(R.style.choosePicAnimation);

        //设置弹出窗口为半透明
        setBackgroundDrawable(new ColorDrawable(0x80000000));

        //点击按钮上方则取消弹窗
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = rootView.findViewById(R.id.root_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP){
                    if (y < height){
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    private void initViews() {
        btn_takePhoto = rootView.findViewById(R.id.btn_takePhoto);
        btn_pickPhoto = rootView.findViewById(R.id.btn_pickPhoto);
        btn_cancel = rootView.findViewById(R.id.btn_cancel);

        btn_takePhoto.setOnClickListener(this);
        btn_pickPhoto.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_takePhoto:
                if (clickItemListener != null){
                    clickItemListener.clickItem(0);
                }
                break;
            case R.id.btn_pickPhoto:
                if (clickItemListener != null){
                    clickItemListener.clickItem(1);
                }
                break;
            case R.id.btn_cancel:
                if (clickItemListener != null){
                    clickItemListener.clickItem(2);
                }
                break;
        }
    }

    public interface ClickItemListener{
        void clickItem(int position);
    }
}
