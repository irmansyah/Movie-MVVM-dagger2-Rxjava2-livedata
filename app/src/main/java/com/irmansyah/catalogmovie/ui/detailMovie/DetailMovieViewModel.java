package com.irmansyah.catalogmovie.ui.detailMovie;

import android.databinding.ObservableField;

import com.irmansyah.catalogmovie.BuildConfig;
import com.irmansyah.catalogmovie.data.DataManager;
import com.irmansyah.catalogmovie.data.model.Movie;
import com.irmansyah.catalogmovie.ui.base.BaseViewModel;
import com.irmansyah.catalogmovie.utils.rx.SchedulerProvider;

/**
 * Created by irmansyah on 26/02/18.
 */

public class DetailMovieViewModel extends BaseViewModel {

    public ObservableField<String> imageUrl = new ObservableField<>("");
    public ObservableField<String> title = new ObservableField<>("");
    public ObservableField<String> overview = new ObservableField<>("");
    public ObservableField<String> releaseDate = new ObservableField<>("");

    public DetailMovieViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void setMovie(Movie movie) {
        imageUrl.set(BuildConfig.BASE_URL_POSTER_PATH_BIG + movie.getPosterPath());
        title.set(movie.getTitle());
        overview.set(movie.getOverview());
        releaseDate.set(movie.getReleaseDate());
    }
}
