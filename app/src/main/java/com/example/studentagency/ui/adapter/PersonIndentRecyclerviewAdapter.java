package com.example.studentagency.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.studentagency.R;
import com.example.studentagency.bean.IndentBean;
import com.example.studentagency.viewholder.PersonIndentActivity.ErrorViewHolder;
import com.example.studentagency.viewholder.PersonIndentActivity.ItemViewHolder;
import com.example.studentagency.viewholder.PersonIndentActivity.NodataViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/02/19
 * desc:
 */
public class PersonIndentRecyclerviewAdapter extends RecyclerView.Adapter {

    private static final String TAG = "PersonIndentRecyclervie";
    private static final int RECYCLE_ITEM = 0;
    private static final int RECYCLE_NODATA = 1;
    private static final int RECYCLE_ERROR = 2;
    private static final int INDENT_PUBLISH = 3;
    private static final int INDENT_ACCEPT = 4;

    private List<Object> dataList;
    private int indentType = INDENT_PUBLISH;

    private AdapterClickListener adapterClickListener;

    /**
     * @param dataList
     * @param indentType:3 代表是已发布的订单，
     *                     4 代表是已接取的订单
     */
    public PersonIndentRecyclerviewAdapter(List<Object> dataList, int indentType) {
        this.dataList = dataList;
        this.indentType = indentType;
    }

