package com.example.hb;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Service;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ecuser on 2015/10/9.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.TextHolder> {
    private List<String> mDataset;
    private ItemClickListener itemClickListener;


    public MainAdapter(List<String> myDataset) {
        mDataset = myDataset;
    }

    public MainAdapter setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        return this;
    }

    @Override
    public MainAdapter.TextHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MainAdapter.TextHolder viewHolder = null;
        LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        TextView tv = (TextView) inflator.inflate(android.R.layout.simple_list_item_1, null);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(view, (String) view.getTag(), (Integer) view.getTag(R.id.et));
                }
            }
        });
        viewHolder = new TextHolder(tv);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TextHolder holder, int position) {
        holder.mTextView.setTag(R.id.et, position);
        holder.mTextView.setTag(mDataset.get(position));
        ((TextHolder) holder).mTextView.setText(mDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setmDataset(List<String> dataset){
        this.mDataset = dataset;
        notifyDataSetChanged();
    }


    /**
     * 文本
     */
    public static class TextHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public TextHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }



    /**
     * 条目点击监听
     */
    public interface ItemClickListener {
        void onItemClick(View view, String data, int position);
    }

}
