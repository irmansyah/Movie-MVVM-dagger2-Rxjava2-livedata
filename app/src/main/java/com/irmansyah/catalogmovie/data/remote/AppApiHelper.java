package com.irmansyah.catalogmovie.data.remote;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.irmansyah.catalogmovie.R;
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
    public Single<MovieResponse> getMovieSearchApiCall(String query) {
        return Rx2AndroidNetworking.get(ApiEndPoint.END_POINT_MOVIE_API)
                .addQueryParameter("api_key", ApiEndPoint.API_KEY)
                .addQueryParameter("language", "en-US")
                .addQueryParameter("query", query)
                .build()
                .getObjectSingle(MovieResponse.class);
    }

    @Override
    public Single<MovieResponse> getMovieNowPlayingApiCall() {
        return Rx2AndroidNetworking.get(ApiEndPoint.END_POINT_MOVIE_NOW_PLAYING_API)
                .addQueryParameter("api_key", ApiEndPoint.API_KEY)
                .addQueryParameter("language", "en-US")
                .build()
                .getObjectSingle(MovieResponse.class);
    }

    @Override
    public Single<MovieResponse> getMovieUpcomingApiCall() {
        return Rx2AndroidNetworking.get(ApiEndPoint.END_POINT_MOVIE_UPCOMING_API)
                .addQueryParameter("api_key", ApiEndPoint.API_KEY)
                .addQueryParameter("language", "en-US")
                .build()
                .getObjectSingle(MovieResponse.class);
    }

    @Override
    public void shareToSocialMedia(String imageUrl) {
        String text = mContext.getResources().getString(R.string.share_string);
        Uri pictureUri = Uri.parse(imageUrl);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        shareIntent.putExtra(Intent.EXTRA_STREAM, pictureUri);
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        mContext.startActivity(Intent.createChooser(shareIntent,
                mContext.getResources().getString(R.string.share_image_url)));
    }
}
