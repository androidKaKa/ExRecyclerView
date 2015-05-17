package com.kale.wfalldemo.aaa.adapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kale.wfalldemo.R;
import com.kale.wfalldemo.aaa.mode.PhotoData;
import com.kale.wfalldemo.extra.fresco.DynamicHeightSimpleDraweeView;

import android.content.Context;
import android.net.Uri;
import android.widget.TextView;


public class AaaWaterFallItem02 extends RvAdapterItem<PhotoData> {

    public AaaWaterFallItem02(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    @Override
    public void setViews(PhotoData data, int position) {
        DynamicHeightSimpleDraweeView contentSdv = getView(R.id.aaa_wf_item_content_DraweeView);
        SimpleDraweeView headPicSdv = getView(R.id.aaa_wf_item_user_head_draweeView);
        TextView positionTv = getView(R.id.aaa_wf_item_positon_textView);
        
        
        contentSdv.setImageURI(Uri.parse(data.photo.path));

        float picRatio = (float) data.photo.height / data.photo.width;
        contentSdv.setHeightRatio(picRatio);

        // 必须设置加载的uri
        headPicSdv.setImageURI(Uri.parse("http://wenwen.soso.com/p/20100203/20100203005516-1158326774.jpg"));

        positionTv.setText("No." + position);
    }
}

