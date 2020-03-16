package com.example.studentagency.ui.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.studentagency.R;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/04
 * desc:
 */
public class AddAddressPopupWindow extends PopupWindow implements View.OnClickListener {

    private static final String TAG = "AddAddressPopupWindow";
    private Context context;
    private ImageView iv_cancel,iv_ensure;
    private EditText et_tag,et_address;
    private LinearLayout layout_tag;
    private SlideButton slideButton;
    private boolean isChecked = false;
    private View rootView;
    private ClickItemListener clickItemListener;

    public void setClickItemListener(ClickItemListener clickItemListener) {
        this.clickItemListener = clickItemListener;
    }

    public AddAddressPopupWindow(Context context) {
        super(context);
        this.context = context;
        rootView = LayoutInflater.from(context).inflate(R.layout.popupwindow_add_address,null);

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
        iv_cancel = rootView.findViewById(R.id.iv_cancel);
        iv_ensure = rootView.findViewById(R.id.iv_ensure);
        iv_cancel.setOnClickListener(this);
        iv_ensure.setOnClickListener(this);

        et_tag = rootView.findViewById(R.id.et_tag);
        et_address = rootView.findViewById(R.id.et_address);
        layout_tag = rootView.findViewById(R.id.layout_tag);

        slideButton = rootView.findViewById(R.id.slideButton);
        slideButton.setOnCheckedListener(new SlideButton.SlideButtonOnCheckedListener() {
            @Override
            public void onCheckedChangeListener(boolean isChecked) {
                AddAddressPopupWindow.this.isChecked = isChecked;
                if (isChecked){
                    layout_tag.setVisibility(View.GONE);
                }else {
                    layout_tag.setVisibility(View.VISIBLE);
                }
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
                    String tag = et_tag.getText().toString().trim();
                    String address = et_address.getText().toString().trim();

                    if (isChecked){
                        tag = "默认";
                        if (TextUtils.isEmpty(address)) {
                            Toast toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
                            toast.setText("请填写收货地址！");
                            toast.show();
                        }else {
                            dismiss();

                            Log.i(TAG, "onClick: tag>>>>>"+tag+" address>>>>>"+address);
                            clickItemListener.clickItem(tag,address);
                        }
                    }else {
                        if (TextUtils.isEmpty(address) || TextUtils.isEmpty(tag)) {
                            Toast toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
                            toast.setText("请填写相关信息！");
                            toast.show();
                        }else {
                            dismiss();

                            Log.i(TAG, "onClick: tag>>>>>"+tag+" address>>>>>"+address);
                            clickItemListener.clickItem(tag,address);
                        }
                    }
                }
                break;
        }
    }

    public interface ClickItemListener{
        void clickItem(String tag,String address);
    }
}
