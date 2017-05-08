package perfectneeds.android.forecastapp.model;

import android.database.Cursor;
import android.databinding.ObservableArrayList;

import java.io.Serializable;


public class DayList  implements Serializable {
    //implements serializable because we need it to pass from one activity to the other
    public final ObservableArrayList<Today> ItemList;

    public DayList() {

        ItemList = new ObservableArrayList<>();
    }

    public DayList(ObservableArrayList<Today> il) {
        ItemList = il;
    }


}