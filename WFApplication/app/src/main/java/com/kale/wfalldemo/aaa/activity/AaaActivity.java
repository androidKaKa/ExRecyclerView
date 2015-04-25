package com.kale.wfalldemo.aaa.activity;


import com.kale.wfalldemo.BaseActivity;
import com.kale.wfalldemo.R;
import com.kale.wfalldemo.ResponseCallback;
import com.kale.wfalldemo.aaa.adapter.AaaWaterFallAdapter;
import com.kale.wfalldemo.aaa.mode.Photo;
import com.kale.wfalldemo.aaa.mode.PhotoData;
import com.kale.wfalldemo.extra.swiprefreshlayout.VerticalSwipeRefreshLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import kale.mylibrary.ExRecyclerView;
import kale.mylibrary.ExStaggeredGridLayoutManager;
import kale.mylibrary.OnRecyclerViewScrollListener;


/**
 * @author Jack Tony
 * @brief
 * @date 2015/4/6
 */
public class AaaActivity extends BaseActivity implements ResponseCallback {

    private final String TAG = getClass().getSimpleName();

    private Toolbar toolbar;
    
    /** 垂直方向下拉刷新的控件 */
    private VerticalSwipeRefreshLayout swipeRefreshLayout;

    private ExRecyclerView waterFallRcv;

    private AaaWaterFallAdapter waterFallAdapter;

    private LinearLayout headerLl;

    private ImageView headerIv;

    private Button footerBtn;

    private ImageView floatIV;

    @Override
    protected int getContentViewId() {
        return R.layout.aaa_main_layout;
    }

    @Override
    protected void findViews() {
        toolbar = (Toolbar) findViewById(R.id.aaa_toolbar);
        swipeRefreshLayout = (VerticalSwipeRefreshLayout) findViewById(R.id.aaa_swipeRefreshLayout);
        waterFallRcv = (ExRecyclerView) findViewById(R.id.aaa_waterFall_recyclerView);
        View headerRoot = LayoutInflater.from(this).inflate(R.layout.aaa_waterfall_header, null);
        headerLl = (LinearLayout) headerRoot.findViewById(R.id.aaa_header_linearLayout);
        headerIv = (ImageView) headerRoot.findViewById(R.id.aaa_header_imageView);
        floatIV = (ImageView) findViewById(R.id.aaa_float_imageButton);
        footerBtn = new Button(this);
    }

    @Override
    protected void setViews() {
        setToolbar();
        setFloatIv();
        setSwipeRefreshLayout();
        setWaterFallRcv();
        setHeaderView();
        setFooterView();
        footerBtn.setVisibility(View.GONE);

        setTabView();
    }

    private void setTabView() {
    }

    /*    private Drawable toolbarBgDrawable;
        private Drawable toolbarNavigationIcon;*/
    private int num = 0;

