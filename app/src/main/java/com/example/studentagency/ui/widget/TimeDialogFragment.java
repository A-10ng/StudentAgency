package com.example.studentagency.ui.widget;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentagency.R;
import com.example.studentagency.ui.adapter.PublishTimeRecycleLeftAdapter;
import com.example.studentagency.ui.adapter.PublishTimeRecycleRightAdapter;
import com.example.studentagency.utils.Utils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/01/13
 * desc:
 */
public class TimeDialogFragment extends DialogFragment {

    private static final String TAG = "TimeDialogFragment";
    private View rootView;
    private Window window;
    private String leftTime = "今天", rightTime;
    private Context context;
    private OnGetPickedTimeListener getPickedTimeListener;

    //确认按钮,取消按钮
    private TextView tv_ensure;
    private LinearLayout linearlayout_cancel;
    private boolean hadChooseTime = false;

    //recyclerview
    private RecyclerView recycle_left, recycle_right;
    private List<String> leftRecycleDataList;
    private List<List<String>> rightRecycleDataList;
    private PublishTimeRecycleLeftAdapter adapter_left;
    private PublishTimeRecycleRightAdapter adapter_right;

    public TimeDialogFragment(List<String> leftRecycleDataList, List<List<String>> rightRecycleDataList, Context context) {
        this.leftRecycleDataList = leftRecycleDataList;
        this.rightRecycleDataList = rightRecycleDataList;
        this.context = context;
    }

    public void setGetPickedTimeListener(OnGetPickedTimeListener getPickedTimeListener) {
        this.getPickedTimeListener = getPickedTimeListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //去掉默认的标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        rootView = inflater.inflate(R.layout.dialogfragment_time, null);

        tv_ensure = rootView.findViewById(R.id.tv_ensure);
        tv_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hadChooseTime){
                    String finalTime = leftTime+" "+rightTime;
                    Log.i(TAG, "onClick: 最终选择的送达时间为>>>>>"+finalTime);
                    if (null != getPickedTimeListener){
                        getPickedTimeListener.getPickedTime(finalTime);
                    }
                    dismiss();
                }else {
                    Toast toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
                    toast.setText("请在当前页面选择时间！");
                    toast.show();
                }
            }
        });

        linearlayout_cancel = rootView.findViewById(R.id.linearlayout_cancel);
        linearlayout_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        initLeftRecyclerview(rootView);
        initRightRecyclerview(rootView);
        return rootView;
    }

    private void initLeftRecyclerview(View rootView) {
        recycle_left = rootView.findViewById(R.id.recycle_left);

        LinearLayoutManager manager = new LinearLayoutManager(context);
        recycle_left.setLayoutManager(manager);

        adapter_left = new PublishTimeRecycleLeftAdapter(leftRecycleDataList,context);
        adapter_left.setOnClickLeftItemListener(new PublishTimeRecycleLeftAdapter.OnClickLeftItemListener() {
            @Override
            public void clickLeftItem(String leftTime, int position) {
                Log.i(TAG, "clickLeftItem: 选择的时间为>>>>>" + leftTime + " 位置是>>>>>" + position);
                TimeDialogFragment.this.leftTime = leftTime;

                if (position == 0) {
                    setRightRecycleAdapter(0);
                } else {
                    setRightRecycleAdapter(1);
                }
            }
        });
        recycle_left.setAdapter(adapter_left);
    }

    private void initRightRecyclerview(View rootView) {
        recycle_right = rootView.findViewById(R.id.recycle_right);

        LinearLayoutManager manager = new LinearLayoutManager(context);
        recycle_right.setLayoutManager(manager);

        setRightRecycleAdapter(0);
    }

    private void setRightRecycleAdapter(int position) {
        hadChooseTime = false;
        adapter_right = new PublishTimeRecycleRightAdapter(rightRecycleDataList.get(position));
        adapter_right.setOnClickRightItemListener(new PublishTimeRecycleRightAdapter.OnClickRightItemListener() {
            @Override
            public void clickRightItem(String rightTime, int position) {
                Log.i(TAG, "clickRightItem: 选择的时间为>>>>>" + rightTime + " 位置是>>>>>" + position);
                TimeDialogFragment.this.rightTime = rightTime;
                hadChooseTime = true;
            }
        });
        recycle_right.setAdapter(adapter_right);
        recycle_right.smoothScrollToPosition(0);
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
        params.gravity = Gravity.BOTTOM;

        //如果不设置宽度，那么即使在布局中设置了宽度为match_parent也不会起作用
        params.width = getResources().getDisplayMetrics().widthPixels;
        params.height = Utils.dp2px(context,300);
        window.setAttributes(params);

        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
    }

    public interface OnGetPickedTimeListener{
        void getPickedTime(String finalTime);
    }
}
