package com.kale.wfalldemo.aaa.adapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kale.wfalldemo.R;
import com.kale.wfalldemo.aaa.mode.PhotoData;
import com.kale.wfalldemo.extra.fresco.DynamicHeightSimpleDraweeView;

import android.content.Context;
import android.net.Uri;
import android.widget.TextView;

import kale.mylibrary.RcvAdapterItem;


public class waterFallWhiteItem extends RcvAdapterItem<PhotoData> {

    /** 内容主体的图片 */
    public DynamicHeightSimpleDraweeView contentSdv;

    /** 图片下方的描述文字 */
    public TextView descriptionTv;

    /** 用户的头像 */
    public SimpleDraweeView headPicSdv;

    /**
     * 标识位置的textView
     */
    public TextView positionTv;

    public waterFallWhiteItem(Context context, int layoutResId) {
        super(context, layoutResId);
        contentSdv = getView(R.id.aaa_wf_item_content_DraweeView);
        descriptionTv = getView(R.id.aaa_wf_item_description_textView);
        headPicSdv = getView(R.id.aaa_wf_item_user_head_draweeView);
        positionTv = getView(R.id.aaa_wf_item_positon_textView);
    }

    @Override
    public void setViews(PhotoData data, int position) {
        contentSdv.setImageURI(Uri.parse(data.photo.path));

        float picRatio = (float) data.photo.height / data.photo.width;
        contentSdv.setHeightRatio(picRatio);

        descriptionTv.setText(data.msg);
        // 必须设置加载的uri
        headPicSdv.setImageURI(Uri.parse("http://wenwen.soso.com/p/20100203/20100203005516-1158326774.jpg"));

        positionTv.setText("No." + position);
    }
}

