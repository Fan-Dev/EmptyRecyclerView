package com.crrain.lib.emptyrecyclerview;

import com.aspsine.irecyclerview.RefreshHeaderLayout;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by fan on 2016/7/31.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private float mSpace;

    /**
     * @param space 传入的值，其单位视为dp
     */
    public SpaceItemDecoration(float space) {
        this.mSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        int tempSpace = dip2px(parent.getContext(), mSpace);
        outRect.left = tempSpace;
        outRect.right = tempSpace;
        outRect.bottom = tempSpace;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildAdapterPosition(view) == 0)
            outRect.top = tempSpace;

        //如果是顶部刷新视图，则取消间隔
        if (view instanceof RefreshHeaderLayout) {
            outRect.top = 0;
            outRect.bottom = 0;
        }
    }

    private int dip2px(Context context, float dpValue) {
        if (context == null) {
            return 0;
        } else {
            float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5F);
        }
    }
}