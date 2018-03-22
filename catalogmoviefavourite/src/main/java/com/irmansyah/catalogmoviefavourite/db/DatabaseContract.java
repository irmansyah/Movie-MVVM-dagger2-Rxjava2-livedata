package com.irmansyah.catalogmoviefavourite.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by irmansyah on 21/03/18.
 */

public class DatabaseContract {

    public static String TABLE_MOVIE = "movie";

    public static final class MovieColumns  implements BaseColumns {

        public static String TITLE = "title";
        public static String OVERVIEW = "overview";
        public static String POSTER_PATH = "poster_path";
        public static String RELEASE_DATE = "releaseDate";
        public static String IS_FAVOURITE = "isFavourite";
        public static String CREATE_AT = "createdAt";
        public static String UPDATE_AT = "updatedAt";
    }

    public static final String AUTHORITY = "com.irmansyah.catalogmovie";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    public static String getColumnBoolean(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }
}
