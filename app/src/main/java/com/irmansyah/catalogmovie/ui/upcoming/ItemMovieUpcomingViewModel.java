package com.irmansyah.catalogmovie.ui.upcoming;

import android.databinding.ObservableField;

import com.irmansyah.catalogmovie.BuildConfig;
import com.irmansyah.catalogmovie.data.model.Movie;

/**
 * Created by irmansyah on 28/02/18.
 */

public class ItemMovieUpcomingViewModel {

    private Movie mMovie;

    public ObservableField<String> imageUrl;
    public ObservableField<String> title;

    public MovieUpcomingItemViewModelListener mListener;

    public ItemMovieUpcomingViewModel(Movie movie, MovieUpcomingItemViewModelListener listener) {
        this.mMovie = movie;
        this.mListener = listener;

        imageUrl = new ObservableField<>(BuildConfig.BASE_URL_POSTER_PATH_SMALL + mMovie.getPosterPath());
        title = new ObservableField<>(mMovie.getTitle());
    }

    public void gotoDetailMovieActivity() {
        mListener.gotoDetailMovieActivity(mMovie);
    }

    public interface MovieUpcomingItemViewModelListener {
        void gotoDetailMovieActivity(Movie movie);
    }
}
