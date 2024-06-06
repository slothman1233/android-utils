package com.cjjc.lib_widget.wid_skeleton.master;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.recyclerview.widget.RecyclerView;


import com.cjjc.lib_base_view.view.adapter.recycle.BaseRecycleListAdapter;
import com.cjjc.lib_base_view.view.adapter.recycle.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 骨架图适配器
 * 仅适用于列表
 * @param <T>
 */
public abstract class BaseRecycleListAdapterSkeleton<T> extends BaseRecycleListAdapter<T> {

    //骨架图适配器：初始化完成回调
    protected SkeletonAdapterInitListener isCanSetAdapterListener;
    //骨架图配置信息
    protected SkeletonConfig skeletonConfig = new SkeletonConfig();


    /**
     * 初始化
     * @param recyclerView 用于 measureHeightRecyclerViewAndItem 测量屏幕高度
     * @param dataList  数据源
     * @param isCanSetAdapterListener 骨架图适配器：初始化完成回调
     */
    public BaseRecycleListAdapterSkeleton(Context context, RecyclerView recyclerView, List<T> dataList, SkeletonAdapterInitListener isCanSetAdapterListener) {
        super(dataList);
        this.context=context;
        this.isCanSetAdapterListener = isCanSetAdapterListener;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    /**
     * 获取Item高度添加 一屏 骨架控件
     *
     * @param recyclerView
     * @param idLayout
     */
    protected void measureHeightRecyclerViewAndItem(final RecyclerView recyclerView, final int idLayout) {
        //获取recyclerView树观察者
        ViewTreeObserver viewTreeObserver = recyclerView.getViewTreeObserver();
        //注册观察者监听
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //设置骨架图配置信息
                //获取recyclerView的高度
                skeletonConfig.setRecyclerViewHeight(recyclerView.getHeight());
                //获取当前加载的Item XML布局
                View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(idLayout, null);
                //测量根布局 宽高
                view.getRootView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                //设置骨架图Item高度
                skeletonConfig.setItemHeight(view.getRootView().getMeasuredHeight());
                //设置显示的骨架图数量  规则：(RecyclerViewHeight/ItemHeight)+1   即：占满一屏Item
                skeletonConfig.setNumberItemShow(Math.round(skeletonConfig.getRecyclerViewHeight() / skeletonConfig.getItemHeight()) + 1);
                //移除视图树观察者
                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                //骨架图初始化完成 回调View 给RecyclerView 设置适配器
                isCanSetAdapterListener.skeletonFinish();
            }
        });
    }
    protected void measureHeightRecyclerViewAndItem(final RecyclerView recyclerView, final int idLayout,int count) {
        //获取recyclerView树观察者
        ViewTreeObserver viewTreeObserver = recyclerView.getViewTreeObserver();
        //注册观察者监听
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //设置骨架图配置信息
                //获取recyclerView的高度
                skeletonConfig.setRecyclerViewHeight(recyclerView.getHeight());
                //获取当前加载的Item XML布局
                View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(idLayout, null);
                //测量根布局 宽高
                view.getRootView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                //设置骨架图Item高度
                skeletonConfig.setItemHeight(view.getRootView().getMeasuredHeight());
                //设置显示的骨架图数量
                skeletonConfig.setNumberItemShow(count);
                //移除视图树观察者
                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                //骨架图初始化完成 回调View 给RecyclerView 设置适配器
                isCanSetAdapterListener.skeletonFinish();
            }
        });
    }

    @Override
    public int getItemCount() {
        //是否开启骨架图
        if (skeletonConfig.isSkeletonIsOn()) {
            //如果ItemHeight==0 那么就只返回一个骨架图Item
            if (skeletonConfig.getItemHeight() == 0) {
                return 1;
            } else {
                //返回一整屏骨架图Item
                return skeletonConfig.getNumberItemShow();
            }
        } else {
            //返回源数据数量
            return dataList.size();
        }
    }

    /**
     * 添加真正的数据源,并且关闭骨架图显示
     * @param dataList
     */
    public void addDataAndSkeletonFinish(List<T> dataList) {
        //添加数据
        this.dataList = new ArrayList<>();
        this.dataList.addAll(dataList);

        //设置骨架图关闭
        skeletonConfig.setSkeletonIsOn(false);
        //通知真实列表所有Item更新
        notifyItemRangeChanged(0, BaseRecycleListAdapterSkeleton.this.dataList.size(), 1);
        //移除多余骨架图
        if (skeletonConfig.getNumberItemShow() > BaseRecycleListAdapterSkeleton.this.dataList.size()) {
            notifyItemRangeRemoved(BaseRecycleListAdapterSkeleton.this.dataList.size(), skeletonConfig.getNumberItemShow());
        }
    }

    //==============================================================================================
    // Getter and Setter
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * 获取骨架图适配器回调监听
     * @return
     */
    public SkeletonAdapterInitListener getIsCanSetAdapterListener() {
        return isCanSetAdapterListener;
    }

    /**
     * 设置骨架图适配器回调监听
     * @param IsCanSetAdapterListener
     */
    public void setIsCanSetAdapterListener(SkeletonAdapterInitListener IsCanSetAdapterListener) {
        this.isCanSetAdapterListener = IsCanSetAdapterListener;
    }

    /**
     * 获取骨架图配置信息
     * @return
     */
    public SkeletonConfig getSkeletonConfig() {
        return skeletonConfig;
    }

    /**
     * 设置骨架图配置信息
     * @param skeletonConfig
     */
    public void setSkeletonConfig(SkeletonConfig skeletonConfig) {
        this.skeletonConfig = skeletonConfig;
    }
}
