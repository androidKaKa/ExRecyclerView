package com.kale.wfalldemo.aaa.adapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kale.wfalldemo.R;
import com.kale.wfalldemo.extra.fresco.DynamicHeightSimpleDraweeView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * @author Jack Tony
 * @brief
 * @date 2015/4/10
 */
public class AaaWaterFallViewHolder extends RecyclerView.ViewHolder {

    /** 内容主体的图片 */
    public DynamicHeightSimpleDraweeView contentSdv;

    /** 图片下方的描述文字 */
    public TextView descriptionTv;

    /** 用户的头像 */
    public SimpleDraweeView headPicSdv;
    
    public TextView positionTv;

    //在布局中找到所含有的UI组件  
    public AaaWaterFallViewHolder(View itemRootView) {
        super(itemRootView);
        contentSdv = (DynamicHeightSimpleDraweeView) itemRootView.findViewById(R.id.aaa_wf_item_content_DraweeView);
        descriptionTv = (TextView) itemRootView.findViewById(R.id.aaa_wf_item_description_textView);
        headPicSdv = (SimpleDraweeView) itemRootView.findViewById(R.id.aaa_wf_item_user_head_draweeView);
        positionTv = (TextView)itemRootView.findViewById(R.id.aaa_wf_item_positon_textView);
    }
}
