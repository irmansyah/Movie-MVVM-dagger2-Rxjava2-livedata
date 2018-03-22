package com.irmansyah.catalogmovie.ui.favourite;

import android.databinding.ObservableField;

import com.irmansyah.catalogmovie.BuildConfig;
import com.irmansyah.catalogmovie.data.DataManager;
import com.irmansyah.catalogmovie.data.model.Movie;
import com.irmansyah.catalogmovie.data.local.db.sqlite.entity.MovieDb;
import com.irmansyah.catalogmovie.ui.base.BaseViewModel;
import com.irmansyah.catalogmovie.utils.rx.SchedulerProvider;

/**
 * Created by irmansyah on 13/03/18.
 */

public class ItemMovieDbFavouriteViewModel extends BaseViewModel {

    private static final String TAG = "ItemMovieDbFavouriteVie";

    private Movie mMovie;

    public ObservableField<String> imageUrl;
    public ObservableField<String> title;

    public ItemMovieDbFavouriteListener mListener;

    public ItemMovieDbFavouriteViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void setMovieDb(Movie movie, ItemMovieDbFavouriteListener listener) {
        this.mMovie = movie;
        this.mListener = listener;

        imageUrl = new ObservableField<>(BuildConfig.BASE_URL_POSTER_PATH_SMALL + mMovie.getPosterPath());
        title = new ObservableField<>(mMovie.getTitle());
    }

    public void gotoDetailMovieActivity() {
        mListener.gotoDetailMovieActivity(mMovie);
    }

    public interface ItemMovieDbFavouriteListener {
        void gotoDetailMovieActivity(Movie movie);
    }
}
