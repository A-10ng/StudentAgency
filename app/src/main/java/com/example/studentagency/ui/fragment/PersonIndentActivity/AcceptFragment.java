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
import com.example.studentagency.bean.IndentBean;
import com.example.studentagency.mvp.presenter.AcceptFragmentBasePresenter;
import com.example.studentagency.mvp.view.AcceptFragmentBaseView;
import com.example.studentagency.ui.activity.IndentActivity;
import com.example.studentagency.ui.adapter.PersonIndentRecyclerviewAdapter;
import com.example.studentagency.utils.VariableName;
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
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/19
 * desc:
 */
public class AcceptFragment extends Fragment implements AcceptFragmentBaseView {

    private static final String TAG = "PublishFragment";
    private static final int INDENT_ACCEPT = 4;
    private AcceptFragmentBasePresenter presenter = new AcceptFragmentBasePresenter(this);
    private View viewRoot;
    private String phoneNum;

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
        viewRoot = inflater.inflate(R.layout.fragment_accept, container, false);

        initViews();

        initSmartRefreshLayout();

        initRecyclerView();

        //发送请求
        presenter.getAcceptIndents();

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

        presenter.getAcceptIndents();
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
        adapter = new PersonIndentRecyclerviewAdapter(dataList, INDENT_ACCEPT);
        adapter.setAdapterClickListener(new PersonIndentRecyclerviewAdapter.AdapterClickListener() {
            @Override
            public void clickItem(int what, int state, int position, Button btn_num1, Button btn_num2,IndentBean indentBean) {
                clickedPosition = position;
                phoneNum = indentBean.getPhoneNum();

                switch (what) {
                    case 106:
                        Log.i(TAG, "clickItem: 点击了已接取-->接单中-->取消，indentBean>>>>>" + indentBean.toString() + " state>>>>>" + state + " position>>>>>" + position);

                        showEnsureDialog(indentBean.getIndentId(), indentBean.getPrice(), 106, "您确定要取消已接的订单吗,这样会扣除一定的信誉积分哦", "取消中...");

                        break;
                    case 107:
                        Log.i(TAG, "clickItem: 点击了已接取-->接单中-->确认送达，indentBean>>>>>" + indentBean.toString() + " state>>>>>" + state + " position>>>>>" + position);

                        showEnsureDialog(indentBean.getIndentId(), indentBean.getPrice(), 107, "您真的确定货物送到手了吗？", "确认中...");

                        break;
                    case 108:
                        Log.i(TAG, "clickItem: 点击了已接取-->已完成未评价-->删除，indentBean>>>>>" + indentBean.toString() + state + " position>>>>>" + position);

                        showEnsureDialog(indentBean.getIndentId(), indentBean.getPrice(), 108, "您确定要删除该订单吗？", "删除中...");

                        break;
                    case 109:
                        Log.i(TAG, "clickItem: 点击了已接取-->已完成已评价-->删除，indentBean>>>>>" + indentBean.toString() + " state>>>>>" + state + " position>>>>>" + position);

                        showEnsureDialog(indentBean.getIndentId(), indentBean.getPrice(), 109, "您确定要删除该订单吗？", "删除中...");

                        break;
                    case 110:
                        Log.i(TAG, "clickItem: 点击了订单，indentBean>>>>>" + indentBean.toString() + " state>>>>>" + state + " position>>>>>" + position);

                        Intent intent = new Intent(getActivity(), IndentActivity.class);
                        intent.putExtra("indentId",indentBean.getIndentId());
                        intent.putExtra("state",state);
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
                    case 106:
                        presenter.cancelIndentHadTaken(indentId, price);
                        break;
                    case 107:
                        presenter.ensureAcceptGoods(indentId, price);
                        break;
                    case 108:
                        presenter.deleteIndentNotComment(indentId, price);
                        break;
                    case 109:
                        presenter.deleteIndentHadComment(indentId, price);
                        break;
                }

            }
        }, 1500);
    }

    @Override
    public void getAcceptIndentsSuccess(List<IndentBean> indentBeanList) {
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
    public void getAcceptIndentsFail() {
        Log.i(TAG, "getPublishIndentsFail: ");

        List<Object> dataList = new ArrayList<>();
        dataList.add("获取失败");
        adapter.update(dataList);

        smartRefreshLayout.finishRefresh();
    }

    @Override
    public void cancelIndentHadTakenSuccess(Integer result) {
        Log.i(TAG, "cancelIndentHadTakenSuccess: result>>>>>" + result);

        if (0 == result) {
            LemonBubble.showError(this, "取消失败，请重试！", 1500);
        } else {
//            sendMessageToPublish(phoneNum,"取消您的订单实在不好意思，请见谅！");
            sendMessageToPublish("18218643171","取消您的订单实在不好意思，请见谅！");

            LemonBubble.showRight(this, "取消成功！", 1500);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    adapter.removeIndent(clickedPosition);
                }
            }, 1600);
        }
    }

    private void sendMessageToPublish(String phoneNum,String content) {
        Conversation.createSingleConversation(phoneNum);

        Message message = JMessageClient.createSingleTextMessage(phoneNum,
                VariableName.JIGUANG_APP_KEY, content);
        JMessageClient.sendMessage(message);
        message.setOnSendCompleteCallback(new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                Log.i(TAG, "gotResult: i>>>>>" + i + "  s>>>>>" + s);
                if (i == 0) {
                    Log.i(TAG, "gotResult: 消息发送成功！");
                } else {
                    Log.i(TAG, "gotResult: 消息发送失败！");
                }
            }
        });
    }

    @Override
    public void cancelIndentHadTakenFail() {
        Log.i(TAG, "cancelIndentHadTakenFail: ");

        LemonBubble.showError(this, "网络异常，请重试！", 1500);
    }

    @Override
    public void deleteIndentNotCommentSuccess(Integer result) {
        Log.i(TAG, "cancelIndentHadTakenSuccess: result>>>>>" + result);

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
        Log.i(TAG, "cancelIndentHadTakenSuccess: result>>>>>" + result);

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
//            sendMessageToPublish(phoneNum,"您的东西已送达，请尽快确认！");
            sendMessageToPublish("18218643171","您的东西已送达，请尽快确认！");

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
        Log.i(TAG, "ensureAcceptGoodsFail: ");

        LemonBubble.showError(this, "网络异常，请重试！", 1500);
    }
}
