package com.example.studentagency.ui.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.studentagency.R;
import com.example.studentagency.bean.AddressBean;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/04
 * desc:
 */
public class EditAddressPopupWindow extends PopupWindow implements View.OnClickListener {

    private static final String TAG = "AddAddressPopupWindow";
    private Context context;
    private ImageView iv_cancel,iv_ensure;
    private Button btn_save,btn_delete;
    private EditText et_tag,et_address;
    private LinearLayout layout_tag;
    private SlideButton slideButton;
    private boolean isChecked = false;
    private View rootView;
    private ClickItemListener clickItemListener;
    private AddressBean addressBean;
    private String originalTag,originalAddress;

    public void setClickItemListener(ClickItemListener clickItemListener) {
        this.clickItemListener = clickItemListener;
    }

    public EditAddressPopupWindow(Context context, AddressBean addressBean) {
        super(context);
        this.context = context;
        this.addressBean = addressBean;

        originalTag = addressBean.getTag();
        originalAddress = addressBean.getAddress();

        rootView = LayoutInflater.from(context).inflate(R.layout.popupwindow_edit_address,null);

        initViews();

        setAddressInfo();

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

    private void setAddressInfo() {
        if (addressBean.getTag().equals("默认")){
            slideButton.setChecked(true);
            layout_tag.setVisibility(View.GONE);
        }else {
            slideButton.setChecked(false);
            et_tag.setText(addressBean.getTag());
            layout_tag.setVisibility(View.VISIBLE);
        }
        et_address.setText(addressBean.getAddress());
    }

    private void initViews() {
        iv_cancel = rootView.findViewById(R.id.iv_cancel);
        iv_ensure = rootView.findViewById(R.id.iv_ensure);
        btn_save = rootView.findViewById(R.id.btn_save);
        btn_delete = rootView.findViewById(R.id.btn_delete);
        iv_cancel.setOnClickListener(this);
        iv_ensure.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_delete.setOnClickListener(this);

        et_tag = rootView.findViewById(R.id.et_tag);
        et_address = rootView.findViewById(R.id.et_address);
        layout_tag = rootView.findViewById(R.id.layout_tag);

        slideButton = rootView.findViewById(R.id.slideButton);
        slideButton.setOnCheckedListener(new SlideButton.SlideButtonOnCheckedListener() {
            @Override
            public void onCheckedChangeListener(boolean isChecked) {
                EditAddressPopupWindow.this.isChecked = isChecked;
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
            case R.id.btn_save:
                if (clickItemListener != null){
                    String tag = et_tag.getText().toString().trim();
                    String address = et_address.getText().toString().trim();

                    if (isChecked){
                        tag = "默认";
                        if (TextUtils.isEmpty(address)) {
                            Toast toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
                            toast.setText("请填写收货地址！");
                            toast.show();
                        }else if (address.equals(originalAddress) && tag.equals(originalTag)){
                            Toast toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
                            toast.setText("您的信息和原来一样，无需保存！");
                            toast.show();
                    }
                        else {
                            dismiss();

                            Log.i(TAG, "onClick: tag>>>>>"+tag+" address>>>>>"+address);
                            clickItemListener.clickItem(201,addressBean.getAddressId(),tag,address);
                        }
                    }else {
                        if (TextUtils.isEmpty(address)) {
                            Toast toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
                            toast.setText("请填写相关信息！");
                            toast.show();
                        }else if (address.equals(originalAddress) && tag.equals(originalTag)){
                            Toast toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
                            toast.setText("您的信息和原来一样，无需保存！");
                            toast.show();
                        }else {
                            dismiss();

                            Log.i(TAG, "onClick: tag>>>>>"+tag+" address>>>>>"+address);
                            clickItemListener.clickItem(201,addressBean.getAddressId(),tag,address);
                        }
                    }
                }
                break;
            case R.id.btn_delete:
                dismiss();

                if (null != clickItemListener){
                    clickItemListener.clickItem(202,addressBean.getAddressId(),"","");
                }
                break;
        }
    }

    public interface ClickItemListener{
        /**
         *
         * @param type 201代表点击的是√和保存,202代表点击的是删除
         * @param addressId
         * @param tag
         * @param address
         */
        void clickItem(int type,int addressId,String tag, String address);
    }
}
