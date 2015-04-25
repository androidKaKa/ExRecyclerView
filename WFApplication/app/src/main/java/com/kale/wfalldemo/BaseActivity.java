package com.kale.wfalldemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * @author Jack Tony
 * @brief
 * @date 2015/4/5
 */
public abstract class BaseActivity extends ActionBarActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        findViews();
        setViews();
    }

    /**
     * 找到所有的views
     */
    protected abstract void findViews();

    /**
     * 设置view的各种初始状态
     */
    protected abstract void setViews();

    protected abstract int getContentViewId();
}
