package com.irmansyah.catalogmovie.data.remote;

import android.content.Context;

import com.irmansyah.catalogmovie.data.model.MovieResponse;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by irmansyah on 23/02/18.
 */

public class AppApiHelper implements ApiHelper {

    private final Context mContext;

    @Inject
    public AppApiHelper(Context context) {
        this.mContext = context;
    }

    @Override
    public Single<MovieResponse> getMovieApiCall(String query) {
        return Rx2AndroidNetworking.get(ApiEndPoint.END_POINT_MOVIE_API)
                .addQueryParameter("api_key", ApiEndPoint.API_KEY)
                .addQueryParameter("language", "en-US")
                .addQueryParameter("query", query)
                .build()
                .getObjectSingle(MovieResponse.class);
    }
}
