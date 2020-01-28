package com.example.studentagency.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.studentagency.R;
import com.example.studentagency.bean.IndentBean;
import com.example.studentagency.mvp.presenter.ClassifyActivityBasePresenter;
import com.example.studentagency.mvp.view.ClassifyActivityBaseView;
import com.example.studentagency.ui.adapter.ClassifyActivityRecyclerviewAdapter;
import com.example.studentagency.ui.adapter.HomeFragmentRecyclerviewAdapter;
import com.example.studentagency.ui.widget.MyRecyclerView;
import com.example.studentagency.ui.widget.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class ClassifyActivity extends BaseActivity implements ClassifyActivityBaseView {

    private static final String TAG = "ClassifyActivity";
    private static final int TYPE_SHOPPING = 0;
    private static final int TYPE_DELIVERY = 1;
    private static final int TYPE_OTHERS = 2;
    private static final int PAGE_NUM = 10;
    //记录用户点击的是什么类型，0为代购，1为代拿快递，2为其他代办
    private int type = 0;
    //网络请求
    private ClassifyActivityBasePresenter presenter;
    //Recyclerview
    private List<IndentBean> allIndentDataList = new ArrayList<>();//所有的订单数据
    private RecyclerView mRecyclerView;
    private int dataList_position;
    private ClassifyActivityRecyclerviewAdapter mAdapter;
    //smartrefreshlayout
    private SmartRefreshLayout mSmartRefreshLayout;

    //加载中的占位图
    private ImageView layout_loading;

    //标题栏
    private TitleBar titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify);

        //获取homeFragment传过来的类型type
        getType();

        findAllViews();

        initTitleBar();

        initRecyclerView();

        initSmartReFreshLayout();
    }

    private void initTitleBar() {
        if (type == TYPE_SHOPPING){
            titleBar.setTitle_name("代购");
        } else if (type == TYPE_DELIVERY) {
            titleBar.setTitle_name("代拿快递");
        }else {
            titleBar.setTitle_name("其他代办");
        }
    }

    private void getType() {
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);
        Log.i(TAG, "getType: type>>>>>" + type);
    }

    private void findAllViews() {
        layout_loading = findViewById(R.id.layout_loading);
        titleBar = findViewById(R.id.titleBar);
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.mRecyclerView);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);

        mAdapter = new ClassifyActivityRecyclerviewAdapter(new ArrayList<Object>());
        mAdapter.setIndentItemClickListener(new ClassifyActivityRecyclerviewAdapter.IndentItemClickListenr() {
            @Override
            public void onIndentItemClick(int indentId, int position) {
                Log.i(TAG, "onIndentItemClick: indentId>>>>>" + indentId + " position>>>>>" + position);
                Intent intent = new Intent(ClassifyActivity.this, IndentActivity.class);
                intent.putExtra("indentId", indentId);
                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    private void initSmartReFreshLayout() {
        mSmartRefreshLayout = findViewById(R.id.mSmartRefreshLayout);
        mSmartRefreshLayout.autoRefresh();
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Log.i(TAG, "onRefresh");

                refreshData();
            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Log.i(TAG, "onLoadMore");

                loadMoreData();
            }
        });
    }

    private void refreshData() {
        layout_loading.setVisibility(View.VISIBLE);

        allIndentDataList.clear();
        dataList_position = 0;

        Log.i(TAG, "refreshData: type>>>>>" + type);
        presenter = new ClassifyActivityBasePresenter(this);
        presenter.getIndentByType(type);
    }

    private void loadMoreData() {
        if (dataList_position >= allIndentDataList.size()){
            //表明数据已经全部加载完，不能再加载更多
        }else {
            List<IndentBean> tempList = new ArrayList<>();
            //表明数据刚好或者不够再加载10个订单
            if (dataList_position + PAGE_NUM >= allIndentDataList.size()){
                tempList.addAll(allIndentDataList.subList(dataList_position,allIndentDataList.size()));
                dataList_position = allIndentDataList.size();
            }else {
                tempList.addAll(allIndentDataList.subList(dataList_position,dataList_position+PAGE_NUM));
                dataList_position += PAGE_NUM;
            }
            mAdapter.loadMoreData(tempList);
        }

        mSmartRefreshLayout.finishLoadMore();
    }

    @Override
    public void getIndentByTypeSuccess(List<IndentBean> indentBeanList) {
        Log.i(TAG, "getIndentByTypeSuccess");
        layout_loading.setVisibility(View.GONE);

        if (indentBeanList.isEmpty()) {
            mAdapter.setIndentData("暂无更多订单");
        } else {
            allIndentDataList = indentBeanList;
            mAdapter.setIndentData(getRuledNumIndent());
        }

        mSmartRefreshLayout.finishRefresh();
    }

    private List<IndentBean> getRuledNumIndent() {
        List<IndentBean> tempList = new ArrayList<>();
        if (allIndentDataList.size() > PAGE_NUM) {
            tempList.addAll(allIndentDataList.subList(0, PAGE_NUM));
            dataList_position = PAGE_NUM;
        } else {
            tempList.addAll(allIndentDataList);
            dataList_position = allIndentDataList.size();
        }
        return tempList;
    }


    @Override
    public void getIndentByTypeFail() {
        Log.i(TAG, "getIndentByTypeFail");
        layout_loading.setVisibility(View.GONE);

        mAdapter.setIndentData("加载失败");

        mSmartRefreshLayout.finishRefresh();
    }
}
