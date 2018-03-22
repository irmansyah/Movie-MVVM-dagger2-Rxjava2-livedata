package com.irmansyah.catalogmovie.data.local.db.sqlite.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.irmansyah.catalogmovie.data.local.db.AppDbHelper;
import com.irmansyah.catalogmovie.data.local.db.DbHelper;
import com.irmansyah.catalogmovie.data.local.db.sqlite.db.DatabaseContract;

import static com.irmansyah.catalogmovie.data.local.db.sqlite.db.DatabaseContract.AUTHORITY;
import static com.irmansyah.catalogmovie.data.local.db.sqlite.db.DatabaseContract.CONTENT_URI;

/**
 * Created by irmansyah on 15/03/18.
 */

public class MovieDbContentProvider extends ContentProvider {

    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_MOVIE, MOVIE);
        sUriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_MOVIE + "/#", MOVIE_ID);
    }

    private DbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new AppDbHelper(getContext());
        mDbHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE :
                cursor = mDbHelper.queryProvider();
                break;

            case MOVIE_ID :
                cursor = mDbHelper.queryByIdProvider(uri.getLastPathSegment());
                break;

            default :
                cursor = null;
                break;
        }
        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long added;
        switch (sUriMatcher.match(uri)) {
            case MOVIE :
                added = mDbHelper.insertProvider(contentValues);
                break;

            default :
                added = 0;
                break;
        }
        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        int updated ;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                updated =  mDbHelper.updateProvider(uri.getLastPathSegment(),contentValues);
                break;
            default:
                updated = 0;
                break;
        }

        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                deleted =  mDbHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleted;
    }
}
