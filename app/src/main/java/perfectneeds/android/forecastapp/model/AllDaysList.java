package perfectneeds.android.forecastapp.model;


import android.databinding.ObservableArrayList;

import java.io.Serializable;

public class AllDaysList implements Serializable {
    //implements serializable because we need it to pass from one activity to the other
    public final ObservableArrayList<AllDays> ItemList;

    public AllDaysList() {

        ItemList = new ObservableArrayList<>();
    }

    public AllDaysList(ObservableArrayList<AllDays> il) {
        ItemList = il;
    }


}