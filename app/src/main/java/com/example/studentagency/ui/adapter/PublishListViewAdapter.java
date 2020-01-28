package com.example.studentagency.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.studentagency.R;
import com.example.studentagency.bean.SpinnerBean;

import java.util.List;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/01/13
 * desc:
 */
public class PublishListViewAdapter extends BaseAdapter {

    private List<SpinnerBean> dataList;
    private Context context;

    public PublishListViewAdapter(List<SpinnerBean> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_spinner_item,parent,false);
            holder.iv_typeIcon = convertView.findViewById(R.id.iv_typeIcon);
            holder.tv_typeText = convertView.findViewById(R.id.tv_typeText);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.iv_typeIcon.setImageResource(dataList.get(position).getIcon());
        holder.tv_typeText.setText(dataList.get(position).getTypeText());
        return convertView;
    }

    private class ViewHolder{
        ImageView iv_typeIcon;
        TextView tv_typeText;
    }
}
