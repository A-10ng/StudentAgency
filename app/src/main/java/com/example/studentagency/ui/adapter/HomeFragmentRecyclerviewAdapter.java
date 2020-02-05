package com.example.studentagency.ui.adapter;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.studentagency.R;
import com.example.studentagency.bean.NewsBean;
import com.example.studentagency.bean.ClassifyBean;
import com.example.studentagency.bean.IndentBean;
import com.example.studentagency.viewholder.HomeFragment.BannerViewHolder;
import com.example.studentagency.viewholder.HomeFragment.ClassifyViewHolder;
import com.example.studentagency.viewholder.HomeFragment.IndentLoadErrorViewHolder;
import com.example.studentagency.viewholder.HomeFragment.IndentViewHolder;

import com.zhengsr.viewpagerlib.bean.PageBean;
import com.zhengsr.viewpagerlib.callback.PageHelperListener;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/12/17
 * desc:
 */
public class HomeFragmentRecyclerviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //标识类型
    public static final int ITEM_BANNER = 0;
    public static final int ITEM_CLASSIFY = 1;
    public static final int ITEM_INDENTS = 2;
    public static final int ITEM_INDENTS_ERROR = 3;
    private static final String TAG = "MyRecyclerviewAdapter";
    PageBean pageBean;
    private List<Object> mDataList;
    private List<NewsBean> newsBeanList;
    private onBannerItemClickListener bannerItemClickListener;
    private onClassifyItemClickListener classifyItemClickListener;
    private onIndentItemClickListener indentItemClickListener;

    public HomeFragmentRecyclerviewAdapter(List<Object> mDataList, List<NewsBean> newsBeanList,
                                           onBannerItemClickListener bannerItemClickListener, onClassifyItemClickListener classifyItemClickListener, onIndentItemClickListener indentItemClickListener) {
        this.mDataList = mDataList;
        this.newsBeanList = newsBeanList;
        this.bannerItemClickListener = bannerItemClickListener;
        this.classifyItemClickListener = classifyItemClickListener;
        this.indentItemClickListener = indentItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;

        //如果是订单的viewHolder
        if (viewType == ITEM_INDENTS) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home_indent_item, parent, false);
            viewHolder = new IndentViewHolder(view);
        }
        //如果是分类的viewHolder
        else if (viewType == ITEM_CLASSIFY) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_classify, parent, false);
            viewHolder = new ClassifyViewHolder(view);
        }
        //如果是轮播图的viewHolder
        else if (viewType == ITEM_BANNER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_banner, parent, false);
            viewHolder = new BannerViewHolder(view);
        } else if (viewType == ITEM_INDENTS_ERROR) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home_indent_loaderror, parent, false);
            viewHolder = new IndentLoadErrorViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        //第一个if先判断是不是indentViewHolder，因为它占用的item数量多，速度应该会稍快点
        if (holder instanceof IndentViewHolder) {
            final IndentBean bean = (IndentBean) mDataList.get(position);

            RequestOptions requestOptions = RequestOptions.circleCropTransform();
            Glide.with(((IndentViewHolder) holder).getView())
                    .load(bean.getAvatar())
                    .placeholder(R.drawable.placeholder_pic)
                    .apply(requestOptions)
                    .into(((IndentViewHolder) holder).iv_avatar);

            ((IndentViewHolder) holder).tv_username.setText(bean.getUsername());
            if (3 == bean.getVerifyState()) {
                ((IndentViewHolder) holder).iv_verifyState.setImageResource(R.drawable.verified);
            } else {
                ((IndentViewHolder) holder).iv_verifyState.setImageResource(R.drawable.unverified);
            }
            ((IndentViewHolder) holder).tv_description.setText(bean.getDescription());
            ((IndentViewHolder) holder).tv_price.setText(bean.getPrice());
            ((IndentViewHolder) holder).tv_address.setText(bean.getAddress());

            ((IndentViewHolder) holder).item_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (indentItemClickListener != null) {
                        indentItemClickListener.onIndentItemClick(bean.getIndentId(), position);
                    }
                }
            });
        }
        //如果是分类类型的
        else if (holder instanceof ClassifyViewHolder) {
            ClassifyBean classifyBean = (ClassifyBean) mDataList.get(position);
            ((ClassifyViewHolder) holder).iv_shopping.setImageResource(classifyBean.getShoppingPic());
            ((ClassifyViewHolder) holder).tv_shopping.setText(classifyBean.getShoppingTxt());
            ((ClassifyViewHolder) holder).iv_delivery.setImageResource(classifyBean.getDeliveryPic());
            ((ClassifyViewHolder) holder).tv_delivery.setText(classifyBean.getDeliveryTxt());
            ((ClassifyViewHolder) holder).iv_others.setImageResource(classifyBean.getOthersPic());
            ((ClassifyViewHolder) holder).tv_others.setText(classifyBean.getOthersTxt());

            ((ClassifyViewHolder) holder).root_shopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != classifyItemClickListener) {
                        classifyItemClickListener.onClassifyItemClick(0);
                    }
                }
            });
            ((ClassifyViewHolder) holder).root_delivery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != classifyItemClickListener) {
                        classifyItemClickListener.onClassifyItemClick(1);
                    }
                }
            });
            ((ClassifyViewHolder) holder).root_others.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != classifyItemClickListener) {
                        classifyItemClickListener.onClassifyItemClick(2);
                    }
                }
            });
        }
        //如果是轮播图类型的
        else if (holder instanceof BannerViewHolder) {
            //如果pageBean为空，则将indicator绑定到viewPager上，不会每次滑到轮播图都会加载一次indicator
            if (null == pageBean) {
                pageBean = new PageBean.Builder<NewsBean>()
                        .data(newsBeanList)
                        .indicator(((BannerViewHolder) holder).zoomIndicator)
                        .builder();
            } else {
                pageBean = new PageBean.Builder<NewsBean>()
                        .data(newsBeanList)
                        .builder();
            }

            ((BannerViewHolder) holder).bannerViewPager.setPageListener(pageBean, R.layout.banner_item_layout, new PageHelperListener<NewsBean>() {
                @Override
                public void getItemView(final View view, final NewsBean bean) {
                    ImageView imageView = view.findViewById(R.id.loop_icon);
                    imageView.setScaleType(ImageView.ScaleType.CENTER);
                    Glide.with(view)
                            .load(bean.getNewsPic())
                            .placeholder(R.drawable.placeholder_pic)
                            .into(imageView);
                    TextView textView = view.findViewById(R.id.loop_title);
                    textView.setText(bean.getTitle());

                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (bannerItemClickListener != null) {
                                bannerItemClickListener.onBannerItemClick(view, bean);
                            }
                        }
                    });
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        //如果当前记录是订单类型的
        if (mDataList.get(position) instanceof IndentBean) {
            return ITEM_INDENTS;
        }
        //如果当前记录是分类类型的
        else if (mDataList.get(position) instanceof ClassifyBean) {
            return ITEM_CLASSIFY;
        }
        //如果当前记录是轮播图类型的
        else if (mDataList.get(position) instanceof NewsBean) {
            return ITEM_BANNER;
        } else if (mDataList.get(position) instanceof String) {
            return ITEM_INDENTS_ERROR;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return (mDataList == null ? 0 : mDataList.size());
    }

    public void addData(List<Object> newList) {
        int position = mDataList.size();
        mDataList.addAll(position, newList);
        notifyItemInserted(position);
    }

    public void setBannerData(List<NewsBean> newsBeanList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.newsBeanList.size(); i++) {
            stringBuilder.append(this.newsBeanList.get(i).getNewsPic() + "/ \n");
        }

        StringBuilder stringBuilder1 = new StringBuilder();
        for (int i = 0; i < this.newsBeanList.size(); i++) {
            stringBuilder1.append(newsBeanList.get(i).getNewsPic() + "/ \n");
        }
        Log.i(TAG, "setBannerData: this.newsBeanList>>>>>" + stringBuilder.toString());
        Log.i(TAG, "setBannerData: newsBeanList>>>>>" + stringBuilder1.toString());

        this.newsBeanList = newsBeanList;
        notifyDataSetChanged();
    }

    public void setIndentData(List<Object> indentList) {
        mDataList.subList(2, mDataList.size()).clear();
        mDataList.addAll(indentList);
        notifyDataSetChanged();
    }

    public interface onBannerItemClickListener {
        void onBannerItemClick(View itemView, NewsBean newsBean);
    }

    public interface onClassifyItemClickListener {
        void onClassifyItemClick(int type);
    }

    public interface onIndentItemClickListener {
        void onIndentItemClick(int indentId, int position);
    }
}
