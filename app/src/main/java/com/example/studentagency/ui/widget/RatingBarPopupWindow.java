package com.example.studentagency.ui.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RatingBar;

import com.example.studentagency.R;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/04
 * desc:
 */
public class RatingBarPopupWindow extends PopupWindow implements View.OnClickListener {

    private ImageView iv_cancel,iv_ensure;
    private RatingBar ratingBar;
    private View rootView;
    private ClickItemListener clickItemListener;
    private int increasement;

    public void setClickItemListener(ClickItemListener clickItemListener) {
        this.clickItemListener = clickItemListener;
    }

    public RatingBarPopupWindow(Context context) {
        super(context);
        rootView = LayoutInflater.from(context).inflate(R.layout.popupwindow_ratingbar,null);

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

//        //点击按钮上方则取消弹窗
//        rootView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int height = rootView.findViewById(R.id.root_layout).getTop();
//                int y = (int) event.getY();
//                if (event.getAction() == MotionEvent.ACTION_UP){
//                    if (y < height){
//                        dismiss();
//                    }
//                }
//                return true;
//            }
//        });
    }

    private void initViews() {
        iv_cancel = rootView.findViewById(R.id.iv_cancel);
        iv_ensure = rootView.findViewById(R.id.iv_ensure);
        iv_cancel.setOnClickListener(this);
        iv_ensure.setOnClickListener(this);

        ratingBar = rootView.findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                increasement = (int)rating;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_cancel:
                dismiss();
                break;
            case R.id.iv_ensure:
                if (clickItemListener != null){
                    if (increasement == 1){
                        clickItemListener.clickItem(-3);
                    }else if (increasement == 2){
                        clickItemListener.clickItem(-1);
                    }else if (increasement == 3){
                        clickItemListener.clickItem(1);
                    }else if (increasement == 4){
                        clickItemListener.clickItem(3);
                    }else {
                        clickItemListener.clickItem(5);
                    }
                }
                break;
        }
    }

    public interface ClickItemListener{
        void clickItem(int increasement);
    }
}
