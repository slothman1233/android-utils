package com.cjjc.lib_widget.wid_skeleton.master;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cjjc.lib_base_view.view.adapter.recycle.BaseRecycleSingleAdapter;
import com.cjjc.lib_base_view.view.adapter.recycle.BaseViewHolder;


/**
 * 骨架图适配器
 * 仅适用于多布局类型,并且非列表,数据只有一个实体
 * @param <T>
 */
public abstract class BaseRecycleSingleAdapterSkeleton<T> extends BaseRecycleSingleAdapter<T> {

    //骨架图适配器：初始化完成回调
    protected SkeletonAdapterInitListener isCanSetAdapterListener;
    //骨架图配置信息
    protected SkeletonConfig skeletonConfig = new SkeletonConfig();

    /**
     * 初始化
     * @param recyclerView 用于 measureHeightRecyclerViewAndItem 测量屏幕高度
     * @param data  数据源
     * @param isCanSetAdapterListener 骨架图适配器：初始化完成回调
     */
    public BaseRecycleSingleAdapterSkeleton(Context context, RecyclerView recyclerView, T data, SkeletonAdapterInitListener isCanSetAdapterListener) {
        super(data);
        this.context = context;
        this.isCanSetAdapterListener = isCanSetAdapterListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
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

    /**
     * 添加真正的数据源,并且关闭骨架图显示
     * @param data
     */
    public void addDataAndSkeletonFinish(T data) {
        //添加数据
        this.data =data;
        //设置骨架图关闭
        skeletonConfig.setSkeletonIsOn(false);
        //通知骨架图Item更新真实数据源
        notifyItemRangeChanged(0, 1, 1);
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
