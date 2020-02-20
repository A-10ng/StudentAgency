package com.example.studentagency.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.example.studentagency.Utils.DateUtils;
import com.example.studentagency.bean.CommentBean;
import com.example.studentagency.bean.IndentBean;
import com.example.studentagency.bean.PublishAndIndentBean;
import com.example.studentagency.bean.UserBean;
import com.example.studentagency.mvp.presenter.IndentActivityBasePresenter;
import com.example.studentagency.mvp.view.IndentActivityBaseView;
import com.example.studentagency.ui.adapter.IndentActivityRecyclerViewAdapter;
import com.example.studentagency.ui.widget.CommentDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.studentagency.ui.activity.MyApp.userId;

public class IndentActivity extends BaseActivity implements IndentActivityBaseView {

    private static final String TAG = "IndentActivity";
    private IndentActivityBasePresenter presenter = new IndentActivityBasePresenter(this);
    private PublishAndIndentBean defaultPAIBean = new PublishAndIndentBean();

    //smartRefreshLayout
    private SmartRefreshLayout mSmartRefreshLayout;

    //recyclerview
    private RecyclerView mRecyclerView;
    private List<Object> originalDataList = new ArrayList<>();
    private List<CommentBean> allDataList = new ArrayList<>();//所有的留言数据
    private List<Object> ruledCommentDataList = new ArrayList<>();//规定的留言数据
    private IndentActivityRecyclerViewAdapter mAdapter;

    //发布方信息
    private ImageView iv_avatar, iv_gender, iv_verifyState;
    private TextView tv_username, tv_creditScore, tv_publishTime;

    //订单信息
    private TextView tv_indentId, tv_price, tv_type, tv_address, tv_planTime;

    //代办描述
    private TextView tv_description;

    //留言区
    private TextView tv_nocomment;

    //主页或者订单记录传过来的indentId
    //-1代表是从主页过来的，显示接单和留言按钮
    //1代表从订单记录传过来的，不显示接单和留言按钮
    private int indentId;
    private int state = -1;

    //接单按钮
    private Button btn_accept;

    //留言按钮
    private Button btn_comment;

    //接单对话框
    private AlertDialog btn_accept_dialog;

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
        Log.i(TAG, "传过来的IndentId: " + indentId + " state: " + state);
    }

    private void initViews() {
        //发布方信息
        iv_avatar = findViewById(R.id.iv_avatar);
        iv_gender = findViewById(R.id.iv_gender);
        iv_verifyState = findViewById(R.id.iv_verifyState);
        tv_username = findViewById(R.id.tv_username);
        tv_creditScore = findViewById(R.id.tv_creditScore);
        tv_publishTime = findViewById(R.id.tv_publishTime);

        //订单信息
        tv_indentId = findViewById(R.id.tv_indentId);
        tv_price = findViewById(R.id.tv_price);
        tv_type = findViewById(R.id.tv_type);
        tv_address = findViewById(R.id.tv_address);
        tv_planTime = findViewById(R.id.tv_planTime);

        //代办描述
        tv_description = findViewById(R.id.tv_description);

        //留言区
        tv_nocomment = findViewById(R.id.tv_nocomment);

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
                                presenter.acceptIndent(indentId, acceptedTime);
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
                    Toast.makeText(IndentActivity.this, "您的输入为空，请重新输入！", Toast.LENGTH_SHORT).show();
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
    }

    private void setRecyclerViewAdapter() {
        mAdapter = new IndentActivityRecyclerViewAdapter(originalDataList);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void refreshData() {
        allDataList.clear();
        ruledCommentDataList.clear();
        setButtonUnClickable();

        presenter.getPublishInfo();
        presenter.getIndentInfo(indentId);
        presenter.getCommentInfo(indentId);
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

    private void setButtonUnClickable() {
        btn_accept.setEnabled(false);
        btn_comment.setEnabled(false);
    }

    @Override
    public void getPublishInfoSuccess(UserBean userBean) {
        Log.i(TAG, "getPublishInfoSuccess: userBean" + userBean.toString());
        setButtonClickable();

        defaultPAIBean.setAvatar(userBean.getAvatar());
        defaultPAIBean.setCreditScore(userBean.getCreditScore());
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
        setButtonUnClickable();

        defaultPAIBean.setAvatar("blank");
        defaultPAIBean.setCreditScore(0);
        defaultPAIBean.setGender(0);
        defaultPAIBean.setVerifyState(0);
        defaultPAIBean.setUsername("");

        mAdapter.setPublishOrIndentData(defaultPAIBean);
    }

    @Override
    public void getIndentInfoSuccess(IndentBean indentBean) {
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
    public void getCommentInfoSuccess(List<CommentBean> commentBeans) {
        Log.i(TAG, "getCommentInfoSuccess");
        setButtonClickable();

        mAdapter.setCommentData(commentBeans);
    }

    @Override
    public void getCommentInfoFail() {
        Log.i(TAG, "getCommentInfoFail");
        setButtonUnClickable();

        mAdapter.setCommentData(new ArrayList<CommentBean>());
    }

    @Override
    public void acceptIndentSuccess(Integer result) {
        Log.i(TAG, "acceptIndentSuccess result:" + result);

        //接单成功
        if (result == 1) {
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
    public void acceptIndentFail() {
        Log.i(TAG, "acceptIndentFail");

        LemonBubble.showError(this, "网络异常，请重试！", 1500);
    }

    @Override
    public void giveACommentSuccess(Integer result) {
        Log.i(TAG, "giveACommentSuccess");

        //留言成功
        if (result == 1) {
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

    @Override
    public void giveACommentFail() {
        Log.i(TAG, "giveACommentFail");
        LemonBubble.showError(this, "网络异常，请重试！", 1500);
    }
}
