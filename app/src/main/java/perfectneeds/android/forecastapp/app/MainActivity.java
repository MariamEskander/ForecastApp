package perfectneeds.android.forecastapp.app;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import perfectneeds.android.forecastapp.R;
import perfectneeds.android.forecastapp.data.ForecastContract;
import perfectneeds.android.forecastapp.data.ForecastQueryHandler;
import perfectneeds.android.forecastapp.model.AllDays;
import perfectneeds.android.forecastapp.model.Today;
import perfectneeds.android.forecastapp.utils.RequestManager;
import perfectneeds.android.forecastapp.utils.response.ForecastResponse;

public class MainActivity extends AppCompatActivity implements MyListener{

    Toolbar toolbar;
    FragmentManager fragmentManager;
    ArrayList<Today> today = new ArrayList<>();
    ArrayList<AllDays> allDays = new ArrayList<>();
    LocalBroadcastManager mLocalBroadcastManager;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    NetworkChangeReceiver networkChangeReceiver;
    ProgressDialog progressDialog;
    boolean fistTime = true;

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);

        fragmentManager = getFragmentManager();
        if (savedInstanceState == null){
            TodayFragment todayFragment = new TodayFragment();
            AllDaysFragment allDaysFragment = new AllDaysFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.todayFrameLayout,todayFragment,"today");
            fragmentTransaction.add(R.id.allDaysFrameLayout,allDaysFragment,"allDays");
            fragmentTransaction.commit();
        }if (savedInstanceState != null) {
            fistTime = false;}

        progressDialog= new ProgressDialog(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Forecast App");

        networkChangeReceiver =  new NetworkChangeReceiver();



    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, intentFilter);

        IntentFilter intenFilter = new IntentFilter("my.result.network.intent");
        mLocalBroadcastManager.registerReceiver(resultBroadcastReceiver,intenFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkChangeReceiver);
        mLocalBroadcastManager.unregisterReceiver(resultBroadcastReceiver);

    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void getData(int position) {
        TodayFragment todayFragment = (TodayFragment) fragmentManager.findFragmentByTag("today");
        todayFragment.setData(position);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onSuccessGetDestinationRequest(final ForecastResponse forecastResponse) {
        Log.i(LOG_TAG ,"json object  " +forecastResponse);
        today = forecastResponse.getToday();
        allDays = forecastResponse.getAllDays();
        deleteTodayTable();
        deleteAlldaysTable();
        createTodayTable(today);
        createAlldaysTable(allDays);
        progressDialog.hide();

    }

    private void deleteAlldaysTable() {
        for (int i = 0; i < allDays.size(); i++) {
            String[] args = {String.valueOf(allDays.get(i).dayPeriod)};
            ForecastQueryHandler handler = new ForecastQueryHandler(this.getContentResolver());
            handler.startDelete(1, null, ForecastContract.AlldaysEntry.CONTENT_URI, ForecastContract.AlldaysEntry.COLUMN_DAY_PERIOD + " =? ", args);
            Log.i("Deleted Row", "Delete Row");
        }
    }

    private void deleteTodayTable() {
        for (int i = 0; i < today.size(); i++) {
            String[] args = {String.valueOf(today.get(i).todayPeriod)};
            ForecastQueryHandler handler = new ForecastQueryHandler(this.getContentResolver());
            handler.startDelete(1, null, ForecastContract.TodayEntry.CONTENT_URI, ForecastContract.TodayEntry.COLUMN_TODAY_PERIOD + " =? ", args);
            Log.i("Deleted Row", "Delete Row");
        }
    }

    private void createAlldaysTable(ArrayList<AllDays> allDays) {
        for (int i = 0; i < allDays.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(ForecastContract.AlldaysEntry.COLUMN_DAY_PERIOD, allDays.get(i).dayPeriod.get());
            values.put(ForecastContract.AlldaysEntry.COLUMN_DAY_NAME, allDays.get(i).dayName.get());
            values.put(ForecastContract.AlldaysEntry.COLUMN_DAY_DATE, allDays.get(i).dayDate.get());
            values.put(ForecastContract.AlldaysEntry.COLUMN_DAY_ICON, allDays.get(i).dayIcon.get());
            values.put(ForecastContract.AlldaysEntry.COLUMN_DAY_DESCRIPTION, allDays.get(i).dayDescription.get());
            values.put(ForecastContract.AlldaysEntry.COLUMN_DAY_FHTEMPERATURE, allDays.get(i).dayFHTemp.get());
            values.put(ForecastContract.AlldaysEntry.COLUMN_DAY_CHTEMPERATURE, allDays.get(i).dayCHTemp.get());
            values.put(ForecastContract.AlldaysEntry.COLUMN_DAY_FLTEMPERATURE, allDays.get(i).dayFLTemp.get());
            values.put(ForecastContract.AlldaysEntry.COLUMN_DAY_CLTEMPERATURE, allDays.get(i).dayCLTemp.get());


            ForecastQueryHandler handler = new ForecastQueryHandler(this.getContentResolver());
            handler.startInsert(1, null, ForecastContract.AlldaysEntry.CONTENT_URI, values);
            Log.i("data base", "Create alldays");
        }
    }

    private void createTodayTable(ArrayList<Today> today) {
        for (int i = 0; i < today.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(ForecastContract.TodayEntry.COLUMN_TODAY_PERIOD,today.get(i).todayPeriod.get());
            values.put(ForecastContract.TodayEntry.COLUMN_TODAY_NAME, today.get(i).todayName.get());
            values.put(ForecastContract.TodayEntry.COLUMN_TODAY_ICON, today.get(i).todayIcon.get());
            values.put(ForecastContract.TodayEntry.COLUMN_TODAY_DESCRIPTION, today.get(i).todayDescription.get());
            values.put(ForecastContract.TodayEntry.COLUMN_DAY_PERIOD, today.get(i).dayPeriod.get());

            ForecastQueryHandler handler = new ForecastQueryHandler(this.getContentResolver());
            handler.startInsert(1, null, ForecastContract.TodayEntry.CONTENT_URI, values);
            Log.i("data base", "Create today");
        }
    }


    private BroadcastReceiver resultBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String s = intent.getStringExtra("status");
            if (! s.equals("No Network")){
                if (fistTime) {
                    RequestManager.getInstance(context).doRequest().do_Request("GetData");
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                    fistTime =  false;
                }
            }
            Toast.makeText(context,s , Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putBoolean("firstTime",false);
    }
}
