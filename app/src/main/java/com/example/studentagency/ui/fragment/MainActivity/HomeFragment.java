package com.example.studentagency.ui.fragment.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.studentagency.R;
import com.example.studentagency.bean.ClassifyBean;
import com.example.studentagency.bean.IndentBean;
import com.example.studentagency.bean.NewsBean;
import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.mvp.presenter.HomeFragmentBasePresenter;
import com.example.studentagency.mvp.view.HomeFragmentBaseView;
import com.example.studentagency.ui.activity.ClassifyActivity;
import com.example.studentagency.ui.activity.IndentActivity;
import com.example.studentagency.ui.activity.PublishActivity;
import com.example.studentagency.ui.activity.WebviewActivity;
import com.example.studentagency.ui.adapter.HomeFragmentRecyclerviewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/11/02
 * desc:
 */
public class HomeFragment extends Fragment implements HomeFragmentBaseView {

    private static final String TAG = "HomeFragment";
    private HomeFragmentBasePresenter presenter;

    //加载时的布局
    private ImageView iv_layout_loading;

    //根视图
    private View root;

    //轮播图
    private List<NewsBean> defaultBannerList = new ArrayList<>();

    //recyclerview
    private List<Object> originalDataList = new ArrayList<>();
    private List<Object> AllIndentDataList = new ArrayList<>();//获取到的总数据
    private List<Object> ruledDataList = new ArrayList<>();//用于上拉加载更多
    private RecyclerView myRecyclerView;
    private HomeFragmentRecyclerviewAdapter adapter;
    private int FIRST_PAGE_NUM = 7;//第一页之所以是12是因为加上了轮播图和分类
    private int PAGE_NUM = 5;
    private int dataListPosition;//读到当前dataList的位置

    //SmartRefreshLayout
    private SmartRefreshLayout smartRefreshLayout;

