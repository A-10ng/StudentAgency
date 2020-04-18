package com.example.studentagency.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lemonbubble.LemonBubble;
import com.example.lemonbubble.enums.LemonBubbleLayoutStyle;
import com.example.lemonbubble.enums.LemonBubbleLocationStyle;
import com.example.lemonhello.LemonHello;
import com.example.lemonhello.LemonHelloAction;
import com.example.lemonhello.LemonHelloInfo;
import com.example.lemonhello.LemonHelloView;
import com.example.lemonhello.interfaces.LemonHelloActionDelegate;
import com.example.studentagency.R;
import com.example.studentagency.bean.CommentBean;
import com.example.studentagency.bean.CreditBean;
import com.example.studentagency.bean.IndentBean;
import com.example.studentagency.bean.PublishAndIndentBean;
import com.example.studentagency.bean.ResponseBean;
import com.example.studentagency.bean.UserBean;
import com.example.studentagency.mvp.presenter.IndentActivityBasePresenter;
import com.example.studentagency.mvp.view.IndentActivityBaseView;
import com.example.studentagency.ui.adapter.IndentActivityRecyclerViewAdapter;
import com.example.studentagency.ui.widget.CommentDialog;
import com.example.studentagency.utils.DateUtils;
import com.example.studentagency.utils.VariableName;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;

import static com.example.studentagency.ui.activity.MyApp.userId;

public class IndentActivity extends BaseActivity implements IndentActivityBaseView {

    private static final String TAG = "IndentActivity";
    private IndentActivityBasePresenter presenter = new IndentActivityBasePresenter(this);
    private PublishAndIndentBean defaultPAIBean = new PublishAndIndentBean();
    private String phoneNum;

    //smartRefreshLayout
    private SmartRefreshLayout mSmartRefreshLayout;

    //recyclerview
    private RecyclerView mRecyclerView;
    private List<Object> originalDataList = new ArrayList<>();
    private List<CommentBean> allDataList = new ArrayList<>();//所有的留言数据
    private List<Object> ruledCommentDataList = new ArrayList<>();//规定的留言数据
    private IndentActivityRecyclerViewAdapter mAdapter;

    //主页或者订单记录传过来的indentId
    //-1代表是从主页过来的，显示接单和留言按钮
    //200-206代表从订单记录传过来的，不显示接单和留言按钮
    //其中203和206还要显示评价内容
    private int indentId;
    private int state = -1;
    private int publishId = -1;

    //接单按钮
    private Button btn_accept;

    //留言按钮
    private Button btn_comment;

