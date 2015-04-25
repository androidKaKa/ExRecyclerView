package com.kale.wfalldemo.aaa.adapter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kale.wfalldemo.R;
import com.kale.wfalldemo.ResponseCallback;
import com.kale.wfalldemo.aaa.mode.Club;
import com.kale.wfalldemo.aaa.mode.PhotoData;
import com.kale.wfalldemo.net.GsonDecode;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import kale.mylibrary.ExBaseRecyclerAdapter;

/**
 * @author Jack Tony
 * @brief
 * @date 2015/4/11
 */
public class AaaWaterFallAdapter extends ExBaseRecyclerAdapter {

    /** 加载最新数据的起点 */
    private final int LATEST_INDEX = 0;

    private Context mContext;

    private ResponseCallback mCallback;

    /** Club的数据源 */
    private GsonDecode<Club> mClubDd;

    /** 下一页的起始数 */
    private int mNextStart;

    /** 数据的list */
    private List<PhotoData> mDataList;

    public AaaWaterFallAdapter(Context context) {
        mContext = context;
        mCallback = (ResponseCallback) context;
        mClubDd = new GsonDecode<>();
        loadNewData();
    }

    /**
     * 加载更多的数据
     */
    public void loadOldData() {
        addDataByIndex(mNextStart);
    }

    /**
     * 加载最新的数据
     */
    public void loadNewData() {
        // 更新到最新的数据，从最近的时间点开始获取
        addDataByIndex(LATEST_INDEX);
    }

    public List<PhotoData> getDataList() {
        return mDataList;
    }

    @Override
    public int getAdapterItemCount() {
        return (mDataList != null) ? mDataList.size() : 0;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, LayoutInflater layoutInflater) {
        View view = layoutInflater.inflate(R.layout.aaa_waterfall_item, parent, false);
        return new AaaWaterFallViewHolder(view);
    }

    @Override
    public int getItemViewType(int position, boolean nothing) {
        return 0;
    }

    // 图片的（高/宽）比率
    float picRatio;

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position, boolean nothing) {
        //Log.d(TAG, "pos ------ = " + position);
        if (viewHolder instanceof AaaWaterFallViewHolder) {
            AaaWaterFallViewHolder holder = (AaaWaterFallViewHolder) viewHolder;
            PhotoData mPhotosList = mDataList.get(position);

            holder.contentSdv.setImageURI(Uri.parse(mPhotosList.photo.path));
            //holder.contentSdv.setImageURI(Uri.parse("http://wenwen.soso.com/p/20100203/20100203005516-1158326774.jpg"));

            picRatio = (float) mPhotosList.photo.height / mPhotosList.photo.width;
            holder.contentSdv.setHeightRatio(picRatio);

            holder.descriptionTv.setText(mPhotosList.msg);
            // 必须设置加载的uri
            holder.headPicSdv.setImageURI(Uri.parse("http://wenwen.soso.com/p/20100203/20100203005516-1158326774.jpg"));

            holder.positionTv.setText("No." + position);

            /*holder.contentSdv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "click item positon = " + position, Toast.LENGTH_SHORT).show();
                }
            });*/
        }

    }


    /**
     * 得到适配器中的数据源，并更新数据
     */
    private void addDataByIndex(final int index) {
        // 请求的地址头部
        final String URL_HEAD
                = "http://www.duitang.com/napi/blog/list/by_club_id/?club_id=54aa79d9a3101a0f75731c62&limit=0&start=";
        // 执行网络请求
        mClubDd.getGsonData(URL_HEAD + index, Club.class, new Response.Listener<Club>() {
            @Override
            public void onResponse(Club club) {
                Log.d(TAG, "获取到数据");
                mNextStart = club.data.next_start;
                if (mDataList == null || index == LATEST_INDEX) {
                    mDataList = club.data.object_list;
                } else {
                    mDataList.addAll(club.data.object_list);

                }
                // 获取数据，更新UI
                notifyDataSetChanged();
                mCallback.onResponse("noting");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(mContext, "网络可能有问题", Toast.LENGTH_SHORT).show();
                mCallback.onError("");
            }
        });
    }
}
