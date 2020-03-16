package com.example.studentagency.ui.fragment.StudentVerifyFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.studentagency.R;
import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.mvp.presenter.VerifyingFragmentBasePresenter;
import com.example.studentagency.mvp.view.VerifyingFragmentBaseView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/17
 * desc:
 */
public class VerifyingFragment extends Fragment implements VerifyingFragmentBaseView {

    private static final String TAG = "VerifyingFragment";
    private VerifyingFragmentBasePresenter presenter = new VerifyingFragmentBasePresenter(this);
    private View root;
    private ImageView iv_verifyPic;
    private TextView tv_checkPic;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.studentverify_fragment_verifying,container,false);

        iv_verifyPic = root.findViewById(R.id.iv_verifyPic);
        tv_checkPic = root.findViewById(R.id.tv_checkPic);
        tv_checkPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_verifyPic.setVisibility(iv_verifyPic.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });

        presenter.getVerifyPic();

        return root;
    }

    @Override
    public void getVerifyPicSuccess(ResponseBean responseBean) {
        String picPath = responseBean.getData().toString();
        Log.i(TAG, "getVerifyPicSuccess: picPath>>>>>"+ picPath);

        Glide.with(this)
                .load(picPath)
                .error(R.drawable.placeholder_pic)
                .into(iv_verifyPic);
    }

    @Override
    public void getVerifyPicFail() {
        Log.i(TAG, "getVerifyPicFail:");

        Glide.with(this)
                .load(R.drawable.placeholder_pic)
                .into(iv_verifyPic);
    }
}
