package perfectneeds.android.forecastapp.utils.response;


import java.util.ArrayList;

import perfectneeds.android.forecastapp.model.AllDays;
import perfectneeds.android.forecastapp.model.Today;

public class ForecastResponse {

    ArrayList<Today> today = new ArrayList<Today>();
    ArrayList<AllDays> allDays = new ArrayList<AllDays>();

    public ArrayList<Today> getToday() {
        return today;
    }

    public void setToday(ArrayList<Today> today) {
        this.today = today;
    }

    public ArrayList<AllDays> getAllDays() {
        return allDays;
    }

    public void setAllDays(ArrayList<AllDays> allDayse) {
        this.allDays = allDayse;
    }
}
