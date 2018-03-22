package com.irmansyah.catalogmovie.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.irmansyah.catalogmovie.data.local.db.DbHelper;
import com.irmansyah.catalogmovie.data.model.Movie;
import com.irmansyah.catalogmovie.data.model.MovieResponse;
import com.irmansyah.catalogmovie.data.remote.ApiHelper;
import com.irmansyah.catalogmovie.di.scope.CatalogMovieScope;

import java.util.List;

import javax.inject.Inject;

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
    public DbHelper open() throws SQLException {
        return mDbHelper.open();
    }

    @Override
    public void close() {
        mDbHelper.close();
    }

    @Override
    public List<Movie> query() {
        return mDbHelper.query();
    }

    @Override
    public long insert(Movie movie) {
        return insert(movie);
    }

    @Override
    public int update(Movie movie) {
        return mDbHelper.update(movie);
    }

    @Override
    public int delete(int id) {
        return mDbHelper.delete(id);
    }

    @Override
    public Cursor queryByIdProvider(String id) {
        return mDbHelper.queryByIdProvider(id);
    }

    @Override
    public Cursor queryProvider() {
        return mDbHelper.queryProvider();
    }

    @Override
    public long insertProvider(ContentValues values) {
        return mDbHelper.insertProvider(values);
    }

    @Override
    public int updateProvider(String id, ContentValues values) {
        return mDbHelper.updateProvider(id, values);
    }

    @Override
    public int deleteProvider(String id) {
        return mDbHelper.deleteProvider(id);
    }
}
