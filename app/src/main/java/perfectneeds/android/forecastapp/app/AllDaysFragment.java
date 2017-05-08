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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import perfectneeds.android.forecastapp.R;
import perfectneeds.android.forecastapp.adapter.MyListCursorAdapter;
import perfectneeds.android.forecastapp.data.ForecastContract;
import perfectneeds.android.forecastapp.model.AllDaysList;

public class AllDaysFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    RecyclerView recyclerView;
    AllDaysList list = new AllDaysList();
    MyListCursorAdapter recyclerAdapter;
    Cursor cursor;
    LinearLayoutManager mLinearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.alldays_fragment , null , false);
        recyclerView = (RecyclerView) view.findViewById(R.id.alldaysrecyclerView);
        getLoaderManager().initLoader(0, null, this);

         setUpRecyclerView();

        return view;
    }


    private void setUpRecyclerView() {


        recyclerAdapter = new MyListCursorAdapter(getActivity(),cursor  );
        recyclerView.setAdapter(recyclerAdapter);
        int display_mode = getResources().getConfiguration().orientation;

        if (display_mode == Configuration.ORIENTATION_PORTRAIT) {
            mLinearLayoutManager = new LinearLayoutManager(getActivity()); // (Context context, int spanCount)
            mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(mLinearLayoutManager);
        } else {
            mLinearLayoutManager = new LinearLayoutManager(getActivity()); // (Context context, int spanCount)
            mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(mLinearLayoutManager);
        }

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                ForecastContract.AlldaysEntry.COLUMN_DAY_PERIOD,
                ForecastContract.AlldaysEntry.COLUMN_DAY_NAME,
                ForecastContract.AlldaysEntry.COLUMN_DAY_DATE,
                ForecastContract.AlldaysEntry.COLUMN_DAY_ICON,
                ForecastContract.AlldaysEntry.COLUMN_DAY_DESCRIPTION,
                ForecastContract.AlldaysEntry.COLUMN_DAY_FHTEMPERATURE,
                ForecastContract.AlldaysEntry.COLUMN_DAY_CHTEMPERATURE,
                ForecastContract.AlldaysEntry.COLUMN_DAY_FLTEMPERATURE,
                ForecastContract.AlldaysEntry.COLUMN_DAY_CLTEMPERATURE
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return  new CursorLoader(getContext(), ForecastContract.AlldaysEntry.CONTENT_URI, projection, null, null, null);
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

}