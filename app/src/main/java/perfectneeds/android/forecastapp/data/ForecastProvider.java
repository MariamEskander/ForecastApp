package perfectneeds.android.forecastapp.data;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import static perfectneeds.android.forecastapp.data.ForecastContract.CONTENT_AUTHORITY;
import static perfectneeds.android.forecastapp.data.ForecastContract.PATH_ALLDAYS;
import static perfectneeds.android.forecastapp.data.ForecastContract.PATH_TODAY;

public class ForecastProvider extends ContentProvider {

    //constants for the operation
    public static final int TODAY = 1;
    public static final int TODAY_PERIOD = 2;
    public static final int ALLDAYS = 3;
    public static final int ALLDAYS_PERIOD = 4;

    public static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // all code we put here will executed the first time any code is called from this class
    static {
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_TODAY, TODAY);
        //  perfectneeds.android.todos.todosprovider/todos
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_TODAY + "/#", TODAY_PERIOD);
        //  perfectneeds.android.todos.todosprovider/todos/4

        uriMatcher.addURI(CONTENT_AUTHORITY , PATH_ALLDAYS, ALLDAYS );
        uriMatcher.addURI(CONTENT_AUTHORITY , PATH_ALLDAYS + "/#", ALLDAYS_PERIOD );
        //authority        //pattern itself       //integer we want to return
    }
    private DatabaseHelper helper;


    @Override
    public boolean onCreate() {
        helper = new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor;
        int match = uriMatcher.match(uri);
        String inTables = ForecastContract.TodayEntry.TABLE_NAME
                +" inner join "
                + ForecastContract.AlldaysEntry.TABLE_NAME
                + " on " + ForecastContract.TodayEntry.COLUMN_DAY_PERIOD + " = "
                + ForecastContract.AlldaysEntry.TABLE_NAME +"."
                + ForecastContract.AlldaysEntry.COLUMN_DAY_PERIOD;

        SQLiteQueryBuilder builder;

        switch (match){
            case TODAY:
                builder = new SQLiteQueryBuilder();
                builder.setTables(inTables);
                cursor =db.query(ForecastContract.TodayEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case TODAY_PERIOD:
                builder = new SQLiteQueryBuilder();
                builder.setTables(inTables);
                selection = ForecastContract.TodayEntry.COLUMN_TODAY_PERIOD + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(ForecastContract.TodayEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case ALLDAYS:
                cursor = db.query(ForecastContract.AlldaysEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case ALLDAYS_PERIOD:
                selection = ForecastContract.AlldaysEntry.COLUMN_DAY_PERIOD+ "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(ForecastContract.AlldaysEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            default:
                throw  new IllegalArgumentException("Unknown URI");

        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int match = uriMatcher.match(uri);
        switch (match){
            case TODAY:
                return insertRecord(uri,values, ForecastContract.TodayEntry.TABLE_NAME);
            case ALLDAYS:
                return insertRecord(uri,values, ForecastContract.AlldaysEntry.TABLE_NAME);
            default:
                throw  new IllegalArgumentException("Insert unknown URI: " + uri);
        }
    }

    private Uri insertRecord(Uri uri, ContentValues values, String table) {
        //this time we need a writable database
        SQLiteDatabase db = helper.getWritableDatabase();
        long id = db.insert(table, null, values);
        if (id == -1) {
            Log.e("Error", "insert error for URI " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int match = uriMatcher.match(uri);
        long id;
        switch (match) {
            case TODAY:
                return deleteRecord(uri, null, null, ForecastContract.TodayEntry.TABLE_NAME);
            case TODAY_PERIOD:
                id = ContentUris.parseId(uri);
                selection = ForecastContract.TodayEntry.COLUMN_TODAY_PERIOD + "=?";
                selectionArgs = new String[]{String.valueOf(id)};
                return deleteRecord(uri, selection, selectionArgs, ForecastContract.TodayEntry.TABLE_NAME);
            case ALLDAYS:
                return deleteRecord(uri, null, null, ForecastContract.AlldaysEntry.TABLE_NAME);
            case ALLDAYS_PERIOD:
                id = ContentUris.parseId(uri);
                selection = ForecastContract.AlldaysEntry.COLUMN_DAY_PERIOD + "=?";
                selectionArgs = new String[]{String.valueOf(id)};
                return deleteRecord(uri, selection, selectionArgs, ForecastContract.AlldaysEntry.TABLE_NAME);

            default:
                throw new IllegalArgumentException("Insert unknown URI: " + uri);
        }
    }

    private int deleteRecord(Uri uri, String selection, String[] selectionArgs, String tableName) {
        //this time we need a writable database
        SQLiteDatabase db = helper.getWritableDatabase();
        int id = db.delete(tableName, selection, selectionArgs);
        if (id == -1) {
            Log.e("Error", "delete unknown URI " + uri);
            return -1;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return id;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int match = uriMatcher.match(uri);
        long id;
        switch (match) {
            case TODAY:
                return updateRecord(uri, values, selection, selectionArgs, ForecastContract.TodayEntry.TABLE_NAME);
            case TODAY_PERIOD:
                id = ContentUris.parseId(uri);
                selection = ForecastContract.TodayEntry.COLUMN_TODAY_PERIOD + "=?";
                selectionArgs = new String[]{String.valueOf(id)};
                return updateRecord(uri, values,  selection, selectionArgs, ForecastContract.TodayEntry.TABLE_NAME);
            case ALLDAYS:
                return updateRecord(uri, values, selection, selectionArgs, ForecastContract.AlldaysEntry.TABLE_NAME);
            case ALLDAYS_PERIOD:
                id = ContentUris.parseId(uri);
                selection = ForecastContract.AlldaysEntry.COLUMN_DAY_PERIOD + "=?";
                selectionArgs = new String[]{String.valueOf(id)};
                return updateRecord(uri, values, selection, selectionArgs, ForecastContract.AlldaysEntry.TABLE_NAME);
            default:
                throw new IllegalArgumentException("Update unknown URI: " + uri);
        }
    }

    private int updateRecord(Uri uri, ContentValues values, String selection, String[] selectionArgs, String tableName) {
        //this time we need a writable database
        SQLiteDatabase db = helper.getWritableDatabase();
        int id = db.update(tableName, values, selection, selectionArgs);
        if (id == 0) {
            Log.e("Error", "update error for URI " + uri);
            return -1;
        }
        return id;
    }


}

