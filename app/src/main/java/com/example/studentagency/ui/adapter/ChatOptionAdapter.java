package com.example.studentagency.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.studentagency.R;
import com.example.studentagency.bean.ChatOptionBean;

import java.util.List;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/27
 * desc:
 */
public class ChatOptionAdapter extends BaseQuickAdapter<ChatOptionBean, BaseViewHolder> {

    public ChatOptionAdapter(int layoutResId, List<ChatOptionBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChatOptionBean item) {
        helper.setImageResource(R.id.iv,item.img);
    }
}
