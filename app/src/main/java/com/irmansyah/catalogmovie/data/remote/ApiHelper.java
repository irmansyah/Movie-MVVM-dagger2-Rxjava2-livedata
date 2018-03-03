package com.irmansyah.catalogmovie.data.remote;

import com.irmansyah.catalogmovie.data.model.MovieResponse;

import io.reactivex.Single;

/**
 * Created by irmansyah on 23/02/18.
 */

public interface ApiHelper {

    Single<MovieResponse> getMovieSearchApiCall(String query);

    Single<MovieResponse> getMovieNowPlayingApiCall();

    Single<MovieResponse> getMovieUpcomingApiCall();

    void shareToSocialMedia(String imageUrl);
}
