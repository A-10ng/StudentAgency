package com.example.studentagency.ui.fragment.CreditScoreRecord;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studentagency.R;
import com.example.studentagency.bean.CreditBean;
import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.mvp.presenter.InputRecordFragmentBasePresenter;
import com.example.studentagency.mvp.view.InputRecordFragmentBaseView;
import com.example.studentagency.ui.adapter.CreditRecordRecyclerviewAdapter;
import com.example.studentagency.ui.widget.MyRecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/18
 * desc:
 */

public class InputRecordFragment extends Fragment implements InputRecordFragmentBaseView {

    private static final String TAG = "InputRecordFragment";
    private View root_layout;
    private InputRecordFragmentBasePresenter presenter = new InputRecordFragmentBasePresenter(this);

    private MyRecyclerView recyclerview;
    private CreditRecordRecyclerviewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root_layout = inflater.inflate(R.layout.fragment_input_record,container,false);

        initRecyclerView();

        presenter.getCreditInputRecord();

        return root_layout;
    }

    private void initRecyclerView() {
        recyclerview = root_layout.findViewById(R.id.recyclerview);

        List<Object> dataList = new ArrayList<>();
        dataList.add("暂无数据");
        adapter = new CreditRecordRecyclerviewAdapter(dataList);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(manager);

        recyclerview.setAdapter(adapter);
    }

    @Override
    public void getCreditInputRecordSuccess(ResponseBean responseBean) {
        if (responseBean.getCode() == 200){
            Gson gson = new Gson();
            List<CreditBean> creditBeans = gson.fromJson(
                    gson.toJson(responseBean.getData()),
                    new TypeToken<List<CreditBean>>() {}.getType());

            int number = creditBeans.size();
            Log.i(TAG, "getCreditRecordSuccess: creditBeans.size()>>>>>"+number);

            List<Object> dataList = new ArrayList<>();
            if (number == 0){
                dataList.add("暂无数据");
                adapter.update(dataList);
            }
            else {
                dataList.addAll(creditBeans);
                adapter.update(dataList);
            }
        }else {
            List<Object> dataList = new ArrayList<>();
            dataList.add("获取失败");
            adapter.update(dataList);
        }
    }

    @Override
    public void getCreditInputRecordFail() {
        Log.i(TAG, "getCreditRecordFail: ");

        List<Object> dataList = new ArrayList<>();
        dataList.add("获取失败");
        adapter.update(dataList);
    }
}
