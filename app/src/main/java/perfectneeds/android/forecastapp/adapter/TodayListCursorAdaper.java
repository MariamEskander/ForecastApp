package perfectneeds.android.forecastapp.adapter;


import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import perfectneeds.android.forecastapp.R;

public class TodayListCursorAdaper  extends CursorRecyclerViewAdapter<TodayListCursorAdaper.TodayViewHolder>{

    private static final String TAG = MyListCursorAdapter.class.getSimpleName();
    private LayoutInflater inflater;  // help us to inflate each layout inside the recyclerview
    Context context;

    public TodayListCursorAdaper(Context context, Cursor cursor){
        super(context,cursor);
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }


    @Override
    public void onBindViewHolder(TodayViewHolder viewHolder, Cursor cursor) {
        if (cursor != null) {
            viewHolder.setData(cursor);
        }
    }


    @Override
    public TodayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.today_list_item, parent, false);
        TodayViewHolder holder = new TodayViewHolder(view);

        return holder;
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


        public void setData(Cursor currObj) {

            title.setText(currObj.getString(currObj.getColumnIndex("todayname")));
            iconDesc.setText(currObj.getString(currObj.getColumnIndex("todaydescription")));
            Picasso.with(context).load(currObj.getString(currObj.getColumnIndex("todayicon"))).fit().centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(icon);

        }
    }
}