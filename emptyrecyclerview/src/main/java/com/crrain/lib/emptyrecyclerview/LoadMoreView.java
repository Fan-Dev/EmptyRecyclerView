package com.crrain.lib.emptyrecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by aspsine on 16/3/14.
 */
public class LoadMoreView extends RelativeLayout {
    private View loadMore, loadingMore, finished, onError;
    /**
     * 是否已全部加载
     */
    private boolean isLoadAll = false;
    /**
     * 是否正在加载
     */
    private boolean isLoading = false;

    public boolean isLoadAll() {
        return isLoadAll;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public LoadMoreView(Context context) {
        this(context, null);
        init(context);
    }

    public LoadMoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context);
    }

    public LoadMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.view_load_more, this);

        loadMore = findViewById(R.id.tv_loadmore);
        loadingMore = findViewById(R.id.rll_loading);
        finished = findViewById(R.id.tv_nomoredata);
        onError = findViewById(R.id.tv_onerror);

        loadMore.setVisibility(View.VISIBLE);
    }

    /**
     * 默认加载更多
     */
    public void loadMore() {
        isLoadAll = false;
        isLoading = false;
        showViewOnly(loadMore);
    }

    /**
     * 正在加载更多
     */
    public void loadingMore() {
        isLoadAll = false;
        isLoading = true;
        showViewOnly(loadingMore);
    }

    /**
     * 全部加载
     */
    public void noMoreData() {
        isLoadAll = true;
        isLoading = false;
        showViewOnly(finished);
    }

    /**
     * 加载出错
     */
    public void onError() {
        isLoadAll = false;
        isLoading = false;
        showViewOnly(onError);
    }

    /**
     * 隐藏除view之外的试图
     *
     * @param view
     */
    private void showViewOnly(View view) {
        loadMore.setVisibility(View.GONE);
        loadingMore.setVisibility(View.GONE);
        finished.setVisibility(View.GONE);
        onError.setVisibility(View.GONE);
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }
}
