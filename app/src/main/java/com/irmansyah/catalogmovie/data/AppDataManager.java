package com.irmansyah.catalogmovie.data;

import android.content.Context;

import com.irmansyah.catalogmovie.data.model.MovieResponse;
import com.irmansyah.catalogmovie.data.remote.ApiHelper;
import com.irmansyah.catalogmovie.di.scope.CatalogMovieScope;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by irmansyah on 23/02/18.
 */
@CatalogMovieScope
public class AppDataManager implements DataManager {

    private final Context mContext;
    private final ApiHelper mAPiHelper;

    @Inject
    public AppDataManager(Context mContext, ApiHelper apiHlper) {
        this.mContext = mContext;
        this.mAPiHelper = apiHlper;
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
}
