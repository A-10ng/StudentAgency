package com.example.studentagency.ui.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.example.studentagency.R;
import com.example.studentagency.bean.SpinnerBean;
import com.example.studentagency.ui.adapter.PublishListViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/01/13
 * desc:
 */
public class SpinnerPopupWindow extends PopupWindow {

    private ListView listView;
    private List<SpinnerBean> list;
    private PublishListViewAdapter adapter;
    private Context context;
    private LayoutInflater inflater;

    public SpinnerPopupWindow(List<SpinnerBean> list, Context context, AdapterView.OnItemClickListener itemClickListener) {
        this.list = list;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        initListView(itemClickListener);
    }

    private void initListView(AdapterView.OnItemClickListener itemClickListener) {
        View view = inflater.inflate(R.layout.layout_popupwindow, null);
        setContentView(view);

        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        setFocusable(true);

        ColorDrawable colorDrawable = new ColorDrawable(0x000000);
        setBackgroundDrawable(colorDrawable);

        listView = view.findViewById(R.id.listView);
        adapter = new PublishListViewAdapter(list, context);
        listView.setOnItemClickListener(itemClickListener);
        listView.setAdapter(adapter);
    }
}
