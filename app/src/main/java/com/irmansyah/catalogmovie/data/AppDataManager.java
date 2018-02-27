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
    public Single<MovieResponse> getMovieApiCall(String query) {
        return mAPiHelper.getMovieApiCall(query);
    }
}
