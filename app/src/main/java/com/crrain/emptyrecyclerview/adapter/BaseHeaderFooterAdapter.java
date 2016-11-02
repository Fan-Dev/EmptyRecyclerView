package com.crrain.emptyrecyclerview.adapter;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by fan on 2016/10/21.
 */
public abstract class BaseHeaderFooterAdapter extends
                                              RecyclerView.Adapter<BaseHeaderFooterAdapter.BaseHeaderFooterHolder> {

    private RecyclerView mRecyclerView;

    private View         VIEW_FOOTER;
    private View         VIEW_HEADER;

    //Type
    private int          TYPE_NORMAL = 1000;
    private int          TYPE_HEADER = 1001;
    private int          TYPE_FOOTER = 1002;

    /**
     * 父类做了特殊处理，不建议复写。请使用doCreateViewHolder方法
     * @param parent
     * @param viewType
     * @return
     */
    @Deprecated
    @Override
    public BaseHeaderFooterAdapter.BaseHeaderFooterHolder onCreateViewHolder(ViewGroup parent,
                                                                             int viewType) {
        if (viewType == TYPE_FOOTER) {
            return new BaseHeaderFooterHolder(VIEW_FOOTER);
        } else if (viewType == TYPE_HEADER) {
            return new BaseHeaderFooterHolder(VIEW_HEADER);
        } else {
            return doCreateViewHolder(parent, viewType);
        }
    }

    /**
     * 父类做了特殊处理，不建议复写。请使用doBindViewHolder方法
     * @param holder
     * @param position
     */
    @Deprecated
    @Override
    public void onBindViewHolder(BaseHeaderFooterHolder holder, int position) {
        if (!isHeaderView(position) && !isFooterView(position)) {
            if (haveHeaderView())
                position--;
            doBindViewHolder(holder, position);
        }
    }

    /**
     * 创建ViewHolder，等同于RecylerView.Adpapter.onCreateViewHolder
     * @param parent
     * @param viewType
     * @return
     */
    public abstract BaseHeaderFooterAdapter.BaseHeaderFooterHolder doCreateViewHolder(ViewGroup parent,
                                                                                      int viewType);

    /**
     * 绑定ViewHolder数据，等同于RecylerView.Adpapter.onBindViewHolder
     * @param holder
     * @param position
     */
    public abstract void doBindViewHolder(BaseHeaderFooterHolder holder, int position);

    /**
     * 返回数据集
     * @return
     */
    public abstract List getData();

    @Override
    public int getItemCount() {
        int count = (getData() == null ? 0 : getData().size());
        if (VIEW_FOOTER != null) {
            count++;
        }

        if (VIEW_HEADER != null) {
            count++;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            return TYPE_HEADER;
        } else if (isFooterView(position)) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        try {
            if (mRecyclerView == null && mRecyclerView != recyclerView) {
                mRecyclerView = recyclerView;
            }
            ifGridLayoutManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected View getLayout(Context mContext, int layoutId) {
        return LayoutInflater.from(mContext).inflate(layoutId, null);
    }

    public void addHeaderView(View headerView) {
        if (haveHeaderView()) {
            throw new IllegalStateException("hearview has already exists!");
        } else {
            //避免出现宽度自适应
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            headerView.setLayoutParams(params);
            VIEW_HEADER = headerView;
            ifGridLayoutManager();
            notifyItemInserted(0);
        }

    }

    public void addFooterView(View footerView) {
        if (haveFooterView()) {
            throw new IllegalStateException("footerView has already exists!");
        } else {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            footerView.setLayoutParams(params);
            VIEW_FOOTER = footerView;
            ifGridLayoutManager();
            notifyItemInserted(getItemCount() - 1);
        }
    }

    private void ifGridLayoutManager() {
        if (mRecyclerView == null)
            return;
        final RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager.SpanSizeLookup originalSpanSizeLookup = ((GridLayoutManager) layoutManager)
                .getSpanSizeLookup();
            ((GridLayoutManager) layoutManager)
                .setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return (isHeaderView(position) || isFooterView(position))
                            ? ((GridLayoutManager) layoutManager).getSpanCount() : 1;
                    }
                });
        }
    }

    public boolean haveHeaderView() {
        return VIEW_HEADER != null;
    }

    public boolean haveFooterView() {
        return VIEW_FOOTER != null;
    }

    private boolean isHeaderView(int position) {
        return haveHeaderView() && position == 0;
    }

    private boolean isFooterView(int position) {
        return haveFooterView() && position == getItemCount() - 1;
    }

    public static class BaseHeaderFooterHolder extends RecyclerView.ViewHolder {
        public BaseHeaderFooterHolder(View itemView) {
            super(itemView);
        }
    }

}