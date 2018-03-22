package com.irmansyah.catalogmovie.data.local.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;

import com.irmansyah.catalogmovie.data.local.db.sqlite.entity.MovieDb;
import com.irmansyah.catalogmovie.data.model.Movie;

import java.util.List;

/**
 * Created by irmansyah on 12/03/18.
 */

public interface DbHelper {

    DbHelper open() throws SQLiteException;

    void close();

    List<Movie> query();

    long insert(Movie movie);

    int update(Movie movie);

    int delete(int id);

    Cursor queryByIdProvider(String id);

    Cursor queryProvider();

    long insertProvider(ContentValues values);

    int updateProvider(String id, ContentValues values);

    int deleteProvider(String id);
}
