package com.irmansyah.catalogmovie.data;

import android.content.Context;
import android.database.Cursor;

import com.irmansyah.catalogmovie.data.local.db.DbHelper;
import com.irmansyah.catalogmovie.data.model.MovieResponse;
import com.irmansyah.catalogmovie.data.model.db.MovieDb;
import com.irmansyah.catalogmovie.data.remote.ApiHelper;
import com.irmansyah.catalogmovie.di.scope.CatalogMovieScope;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by irmansyah on 23/02/18.
 */
@CatalogMovieScope
public class AppDataManager implements DataManager {

    private final Context mContext;
    private final ApiHelper mAPiHelper;
    private final DbHelper mDbHelper;

    @Inject
    public AppDataManager(Context mContext, ApiHelper apiHelper, DbHelper dbHelper) {
        this.mContext = mContext;
        this.mAPiHelper = apiHelper;
        this.mDbHelper = dbHelper;
    }

    @Override
    public Single<MovieResponse> getMovieSearchApiCall(String query) {
        return mAPiHelper.getMovieSearchApiCall(query);
    }

    @Override
    public Single<MovieResponse> getMovieNowPlayingApiCall() {
        return mAPiHelper.getMovieNowPlayingApiCall();
    }

    @Override
    public Single<MovieResponse> getMovieUpcomingApiCall() {
        return mAPiHelper.getMovieUpcomingApiCall();
    }

    @Override
    public void shareToSocialMedia(String imageUrl) {
       mAPiHelper.shareToSocialMedia(imageUrl);
    }

    @Override
    public Observable<Boolean> insertFavouriteMovie(MovieDb movieDb) {
        return mDbHelper.insertFavouriteMovie(movieDb);
    }

    @Override
    public Observable<Boolean> deleteFavouriteMovie(MovieDb movieDb) {
        return mDbHelper.deleteFavouriteMovie(movieDb);
    }

    @Override
    public Observable<List<MovieDb>> getFavouriteMovies() {
        return mDbHelper.getFavouriteMovies();
    }

    @Override
    public Observable<MovieDb> getSingleFavouriteMovie(int id) {
        return mDbHelper.getSingleFavouriteMovie(id);
    }

    @Override
    public Observable<Boolean> insertFavouriteMovieCP(MovieDb movieDb) {
        return mDbHelper.insertFavouriteMovieCP(movieDb);
    }

    @Override
    public Observable<Boolean> deleteFavouriteMovieCP(MovieDb movieDb) {
        return mDbHelper.deleteFavouriteMovieCP(movieDb);
    }

    @Override
    public Observable<Cursor> getFavouriteMoviesCP() {
        return mDbHelper.getFavouriteMoviesCP();
    }

    @Override
    public Observable<Cursor> getSingleFavouriteMovieCP(int id) {
        return mDbHelper.getSingleFavouriteMovieCP(id);
    }
}
