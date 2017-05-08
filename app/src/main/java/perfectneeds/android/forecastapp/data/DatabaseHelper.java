package perfectneeds.android.forecastapp.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper  extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "forecastsapp.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_ALLDAYS_CREATE=
            "CREATE TABLE " + ForecastContract.AlldaysEntry.TABLE_NAME + " (" +
                    ForecastContract.AlldaysEntry.COLUMN_DAY_PERIOD + " INTEGER PRIMARY KEY , " +
                    ForecastContract.AlldaysEntry.COLUMN_DAY_NAME + " TEXT, " +
                    ForecastContract.AlldaysEntry.COLUMN_DAY_DATE + " TEXT, " +
                    ForecastContract.AlldaysEntry.COLUMN_DAY_ICON + " TEXT, " +
                    ForecastContract.AlldaysEntry.COLUMN_DAY_DESCRIPTION + " TEXT, " +
                    ForecastContract.AlldaysEntry.COLUMN_DAY_FHTEMPERATURE + " TEXT, " +
                    ForecastContract.AlldaysEntry.COLUMN_DAY_CHTEMPERATURE + " TEXT, " +
                    ForecastContract.AlldaysEntry.COLUMN_DAY_FLTEMPERATURE + " TEXT, " +
                    ForecastContract.AlldaysEntry.COLUMN_DAY_CLTEMPERATURE + " TEXT " + ")";
    private static final String TABLE_TODAY_CREATE =
            "CREATE TABLE " + ForecastContract.TodayEntry.TABLE_NAME + " (" +
                    ForecastContract.TodayEntry.COLUMN_TODAY_PERIOD + " INTEGER PRIMARY KEY, " +
                    ForecastContract.TodayEntry.COLUMN_TODAY_NAME + " TEXT, " +
                    ForecastContract.TodayEntry.COLUMN_TODAY_ICON + " TEXT, " +
                    ForecastContract.TodayEntry.COLUMN_TODAY_DESCRIPTION + " TEXT, " +
                    ForecastContract.TodayEntry.COLUMN_DAY_PERIOD + " INTEGER, " +
                    " FOREIGN KEY("+ ForecastContract.TodayEntry.COLUMN_DAY_PERIOD + ") REFERENCES " +
                     ForecastContract.AlldaysEntry.TABLE_NAME +
                  "(" + ForecastContract.AlldaysEntry.COLUMN_DAY_PERIOD +") " + ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_ALLDAYS_CREATE);
        db.execSQL(TABLE_TODAY_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ForecastContract.AlldaysEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ForecastContract.TodayEntry.TABLE_NAME);
        onCreate(db);
    }
}