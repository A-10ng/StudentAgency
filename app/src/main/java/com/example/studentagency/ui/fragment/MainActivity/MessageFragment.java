package com.example.studentagency.ui.fragment.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studentagency.R;
import com.example.studentagency.ui.activity.ChatActivity;
import com.example.studentagency.ui.activity.MainActivity;
import com.example.studentagency.ui.activity.MyApp;
import com.example.studentagency.ui.adapter.MessageFragmentRecyclerviewAdapter;
import com.example.studentagency.utils.VariableName;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/11/02
 * desc:
 */
public class MessageFragment extends Fragment {

    private static final String TAG = "MessageFragment";

    //根视图
    private View root;

    //recyclerview
    private RecyclerView recyclerView;
    private List<Object> conversationList = new ArrayList<>();
    private MessageFragmentRecyclerviewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_message, container, false);

        initRecyclerview();

        //订阅接收消息,子类只要重写onEvent就能收到消息
        JMessageClient.registerEventReceiver(this);

        return root;
    }

    private void initRecyclerview() {
        recyclerView = root.findViewById(R.id.recyclerview);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        adapter = new MessageFragmentRecyclerviewAdapter(conversationList);
        adapter.setOnClickItemListener(new MessageFragmentRecyclerviewAdapter.OnClickItemListener() {
            @Override
            public void clickItem(int position) {
                Conversation conversation = (Conversation) conversationList.get(position);

                conversation.updateConversationExtra("");
                adapter.notifyItemChanged(position);

                UserInfo userInfo = (UserInfo) conversation.getTargetInfo();

                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("nickname", userInfo.getNickname());
                intent.putExtra("username", userInfo.getUserName());
                startActivity(intent);

                checkNew();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        refresh();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK){
            return;
        }

        switch (requestCode){
            case VariableName.REQUEST_CODE_ONE:
            refresh();
            break;
        }
    }

    private void refresh() {
        conversationList.clear();
        if (MyApp.hadLogin) {
            List<Conversation> conversations = JMessageClient.getConversationList();
            Log.i(TAG, "refresh: conversations.size>>>>>" + conversations.size());

            if (conversations.isEmpty()) {
                conversationList.add("错误");
            } else {
//                if (conversations.size() == 1){
//                    if (((UserInfo)conversations.get(0).getTargetInfo()).getNickname().equals("")){
//                        conversationList.add("错误");
//                    }else {
//                        conversationList.addAll(conversations);
//                    }
//                }else {
//                    conversationList.addAll(conversations);
//                }
                conversationList.addAll(conversations);
            }
        } else {
            conversationList.add("错误");
        }
        adapter.notifyDataSetChanged();

        checkNew();
    }

    //接受了在线消息
    public void onEventMainThread(MessageEvent event) {
        boolean handlable = false;
        Message msg = event.getMessage();
        if (msg.getTargetType() == ConversationType.single) {
            UserInfo userInfo = (UserInfo) msg.getTargetInfo();

            for (Object object : conversationList) {
                if (object instanceof Conversation){
                    Conversation conversation = (Conversation) object;
                    if (conversation.getType() == ConversationType.single) {
                        UserInfo userInfo1 = (UserInfo) conversation.getTargetInfo();

                        if (userInfo1.getUserName().equals(userInfo.getUserName())) {

                            conversation.updateConversationExtra(VariableName.NEW_MESSAGE);

                            handlable = true;

                            adapter.notifyItemChanged(conversationList.indexOf(conversation));
                        }
                    }
                }else {
                    conversationList.clear();
                    adapter.notifyDataSetChanged();
                }
            }

            if (!handlable) {
                Conversation conversation = JMessageClient.getSingleConversation(userInfo.getUserName(),
                        VariableName.JIGUANG_APP_KEY);
                if (conversation.getTargetInfo() instanceof UserInfo){
                    conversation.updateConversationExtra(VariableName.NEW_MESSAGE);
                    conversationList.add(conversation);
                }
                adapter.notifyItemInserted(conversationList.size() - 1);
                adapter.notifyDataSetChanged();
            }
        }

        checkNew();
    }

    public void checkNew() {
        if (getActivity() == null) {
            return;
        }
        boolean hasNew = false;

        for (Object object : conversationList) {
            if (object instanceof Conversation){
                if (((Conversation)object).getExtra().equals(VariableName.NEW_MESSAGE)) {
                    setNew(true);
                    hasNew = true;
                }
            }
        }

        if (!hasNew) {
            setNew(false);
        }
    }

    private void setNew(boolean news) {
        if (getActivity() == null) return;
        ((MainActivity)getActivity()).setNew(news);
    }
}
