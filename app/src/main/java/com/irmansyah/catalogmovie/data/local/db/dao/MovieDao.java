package com.irmansyah.catalogmovie.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.irmansyah.catalogmovie.data.model.db.MovieDb;

import java.util.List;

/**
 * Created by irmansyah on 19/02/18.
 */
@Dao
public interface MovieDao {

    @Query("SELECT * FROM moviedbs")
    List<MovieDb> loadAll();

    @Query("SELECT * FROM moviedbs WHERE id IN (:moviedbIds)")
    List<MovieDb> loadAllByIds(List<Long> moviedbIds);

    @Query("SELECT * FROM moviedbs WHERE id LIKE :id LIMIT 1")
    MovieDb findById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieDb movieDb);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MovieDb> moviedbs);

    @Delete
    void delete(MovieDb moviedb);
}
