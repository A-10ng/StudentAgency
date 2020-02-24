package com.example.studentagency.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.studentagency.R;
import com.example.studentagency.bean.AddressBean;
import com.example.studentagency.viewholder.AddressActivity.ErrorViewHolder;
import com.example.studentagency.viewholder.AddressActivity.ItemViewHolder;
import com.example.studentagency.viewholder.AddressActivity.NodataViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/24
 * desc:
 */
public class AddressActivityRecyclerviewAdapter extends RecyclerView.Adapter {

    private static final String TAG = "AddressActivityRecycler";
    private static final int ITEM_ADDRESS = 1;
    private static final int ITEM_NODATA = 2;
    private static final int ITEM_ERROR = 3;
    private List<Object> dataList;
    private OnClickItemListener clickItemListener;

    public AddressActivityRecyclerviewAdapter(List<Object> dataList) {
        this.dataList = dataList;
    }

    public void setClickItemListener(OnClickItemListener clickItemListener) {
        this.clickItemListener = clickItemListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View view;
        if (ITEM_ADDRESS == viewType) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_address_item, parent, false);
            holder = new ItemViewHolder(view);
        } else if (ITEM_NODATA == viewType) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_address_nodata, parent, false);
            holder = new NodataViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_credit_record_error, parent, false);
            holder = new ErrorViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            AddressBean bean = (AddressBean) dataList.get(position);

            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.item_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickItemListener != null) {
                        clickItemListener.clickItem(101, bean,position,null);
                    }
                }
            });

            String tag = bean.getTag();
            if (tag.equals("默认")) {
                itemViewHolder.tv_defaultTag.setVisibility(View.VISIBLE);
                itemViewHolder.tv_tag.setText(bean.getUsername().charAt(0)+"");//如果是默认标签，则取用户名第一个字符
            } else if (tag.equals("")){
                itemViewHolder.tv_defaultTag.setVisibility(View.GONE);
                itemViewHolder.tv_tag.setText(bean.getUsername().charAt(0)+"");//如果是默认标签，则取用户名第一个字符
            }
            else {
                itemViewHolder.tv_defaultTag.setVisibility(View.GONE);
                itemViewHolder.tv_tag.setText(tag.charAt(0)+"");//如果是自定义标签，则取其第一个字符
            }

            itemViewHolder.tv_username.setText(bean.getUsername());
            itemViewHolder.tv_address.setText(bean.getAddress());

            itemViewHolder.tv_editAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickItemListener != null) {
                        clickItemListener.clickItem(102,bean,position,itemViewHolder.tv_editAddress);
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (dataList.get(position) instanceof AddressBean) {
            return ITEM_ADDRESS;
        } else if (dataList.get(position) == "暂无数据") {
            return ITEM_NODATA;
        } else if (dataList.get(position) == "获取失败") {
            return ITEM_ERROR;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public void updateAddress(List<AddressBean> addressBeans) {
        dataList.clear();
        dataList.addAll(addressBeans);
        notifyDataSetChanged();
    }

    public void updateFail() {
        dataList.clear();
        dataList.add("获取失败");
        notifyDataSetChanged();
    }

    public void updateItem(String changedTag, String changedAddress, int addressEdittingPosition,boolean isDefault) {
        if (isDefault){
            removeDefaultTag();
        }

        AddressBean addressBean = (AddressBean) dataList.get(addressEdittingPosition);
        addressBean.setAddress(changedAddress);
        addressBean.setTag(changedTag);
        dataList.set(addressEdittingPosition,addressBean);

        notifyItemChanged(addressEdittingPosition);
        notifyDataSetChanged();
    }

    public void addAddress(AddressBean addressBean,boolean isDefault) {
        dataList.add(addressBean);
        if (isDefault){
            removeDefaultTag();
        }
        notifyItemInserted(dataList.size());
        notifyDataSetChanged();
    }

    private void removeDefaultTag() {
        for (int i = 0; i < dataList.size(); i++) {
            if (((AddressBean)dataList.get(i)).getTag().equals("默认")){
                Log.i(TAG, "removeDefaultTag: pos>>>>>"+i);
                ((AddressBean)dataList.get(i)).setTag("");
                return;
            }
        }
    }

    public void deleteAddress(int clickedPosition) {
        dataList.remove(clickedPosition);
        notifyDataSetChanged();
    }

    public interface OnClickItemListener {
        /**
         *
         * @param type 101代表点击的整个ITEM，102代表点击的是“编辑”
         * @param addressBean
         * @param position
         */
        void clickItem(int type, AddressBean addressBean, int position, TextView tv_editAddress);
    }
}
