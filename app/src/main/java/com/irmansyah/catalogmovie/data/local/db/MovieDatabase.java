package com.irmansyah.catalogmovie.data.local.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.irmansyah.catalogmovie.data.local.db.dao.MovieDao;
import com.irmansyah.catalogmovie.data.model.db.MovieDb;

/**
 * Created by irmansyah on 12/03/18.
 */
@Database(entities = MovieDb.class, version = 1)
public abstract class MovieDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();
}
