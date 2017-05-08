package perfectneeds.android.forecastapp.model;


import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import java.io.Serializable;

public class Today implements Serializable {
    public final ObservableInt todayPeriod = new ObservableInt();
    public final ObservableInt  dayPeriod =new ObservableInt();
    public final ObservableField<String> todayName = new ObservableField<String>();
    public final ObservableField<String> todayIcon = new ObservableField<String>();
    public final ObservableField<String> todayDescription = new ObservableField<String>();


    public Today(int todayPeriod, String todayName,String todayIcon, String todayDescription , int dayPeriod ){
        this.todayPeriod.set(todayPeriod);
        this.todayName.set(todayName);
        this.todayIcon.set(todayIcon);
        this.todayDescription.set(todayDescription);
        this.dayPeriod.set(dayPeriod);
    }
}