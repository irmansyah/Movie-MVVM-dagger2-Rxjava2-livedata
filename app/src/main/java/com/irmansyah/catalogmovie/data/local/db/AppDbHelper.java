package com.irmansyah.catalogmovie.data.local.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.irmansyah.catalogmovie.data.local.db.sqlite.db.DatabaseHelper;
import com.irmansyah.catalogmovie.data.local.db.sqlite.entity.MovieDb;
import com.irmansyah.catalogmovie.data.model.Movie;
import com.irmansyah.catalogmovie.di.scope.CatalogMovieScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static android.provider.BaseColumns._ID;
import static com.irmansyah.catalogmovie.data.local.db.sqlite.db.DatabaseContract.MovieColumns.POSTER_PATH;
import static com.irmansyah.catalogmovie.data.local.db.sqlite.db.DatabaseContract.MovieColumns.IS_FAVOURITE;
import static com.irmansyah.catalogmovie.data.local.db.sqlite.db.DatabaseContract.MovieColumns.OVERVIEW;
import static com.irmansyah.catalogmovie.data.local.db.sqlite.db.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.irmansyah.catalogmovie.data.local.db.sqlite.db.DatabaseContract.MovieColumns.TITLE;

import static com.irmansyah.catalogmovie.data.local.db.sqlite.db.DatabaseContract.TABLE_MOVIE;

/**
 * Created by irmansyah on 12/03/18.
 */
@CatalogMovieScope
public class AppDbHelper implements DbHelper {

    private static String DATABASE_TABLE = TABLE_MOVIE;

    private final Context mContext;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    @Inject
    public AppDbHelper(Context context) {
        this.mContext = context;
    }

    @Override
    public DbHelper open() throws SQLiteException {
        databaseHelper = new DatabaseHelper(mContext);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        databaseHelper.close();
    }

    @Override
    public List<Movie> query() {
        ArrayList<Movie> arrayList = new ArrayList<Movie>();
        Cursor cursor = database.query(DATABASE_TABLE,null,null,null,null,null,_ID +" DESC",null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                movie.setFavourite(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(IS_FAVOURITE))));
//                movieDb.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(CREATE_AT)));
//                movieDb.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(UPDATE_AT)));

                arrayList.add(movie);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    @Override
    public long insert(Movie movie) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(_ID, movie.getId());
        initialValues.put(TITLE, movie.getTitle());
        initialValues.put(OVERVIEW, movie.getOverview());
        initialValues.put(POSTER_PATH, movie.getPosterPath());
        initialValues.put(RELEASE_DATE, movie.getReleaseDate());
        initialValues.put(IS_FAVOURITE, movie.isFavourite());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    @Override
    public int update(Movie movie) {
        ContentValues args = new ContentValues();
        args.put(_ID, movie.getId());
        args.put(TITLE, movie.getTitle());
        args.put(OVERVIEW, movie.getOverview());
        args.put(POSTER_PATH, movie.getPosterPath());
        args.put(RELEASE_DATE, movie.getReleaseDate());
        args.put(IS_FAVOURITE, movie.isFavourite());
        return database.update(DATABASE_TABLE, args,
                _ID + "= '" + movie.getId() + "'", null);
    }

    @Override
    public int delete(int id) {
        return database.delete(TABLE_MOVIE, _ID + " = '" + id + "'", null);
    }

    @Override
    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }

    @Override
    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC");
    }

    @Override
    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    @Override
    public int updateProvider(String id, ContentValues values) {
        return  database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    @Override
    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
