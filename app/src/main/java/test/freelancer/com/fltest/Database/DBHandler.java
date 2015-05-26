package test.freelancer.com.fltest.Database;

/**
 * Created by Android 17 on 5/26/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import test.freelancer.com.fltest.Model.TVShow;


public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tvshows.db";

    //TABLE INITIAL INSTALL
    private static final String TABLE_INITIALINSTALL = "Initial_Install";
    public static final String COLUMN_ISFIRSTINSTALL = "isFirstInstall";

    //TABLE USERS
    private static final String TABLE_SHOWS = "tv_shows";
    public static final String COLUMN_TVSHOWID = "_id";
    public static final String COLUMN_TVSHOWNAME = "name";
    public static final String COLUMN_STARTTIME = "start_time";
    public static final String COLUMN_ENDTIME = "end_time";
    public static final String COLUMN_CHANNEL = "channel";
    public static final String COLUMN_RATING = "rating";


    public DBHandler(Context context, String name,
                     SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_INITIAL_INSTALL_TABLE = "CREATE TABLE " +
                TABLE_INITIALINSTALL + "("
                + COLUMN_ISFIRSTINSTALL + " INTEGER NOT NULL)";
        db.execSQL(CREATE_INITIAL_INSTALL_TABLE);
        ContentValues values = new ContentValues();
        values.put(COLUMN_ISFIRSTINSTALL, "1");
        db.insert(TABLE_INITIALINSTALL, null, values);

        String CREATE_SHOWS_TABLE = "CREATE TABLE " +
                TABLE_SHOWS + "("
                + COLUMN_TVSHOWID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + COLUMN_TVSHOWNAME
                + " TEXT," + COLUMN_STARTTIME + " DATETIME," + COLUMN_ENDTIME + " DATETIME," + COLUMN_CHANNEL + " TEXT," + COLUMN_RATING + " TEXT" + ")";
        db.execSQL(CREATE_SHOWS_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOWS);
        onCreate(db);

    }

    //USER TABLE
    public void addShow(TVShow tvShow) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_TVSHOWNAME, tvShow.getName());
        values.put(COLUMN_STARTTIME, tvShow.getStartTime());
        values.put(COLUMN_ENDTIME, tvShow.getEndTime());
        values.put(COLUMN_CHANNEL, tvShow.getChannel());
        values.put(COLUMN_RATING, tvShow.getRating());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_SHOWS, null, values);
        db.close();
    }

    public boolean isShowExist(TVShow tvShow) {
        String query = "Select * FROM " + TABLE_SHOWS
                + " WHERE "
                + COLUMN_TVSHOWNAME + " =  \"" + tvShow.getName() + "\" "
                + " AND "
                + COLUMN_STARTTIME + " =  \"" + tvShow.getStartTime() + "\""
                + " AND "
                + COLUMN_ENDTIME + " =  \"" + tvShow.getEndTime() + "\""
                + " AND "
                + COLUMN_CHANNEL + " =  \"" + tvShow.getChannel() + "\""
                + " AND "
                + COLUMN_RATING + " =  \"" + tvShow.getRating() + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        boolean result;

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                result = true;
            } else {
                result = false;
            }
            cursor.close();
        } else {
            result = false;
        }
        db.close();
        return result;
    }

    public ArrayList<TVShow> getAllTVShow() {
        ArrayList<TVShow> arr_shows = new ArrayList<TVShow>();
        String query = "Select * FROM " + TABLE_SHOWS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            if (cursor.getCount() > 0)

                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    TVShow tvShow = new TVShow();
                    tvShow.setName(cursor.getString(cursor.getColumnIndex(COLUMN_TVSHOWNAME)));
                    tvShow.setStartTime(cursor.getString(cursor.getColumnIndex(COLUMN_STARTTIME)));
                    tvShow.setEndTime(cursor.getString(cursor.getColumnIndex(COLUMN_ENDTIME)));
                    tvShow.setChannel(cursor.getString(cursor.getColumnIndex(COLUMN_CHANNEL)));
                    tvShow.setRating(cursor.getString(cursor.getColumnIndex(COLUMN_RATING)));
                    arr_shows.add(tvShow);
                }
            cursor.close();
        }
        db.close();

        Log.i("Count on DB", String.valueOf(arr_shows.size()));
        return arr_shows;
    }


}

