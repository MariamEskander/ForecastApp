package perfectneeds.android.forecastapp.model;


import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import java.io.Serializable;

public class AllDays implements Serializable {
    public final ObservableInt dayPeriod = new ObservableInt();
    public final ObservableField<String> dayName = new ObservableField<String>();
    public final ObservableField<String> dayDate = new ObservableField<String>();
    public final ObservableField<String> dayIcon = new ObservableField<String>();
    public final ObservableField<String> dayDescription = new ObservableField<String>();
    public final ObservableField<String> dayFHTemp = new ObservableField<String>();
    public final ObservableField<String> dayCHTemp = new ObservableField<String>();
    public final ObservableField<String> dayFLTemp = new ObservableField<String>();
    public final ObservableField<String> dayCLTemp = new ObservableField<String>();


    public AllDays(int dayPeriod, String dayName, String dayDate,String dayIcon, String dayDescription
            , String dayFHTemp ,String dayCHTemp, String dayFLTemp ,String dayCLTemp ){
        this.dayPeriod.set(dayPeriod);
        this.dayName.set(dayName);
        this.dayDate.set(dayDate);
        this.dayIcon.set(dayIcon);
        this.dayDescription.set(dayDescription);
        this.dayFHTemp.set(dayFHTemp);
        this.dayCHTemp.set(dayCHTemp);
        this.dayFLTemp.set(dayFLTemp);
        this.dayCLTemp.set(dayCLTemp);


    }
}