    //留言区
    private CommentDialog commentDialog;
    private String commentTime;//留言时间
    private String content;//留言内容

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indent);

        //获取传过来的indentId和state
        getPassedInfo();

        initViews();

        setViewsOnClick();

        initRecyclerView();

        initSmartRefreshLayout();
    }

    private void getPassedInfo() {
        Intent intent = getIntent();
        indentId = intent.getIntExtra("indentId", -1);
        state = intent.getIntExtra("state", -1);
        publishId = intent.getIntExtra("publishId", -1);
        Log.i(TAG, "传过来的IndentId: " + indentId + " state: " + state + " publishId: " + publishId);
    }

    private void initViews() {
        //接单以及留言按钮
        btn_accept = findViewById(R.id.btn_accept);
        btn_comment = findViewById(R.id.btn_comment);
        if (state == -1) {
            btn_accept.setVisibility(View.VISIBLE);
            btn_comment.setVisibility(View.VISIBLE);
        } else {
            btn_accept.setVisibility(View.GONE);
            btn_comment.setVisibility(View.GONE);
        }
    }

    private void setViewsOnClick() {
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: 接单");
                showAcceptDialog();
            }
        });

        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: 留言");

                showCommentDialog();
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.rv_indentInfo);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);

        initOriginalData();

        setRecyclerViewAdapter();
    }

    private void initSmartRefreshLayout() {
        mSmartRefreshLayout = findViewById(R.id.smartRefreshLayout);

        mSmartRefreshLayout.setEnableLoadMore(false);
        mSmartRefreshLayout.autoRefresh();
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Log.i(TAG, "onRefresh");

                refreshData();
                mSmartRefreshLayout.finishRefresh();
            }
        });
    }

    private void showAcceptDialog() {
        LemonHello.getInformationHello("提示", "您确定要接下该订单吗？")
                .addAction(new LemonHelloAction("取消", new LemonHelloActionDelegate() {
                    @Override
                    public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                        Log.i(TAG, "onClick: 取消接单");
                        lemonHelloView.hide();
                    }
                }))
                .addAction(new LemonHelloAction("确定", new LemonHelloActionDelegate() {
                    @Override
                    public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                        Log.i(TAG, "onClick: 确定接单");
                        lemonHelloView.hide();

                        LemonBubble.getRoundProgressBubbleInfo()
                                .setLocationStyle(LemonBubbleLocationStyle.BOTTOM)
                                .setLayoutStyle(LemonBubbleLayoutStyle.ICON_LEFT_TITLE_RIGHT)
                                .setBubbleSize(200, 50)
                                .setProportionOfDeviation(0.1f)
                                .setTitle("接单中...")
                                .show(IndentActivity.this);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                String acceptedTime = getAcceptedTime();
                                Log.i(TAG, "acceptedTime>>>>>" + acceptedTime);
                                presenter.acceptIndent(userId,indentId, acceptedTime);
                            }
                        }, 1500);
                    }
                }))
                .show(IndentActivity.this);
    }

    private void showCommentDialog() {
        commentDialog = new CommentDialog(this, R.style.dialog_comment_dialog);
        commentDialog.setDialogSize(310, 284);
        commentDialog.setCanceledOnTouchOutside(false);
        commentDialog.setEnsureClickListener(new CommentDialog.onEnsureClickListener() {
            @Override
            public void clickEnsure(final String content) {
                Log.i(TAG, "clickEnsure：点击了确定留言");

                //如果输入的内容有效
                if (isValidityOfContent(content)) {

                    IndentActivity.this.content = content;

                    commentDialog.dismiss();

                    LemonBubble.getRoundProgressBubbleInfo()
                            .setLocationStyle(LemonBubbleLocationStyle.BOTTOM)
                            .setLayoutStyle(LemonBubbleLayoutStyle.ICON_LEFT_TITLE_RIGHT)
                            .setBubbleSize(200, 50)
                            .setProportionOfDeviation(0.1f)
                            .setTitle("留言中...")
                            .show(IndentActivity.this);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            commentTime = getCommentTime();
                            Log.i(TAG, "commentTime>>>>>" + commentTime);
                            presenter.giveAComment(indentId, userId, content, commentTime);
                        }
                    }, 1500);
                }
                //如果输入的内容无效
                else {
                    Toast toast = Toast.makeText(IndentActivity.this, "", Toast.LENGTH_SHORT);
                    toast.setText("您的输入为空，请重新输入！");
                    toast.show();
                }
            }
        });
        commentDialog.setCancelClickListener(new CommentDialog.onCancelClickListener() {
            @Override
            public void clickCancel() {
                Log.i(TAG, "clickCancel：点击了取消留言");
                commentDialog.dismiss();
            }
        });
        commentDialog.show();
    }

    private void initOriginalData() {
        //添加发布方和订单信息
        addPublishAndIndentData();

        //留言
        addCommentData();

        //评价
        if (state == 203 || state == 206) {
            addRatingStarsData();
        }
    }

    private void setRecyclerViewAdapter() {
        mAdapter = new IndentActivityRecyclerViewAdapter(originalDataList, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void refreshData() {
        allDataList.clear();
        ruledCommentDataList.clear();
        setButtonUnClickable();

        presenter.getPublishInfo(publishId);
        presenter.getIndentInfo(indentId);
        presenter.getCommentInfo(indentId);

        if (state == 203 || state == 206) {
            presenter.getRatingStarsInfo(indentId);
        }
    }

    private String getAcceptedTime() {
        String result = DateUtils.getCurrentDateByFormat("yyyy-MM-dd HH:mm:ss");
        return result;
    }

    private boolean isValidityOfContent(String content) {
        if (TextUtils.isEmpty(content)) {
            return false;
        } else {
            return true;
        }
    }

    private String getCommentTime() {
        String result = DateUtils.getCurrentDateByFormat("yyyy-MM-dd HH:mm:ss");
        return result;
    }

    private void addPublishAndIndentData() {
        defaultPAIBean.setIndentId(0);
        defaultPAIBean.setAddress("");
        defaultPAIBean.setDescription("加载失败");
        defaultPAIBean.setPlanTime("");
        defaultPAIBean.setPrice("");
        defaultPAIBean.setType(0);
        defaultPAIBean.setPublishTime("");
        defaultPAIBean.setAvatar("blank");
        defaultPAIBean.setCreditScore(0);
        defaultPAIBean.setGender(0);
        defaultPAIBean.setVerifyState(0);
        defaultPAIBean.setUsername("");
        originalDataList.add(defaultPAIBean);
    }

    private void addCommentData() {
        originalDataList.add("暂无留言");
    }

    private void addRatingStarsData() {
        originalDataList.add(new CreditBean());
        originalDataList.add("空白处");
    }

    private void setButtonUnClickable() {
        btn_accept.setEnabled(false);
        btn_comment.setEnabled(false);
    }

    @Override
    public void getPublishInfoSuccess(ResponseBean responseBean) {
        Gson gson = new Gson();
        UserBean userBean = gson.fromJson(gson.toJson(responseBean.getData()),UserBean.class);

        Log.i(TAG, "getPublishInfoSuccess: userBean" + userBean.toString());

        phoneNum = userBean.getPhoneNum();

        setButtonClickable();

        defaultPAIBean.setAvatar(userBean.getAvatar());
        defaultPAIBean.setCreditScore(userBean.getCreditScore());
        defaultPAIBean.setUserId(userBean.getUserId());
        defaultPAIBean.setGender(userBean.getGender());
        defaultPAIBean.setVerifyState(userBean.getVerifyState());
        defaultPAIBean.setUsername(userBean.getUsername());

        mAdapter.setPublishOrIndentData(defaultPAIBean);
    }

    private void setButtonClickable() {
        btn_accept.setEnabled(true);
        btn_comment.setEnabled(true);
    }

    @Override
    public void getPusblishInfoFail() {
        Log.i(TAG, "getPusblishInfoFail");

        phoneNum = "";

        setButtonUnClickable();

        defaultPAIBean.setAvatar("blank");
        defaultPAIBean.setCreditScore(0);
        defaultPAIBean.setUserId(0);
        defaultPAIBean.setGender(0);
        defaultPAIBean.setVerifyState(0);
        defaultPAIBean.setUsername("");

        mAdapter.setPublishOrIndentData(defaultPAIBean);
    }

    @Override
    public void getIndentInfoFail() {
        Log.i(TAG, "getIndentInfoFail");

        setButtonUnClickable();

        defaultPAIBean.setIndentId(0);
        defaultPAIBean.setAddress("");
        defaultPAIBean.setDescription("加载失败");
        defaultPAIBean.setPlanTime("");
        defaultPAIBean.setPrice("");
        defaultPAIBean.setType(0);
        defaultPAIBean.setPublishTime("");

        mAdapter.setPublishOrIndentData(defaultPAIBean);
    }

    @Override
    public void getCommentInfoFail() {
        Log.i(TAG, "getCommentInfoFail");
        setButtonUnClickable();

        if (state == 203 || state == 206)
            mAdapter.setCommentData(new ArrayList<CommentBean>(), true);
        else
            mAdapter.setCommentData(new ArrayList<CommentBean>(), false);
    }

    @Override
    public void getRatingStarsInfoFail() {
        Log.i(TAG, "getRatingStarsInfoFail: ");

        mAdapter.setRatingStarsData(new CreditBean());
    }

    @Override
    public void acceptIndentFail() {
        Log.i(TAG, "acceptIndentFail");

        LemonBubble.showError(this, "网络异常，请重试！", 1500);
    }

    @Override
    public void giveACommentFail() {
        Log.i(TAG, "giveACommentFail");
        LemonBubble.showError(this, "网络异常，请重试！", 1500);
    }

    @Override
    public void getIndentInfoSuccess(ResponseBean responseBean) {
        Gson gson = new Gson();
        IndentBean indentBean = gson.fromJson(gson.toJson(responseBean.getData()),IndentBean.class);

        Log.i(TAG, "getIndentInfoSuccess: indentBean>>>>>" + indentBean.toString());
        setButtonClickable();

        defaultPAIBean.setIndentId(indentBean.getIndentId());
        defaultPAIBean.setAddress(indentBean.getAddress());
        defaultPAIBean.setDescription(indentBean.getDescription());
        defaultPAIBean.setPlanTime(indentBean.getPlanTime());
        defaultPAIBean.setPrice(indentBean.getPrice());
        defaultPAIBean.setType(indentBean.getType());
        defaultPAIBean.setPublishTime(indentBean.getPublishTime());

        mAdapter.setPublishOrIndentData(defaultPAIBean);
    }

    @Override
    public void getCommentInfoSuccess(ResponseBean responseBean) {
        Log.i(TAG, "getCommentInfoSuccess");

        Gson gson = new Gson();
        List<CommentBean> commentBeans = gson.fromJson(
                gson.toJson(responseBean.getData()),
                new TypeToken<List<CommentBean>>() {}.getType());

        setButtonClickable();

        if (state == 203 || state == 206) mAdapter.setCommentData(commentBeans, true);
        else mAdapter.setCommentData(commentBeans, false);
    }

    @Override
    public void getRatingStarsInfoSuccess(ResponseBean responseBean) {

        Gson gson = new Gson();
        CreditBean creditBean = gson.fromJson(gson.toJson(responseBean.getData()),CreditBean.class);

        Log.i(TAG, "getRatingStarsInfoSuccess: creditBean>>>>>" + creditBean.toString());

        mAdapter.setRatingStarsData(creditBean);
    }

    @Override
        public void acceptIndentSuccess(ResponseBean responseBean) {
        Log.i(TAG, "acceptIndentSuccess result:" + responseBean.getCode());

        //接单成功
        if (responseBean.getCode() == 200) {
            phoneNum = "18218643171";
            Conversation.createSingleConversation(phoneNum);

            Message message = JMessageClient.createSingleTextMessage(phoneNum,
                    VariableName.JIGUANG_APP_KEY,
                    "我已接单成功，正火速赶往中！");
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

            LemonBubble.showRight(this, "接单成功！", 1500);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(IndentActivity.this, MainActivity.class));
                }
            }, 2000);
        }
        //接单失败
        else {
            LemonBubble.showError(this, "接单失败！", 1500);
        }
    }

    @Override
    public void giveACommentSuccess(ResponseBean responseBean) {
        Log.i(TAG, "giveACommentSuccess");

        //留言成功
        if (responseBean.getCode() == 200) {
            LemonBubble.showRight(this, "留言成功！", 1500);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    CommentBean commentBean = new CommentBean();
                    commentBean.setAvatar("blank");
                    commentBean.setCommentTime(commentTime);
                    commentBean.setContent(content);
                    commentBean.setUsername("LongSh1z");
                    mAdapter.addComment(commentBean);
                }
            }, 2000);
        }
        //留言失败
        else {
            LemonBubble.showError(this, "留言失败！", 1500);
        }
    }
}
