package perfectneeds.android.forecastapp.data;


import android.content.AsyncQueryHandler;
import android.content.ContentResolver;

public class ForecastQueryHandler extends AsyncQueryHandler {

    public ForecastQueryHandler(ContentResolver cr) {
        super(cr);
    }

}
