package perfectneeds.android.forecastapp.adapter;


import android.content.Context;
import android.databinding.ObservableArrayList;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import perfectneeds.android.forecastapp.R;
import perfectneeds.android.forecastapp.model.Today;

public class TodayRecyclerAdapter  extends RecyclerView.Adapter<TodayRecyclerAdapter.TodayViewHolder> {

    private static final String TAG = TodayRecyclerAdapter.class.getSimpleName();
    private LayoutInflater inflater;  // help us to inflate each layout inside the recyclerview
    public ObservableArrayList<Today> mData = new ObservableArrayList<>();

    public TodayRecyclerAdapter(Context context, ObservableArrayList<Today>  mData) {
        this.inflater = LayoutInflater.from(context);
        this.mData = mData;
    }

    @Override
    public TodayRecyclerAdapter.TodayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder");

        View view = inflater.inflate(R.layout.today_list_item, parent, false);
        TodayViewHolder holder = new TodayViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(TodayViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder " + position);
        Today currObj = mData.get(position);
        holder.setData(currObj);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class TodayViewHolder extends RecyclerView.ViewHolder {


        TextView title;
        ImageView icon;
        TextView iconDesc;

        public TodayViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.todayname);
            icon = (ImageView) itemView.findViewById(R.id.todayicon);
            iconDesc = (TextView) itemView.findViewById(R.id.todaydes);
        }


        public void setData(Today currObj) {

            title.setText(currObj.todayName.get());
            iconDesc.setText(currObj.todayDescription.get());

        }
    }
}
