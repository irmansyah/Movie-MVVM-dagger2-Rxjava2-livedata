package com.irmansyah.catalogmovie.data.local.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

import com.irmansyah.catalogmovie.data.local.db.dao.MovieDao;
import com.irmansyah.catalogmovie.data.model.db.MovieDb;

/**
 * Created by irmansyah on 12/03/18.
 */
@Database(entities = MovieDb.class, version = 1)
public abstract class MovieDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();

    private static MovieDatabase sInstance;

    public static synchronized MovieDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room
                    .databaseBuilder(context.getApplicationContext(), MovieDatabase.class, "ex")
                    .build();
            sInstance.populateInitialData();
        }
        return sInstance;
    }

    @VisibleForTesting
    public static void switchToInMemory(Context context) {
        sInstance = Room.inMemoryDatabaseBuilder(context.getApplicationContext(),
                MovieDatabase.class).build();
    }

    private void populateInitialData() {
        if (movieDao().count() == 0) {
            MovieDb movieDb = new MovieDb();
            beginTransaction();
            try {
                for (int i = 0; i < 1; i++) {
                    movieDb.title = "name" + i;
                    movieDao().insert(movieDb);
                }
                setTransactionSuccessful();
            } finally {
                endTransaction();
            }
        }
    }
}