    //悬浮按钮
    private FloatingActionButton floatingButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);

        findAllViews();

        //加载默认的轮播图进defaultBannerList
        loadDefaultBannerData();

        initFloatingButton();

        initRecyclerview();

        initSmartRefreshLayout();

        refreshData();

        return root;
    }

    private void findAllViews() {
        iv_layout_loading = root.findViewById(R.id.layout_loading);
    }

    private void loadDefaultBannerData() {
        NewsBean newsBean = new NewsBean();
        newsBean.setNewsPic("about:blank");
        newsBean.setTitle("加载失败");
        newsBean.setNewsUrl("www.longsh1z.to");

        for (int i = 0; i < 3; i++) {
            defaultBannerList.add(newsBean);
        }
    }

    private void initFloatingButton() {
        floatingButton = root.findViewById(R.id.floatingButton);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "你点击了floatingButton");
                startActivity(new Intent(getContext(), PublishActivity.class));
            }
        });
    }

    private void initRecyclerview() {
        myRecyclerView = root.findViewById(R.id.rc_indent);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        myRecyclerView.setLayoutManager(manager);

        initOriginalData();

        setRecyclerViewAdapter();
    }

    private void initSmartRefreshLayout() {
        smartRefreshLayout = root.findViewById(R.id.smartRefreshLayout);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Log.i(TAG, "onRefresh");

                //加载数据
                refreshData();
            }
        });

        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //加载更多数据
                loadMoreData();
                smartRefreshLayout.finishLoadMore();
            }
        });
    }

    private void refreshData() {
        iv_layout_loading.setVisibility(View.VISIBLE);

        //先清空dataList里面的数据，防止重复
        AllIndentDataList.clear();
        dataListPosition = 0;

        presenter = new HomeFragmentBasePresenter(this);
        presenter.getBannerData();
        presenter.getIndentData();
    }

    private void initOriginalData() {
        //添加轮播图的数据
        // 这里不需要添加其他数据，只需要添加一个空的BannerViewHolder即可，adapter内部已经实现
        originalDataList.add(new NewsBean());

        //添加分类的数据
        ClassifyBean classifyBean = new ClassifyBean();
        classifyBean.setShoppingPic(R.drawable.shopping);
        classifyBean.setShoppingTxt("代购");
        classifyBean.setDeliveryPic(R.drawable.deliverly);
        classifyBean.setDeliveryTxt("代拿快递");
        classifyBean.setOthersPic(R.drawable.others);
        classifyBean.setOthersTxt("其他代办");
        originalDataList.add(classifyBean);

        //添加订单的数据，这里先用加载失败的item
        originalDataList.add("加载失败");
    }

    private void setRecyclerViewAdapter() {
        adapter = new HomeFragmentRecyclerviewAdapter(originalDataList, defaultBannerList,
                new HomeFragmentRecyclerviewAdapter.onBannerItemClickListener() {
                    @Override
                    public void onBannerItemClick(View itemView, NewsBean newsBean) {
                        Log.i(TAG, "onBannerItemClick: 你点击了标题为" + newsBean.getTitle() + "的view");

                        //跳转到WebviewActivity
                        Intent webViewIntent = new Intent(getContext(), WebviewActivity.class);
                        webViewIntent.putExtra("url", newsBean.getNewsUrl());
                        startActivity(webViewIntent);
                    }
                },
                new HomeFragmentRecyclerviewAdapter.onClassifyItemClickListener() {
                    @Override
                    public void onClassifyItemClick(int type) {
                        //0代表点击的是代购
                        if (type == 0) {
                            Log.i(TAG, "onClassifyItemClick: 你点击了代购");
                            Intent intent = new Intent(getActivity(), ClassifyActivity.class);
                            intent.putExtra("type", 0);
                            startActivity(intent);
                        }
                        //1代表点击的是代拿快递
                        else if (type == 1) {
                            Log.i(TAG, "onClassifyItemClick: 你点击了代拿快递");
                            Intent intent = new Intent(getActivity(), ClassifyActivity.class);
                            intent.putExtra("type", 1);
                            startActivity(intent);
                        }
                        //2代表点击的是其他代办
                        else {
                            Log.i(TAG, "onClassifyItemClick: 你点击了其他代办");
                            Intent intent = new Intent(getActivity(), ClassifyActivity.class);
                            intent.putExtra("type", 2);
                            startActivity(intent);
                        }
                    }
                },
                new HomeFragmentRecyclerviewAdapter.onIndentItemClickListener() {
                    @Override
                    public void onIndentItemClick(int indentId, int position,int publishId) {
                        Log.i(TAG, "onIndentItemClick: 你点击了位置为" + position + "的订单");
                        Log.i(TAG, "onIndentItemClick: 你点击了indentId为" + indentId + "的订单");
                        Intent intent = new Intent(getActivity(), IndentActivity.class);
                        intent.putExtra("indentId", indentId);
                        intent.putExtra("publishId", publishId);
                        startActivity(intent);
                    }
                });
        myRecyclerView.setAdapter(adapter);

        Log.i(TAG, "setRecyclerViewAdapter: originalDataList.size()>>>>>" + originalDataList.size());
    }

    private void loadMoreData() {
        //如果数据没有加载完，则可以再加载更多
        Log.i(TAG, "loadMoreData: originalDataList.size()>>>>>" + AllIndentDataList.size());
        Log.i(TAG, "loadMoreData: dataListPosition>>>>>" + dataListPosition);

        if (dataListPosition < AllIndentDataList.size()) {
            List<Object> tempList = new ArrayList<>();
            if (dataListPosition + PAGE_NUM >= AllIndentDataList.size()) {
                tempList.addAll(AllIndentDataList.subList(dataListPosition, AllIndentDataList.size()));
                dataListPosition = AllIndentDataList.size();
            } else {
                tempList.addAll(AllIndentDataList.subList(dataListPosition, dataListPosition + PAGE_NUM));
                dataListPosition = dataListPosition + PAGE_NUM;
            }
            adapter.addData(tempList);
        }
    }

    @Override
    public void getBannerDataSuccess(ResponseBean responseBean) {
        Log.i(TAG, "connect getBannerDataSuccess: 获取成功 originalDataList.size()>>>>>" + originalDataList.size());

        Gson gson = new Gson();
        List<NewsBean> newsBeanList = gson.fromJson(gson.toJson(responseBean.getData()),
                new TypeToken<List<NewsBean>>() {
                }.getType());

        StringBuilder stringBuilder1 = new StringBuilder();
        for (int i = 0; i < newsBeanList.size(); i++) {
            stringBuilder1.append(newsBeanList.get(i).getNewsPic() + "/ \n");
        }
        Log.i(TAG, "getBannerDataSuccess: newsBeanList>>>>>" + stringBuilder1.toString());
        adapter.setBannerData(newsBeanList);
    }

    @Override
    public void getBannerDataFail() {
        Log.i(TAG, "connect getBannerDataFail: 获取失败 originalDataList.size()>>>>>" + originalDataList.size());
        adapter.setBannerData(defaultBannerList);
    }

    @Override
    public void getIndentsDataSuccess(ResponseBean responseBean) {
        Log.i(TAG, "connect getIndentsDataSuccess: originalDataList.size()>>>>>" + originalDataList.size());

        Gson gson = new Gson();
        List<IndentBean> indentBeanList = gson.fromJson(gson.toJson(responseBean.getData()),
                new TypeToken<List<IndentBean>>() {
                }.getType());

        AllIndentDataList.addAll(indentBeanList);

        setRuleDataList();

        List<Object> temp = new ArrayList<>();
        temp.addAll(ruledDataList);
        adapter.setIndentData(temp);

        iv_layout_loading.setVisibility(View.GONE);
        smartRefreshLayout.finishRefresh();
    }

    private void setRuleDataList() {
        Log.i(TAG, "before setRuleDataList: originalDataList.size()>>>>>" + AllIndentDataList.size());

        ruledDataList.clear();
        //如果dataList的容量大于10
        if (AllIndentDataList.size() > PAGE_NUM) {
            ruledDataList.addAll(AllIndentDataList.subList(0, PAGE_NUM));
            dataListPosition = PAGE_NUM;
        }
        //否则加载全部
        else {
            ruledDataList.addAll(AllIndentDataList.subList(0, AllIndentDataList.size()));
            dataListPosition = AllIndentDataList.size();
        }

        Log.i(TAG, "after setRuleDataList: originalDataList.size()>>>>>" + originalDataList.size());
    }

    @Override
    public void getIndentsDataFail() {
        Log.i(TAG, "getIndentsDataFail: 获取失败");
        Log.i(TAG, "connect getIndentsDataFail: originalDataList.size()>>>>>" + originalDataList.size());

        List<Object> temp = new ArrayList<>();
        temp.add("加载失败");
        adapter.setIndentData(temp);

        iv_layout_loading.setVisibility(View.GONE);
        smartRefreshLayout.finishRefresh();
    }
}
