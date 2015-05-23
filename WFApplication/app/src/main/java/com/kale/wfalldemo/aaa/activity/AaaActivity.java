package com.kale.wfalldemo.aaa.activity;


import com.kale.wfalldemo.BaseActivity;
import com.kale.wfalldemo.R;
import com.kale.wfalldemo.ResponseCallback;
import com.kale.wfalldemo.aaa.adapter.DataManager;
import com.kale.wfalldemo.aaa.adapter.waterFallOrangeItem;
import com.kale.wfalldemo.aaa.adapter.waterFallWhiteItem;
import com.kale.wfalldemo.aaa.mode.Photo;
import com.kale.wfalldemo.aaa.mode.PhotoData;
import com.kale.wfalldemo.extra.swiprefreshlayout.VerticalSwipeRefreshLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kale.mylibrary.CommonRcvAdapter;
import kale.mylibrary.DividerGridItemDecoration;
import kale.mylibrary.ExRecyclerView;
import kale.mylibrary.ExStaggeredGridLayoutManager;
import kale.mylibrary.OnRecyclerViewScrollListener;
import kale.mylibrary.RcvAdapterItem;


/**
 * @author Jack Tony
 * @date 2015/4/6
 */
public class AaaActivity extends BaseActivity implements ResponseCallback {

    private final String TAG = getClass().getSimpleName();

    private Context mContext;

    private Toolbar toolbar;

    /** 垂直方向下拉刷新的控件 */
    private VerticalSwipeRefreshLayout swipeRefreshLayout;

    private ExRecyclerView waterFallRcv;

    private AaaWaterFallAdapter waterFallAdapter;

    private LinearLayout headerLl;

    private ImageView headerIv;

    private Button footerBtn;

    private ImageView floatIV;

    private DataManager mDataManager = new DataManager();

    private boolean isFirstLoadData = true;

    @Override
    protected int getContentViewId() {
        mContext = this;
        return R.layout.aaa_main_layout;
    }

    @Override
    protected void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        swipeRefreshLayout = (VerticalSwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        waterFallRcv = (ExRecyclerView) findViewById(R.id.waterFall_recyclerView);
        View headerRoot = LayoutInflater.from(this).inflate(R.layout.waterfall_header, null);
        headerLl = (LinearLayout) headerRoot.findViewById(R.id.header_linearLayout);
        headerIv = (ImageView) headerRoot.findViewById(R.id.header_imageView);
        floatIV = (ImageView) findViewById(R.id.float_imageButton);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataManager.loadNewData(this);
    }

    private void setTabView() {
    }

    class AaaWaterFallAdapter extends CommonRcvAdapter<PhotoData> {

        protected AaaWaterFallAdapter(List<PhotoData> data) {
            super(data);
        }

        @NonNull
        @Override
        protected RcvAdapterItem initItemView(Context context, int type) {
            switch (type) {
                case PhotoData.FIRST:
                    return new waterFallOrangeItem(context, R.layout.aaa_waterfall_orange_item);
                case PhotoData.Second:
                default:
                    return new waterFallWhiteItem(context, R.layout.aaa_waterfall_white_item);
            }
        }

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

                    mDataManager.getData().add(0, photo);
                    waterFallAdapter.updateData(mDataManager.getData());
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
                    mDataManager.loadNewData(AaaActivity.this);
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

        StaggeredGridLayoutManager staggeredGridLayoutManager = new ExStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, true);// 可替换
        waterFallRcv.setLayoutManager(staggeredGridLayoutManager);

        // 添加分割线
        waterFallRcv.addItemDecoration(new DividerGridItemDecoration(this));
        //waterFallRcv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));//可替换

        List<PhotoData> list = new ArrayList<>();// 先放一个空的list
        waterFallAdapter = new AaaWaterFallAdapter(list);
        waterFallRcv.setAdapter(waterFallAdapter);
        
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
                    mDataManager.loadOldData(AaaActivity.this);
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
        waterFallRcv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AaaActivity.this, "on click", Toast.LENGTH_SHORT).show();
                mDataManager.getData().remove(position);
                waterFallAdapter.updateData(mDataManager.getData());
            }
        });
        waterFallRcv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AaaActivity.this, "on long click", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
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
    public void onSuccess(Object object) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
        isLoadingData = false;
        footerBtn.setVisibility(View.GONE);
        if (isFirstLoadData) {
            waterFallAdapter.updateData((List<PhotoData>) object);
            isFirstLoadData = false;
        }
        waterFallAdapter.updateData(mDataManager.getData());
    }

    @Override
    public void onError(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }
}