    /**
     * 设置toolbar的背景图和menu点击事件
     */
    private void setToolbar() {
        //setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.aaa_menu_main);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_launcher));//设置导航按钮
        toolbar.setTitle("Saber");
        toolbar.setAlpha(0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                waterFallRcv.smoothScrollToPosition(0, false);
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.action_add_new_item) {
                    num++;
                    // 数据全部交给适配器进行管理
                    PhotoData photo = new PhotoData();
                    photo.msg = "我是第" + num + "个新来的";
                    photo.favorite_count = -5;
                    photo.photo = new Photo();
                    photo.photo.width = 800;
                    photo.photo.height = 1000;
                    photo.photo.path = "http://images.cnitblog.com/blog/651487/201501/292018114419518.png";
                    waterFallAdapter.insert(waterFallAdapter.getDataList(), photo, 0);
                    return true;
                } else {
                    return false;
                }
            }
        });

        /**
         * 本范例让toolbar(view)整体的随着滚动而渐变，
         * 如果你仅仅想要让背景渐变可以用下面的drawable来设置透明度，
         * 它们都是drawable，透明度最大值是255
         *
         * toolbar.getBackground();
         * toolbar.getNavigationIcon()
         */
    }

    private void setFloatIv() {
        floatIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                waterFallRcv.smoothScrollToPosition(0);
            }
        });
    }

    private boolean isLoadingData = false;

    /**
     * 设置下拉刷新控件，下拉后加载新的数据
     */
    private void setSwipeRefreshLayout() {
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isLoadingData) {
                    //Log.d(TAG, "加载新的数据");
                    ((AaaWaterFallAdapter) waterFallRcv.getAdapter()).loadNewData();
                    isLoadingData = true;
                }
            }
        });
    }


    /**
     * 设置瀑布流控件
     */
    private void setWaterFallRcv() {
        // 设置头部或底部的操作应该在setAdapter之前
        waterFallRcv.addHeaderView(headerLl);
        waterFallRcv.addFooterView(footerBtn);

        

        StaggeredGridLayoutManager layoutManager = new ExStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        GridLayoutManager layoutManager2 = new GridLayoutManager(this, 2);
        waterFallRcv.setLayoutManager(layoutManager);

        // 不显示滚动到顶部/底部的阴影（减少绘制）
        waterFallRcv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        //waterFallRcv.setClipToPadding(true);
        /**
         * 监听滚动事件
         */
        waterFallRcv.setOnScrollListener(new OnRecyclerViewScrollListener() {

            @Override
            public void onScrollUp() {
                //Log.d(TAG, "onScrollUp");
                hideViews();
            }

            @Override
            public void onScrollDown() {
                //Log.d(TAG, "onScrollDown");
                showViews();
            }

            @Override
            public void onBottom() {
                //Log.d(TAG, "on bottom");
                Toast.makeText(AaaActivity.this, "bottom", Toast.LENGTH_SHORT).show();
                // 到底部自动加载
                if (!isLoadingData) {
                    isLoadingData = true;
                    Log.d(TAG, "loading old data");
                    waterFallAdapter.loadOldData();
                    footerBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onMoved(int distanceX, int distanceY) {
                // Log.d(TAG, "distance X = " + distanceX + "distance Y = " + distanceY);
                setToolbarBgByScrollDistance(distanceY);
            }
        });
        
        /**
         * 监听点击和长按事件
         */
        waterFallRcv.setOnItemClickListener(new ExRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(AaaActivity.this, "on click", Toast.LENGTH_SHORT).show();
                waterFallAdapter.remove(waterFallAdapter.getDataList(), position);
            }
        });
        waterFallRcv.setOnItemLongClickListener(new ExRecyclerView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, int position) {
                Toast.makeText(AaaActivity.this, "on long click", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        waterFallAdapter = new AaaWaterFallAdapter(this);
        waterFallRcv.setAdapter(waterFallAdapter);
    }

    private float headerHeight;

    /**
     * 设置头部的view
     */
    private void setHeaderView() {
        headerIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转到某个位置
                waterFallRcv.scrollToPosition(10);
            }
        });
        headerIv.post(new Runnable() {
            @Override
            public void run() {
                headerHeight = headerIv.getHeight();
                //Log.d(TAG, "headerHeight" + headerHeight);
            }
        });
    }

    /**
     * 设置底部的view
     */
    private void setFooterView() {
        footerBtn.setText("正在加载...");
        footerBtn.getBackground().setAlpha(80);
    }


    /**
     * 滑动时影藏float button
     */
    private void hideViews() {
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) floatIV.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        floatIV.animate().translationY(floatIV.getHeight() + fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    /**
     * 滑动时出现float button
     */
    private void showViews() {
        floatIV.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    /**
     * 通过滚动的状态来设置toolbar的透明度
     */
    private void setToolbarBgByScrollDistance(int distance) {
        //Log.d(TAG, "distance" + distance);
        final float ratio = Math.min(distance / headerHeight, 1);
        final float newAlpha = ratio * 1;
        toolbar.setAlpha(newAlpha);
    }


    @Override
    public void onResponse(Object object) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
        isLoadingData = false;
        footerBtn.setVisibility(View.GONE);
    }

    @Override
    public void onError(String msg) {

    }

    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }
}
