package perfectneeds.android.forecastapp.utils;


import android.content.Context;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;

import perfectneeds.android.forecastapp.utils.request.JsonObjectRequest;
import perfectneeds.android.forecastapp.utils.request.OkHttpStack;

public class RequestProxy {
    private static final String LOG_TAG = RequestProxy.class.getSimpleName();
    private static final int SOCKET_TIMEOUT = 30000;
    public static final int DEFAULT_MAX_RETRIES = 3;

    private static RetryPolicy policy;
    private RequestQueue mRequestQueue;
    private Context mContext;


    public RequestProxy(Context context) {
        this.mContext = context;
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext(), new OkHttpStack());
    }

    public static RetryPolicy getPolicy() {
        if (policy == null) {
            policy = new DefaultRetryPolicy(SOCKET_TIMEOUT, DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        }
        return policy;
    }

    public void cancelAllRequests(String targetRequestTag) {mRequestQueue.cancelAll(targetRequestTag);}

    public Cache.Entry getCacheByKey(String key) {
        return mRequestQueue.getCache().get(key);
    }

    public void removeCache(String key) {mRequestQueue.getCache().remove(key);}

    public void removeCache() {mRequestQueue.getCache().clear();}

    public RequestQueue getmRequestQueue(){
        return  mRequestQueue;
    }

    public void do_Request(String requestTag) {
        JsonObjectRequest doRequest = new JsonObjectRequest();
        doRequest.setRetryPolicy(getPolicy());
        doRequest.setTag("");
        doRequest.setShouldCache(false);
        mRequestQueue.add(doRequest);
    }
}
