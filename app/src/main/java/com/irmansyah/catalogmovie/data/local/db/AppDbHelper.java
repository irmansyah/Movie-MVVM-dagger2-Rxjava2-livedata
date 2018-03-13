package com.irmansyah.catalogmovie.data.local.db;

import com.irmansyah.catalogmovie.data.model.db.MovieDb;
import com.irmansyah.catalogmovie.di.scope.CatalogMovieScope;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by irmansyah on 12/03/18.
 */
@CatalogMovieScope
public class AppDbHelper implements DbHelper {

    private final MovieDatabase mMovieDatabase;

    @Inject
    public AppDbHelper(MovieDatabase movieDatabase) {
        this.mMovieDatabase = movieDatabase;
    }

    @Override
    public Observable<Boolean> insertFavouriteMovie(final MovieDb movieDb) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mMovieDatabase.movieDao().insert(movieDb);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> deleteFavouriteMovie(final MovieDb movieDb) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mMovieDatabase.movieDao().delete(movieDb);
                return true;
            }
        });
    }

    @Override
    public Observable<List<MovieDb>> getFavouriteMovies() {
        return Observable.fromCallable(new Callable<List<MovieDb>>() {
            @Override
            public List<MovieDb> call() throws Exception {
                return mMovieDatabase.movieDao().loadAll();
            }
        });
    }

    @Override
    public Observable<MovieDb> getSingleFavouriteMovie(final int id) {
        return Observable.fromCallable(new Callable<MovieDb>() {
            @Override
            public MovieDb call() throws Exception {
                return mMovieDatabase.movieDao().findById(id);
            }
        });
    }
}
