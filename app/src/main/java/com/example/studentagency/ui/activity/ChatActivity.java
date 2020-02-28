package com.example.studentagency.ui.activity;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.DownloadCompletionCallback;
import cn.jpush.im.android.api.content.CustomContent;
import cn.jpush.im.android.api.content.FileContent;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.PromptContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.content.VideoContent;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.enums.MessageDirect;
import cn.jpush.im.android.api.enums.MessageStatus;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.MessageRetractEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import io.github.rockerhieu.emojicon.EmojiconEditText;
import io.github.rockerhieu.emojicon.EmojiconGridFragment;
import io.github.rockerhieu.emojicon.EmojiconsFragment;
import io.github.rockerhieu.emojicon.emoji.Emojicon;
import me.leefeng.promptlibrary.PromptDialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.studentagency.R;
import com.example.studentagency.bean.ChatBean;
import com.example.studentagency.ui.adapter.ChatActivityRecyclerviewAdapter;
import com.example.studentagency.ui.fragment.ChatActivity.ChatOptionFragment;
import com.example.studentagency.ui.widget.SoundTextView;
import com.example.studentagency.ui.widget.TitleBar;
import com.example.studentagency.utils.FileHelper;
import com.example.studentagency.utils.PlayVoiceUtil;
import com.example.studentagency.utils.SoftKeyBoardListener;
import com.example.studentagency.utils.Utils;
import com.example.studentagency.utils.VariableName;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends BaseActivity implements EmojiconGridFragment.OnEmojiconClickedListener,
        EmojiconsFragment.OnEmojiconBackspaceClickedListener {

    private static final String TAG = "ChatActivity";
    public Conversation conversation = null;
    public List<ChatBean> chatBeanList = new ArrayList<>();
    public ChatActivityRecyclerviewAdapter adapter;
    private MyHandler handler = new MyHandler(this);
    private PromptDialog dialog;
    private String imagePath;
    private String fileName;
    //传过来的数据
    private String username;
    private String nickname;
    //标题
    private TitleBar titleBar;
    //聊天区域
    private RecyclerView recyclerView;
    //语音输入
    private ImageView iv_microphone;
    private SoundTextView tv_soundTextView;
    private boolean showSound = false;
    private PlayVoiceUtil playVoiceUtil = null;

    //表情
    private EmojiconEditText et_emojiIconEditText;
    private ImageView iv_emoji;
    private EmojiconsFragment emojiconsFragment = EmojiconsFragment.newInstance(false);
    private boolean showOption = false;
    private boolean showEmoji = false;
    private boolean keyBoardShow = false;

    //发送
    private TextView tv_send;

    //选择按钮
    private ImageView iv_option;

    //功能区
    private FrameLayout frame_layout;
    private ChatOptionFragment chatOptionFragment = new ChatOptionFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        checkVoicePermission();

        getPassedInfo();

        initViews();

        //初始化recyclerview
        initRecyclerView();

        //初始化会话
        initConversation();

        //初始化输入
        initInput();

        //初始化选择区
        initOptionEmotion();

        //初始化语音设置
        initVoice();
    }

    private void checkVoicePermission() {
        // 申请多个权限。
        AndPermission.with(this)
                .runtime()
                .permission(Permission.RECORD_AUDIO)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Log.i(TAG, "onAction: 授权成功录音权限！");
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Toast.makeText(ChatActivity.this, "您将无法发送语音，请前往设置授权！", Toast.LENGTH_SHORT).show();
                    }
                })
                .start();
    }

    private void getPassedInfo() {
        Intent intent = getIntent();
        if (null != intent) {
            username = intent.getStringExtra("username");//phoneNum
            nickname = intent.getStringExtra("nickname");//username

            chatOptionFragment.username = username;
        }
    }

    private void initViews() {
        dialog = new PromptDialog(this);

        //标题
        titleBar = findViewById(R.id.titleBar);
        titleBar.setTitle_name(nickname);

        //聊天区域
        recyclerView = findViewById(R.id.recyclerView);

        //语音输入
        iv_microphone = findViewById(R.id.iv_microphone);
        tv_soundTextView = findViewById(R.id.tv_soundTextView);

        //表情
        et_emojiIconEditText = findViewById(R.id.et_emojiIconEditText);
        iv_emoji = findViewById(R.id.iv_emoji);

        //发送
        tv_send = findViewById(R.id.tv_send);

        //选择按钮
        iv_option = findViewById(R.id.iv_option);

        //功能区
        frame_layout = findViewById(R.id.frame_layout);
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        adapter = new ChatActivityRecyclerviewAdapter(chatBeanList);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (chatBeanList.get(position).message.getDirect() == MessageDirect.send) {
                    return;
                }
                Intent intent = new Intent(ChatActivity.this, OtherPersonActivity.class);
                intent.putExtra("phoneNum", username);
                startActivity(intent);
            }
        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ChatBean chatBean = chatBeanList.get(position);
                //浏览图片
                if (chatBean.getItemType() == ChatBean.IMG_SEND ||
                        chatBean.getItemType() == ChatBean.IMG_RECEIVE) {
                    int first = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                    int last = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();

                    if (position < first || position > last) {
                        return;
                    }

                    ImageContent imageContent = (ImageContent) chatBean.message.getContent();
                    imagePath = "";
                    if (!TextUtils.isEmpty(imageContent.getLocalThumbnailPath())) {
                        imagePath = imageContent.getLocalThumbnailPath();
                    }
                    dialog.showLoading("下载图片中");
                    imageContent.downloadOriginImage(chatBean.message, new DownloadCompletionCallback() {
                        @Override
                        public void onComplete(int responseCode, String s, File file) {
                            dialog.dismiss();
                            Intent intent = new Intent(ChatActivity.this, PreviewImageActivity.class);
                            if (responseCode == 0) {
                                intent.putExtra("filePath", file.getPath());
                            } else {
                                intent.putExtra("filePath", imagePath);
                            }
                            startActivity(intent);
                        }
                    });
                }

                //视频
                if (chatBeanList.get(position).itemType == ChatBean.VIDEO_RECEIVE ||
                        chatBeanList.get(position).itemType == ChatBean.VIDEO_SEND) {
                    Message message = chatBeanList.get(position).message;

                    VideoContent videoContent = (VideoContent) message.getContent();


                    dialog.showLoading("下载视频中");
                    videoContent.downloadVideoFile(message,
                            new DownloadCompletionCallback() {
                                @Override
                                public void onComplete(int i, String s, File file) {
                                    dialog.dismiss();
                                    if (file == null || i != 0) {
                                        Log.i(TAG, "onComplete: 视频下载失败");
                                        return;
                                    }
                                    Intent intent = new Intent(ChatActivity.this, VideoPlayerActivity.class);
                                    intent.putExtra("path", file.getPath());
                                    startActivity(intent);
                                }
                            });
                }

                //打开文件
                if (chatBean.getItemType() == ChatBean.FILE_SEND ||
                        chatBean.getItemType() == ChatBean.FILE_RECEIVE) {
                    Message message = chatBean.message;

                    FileContent content = (FileContent) message.getContent();
                    fileName = content.getFileName();
                    String extra = content.getStringExtra("video");
                    if (extra != null) {
                        fileName = message.getServerMessageId().toString() + "." + extra;
                    }
                    String path = content.getLocalPath();
                    if (path != null && new File(path).exists()) {
                        String newPath = VariableName.FILE_DIR + fileName;
                        File file = new File(newPath);
                        if (file.exists() && file.isFile()) {
                            browseDocument(fileName, newPath);
                        } else {
                            dialog.showLoading("");
                            FileHelper.getInstance().copyFile(fileName, path, ChatActivity.this,
                                    new FileHelper.CopyFileCallback() {
                                        @Override
                                        public void copyCallback(Uri uri) {
                                            dialog.dismiss();
                                            browseDocument(fileName, newPath);
                                        }
                                    });
                        }
                    } else {
                        dialog.showLoading("下载文件中");
                        content.downloadFile(message, new DownloadCompletionCallback() {
                            @Override
                            public void onComplete(int i, String s, File file) {
                                dialog.dismiss();
                                if (i == 0) {
                                    Log.i(TAG, "onComplete: 下载成功");
                                }
                            }
                        });
                    }
                }

                //播放录音
                if (chatBean.getItemType() == ChatBean.VOICE_SEND ||
                        chatBean.getItemType() == ChatBean.VOICE_RECEIVE) {
                    playVoiceUtil.playVoice(chatBeanList, position);
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void initConversation() {
        //进入会话
        conversation = JMessageClient.getSingleConversation(username);
        if (null != conversation) {
            conversation = Conversation.createSingleConversation(username);
        }
        conversation.updateConversationExtra("");

        //获取会话记录
        if (conversation.getAllMessage() != null) {
            for (Message msg : conversation.getAllMessage()) {
                addMessage(msg);
            }
        }
    }

    private void initInput() {
        //发送文字或者表情包
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTextMessage(et_emojiIconEditText.getText().toString());
                et_emojiIconEditText.setText("");
            }
        });

        //输入文字后，出现发送按钮，否则隐藏
        et_emojiIconEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    iv_option.setVisibility(View.VISIBLE);
                    tv_send.setVisibility(View.INVISIBLE);
                } else {
                    iv_option.setVisibility(View.INVISIBLE);
                    tv_send.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initOptionEmotion() {
        addFragment(chatOptionFragment);

        addFragment(emojiconsFragment);

        hideFragment(chatOptionFragment);

        hideFragment(emojiconsFragment);

        handler.sendEmptyMessage(VariableName.HIDEN_BOTTOM);

        iv_emoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //软键盘和表情包不能同时出现
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(et_emojiIconEditText.getWindowToken(), 0);
                //强制隐藏键盘

                if (showOption) {
                    showOption = false;
                    hideOption();
                }

                showHideEmoji(!showEmoji);
                showEmoji = !showEmoji;
            }
        });

        iv_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //软键盘和表情包不能同时出现
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(et_emojiIconEditText.getWindowToken(), 0);
                //强制隐藏键盘

                if (showEmoji) {
                    showEmoji = false;
                    showHideEmoji(showEmoji);
                }
                if (showOption) {
                    showOption = false;
                    hideOption();
                } else {
                    showOption = true;
                    showOption();
                }
            }
        });

        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                keyBoardShow = true;

                showEmoji = false;
                showOption = false;

                handler.sendEmptyMessage(VariableName.HIDEN_BOTTOM);
            }

            @Override
            public void keyBoardHide(int height) {
                keyBoardShow = false;
            }
        });
    }

    private void initVoice() {
        playVoiceUtil = new PlayVoiceUtil(this, adapter);

        iv_microphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showSound) {
                    tv_soundTextView.setVisibility(View.INVISIBLE);
                    et_emojiIconEditText.setVisibility(View.VISIBLE);
                    iv_microphone.setImageResource(R.drawable.icon_microphone);
                } else {
                    tv_soundTextView.setVisibility(View.VISIBLE);
                    et_emojiIconEditText.setVisibility(View.INVISIBLE);
                    iv_microphone.setImageResource(R.drawable.icon_softkeyboard);
                }
                showSound = !showSound;
            }
        });

        tv_soundTextView.mConv = conversation;
        tv_soundTextView.onNewMessage = new SoundTextView.OnNewMessage() {
            @Override
            public void newMessage(Message message) {
                if (null == message) return;
                addMessage(message);
                int now = chatBeanList.size();
                chatBeanList.get(now - 1).upload = false;
                adapter.notifyItemChanged(now - 1);
//                adapter.notifyDataSetChanged();

                message.setOnSendCompleteCallback(new BasicCallback() {
                    @Override
                    public void gotResult(int responseCode, String s) {
                        if (responseCode == 0) {
                            chatBeanList.get(now - 1).upload = true;
                            adapter.notifyItemChanged(now - 1);
//                            adapter.notifyDataSetChanged();
                        } else {
                            Log.i(TAG, "gotResult: 发送失败");
                            return;
                        }
                    }
                });
            }
        };
    }

    private void browseDocument(String fileName, String path) {
        try {
            String ext = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            String mime = mimeTypeMap.getMimeTypeFromExtension(ext);
            File file = new File(path);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri uri = FileProvider.getUriForFile(this,
                        "com.example.studentagency.FileProvider",
                        file);
                intent.setDataAndType(uri, mime);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                intent.setDataAndType(Uri.fromFile(file), mime);
            }

            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //消息加入和刷新页面
    private void addMessage(Message message) {
        conversation.updateConversationExtra("");

        if (message.getStatus() == MessageStatus.send_fail) return;

        if (message.getContentType() == ContentType.eventNotification) return;

        if (message.getTargetType() == ConversationType.single) {
            UserInfo userInfo = (UserInfo) message.getTargetInfo();
            String targetId = userInfo.getUserName();
            if (!targetId.equals(username)) return;
        }

        if (message.getContent() instanceof PromptContent) {
            chatBeanList.add(new ChatBean(message, ChatBean.RETRACT));
            adapter.notifyItemInserted(chatBeanList.size() - 1);
            handler.sendEmptyMessageDelayed(VariableName.SCROLL_BOTTOM, 100);
            return;
        }

        switch (message.getContentType()) {
            case text:
                if (message.getDirect() == MessageDirect.send) {
                    chatBeanList.add(new ChatBean(message, ChatBean.TEXT_SEND));
                } else {
                    chatBeanList.add(new ChatBean(message, ChatBean.TEXT_RECEIVE));
                }
                break;
            case image:
                if (message.getDirect() == MessageDirect.send) {
                    chatBeanList.add(new ChatBean(message, ChatBean.IMG_SEND));
                } else {
                    chatBeanList.add(new ChatBean(message, ChatBean.IMG_RECEIVE));
                }
                break;
            case video:
                if (message.getDirect() == MessageDirect.send) {
                    chatBeanList.add(new ChatBean(message, ChatBean.VIDEO_SEND));
                } else {
                    chatBeanList.add(new ChatBean(message, ChatBean.VIDEO_RECEIVE));
                }
                break;
            case voice:
                if (message.getDirect() == MessageDirect.send) {
                    chatBeanList.add(new ChatBean(message, ChatBean.VOICE_SEND));
                } else {
                    chatBeanList.add(new ChatBean(message, ChatBean.VOICE_RECEIVE));
                }
                break;
            case file:
                if (message.getDirect() == MessageDirect.send) {
                    chatBeanList.add(new ChatBean(message, ChatBean.FILE_SEND));
                } else {
                    chatBeanList.add(new ChatBean(message, ChatBean.FILE_RECEIVE));
                }
                break;
            case location:
                if (message.getDirect() == MessageDirect.send) {
                    chatBeanList.add(new ChatBean(message, ChatBean.ADDRESS_SEND));
                } else {
                    chatBeanList.add(new ChatBean(message, ChatBean.ADDRESS_RECEIVE));
                }
                break;
            case custom:
                String type = ((CustomContent) message.getContent()).getStringValue(VariableName.TYPE);
                if (TextUtils.isEmpty(type)) return;

                if (type.equals(VariableName.RED_PACKEGE)) {
                    if (message.getDirect() == MessageDirect.send) {
                        chatBeanList.add(new ChatBean(message, ChatBean.REDP_SEND));
                    } else {
                        chatBeanList.add(new ChatBean(message, ChatBean.REDP_RECEIVE));
                    }
                } else if (type.equals(VariableName.CARD)) {
                    if (message.getDirect() == MessageDirect.send) {
                        chatBeanList.add(new ChatBean(message, ChatBean.CARD_SEND));
                    } else {
                        chatBeanList.add(new ChatBean(message, ChatBean.CARD_RECEIVE));
                    }
                } else if (type.equals(VariableName.INVITATION)) {
                    if (message.getDirect() == MessageDirect.send) {
                        chatBeanList.add(new ChatBean(message, ChatBean.GROUP_INVITA_SEND));
                    } else {
                        chatBeanList.add(new ChatBean(message, ChatBean.GROUP_INVITA_RECEIVE));
                    }
                } else if (type.equals(VariableName.VIDEO_PHONE)) {
                    if (message.getDirect() == MessageDirect.send) {
                        chatBeanList.add(new ChatBean(message, ChatBean.VIDEO_PHONE_SEND));
                    } else {
                        chatBeanList.add(new ChatBean(message, ChatBean.VIDEO_PHONE_RECEIVE));
                    }
                }
                break;
        }

        adapter.notifyItemInserted(chatBeanList.size() - 1);
        handler.sendEmptyMessageDelayed(VariableName.SCROLL_BOTTOM, 100);
    }

    private void sendTextMessage(String text) {
        if (!TextUtils.isEmpty(text)) {
            String content = text;
            TextContent textContent = new TextContent(content);
            Message msg = conversation.createSendMessage(textContent);
            ChatBean bean = new ChatBean(msg, ChatBean.TEXT_SEND);
            bean.upload = false;
            chatBeanList.add(bean);

            adapter.notifyItemInserted(chatBeanList.size() - 1);
//            adapter.notifyDataSetChanged();

            int now = chatBeanList.size();

            handler.sendEmptyMessageDelayed(VariableName.SCROLL_BOTTOM, 100);

            msg.setOnSendCompleteCallback(new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    if (i != 0) {
                        return;
                    }
                    chatBeanList.get(now - 1).upload = true;
                    adapter.notifyItemChanged(chatBeanList.size() - 1);
//                    adapter.notifyDataSetChanged();
                }
            });

            JMessageClient.sendMessage(msg);
        }
    }

    private void addFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout, fragment)
                .commit();
    }

    private void hideFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .hide(fragment)
                .commit();
    }

    //隐藏多种选择
    private void hideOption() {
        handler.sendEmptyMessage(VariableName.HIDEN_BOTTOM);
        hideFragment(chatOptionFragment);
    }

    //控制表情包的出现和隐藏
    private void showHideEmoji(boolean showEmoji) {
        if (showEmoji) {
            handler.sendEmptyMessage(VariableName.SHOW_BOTTOM);

            showFragment(emojiconsFragment);
        } else {
            handler.sendEmptyMessage(VariableName.HIDEN_BOTTOM);

            hideFragment(emojiconsFragment);
        }
    }

    private void showOption() {
        handler.sendEmptyMessage(VariableName.SHOW_BOTTOM);

        showFragment(chatOptionFragment);
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .show(fragment)
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        handler.removeCallbacksAndMessages(null);

        if (playVoiceUtil != null) {
            playVoiceUtil.mp.stop();
        }
    }

    //接受了在线消息
    public void onEventMainThread(MessageEvent event) {
        addMessage(event.getMessage());
    }

    //接受了离线消息
    public void onEvent(OfflineMessageEvent event) {
        for (Message message : event.getOfflineMessageList())
            addMessage(message);
    }

    //消息被对方撤回
    public void onEvent(MessageRetractEvent event) {
        for (ChatBean bean : chatBeanList) {
            if (event.getRetractedMessage().getId() == bean.message.getId()) {
                bean.itemType = ChatBean.RETRACT;
                adapter.notifyItemChanged(chatBeanList.indexOf(bean));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) return;

        if (requestCode == VariableName.REQUEST_CODE_TWO) {
            //清空聊天记录的反馈
            chatBeanList.clear();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        JMessageClient.exitConversation();
    }

    @Override
    protected void onResume() {
        super.onResume();

        JMessageClient.enterSingleConversation(username);
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(et_emojiIconEditText, emojicon);
    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(et_emojiIconEditText);
    }

    public void addMessageRefresh(ChatBean bean) {
        chatBeanList.add(bean);
        adapter.notifyItemInserted(chatBeanList.size() - 1);
        handler.sendEmptyMessageDelayed(VariableName.SCROLL_BOTTOM, 100);
    }

    private static class MyHandler extends Handler {

        private WeakReference<Context> reference;

        public MyHandler(Context context) {
            this.reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);

            ChatActivity activity = (ChatActivity) reference.get();
            if (null != activity) {
                switch (msg.what) {
                    case VariableName.SCROLL_BOTTOM:
                        activity.recyclerView.scrollToPosition(activity.chatBeanList.size() - 1);
                        break;
                    case VariableName.HIDEN_BOTTOM:
                        ViewGroup.LayoutParams params = activity.frame_layout.getLayoutParams();
                        params.height = Utils.dp2px(activity, 0f);
                        activity.frame_layout.setLayoutParams(params);
                        break;
                    case VariableName.SHOW_BOTTOM:
                        ViewGroup.LayoutParams params1 = activity.frame_layout.getLayoutParams();
                        params1.height = Utils.dp2px(activity, 270f);
                        activity.frame_layout.setLayoutParams(params1);
                        break;
                }
            }
        }
    }
}
