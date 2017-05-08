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
import perfectneeds.android.forecastapp.app.MyListener;


public class MyListCursorAdapter extends CursorRecyclerViewAdapter<MyListCursorAdapter.AllDaysViewHolder>{

    private static final String TAG = MyListCursorAdapter.class.getSimpleName();
    private LayoutInflater inflater;  // help us to inflate each layout inside the recyclerview
    Context context;

    public MyListCursorAdapter(Context context, Cursor cursor){
        super(context,cursor);
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }


    @Override
    public void onBindViewHolder(AllDaysViewHolder viewHolder, Cursor cursor) {
        if (cursor != null) {
            final int pos = cursor.getPosition();
            viewHolder.setData(cursor);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyListener myListener = (MyListener) context;
                    myListener.getData(pos);
                }
            });
        }
    }


    @Override
    public AllDaysViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.alldays_list_item, parent, false);
        AllDaysViewHolder holder = new AllDaysViewHolder(view);

        return holder;
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


        public void setData(Cursor currObj) {
            dayname.setText(currObj.getString(currObj.getColumnIndex("dayname")));
             daydate.setText(currObj.getString(currObj.getColumnIndex("daydate")));
            daydesc.setText(currObj.getString(currObj.getColumnIndex("daydescription")));
           fhTemp.setText(currObj.getString(currObj.getColumnIndex("dayfhtemp")));
           chTemp.setText(currObj.getString(currObj.getColumnIndex("daychtemp")));
            flTemp.setText(currObj.getString(currObj.getColumnIndex("dayfltemp")));
            clTemp.setText(currObj.getString(currObj.getColumnIndex("daycltemp")));
            Picasso.with(context).load(currObj.getString(currObj.getColumnIndex("dayicon"))).fit().centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(dayicon);
        }
    }
}