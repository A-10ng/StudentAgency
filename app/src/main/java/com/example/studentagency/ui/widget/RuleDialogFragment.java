package com.example.studentagency.ui.widget;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.studentagency.R;
import com.example.studentagency.Utils.Utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/18
 * desc:
 */
public class RuleDialogFragment extends DialogFragment {

    private View rootView;
    private ImageView iv_cancel;
    private Window window;
    private Context context;

    public RuleDialogFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //去掉默认的标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        rootView = inflater.inflate(R.layout.dialogfragment_rule, null);

        initViews();

        return rootView;
    }

    private void initViews() {
        iv_cancel = rootView.findViewById(R.id.iv_cancel);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        /**
         * 下面这些设置必须在此方法onStart中才有效
         */
        window = getDialog().getWindow();

        //如果不设置这行代码，那么弹框就会与四边都有一定的距离
        window.setBackgroundDrawableResource(R.color.transparent);

        //设置进出动画
        window.setWindowAnimations(R.style.timeDialogFragment);

        //设置该view显示在底部
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;


        //如果不设置宽度，那么即使在布局中设置了宽度为match_parent也不会起作用
        params.width = Utils.dp2px(context,380);
        params.height = Utils.dp2px(context,400);
        window.setAttributes(params);

        getDialog().setCancelable(true);
        getDialog().setCanceledOnTouchOutside(true);
    }
}
