package com.kale.wfalldemo.extra.fresco;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @author Jack Tony
 * @brief
 * @date 2015/4/6
 */
public class DynamicHeightSimpleDraweeView extends SimpleDraweeView {

    private double mHeightRatio;

    public DynamicHeightSimpleDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public DynamicHeightSimpleDraweeView(Context context) {
        super(context);
    }

    public DynamicHeightSimpleDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setHeightRatio(double ratio) {
        if (ratio != mHeightRatio) {
            mHeightRatio = ratio;
            requestLayout();
        }
    }

    public double getHeightRatio() {
        return mHeightRatio;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mHeightRatio > 0.0) {
            // set the image views size
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = (int) (width * mHeightRatio);
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
