package com.crrain.emptyrecyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.crrain.emptyrecyclerview.adapter.TestStringAdapter;
import com.crrain.emptyrecyclerview.views.GodEmptyRecyclerView;
import com.crrain.lib.emptyrecyclerview.LoadMoreView;
import com.crrain.lib.emptyrecyclerview.listener.EmptyViewRetryListener;

import java.util.ArrayList;

/**
 * Created by fan on 2016/10/21.
 */
public class MainActivity extends Activity {
    private GodEmptyRecyclerView irv_list_view;
    private LoadMoreView         loadMoreView;
    private int                  startPage;
    private TestStringAdapter    testStringAdapter;
    private ArrayList<String>    datas = new ArrayList<>();
    private int                  tempI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        irv_list_view = (GodEmptyRecyclerView) findViewById(R.id.irv_list_view);

        irv_list_view.setLayoutManager(new LinearLayoutManager(this));
        irv_list_view.setLoadMoreEnabled(true);
        irv_list_view.setRefreshEnabled(true);

        loadMoreView = (LoadMoreView) irv_list_view.getLoadMoreFooterView();

        irv_list_view.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(View view) {
                Log.d("1", "onLoadMore");
                if (loadMoreView.isLoading()) {
                    return;
                }
                if (loadMoreView.isLoadAll()) {
                    loadMoreView.noMoreData();
                    return;
                } else {
                    loadMoreView.loadingMore();
                }
                startPage++;
                requestData();
            }
        });

        irv_list_view.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("1", "onRefresh");
                startPage = 0;
                requestData();
            }
        });

        //刷新重试
        irv_list_view.setEmptyViewRetryListener(new EmptyViewRetryListener() {
            @Override
            public void onRefresh(View view) {
                startPage = 0;
                requestData();
            }
        });

        testStringAdapter = new TestStringAdapter(this, datas);
        irv_list_view.setIAdapter(testStringAdapter);

        irv_list_view.postDelayed(new Runnable() {
            @Override
            public void run() {
                irv_list_view.setRefreshing(true);
            }
        }, 500);
    }

    /**
     * 加载数据
     */
    private void requestData() {
        irv_list_view.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (startPage == 0) {
                    datas.clear();
                }
                for (int i = 0; i < 10; i++) {
                    datas.add(startPage + "data" + (tempI++));
                }
                if (startPage == 3) {
                    //没有更多数据
                    loadMoreView.noMoreData();
                } else {
                    //还有更多数据
                    loadMoreView.loadMore();
                }
                //刷新结束
                irv_list_view.setRefreshing(false);
                testStringAdapter.notifyDataSetChanged();
            }
        }, 2000);
    }
}
