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
import perfectneeds.android.forecastapp.app.MyListener;
import perfectneeds.android.forecastapp.model.AllDays;

public class AllDaysRecyclerAdaper extends RecyclerView.Adapter<AllDaysRecyclerAdaper.AllDaysViewHolder> {

    private static final String TAG = AllDaysRecyclerAdaper.class.getSimpleName();
    private LayoutInflater inflater;  // help us to inflate each layout inside the recyclerview
     ObservableArrayList<AllDays> mData = new ObservableArrayList<>();
    Context context;

    public AllDaysRecyclerAdaper(Context context,  ObservableArrayList<AllDays> mData) {
        this.inflater = LayoutInflater.from(context);
        this.mData = mData;
        this.context = context;
    }

    @Override
    public AllDaysRecyclerAdaper.AllDaysViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder");

        View view = inflater.inflate(R.layout.alldays_list_item, parent, false);
        AllDaysViewHolder holder = new AllDaysViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AllDaysViewHolder holder, final int position) {
        Log.i(TAG, "onBindViewHolder " + position);
        AllDays currObj = mData.get(position);
        holder.setData(currObj);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyListener myListener = (MyListener) context;
                myListener.getData(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class AllDaysViewHolder extends RecyclerView.ViewHolder {


        TextView dayname;
        TextView daydate;
        ImageView dayicon;
        TextView timezone;
        TextView daydesc;
        TextView fhTemp;
        TextView chTemp;
        TextView flTemp;
        TextView clTemp;

        public AllDaysViewHolder(View itemView) {
            super(itemView);
            dayname = (TextView) itemView.findViewById(R.id.dayname);
            daydate = (TextView) itemView.findViewById(R.id.daydate);
            dayicon = (ImageView) itemView.findViewById(R.id.dayicon);
            daydesc = (TextView) itemView.findViewById(R.id.daydesc);
            fhTemp = (TextView) itemView.findViewById(R.id.fhTemp);
            chTemp = (TextView) itemView.findViewById(R.id.chTemp);
            flTemp = (TextView) itemView.findViewById(R.id.flTemp);
            clTemp = (TextView) itemView.findViewById(R.id.clTemp);
        }


        public void setData(AllDays currObj) {
            dayname.setText(currObj.dayName.get());
            daydate.setText(currObj.dayDate.get());
            daydesc.setText(currObj.dayDescription.get());
            fhTemp.setText(currObj.dayFHTemp.get());
            chTemp.setText(currObj.dayCHTemp.get());
            flTemp.setText(currObj.dayFLTemp.get());
            clTemp.setText(currObj.dayCLTemp.get());
        }
    }
}

