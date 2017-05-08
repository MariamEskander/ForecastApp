package perfectneeds.android.forecastapp.utils;


import android.content.Context;

public class RequestManager {
    private static RequestManager instance;

    private RequestProxy mAdsRequestProxy;

    private RequestManager(Context context) {
        mAdsRequestProxy = new RequestProxy(context);
    }

    // This method should be called first to do singleton initialization
    public static synchronized RequestManager getInstance(Context context) {
        if (instance == null) {
            instance = new RequestManager(context);
        }
        return instance;
    }

    public RequestProxy doRequest() {
        return mAdsRequestProxy;
    }

}
