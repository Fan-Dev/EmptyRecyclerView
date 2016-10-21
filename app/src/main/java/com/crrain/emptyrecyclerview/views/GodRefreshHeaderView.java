package com.crrain.emptyrecyclerview.views;

import com.aspsine.irecyclerview.RefreshTrigger;
import com.crrain.emptyrecyclerview.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by aspsine on 16/3/14.
 */
public class GodRefreshHeaderView extends RelativeLayout implements RefreshTrigger {
    private ImageView iv_loading;

    private TextView  tvRefresh;

    private int       mHeight;

    public GodRefreshHeaderView(Context context) {
        this(context, null);
    }

    public GodRefreshHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GodRefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.layout_god_refresh_header_view, this);

        tvRefresh = (TextView) findViewById(R.id.tvRefresh);

        iv_loading = (ImageView) findViewById(R.id.iv_loading);
    }

    @Override
    public void onStart(boolean automatic, int headerHeight, int finalHeight) {
        this.mHeight = headerHeight;
        iv_loading.setRotationY(0);
    }

    @Override
    public void onMove(boolean isComplete, boolean automatic, int moved) {
        if (!isComplete) {
            if (moved <= mHeight) {
                tvRefresh.setText("下拉刷新");
            } else {
                tvRefresh.setText("松开即刻刷新");
            }
        }
        iv_loading.setRotationY(moved / (float) mHeight * 360);
    }

    @Override
    public void onRefresh() {
        tvRefresh.setText("刷新中");
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onComplete() {
        tvRefresh.setText("已刷新");
    }

    @Override
    public void onReset() {
        tvRefresh.setText("下拉刷新");
        iv_loading.setRotationY(0);
    }
}
