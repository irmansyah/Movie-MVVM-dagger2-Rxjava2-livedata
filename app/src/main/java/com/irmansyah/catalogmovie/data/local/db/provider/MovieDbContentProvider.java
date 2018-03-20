package com.irmansyah.catalogmovie.data.local.db.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.irmansyah.catalogmovie.data.local.db.AppDbHelper;
import com.irmansyah.catalogmovie.data.local.db.DbHelper;
import com.irmansyah.catalogmovie.data.local.db.MovieDatabase;
import com.irmansyah.catalogmovie.data.local.db.dao.MovieDao;
import com.irmansyah.catalogmovie.data.model.db.MovieDb;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by irmansyah on 15/03/18.
 */

public class MovieDbContentProvider extends ContentProvider {

    private static final String TAG = "MovieDbContentProvider";

    public static final String AUTHORITY = "com.irmansyah.catalogmovie";

//    public static final Uri URI_MOVIE_DB = Uri.parse(
//            "content://" + AUTHORITY + "/" + MovieDb.TABLE_NAME);

    public static final Uri URI_MOVIE_DB = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(MovieDb.TABLE_NAME)
            .build();

    private static final int CODE_MOVIE_DB_DIR = 1;

    private static final int CODE_MOVIE_DB_ITEM = 2;

    /** The URI matcher. */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    @Inject
    MovieDatabase mMovieDatabase;

    @Inject
    DbHelper mDbHelper;

    static {
        sUriMatcher.addURI(AUTHORITY, MovieDb.TABLE_NAME, CODE_MOVIE_DB_DIR);
        sUriMatcher.addURI(AUTHORITY, MovieDb.TABLE_NAME + "/#", CODE_MOVIE_DB_ITEM);
    }


    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor;
        final int code = sUriMatcher.match(uri);
        if (code == CODE_MOVIE_DB_DIR || code == CODE_MOVIE_DB_ITEM) {

        }
        switch (sUriMatcher.match(uri)) {
            case CODE_MOVIE_DB_DIR :
                cursor = mMovieDatabase.movieDao().selectAllCP();
                break;
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
