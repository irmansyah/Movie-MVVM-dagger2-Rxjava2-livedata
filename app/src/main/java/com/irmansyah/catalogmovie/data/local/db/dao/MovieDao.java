package com.irmansyah.catalogmovie.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.irmansyah.catalogmovie.data.model.db.MovieDb;

import java.util.List;

/**
 * Created by irmansyah on 19/02/18.
 */
@Dao
public interface MovieDao {

    @Query("SELECT * FROM moviedbs")
    List<MovieDb> loadAll();

    @Query("SELECT * FROM moviedbs WHERE mId IN (:moviedbIds)")
    List<MovieDb> loadAllByIds(List<Long> moviedbIds);

    @Query("SELECT * FROM moviedbs WHERE mId LIKE :mId LIMIT 1")
    MovieDb findById(int mId);

    @Query("SELECT COUNT(mId) FROM moviedbs")
    int count();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieDb movieDb);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MovieDb> moviedbs);

    @Delete
    void delete(MovieDb moviedb);

    @Insert
    long[] insertAllCP(MovieDb[] movieDbs);

    @Query("SELECT * FROM moviedbs")
    Cursor loadAllCP();

    @Query("SELECT * FROM moviedbs WHERE " + MovieDb.COLUMN_ID + " = :id")
    Cursor selectByIdCP(long id);

    @Query("SELECT * FROM moviedbs WHERE mId LIKE :mId LIMIT 1")
    Cursor findByIdCP(int mId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCP(MovieDb movieDb);

    @Query("DELETE FROM " + MovieDb.TABLE_NAME + " WHERE " + MovieDb.COLUMN_ID + " = :id")
    int deleteByIdCP(long id);

    @Update
    int updateCP(MovieDb movieDb);

    @Query("SELECT * FROM " + MovieDb.TABLE_NAME)
    Cursor selectAllCP();

    @Delete
    void deleteCP(MovieDb moviedb);
}
