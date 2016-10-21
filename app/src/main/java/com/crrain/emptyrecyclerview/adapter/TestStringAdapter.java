package com.crrain.emptyrecyclerview.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.crrain.emptyrecyclerview.R;

import java.util.ArrayList;

/**
 * Created by fan on 2016/10/21.
 */
public class TestStringAdapter extends RecyclerView.Adapter {
    private Context           context;
    private ArrayList<String> datas = new ArrayList<>();

    public TestStringAdapter(Context context, ArrayList<String> datas) {
        this.context = context;
        if (datas == null) {
            datas = new ArrayList<>();
        }
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_test, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder myHolder = (MyViewHolder) holder;
        final String data = datas.get(position);
        myHolder.tv_test.setText(data);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    final class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_test;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_test = (TextView) itemView.findViewById(R.id.tv_test);
        }
    }
}
