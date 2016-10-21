package com.crrain.lib.emptyrecyclerview;

import com.aspsine.irecyclerview.IRecyclerView;
import com.crrain.lib.emptyrecyclerview.listener.EmptyViewRetryListener;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/7/28.
 */
public class EmptyRecyclerView extends IRecyclerView {
    private View                      emptyView, retryView;
    final private AdapterDataObserver observer = new AdapterDataObserver() {
                                                   @Override
                                                   public void onChanged() {
                                                       checkIfEmpty();
                                                   }

                                                   @Override
                                                   public void onItemRangeInserted(int positionStart,
                                                                                   int itemCount) {
                                                       checkIfEmpty();
                                                   }

                                                   @Override
                                                   public void onItemRangeRemoved(int positionStart,
                                                                                  int itemCount) {
                                                       checkIfEmpty();
                                                   }
                                               };

    private EmptyViewRetryListener    emptyViewRetryListener;

    public void setEmptyViewRetryListener(EmptyViewRetryListener emptyViewRetryListener) {
        this.emptyViewRetryListener = emptyViewRetryListener;
    }

    public EmptyRecyclerView(Context context) {
        super(context);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void checkIfEmpty() {
        Adapter adapter = getMyAdapter();
        if (emptyView != null && adapter != null) {
            final boolean emptyViewVisible = adapter.getItemCount() == 0;
            emptyView.setVisibility(emptyViewVisible ? VISIBLE : GONE);
            //            setVisibility(emptyViewVisible ? GONE : VISIBLE);
            getLoadMoreFooterView().setVisibility(emptyViewVisible ? GONE : VISIBLE);
        }
    }

    /**
     * 指定垂直分割线的间距
     * @param itemDecorationInDP
     */
    public void addItemDecoration(int itemDecorationInDP) {
        //垂直布局则添加垂直分割间距
        addItemDecoration(new SpaceItemDecoration(itemDecorationInDP));
    }

    /**
     * 对getIAdapter做些简单处理，避免跟getAdapter重名，叫getMyAdapter。
     *
     * @return
     */
    private Adapter getMyAdapter() {
        Adapter adapter = null;
        try {
            adapter = getIAdapter();
        } catch (Exception e) {

        }
        return adapter;
    }

    private boolean isFirstLoaded = false;

    @Override
    public void setRefreshing(boolean refreshing) {
        super.setRefreshing(refreshing);
        //停止刷新，就认为是刷新结束
        if (!refreshing && !isFirstLoaded) {
            isFirstLoaded = true;
            onFirstLoaded();
        }
    }

    /**
     * 第一次加载完成
     */
    public void onFirstLoaded() {

    }

    /**
     * 请使用setIAdapter替代
     * @param adapter
     */
    @Deprecated
    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
    }

    @Override
    public void setIAdapter(Adapter adapter) {
        final Adapter oldAdapter = getMyAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }
        super.setIAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }
        checkIfEmpty();
    }

    public void setEmptyView(View emptyView, View retryView) {
        setEmptyView(emptyView);
        this.retryView = emptyView;
        if (retryView != null) {
            retryView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (emptyViewRetryListener != null) {
                        emptyViewRetryListener.onRefresh(v);
                    }
                }
            });
        }
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        checkIfEmpty();

        getFooterContainer().removeView(emptyView);
        addFooterView(emptyView);

        //        if (emptyView.getParent() == null) {
        //            //如果emptyView没有父布局，则添加至当前RecyclerView的父View中
        //            ViewParent parent = getParent();
        //            if (parent instanceof RelativeLayout) {
        //                //相对布局
        //                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
        //                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        //                ViewGroup parentView = (ViewGroup) getParent();
        //                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        //                parentView.addView(emptyView, layoutParams);
        //            } else if (getParent() instanceof ViewGroup) {
        //                ViewGroup parentView = (ViewGroup) getParent();
        //                parentView.addView(emptyView);
        //            }
        //        }
    }
}