package com.kale.wfalldemo.net;

import com.android.volley.Response;
import com.kale.wfalldemo.application.WaterFallApplication;


/**
 * @author Jack Tony
 * @brief
 * @date 2015/4/5
 */
public class GsonDecode<T> {

    /**
     * 向服务器请求数据，进行回调
     */
    public void getGsonData(String url, Class<T> cls, Response.Listener<T> listener,
            Response.ErrorListener errorListener) {
        
        GsonRequest<T> gsonRequest = new GsonRequest<T>(
                url,
                cls, listener, errorListener);
        WaterFallApplication.requestQueue.add(gsonRequest);
    }
}
