package perfectneeds.android.forecastapp.app;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class NetworkChangeReceiver extends BroadcastReceiver {
    String status;

    @Override
    public void onReceive(Context context, Intent intent) {

        LocalBroadcastManager mLocalBroadcastManager;

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                status = "wifi";

            else if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                status = "mobile";
        }else {
            status = "No Network";
        }

        Log.i("Status" , status);

        Intent returnIntent = new Intent("my.result.network.intent");
        returnIntent.putExtra("status",status);
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(context);
        mLocalBroadcastManager.sendBroadcast(returnIntent);

    }


}
