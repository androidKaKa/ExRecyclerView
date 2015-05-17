package com.kale.wfalldemo.aaa.adapter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kale.wfalldemo.ResponseCallback;
import com.kale.wfalldemo.aaa.mode.Club;
import com.kale.wfalldemo.aaa.mode.PhotoData;
import com.kale.wfalldemo.net.GsonDecode;

import android.util.Log;

import java.util.List;

/**
 * @author Jack Tony
 * @date 2015/5/17
 */
public class DataManager {

    private String TAG = getClass().getSimpleName();

    /** 加载最新数据的起点 */
    public final int LATEST_INDEX = 0;

    private List mData;

    /** 下一页的起始数 */
    private int mNextStart;

    /** 数据的list */
    private List<PhotoData> mDataList;

    public void loadNewData(final ResponseCallback callback) {
        loadData(0, callback);
    }

    public void loadData(final int index, final ResponseCallback callback) {
        // 请求的地址头部
        final String URL_HEAD
                = "http://www.duitang.com/napi/blog/list/by_club_id/?club_id=54aa79d9a3101a0f75731c62&limit=0&start=";
        // 执行网络请求
        GsonDecode<Club> mClubDd = new GsonDecode<>();

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
                callback.onSuccess(mDataList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onError("time out");
            }
        });
    }

    public List<PhotoData> getData() {
        return mDataList;
    }

    public void loadOldData(final ResponseCallback callback) {
        loadData(mNextStart, callback);
    }

}
