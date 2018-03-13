package com.irmansyah.catalogmovie.data.local.db;

import com.irmansyah.catalogmovie.data.model.db.MovieDb;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by irmansyah on 12/03/18.
 */

public interface DbHelper {

    Observable<Boolean> insertFavouriteMovie(final MovieDb movieDb);

    Observable<Boolean> deleteFavouriteMovie(final MovieDb movieDb);

    Observable<List<MovieDb>> getFavouriteMovies();

    Observable<MovieDb> getSingleFavouriteMovie(int id);
}