    public void setAdapterClickListener(AdapterClickListener adapterClickListener) {
        this.adapterClickListener = adapterClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View view;
        if (RECYCLE_ITEM == viewType) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_person_indent_item, parent, false);
            holder = new ItemViewHolder(view);
        } else if (RECYCLE_NODATA == viewType) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_person_indent_nodata, parent, false);
            holder = new NodataViewHolder(view);
        } else if (RECYCLE_ERROR == viewType) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_person_indent_error, parent, false);
            holder = new ErrorViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            IndentBean indentBean = (IndentBean) dataList.get(position);
            ItemViewHolder itemViewHolder = ((ItemViewHolder) holder);
            itemViewHolder.tv_price.setText("￥" + indentBean.getPrice());

            /**
             * 类型
             */
            if (indentBean.getType() == 0) {
                itemViewHolder.tv_type.setText("代购");
            } else if (indentBean.getType() == 1) {
                itemViewHolder.tv_type.setText("代拿快递");
            } else {
                itemViewHolder.tv_type.setText("其他代办");
            }

            /**
             * 代办状态和设置按钮文字
             */
            itemViewHolder.btn_num1.setVisibility(View.VISIBLE);
            itemViewHolder.btn_num2.setVisibility(View.VISIBLE);
            if (indentType == INDENT_PUBLISH) {
                if (indentBean.getState() == 0) {
                    itemViewHolder.tv_state.setText("未接单");

                    itemViewHolder.btn_num1.setText("取消");
                    itemViewHolder.btn_num2.setVisibility(View.GONE);
                } else if (indentBean.getState() == 1) {
                    itemViewHolder.tv_state.setText("进行中");

                    itemViewHolder.btn_num1.setText("取消");
                    itemViewHolder.btn_num2.setText("确认送达");
                } else if (indentBean.getState() == 2) {
                    itemViewHolder.tv_state.setText("已完成未评价");

                    itemViewHolder.btn_num1.setText("评价");
                    itemViewHolder.btn_num2.setText("删除");
                } else {
                    itemViewHolder.tv_state.setText("已完成已评价");

                    itemViewHolder.btn_num1.setText("删除");
                    itemViewHolder.btn_num2.setVisibility(View.GONE);
                }
            } else {
                if (indentBean.getState() == 0) {
                    itemViewHolder.tv_state.setText("进行中");

                    itemViewHolder.btn_num1.setText("取消");
                    itemViewHolder.btn_num2.setText("确认送达");
                } else if (indentBean.getState() == 1) {
                    itemViewHolder.tv_state.setText("已完成未评价");

                    itemViewHolder.btn_num1.setText("删除");
                    itemViewHolder.btn_num2.setVisibility(View.GONE);
                } else {//indentBean.getState() == 2
                    itemViewHolder.tv_state.setText("已完成已评价");

                    itemViewHolder.btn_num1.setText("删除");
                    itemViewHolder.btn_num2.setVisibility(View.GONE);
                }
            }

            /**
             * 详情
             */
            itemViewHolder.tv_description.setText(indentBean.getDescription());

            /**
             * 收货地点
             */
            itemViewHolder.tv_address.setText(indentBean.getAddress());

            /**
             * 送达时间
             */
            itemViewHolder.tv_planTime.setText(indentBean.getPlanTime());

            /**
             * 跳转订单跳转至订单详情
             */
            itemViewHolder.layout_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (adapterClickListener != null) {
                        if (indentType == INDENT_PUBLISH) {
                            if (indentBean.getState() == 0) {//别人未接单
                                if (itemViewHolder.btn_num1.getText().equals("取消")) {
                                    adapterClickListener.clickItem(110, 200, position, null, null, indentBean.getIndentId(), indentBean.getPrice());
                                }
                            } else if (indentBean.getState() == 1) {//接单中
                                if (itemViewHolder.btn_num1.getText().equals("取消")) {
                                    adapterClickListener.clickItem(110, 201, position, null, null, indentBean.getIndentId(), indentBean.getPrice());
                                }
                            } else if (indentBean.getState() == 2) {//已完成未评价
                                if (itemViewHolder.btn_num1.getText().equals("评价")) {
                                    adapterClickListener.clickItem(110, 202, position, null, null, indentBean.getIndentId(), indentBean.getPrice());
                                }
                            } else {//indentBean.getState() == 3 已完成已删除
                                if (itemViewHolder.btn_num1.getText().equals("删除")) {
                                    adapterClickListener.clickItem(110, 203, position, null, null, indentBean.getIndentId(), indentBean.getPrice());
                                }
                            }
                        } else {
                            if (indentBean.getState() == 0) {
                                if (itemViewHolder.btn_num1.getText().equals("取消")) {//接单中
                                    adapterClickListener.clickItem(110, 204, position, null, null, indentBean.getIndentId(), indentBean.getPrice());
                                }
                            } else if (indentBean.getState() == 1) {//已完成未评价
                                if (itemViewHolder.btn_num1.getText().equals("删除")) {
                                    adapterClickListener.clickItem(110, 205, position, null, null, indentBean.getIndentId(), indentBean.getPrice());
                                }
                            } else {//indentBean.getState() == 2 已完成已评价
                                if (itemViewHolder.btn_num1.getText().equals("删除")) {
                                    adapterClickListener.clickItem(110, 206, position, null, null, indentBean.getIndentId(), indentBean.getPrice());
                                }
                            }
                        }
                    }
                }
            });

            bindButton(holder, indentBean,position);
        }
    }

    private void bindButton(RecyclerView.ViewHolder holder, IndentBean indentBean,int position) {
        ItemViewHolder itemViewHolder = ((ItemViewHolder) holder);
        //按钮1
        Button btn_num1 = itemViewHolder.btn_num1;
        //按钮2
        Button btn_num2 = itemViewHolder.btn_num2;

        btn_num1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapterClickListener != null) {
                    if (indentType == INDENT_PUBLISH) {
                        if (indentBean.getState() == 0) {//别人未接单
                            if (btn_num1.getText().equals("取消")) {
                                adapterClickListener.clickItem(100, 200, position, btn_num1, btn_num2, indentBean.getIndentId(), indentBean.getPrice());
                            }
                        } else if (indentBean.getState() == 1) {//接单中
                            if (btn_num1.getText().equals("取消")) {
                                adapterClickListener.clickItem(101, 201, position, btn_num1, btn_num2, indentBean.getIndentId(), indentBean.getPrice());
                            }
                        } else if (indentBean.getState() == 2) {//已完成未评价
                            if (btn_num1.getText().equals("评价")) {
                                adapterClickListener.clickItem(103, 202, position, btn_num1, btn_num2, indentBean.getIndentId(), indentBean.getPrice());
                            }
                        } else {//indentBean.getState() == 3 已完成已删除
                            if (btn_num1.getText().equals("删除")) {
                                adapterClickListener.clickItem(105, 203, position, btn_num1, btn_num2, indentBean.getIndentId(), indentBean.getPrice());
                            }
                        }
                    } else {
                        if (indentBean.getState() == 0) {
                            if (btn_num1.getText().equals("取消")) {//接单中
                                adapterClickListener.clickItem(106, 204, position, btn_num1, btn_num2, indentBean.getIndentId(), indentBean.getPrice());
                            }
                        } else if (indentBean.getState() == 1) {//已完成未评价
                            if (btn_num1.getText().equals("删除")) {
                                adapterClickListener.clickItem(108, 205, position, btn_num1, btn_num2, indentBean.getIndentId(), indentBean.getPrice());
                            }
                        } else {//indentBean.getState() == 2 已完成已评价
                            if (btn_num1.getText().equals("删除")) {
                                adapterClickListener.clickItem(109, 206, position, btn_num1, btn_num2, indentBean.getIndentId(), indentBean.getPrice());
                            }
                        }
                    }
                }
            }
        });

        btn_num2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapterClickListener != null) {
                    if (indentType == INDENT_PUBLISH) {
                        if (indentBean.getState() == 1) {
                            if (btn_num2.getText().equals("确认送达")) {
                                adapterClickListener.clickItem(102, 201, position, btn_num1, btn_num2, indentBean.getIndentId(), indentBean.getPrice());
                            }
                        } else {//indentBean.getState() == 2
                            if (btn_num2.getText().equals("删除")) {
                                adapterClickListener.clickItem(104, 202, position, btn_num1, btn_num2, indentBean.getIndentId(), indentBean.getPrice());
                            }
                        }
                    } else {
                        if (indentBean.getState() == 0) {
                            if (btn_num2.getText().equals("确认送达")) {
                                adapterClickListener.clickItem(107, 204, position, btn_num1, btn_num2, indentBean.getIndentId(), indentBean.getPrice());
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (dataList.get(position) instanceof IndentBean) {
            return RECYCLE_ITEM;
        } else if (dataList.get(position) == "获取失败") {
            return RECYCLE_ERROR;
        } else if (dataList.get(position) == "暂无数据") {
            return RECYCLE_NODATA;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public void update(List<Object> newDataList) {
        Log.i(TAG, "update: newDataList.size>>>>>" + newDataList.size() + "\n before dataList.size>>>>>" + dataList.size());

        dataList.clear();
        dataList.addAll(newDataList);

        Log.i(TAG, "update: after dataList.size>>>>>" + dataList.size());

        notifyDataSetChanged();
    }

    public void loadMoreData(List<Object> tempList) {
        dataList.addAll(tempList);
        notifyDataSetChanged();
    }

    public void removeIndent(int clickedPosition) {
        Log.i(TAG, "cancelIndentNotTaken: clickedPosition>>>>>"+clickedPosition);
        dataList.remove(clickedPosition);

        notifyItemRemoved(clickedPosition);
        notifyDataSetChanged();
    }

    public void ensureAcceptGoods(int clickedPosition) {
        IndentBean oldIndentBean = (IndentBean) dataList.get(clickedPosition);

//        IndentBean newIndentBean = oldIndentBean;
//        newIndentBean.setPrice(oldIndentBean.getPrice());
//        newIndentBean.setType(oldIndentBean.getType());
//
//        newIndentBean.setDescription(oldIndentBean.getDescription());
//        newIndentBean.setAddress(oldIndentBean.getAddress());
//        newIndentBean.setPlanTime(oldIndentBean.getPlanTime());
        if (INDENT_PUBLISH == indentType){
            oldIndentBean.setState(2);
        }else {
            oldIndentBean.setState(1);
        }

        dataList.set(clickedPosition,oldIndentBean);
        notifyItemChanged(clickedPosition);
        notifyDataSetChanged();
    }

    public void comment(int clickedPosition) {
        IndentBean oldIndentBean = (IndentBean) dataList.get(clickedPosition);

//        IndentBean newIndentBean = oldIndentBean;
//        newIndentBean.setPrice(oldIndentBean.getPrice());
//        newIndentBean.setType(oldIndentBean.getType());
//
//        newIndentBean.setDescription(oldIndentBean.getDescription());
//        newIndentBean.setAddress(oldIndentBean.getAddress());
//        newIndentBean.setPlanTime(oldIndentBean.getPlanTime());
        if (INDENT_PUBLISH == indentType){
            oldIndentBean.setState(3);
        }else {
            oldIndentBean.setState(2);
        }

        dataList.set(clickedPosition,oldIndentBean);
        notifyItemChanged(clickedPosition);
        notifyDataSetChanged();
    }

    public interface AdapterClickListener {

        /**
         * @param what     已发布-->
         *                 别人未接(state:200)：取消（100）
         *                 接单中(state:201)：  取消（102），确认送达（103）
         *                 已完成未评价(202)：  评价（104），删除（105）
         *                 已完成已评价(203)：  删除（106）
         *                 <p>
         *                 已接取-->
         *                 接单中(204)：  取消（107），确认送达（108）
         *                 已完成未评价(205)：  删除（109）
         *                 已完成已评价(206)：  删除（110）
         * @param state
         * @param btn_num1
         * @param btn_num2
         * @param indentId
         * @param price
         */
        void clickItem(int what, int state, int position, Button btn_num1, Button btn_num2, int indentId, String price);
    }
}
