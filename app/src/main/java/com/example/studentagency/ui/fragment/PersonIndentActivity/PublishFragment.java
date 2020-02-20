package com.example.studentagency.ui.fragment.PersonIndentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lemonbubble.LemonBubble;
import com.example.lemonbubble.enums.LemonBubbleLayoutStyle;
import com.example.lemonbubble.enums.LemonBubbleLocationStyle;
import com.example.lemonhello.LemonHello;
import com.example.lemonhello.LemonHelloAction;
import com.example.lemonhello.LemonHelloInfo;
import com.example.lemonhello.LemonHelloView;
import com.example.lemonhello.interfaces.LemonHelloActionDelegate;
import com.example.studentagency.R;
import com.example.studentagency.Utils.DateUtils;
import com.example.studentagency.Utils.FileUtils;
import com.example.studentagency.bean.IndentBean;
import com.example.studentagency.mvp.presenter.PublishFragmentBasePresenter;
import com.example.studentagency.mvp.view.PublishFragmentBaseView;
import com.example.studentagency.ui.activity.IndentActivity;
import com.example.studentagency.ui.activity.MyApp;
import com.example.studentagency.ui.adapter.PersonIndentRecyclerviewAdapter;
import com.example.studentagency.ui.widget.RatingBarPopupWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.File;
import java.io.IOException;
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
 * time：2020/02/19
 * desc:
 * 已发布-->
 * 别人未接(state:200)：取消（100）
 * 接单中(state:201)：  取消（101），确认送达（102）
 * 已完成未评价(202)：  评价（103），删除（104）
 * 已完成已评价(203)：  删除（105）
 * <p>
 * 已接取-->
 * 接单中(204)：  取消（106），确认送达（107）
 * 已完成未评价(205)：  删除（108）
 * 已完成已评价(206)：  删除（109）
 */
public class PublishFragment extends Fragment implements PublishFragmentBaseView {

    private static final String TAG = "PublishFragment";
    private static final int INDENT_PUBLISH = 3;
    private PublishFragmentBasePresenter presenter = new PublishFragmentBasePresenter(this);
    private View viewRoot;

    //recyclerview
    private RecyclerView mRecyclerView;
    private PersonIndentRecyclerviewAdapter adapter;
    private List<Object> AllIndentDataList = new ArrayList<>();//获取到的总数据
    private int PAGE_NUM = 5;
    private int dataListPosition;//读到当前AllIndentDataList的位置
    private int clickedPosition;//recyclerview的当前点击位置

