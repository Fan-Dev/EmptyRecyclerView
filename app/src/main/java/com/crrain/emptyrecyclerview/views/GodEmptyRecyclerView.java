package com.crrain.emptyrecyclerview.views;

import com.crrain.emptyrecyclerview.R;
import com.crrain.lib.emptyrecyclerview.EmptyRecyclerView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 当前项目使用RecyclerView ，多了些默认设置
 * Created by Administrator on 2016/7/28.
 */
public class GodEmptyRecyclerView extends EmptyRecyclerView {
    private View ll_empty_view, ll_loading_view;

    public GodEmptyRecyclerView(Context context) {
        super(context);
    }

    public GodEmptyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GodEmptyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        initView(getContext());
    }

    @Override
    public void onFirstLoaded() {
        ll_loading_view.setVisibility(GONE);
        ll_empty_view.setVisibility(VISIBLE);
    }

    private void initView(Context context) {
        View emptyView = LayoutInflater.from(context).inflate(R.layout.layout_empty_view, null);
        ll_empty_view = emptyView.findViewById(R.id.ll_empty_view);
        ll_loading_view = emptyView.findViewById(R.id.ll_loading_view);
        setEmptyView(emptyView, ll_empty_view);
    }
}