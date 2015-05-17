package com.kale.wfalldemo.aaa.adapter;

import com.kale.wfalldemo.aaa.mode.IAdapterModel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;


/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public abstract class RvAdapterItem<T extends IAdapterModel> extends RecyclerView.ViewHolder {

    protected RvAdapterItem(Context context, int layoutResId) {
        super(LayoutInflater.from(context).inflate(layoutResId, null));
    }

    public abstract void setViews(T data, int position);

    @SuppressWarnings("unchecked")
    protected <T extends View> T getView(int id) {
        return (T) itemView.findViewById(id);
    }
}  