    //smartRefreshLayout
    private SmartRefreshLayout smartRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.fragment_publish, container, false);

        initViews();

        initSmartRefreshLayout();

        initRecyclerView();

        //发送请求
        presenter.getPublishIndents();

        return viewRoot;
    }

    private void initViews() {
        mRecyclerView = viewRoot.findViewById(R.id.mRecyclerView);

        smartRefreshLayout = viewRoot.findViewById(R.id.smartRefreshLayout);
    }

    private void initSmartRefreshLayout() {
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshData();
            }
        });

        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMoreData();
            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);

        setAdapter();

        mRecyclerView.setAdapter(adapter);
    }

    private void refreshData() {
        AllIndentDataList.clear();
        dataListPosition = 0;

        presenter.getPublishIndents();
    }

    private void loadMoreData() {
        if (dataListPosition >= AllIndentDataList.size()) {
            //表明数据已经全部加载完，不能再加载更多
        } else {
            List<Object> tempList = new ArrayList<>();
            //表明数据刚好或者不够再加载10个订单
            if (dataListPosition + PAGE_NUM >= AllIndentDataList.size()) {
                tempList.addAll(AllIndentDataList.subList(dataListPosition, AllIndentDataList.size()));
                dataListPosition = AllIndentDataList.size();
            } else {
                tempList.addAll(AllIndentDataList.subList(dataListPosition, dataListPosition + PAGE_NUM));
                dataListPosition += PAGE_NUM;
            }
            adapter.loadMoreData(tempList);
        }

        smartRefreshLayout.finishLoadMore();
    }

    private void setAdapter() {
        List<Object> dataList = new ArrayList<>();
        adapter = new PersonIndentRecyclerviewAdapter(dataList, INDENT_PUBLISH);
        adapter.setAdapterClickListener(new PersonIndentRecyclerviewAdapter.AdapterClickListener() {
            @Override
            public void clickItem(int what, int state, int position, Button btn_num1, Button btn_num2, int indentId, String price) {
                clickedPosition = position;
                switch (what) {
                    case 100:
                        Log.i(TAG, "clickItem: 点击了已发布-->别人未接-->取消，indentId>>>>>" + indentId + " price>>>>>" + price + " state>>>>>" + state + " position>>>>>" + position);

                        showEnsureDialog(indentId, price, 100, "您确定要取消该订单吗？", "取消中...");

                        break;
                    case 101:
                        Log.i(TAG, "clickItem: 点击了已发布-->接单中-->取消，indentId>>>>>" + indentId + " price>>>>>" + price + " state>>>>>" + state + " position>>>>>" + position);

                        showEnsureDialog(indentId, price, 101, "取消已接订单会扣除信誉积分哦，您确定还要取消该订单吗？", "取消中...");

                        break;
                    case 102:
                        Log.i(TAG, "clickItem: 点击了已发布-->接单中-->确认送达，indentId>>>>>" + indentId + " price>>>>>" + price + " state>>>>>" + state + " position>>>>>" + position);

                        showEnsureDialog(indentId, price, 102, "您真的确定货物到手了吗？", "确认中...");

                        break;
                    case 103:
                        Log.i(TAG, "clickItem: 点击了已发布-->已完成未评价-->评价，indentId>>>>>" + indentId + " price>>>>>" + price + " state>>>>>" + state + " position>>>>>" + position);

                        showCommentPopupWindow(btn_num1);

                        break;
                    case 104:
                        Log.i(TAG, "clickItem: 点击了已发布-->已完成未评价-->删除，indentId>>>>>" + indentId + " price>>>>>" + price + " state>>>>>" + state + " position>>>>>" + position);

                        showEnsureDialog(indentId, price, 104, "您还没进行评价哦，确定还要删除该订单吗？", "删除中...");

                        break;
                    case 105:
                        Log.i(TAG, "clickItem: 点击了已发布-->已完成已评价-->删除，indentId>>>>>" + indentId + " price>>>>>" + price + " state>>>>>" + state + " position>>>>>" + position);

                        showEnsureDialog(indentId, price, 105, "您确定要删除该订单吗？", "删除中...");

                        break;
                    case 110:
                        Log.i(TAG, "clickItem: 点击了订单，indentId>>>>>" + indentId + " price>>>>>" + price + " state>>>>>" + state + " position>>>>>" + position);

                        Intent intent = new Intent(getActivity(), IndentActivity.class);
                        intent.putExtra("indentId",indentId);
                        intent.putExtra("state",1);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    private void showEnsureDialog(int indentId, String price, int code, String content, String title) {
        LemonHello.getInformationHello("提示", content)
                .addAction(new LemonHelloAction("取消", new LemonHelloActionDelegate() {
                    @Override
                    public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                        Log.i(TAG, "onClick: 取消");
                        lemonHelloView.hide();
                    }
                }))
                .addAction(new LemonHelloAction("确定", new LemonHelloActionDelegate() {
                    @Override
                    public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                        Log.i(TAG, "onClick: 确定");
                        lemonHelloView.hide();

                        SendRequestWithHint(indentId, price, code, title);
                    }
                }))
                .show(getActivity());
    }

    private void showCommentPopupWindow(Button btn_num1) {
        RatingBarPopupWindow popupWindow = new RatingBarPopupWindow(getActivity());
        popupWindow.setClickItemListener(new RatingBarPopupWindow.ClickItemListener() {
            @Override
            public void clickItem(int increasement) {
                popupWindow.dismiss();

                LemonBubble.getRoundProgressBubbleInfo()
                        .setLocationStyle(LemonBubbleLocationStyle.BOTTOM)
                        .setLayoutStyle(LemonBubbleLayoutStyle.ICON_LEFT_TITLE_RIGHT)
                        .setBubbleSize(200, 50)
                        .setProportionOfDeviation(0.1f)
                        .setTitle("评价中...")
                        .show(getActivity());

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String happenTime = DateUtils.getCurrentDateByFormat("yyyy-MM-dd HH:mm:ss");
                        Log.i(TAG, "clickItem: increasement>>>>>"+increasement+" happenTime>>>>>"+happenTime);
                        presenter.giveRating(increasement,happenTime);
                    }
                }, 1500);
            }
        });
        popupWindow.showAsDropDown(btn_num1);
    }

    private void SendRequestWithHint(int indentId, String price, int code, String title) {
        LemonBubble.getRoundProgressBubbleInfo()
                .setLocationStyle(LemonBubbleLocationStyle.BOTTOM)
                .setLayoutStyle(LemonBubbleLayoutStyle.ICON_LEFT_TITLE_RIGHT)
                .setBubbleSize(200, 50)
                .setProportionOfDeviation(0.1f)
                .setTitle(title)
                .show(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "onClick: " +
                        "indentId>>>>>" + indentId + "\n" +
                        "price>>>>>" + price);

                switch (code) {
                    case 100:
                        presenter.cancelIndentNotTaken(indentId, price);
                        break;
                    case 101:
                        presenter.cancelIndentHadTaken(indentId, price);
                        break;
                    case 102:
                        presenter.ensureAcceptGoods(indentId, price);
                        break;
                    case 104:
                        presenter.deleteIndentNotComment(indentId, price);
                        break;
                    case 105:
                        presenter.deleteIndentHadComment(indentId, price);
                        break;
                }

            }
        }, 1500);
    }

    @Override
    public void getPublishIndentsSuccess(List<IndentBean> indentBeanList) {
        int dataSize = indentBeanList.size();
        Log.i(TAG, "getPublishIndentsSuccess: indentBeanList.size>>>>>" + dataSize);


        if (dataSize == 0) {
            List<Object> dataList = new ArrayList<>();
            dataList.add("暂无数据");
            adapter.update(dataList);
        } else {
            AllIndentDataList.addAll(indentBeanList);
            adapter.update(getRuledNumIndent());
        }
        smartRefreshLayout.finishRefresh();
    }

    private List<Object> getRuledNumIndent() {
        List<Object> tempList = new ArrayList<>();
        if (AllIndentDataList.size() > PAGE_NUM) {
            tempList.addAll(AllIndentDataList.subList(0, PAGE_NUM));
            dataListPosition = PAGE_NUM;
        } else {
            tempList.addAll(AllIndentDataList);
            dataListPosition = AllIndentDataList.size();
        }
        return tempList;
    }

    @Override
    public void getPublishIndentsFail() {
        Log.i(TAG, "getPublishIndentsFail: ");

        List<Object> dataList = new ArrayList<>();
        dataList.add("获取失败");
        adapter.update(dataList);

        smartRefreshLayout.finishRefresh();
    }

    @Override
    public void cancelIndentNotTakenSuccess(Integer result) {
        Log.i(TAG, "cancelIndentNotTakenSuccess: result>>>>>" + result);

        if (0 == result) {
            LemonBubble.showError(this, "取消失败，请重试！", 1500);
        } else {
            LemonBubble.showRight(this, "取消成功！", 1500);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    adapter.removeIndent(clickedPosition);
                }
            }, 1600);
        }
    }

    @Override
    public void cancelIndentNotTakenFail() {
        Log.i(TAG, "cancelIndentNotTakenFail: ");

        LemonBubble.showError(this, "网络异常，请重试！", 1500);
    }

    @Override
    public void cancelIndentHadTakenSuccess(Integer result) {
        Log.i(TAG, "cancelIndentHadTakenSuccess: result>>>>>" + result);

        if (0 == result) {
            LemonBubble.showError(this, "取消失败，请重试！", 1500);
        } else {
            LemonBubble.showRight(this, "取消成功！", 1500);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    adapter.removeIndent(clickedPosition);
                }
            }, 1600);
        }
    }

    @Override
    public void cancelIndentHadTakenFail() {
        Log.i(TAG, "cancelIndentHadTakenFail: ");

        LemonBubble.showError(this, "网络异常，请重试！", 1500);
    }

    @Override
    public void deleteIndentNotCommentSuccess(Integer result) {
        Log.i(TAG, "deleteIndentNotCommentSuccess: result>>>>>" + result);

        if (0 == result) {
            LemonBubble.showError(this, "删除失败，请重试！", 1500);
        } else {
            LemonBubble.showRight(this, "删除成功！", 1500);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    adapter.removeIndent(clickedPosition);
                }
            }, 1600);
        }
    }

    @Override
    public void deleteIndentNotCommentFail() {
        Log.i(TAG, "deleteIndentNotCommentFail: ");

        LemonBubble.showError(this, "网络异常，请重试！", 1500);
    }

    @Override
    public void deleteIndentHadCommentSuccess(Integer result) {
        Log.i(TAG, "deleteIndentHadCommentSuccess: result>>>>>" + result);

        if (0 == result) {
            LemonBubble.showError(this, "删除失败，请重试！", 1500);
        } else {
            LemonBubble.showRight(this, "删除成功！", 1500);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    adapter.removeIndent(clickedPosition);
                }
            }, 1600);
        }
    }

    @Override
    public void deleteIndentHadCommentFail() {
        Log.i(TAG, "deleteIndentHadCommentFail: ");

        LemonBubble.showError(this, "网络异常，请重试！", 1500);
    }

    @Override
    public void ensureAcceptGoodsSuccess(Integer result) {
        Log.i(TAG, "ensureAcceptGoodsSuccess: result>>>>>" + result);

        if (0 == result) {
            LemonBubble.showError(this, "确认失败，请重试！", 1500);
        } else {
            LemonBubble.showRight(this, "确认成功！", 1500);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    adapter.ensureAcceptGoods(clickedPosition);
                }
            }, 1600);
        }
    }

    @Override
    public void ensureAcceptGoodsFail() {
        Log.i(TAG, "deleteIndentHadCommentFail: ");

        LemonBubble.showError(this, "网络异常，请重试！", 1500);
    }

    @Override
    public void giveRatingSuccess(Integer result) {
        Log.i(TAG, "giveRatingSuccess: result>>>>>" + result);

        if (0 == result) {
            LemonBubble.showError(this, "评价失败，请重试！", 1500);
        } else {
            LemonBubble.showRight(this, "评价成功！", 1500);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    adapter.comment(clickedPosition);
                }
            }, 1600);
        }
    }

    @Override
    public void giveRatingFail() {
        LemonBubble.showError(this, "网络异常，请重试！", 1500);
    }
}
