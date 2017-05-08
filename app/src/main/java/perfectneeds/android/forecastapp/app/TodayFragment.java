package perfectneeds.android.forecastapp.app;


import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import perfectneeds.android.forecastapp.R;
import perfectneeds.android.forecastapp.adapter.TodayListCursorAdaper;
import perfectneeds.android.forecastapp.data.ForecastContract;
import perfectneeds.android.forecastapp.model.DayList;

public class TodayFragment extends Fragment  implements LoaderManager.LoaderCallbacks<Cursor>{

    RecyclerView recyclerView;
   // TodayRecyclerAdapter recyclerAdapter;
    DayList list = new DayList();
    TodayListCursorAdaper recyclerAdapter;
    Cursor cursor;
    private int position;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.today_fragment , null , false);
        if (savedInstanceState != null){
            position = savedInstanceState.getInt("position");
            Log.i("position saved",position+"");
        } else {
            position=1;
            Log.i("position",position+"");
        }
        recyclerView = (RecyclerView) view.findViewById(R.id.todayrecyclerView);
        getLoaderManager().initLoader(0, null, this);
        setUpRecyclerView();

        return view;
    }

    private void setUpRecyclerView() {


        recyclerAdapter = new TodayListCursorAdaper(getActivity() ,cursor );
        recyclerView.setAdapter(recyclerAdapter);
        int display_mode = getResources().getConfiguration().orientation;

        if (display_mode == Configuration.ORIENTATION_PORTRAIT) {
            LinearLayoutManager mLinearLayoutManagerHorizontal = new LinearLayoutManager(getActivity()); // (Context context, int spanCount)
            mLinearLayoutManagerHorizontal.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(mLinearLayoutManagerHorizontal);
        } else {
            LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getActivity()); // (Context context, int spanCount)
            mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(mLinearLayoutManagerVertical);
        }

    }


    public void setData(int pos) {

        position =  pos+1;
        getLoaderManager().restartLoader(0, null, this);
        setUpRecyclerView();

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                ForecastContract.TodayEntry.COLUMN_TODAY_PERIOD,
                ForecastContract.TodayEntry.COLUMN_TODAY_NAME,
                ForecastContract.TodayEntry.COLUMN_TODAY_ICON,
                ForecastContract.TodayEntry.COLUMN_TODAY_DESCRIPTION,
                ForecastContract.TodayEntry.COLUMN_DAY_PERIOD
        };

        String selection;
        String[] arguments = new String[1];
            selection = ForecastContract.TodayEntry.COLUMN_DAY_PERIOD + "=?";
            arguments[0] = String.valueOf(position);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return  new CursorLoader(getContext(), ForecastContract.TodayEntry.CONTENT_URI, projection, selection, arguments, null);
        }else
            return null;
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor data) {
        recyclerAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {
        recyclerAdapter.swapCursor(null);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position",position);
    }
}
