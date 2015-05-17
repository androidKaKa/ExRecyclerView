package com.kale.wfalldemo.aaa.adapter;

import com.kale.wfalldemo.aaa.mode.IAdapterModel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import kale.mylibrary.BaseRecyclerAdapter;

/**
 * @author Jack Tony
 * @date 2015/5/17
 */
public abstract class CommonRvAdapter<T extends IAdapterModel> extends BaseRecyclerAdapter {

    protected Context mContext;

    protected List<T> mData;

    protected CommonRvAdapter(Context context, List<T> data) {
        mContext = context;
        mData = data;
    }


    @Override
    public int getAdapterItemCount() {
        return mData.size();
    }

    @Override
    public int getAdapterItemType(int position) {
        return mData.get(position).getDataType();
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(int viewType) {
        return initItemView(viewType);
    }

    protected abstract RvAdapterItem initItemView(int type);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, boolean nothing) {
        RvAdapterItem adapterItem = (RvAdapterItem) viewHolder;
        adapterItem.setViews(mData.get(position), position);
    }

    public void updateData(List<T> data) {
        mData = data;
        notifyDataSetChanged();
    }
}
