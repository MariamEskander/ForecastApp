package perfectneeds.android.forecastapp.data;


import android.net.Uri;
import android.provider.BaseColumns;

public final class ForecastContract {


    public static final String CONTENT_AUTHORITY = "perfectneeds.android.forecastapp.forecastprovider";
    public static final String PATH_TODAY = "today";
    public static final String PATH_ALLDAYS ="alldays";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    //content (Scheme)   //CONTENT_AUTHORITY (package name+provider name)   //path of table

    public String concatContent(String path){
        return "content://" + path;
    }

    public static final class TodayEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI , PATH_TODAY);

        // Table name
        public static final String TABLE_NAME = "today";
        //column (field) names
       // public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TODAY_PERIOD = "todayperiod";
        public static final String COLUMN_TODAY_NAME = "todayname";
        public static final String COLUMN_TODAY_ICON = "todayicon";
        public static final String COLUMN_TODAY_DESCRIPTION = "todaydescription";
        public static final String COLUMN_DAY_PERIOD = "dayperiod";
    }

    public static final class AlldaysEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI , PATH_ALLDAYS);
        // Table name
        public static final String TABLE_NAME = "alldays";
        //column names
       // public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_DAY_PERIOD = "dayperiod";
        public static final String COLUMN_DAY_NAME = "dayname";
        public static final String COLUMN_DAY_DATE = "daydate";
        public static final String COLUMN_DAY_ICON = "dayicon";
        public static final String COLUMN_DAY_DESCRIPTION = "daydescription";
        public static final String COLUMN_DAY_FHTEMPERATURE = "dayfhtemp";
        public static final String COLUMN_DAY_CHTEMPERATURE = "daychtemp";
        public static final String COLUMN_DAY_FLTEMPERATURE = "dayfltemp";
        public static final String COLUMN_DAY_CLTEMPERATURE = "daycltemp";

    }
}
