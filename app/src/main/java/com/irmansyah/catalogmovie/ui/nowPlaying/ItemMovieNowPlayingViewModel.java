package com.irmansyah.catalogmovie.ui.nowPlaying;

import android.databinding.ObservableField;

import com.irmansyah.catalogmovie.BuildConfig;
import com.irmansyah.catalogmovie.data.DataManager;
import com.irmansyah.catalogmovie.data.model.Movie;
import com.irmansyah.catalogmovie.ui.base.BaseViewModel;
import com.irmansyah.catalogmovie.utils.rx.SchedulerProvider;

/**
 * Created by irmansyah on 28/02/18.
 */

public class ItemMovieNowPlayingViewModel extends BaseViewModel {

    private Movie mMovie;

    public ObservableField<String> imageUrl;
    public ObservableField<String> title;
    public ObservableField<String> overview;
    public ObservableField<String> releaseDate;

    public MovieNowPlayingItemViewModelListener mListener;

    public ItemMovieNowPlayingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void setMovie(Movie movie, MovieNowPlayingItemViewModelListener listener) {
        this.mMovie = movie;
        this.mListener = listener;

        imageUrl = new ObservableField<>(BuildConfig.BASE_URL_POSTER_PATH_SMALL + mMovie.getPosterPath());
        title = new ObservableField<>(mMovie.getTitle());
        overview = new ObservableField<>(mMovie.getOverview());
        releaseDate = new ObservableField<>(mMovie.getReleaseDate());
    }

    public void gotoDetailMovieActivity() {
        mListener.gotoDetailMovieActivity(mMovie);
    }

    public void shareMovie() {
        getDataManager().shareToSocialMedia(mMovie.getPosterPath());
    }

    public interface MovieNowPlayingItemViewModelListener {
        void gotoDetailMovieActivity(Movie movie);
    }
}
