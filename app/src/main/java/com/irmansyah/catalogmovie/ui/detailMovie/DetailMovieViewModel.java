package com.irmansyah.catalogmovie.ui.detailMovie;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;

import com.irmansyah.catalogmovie.BuildConfig;
import com.irmansyah.catalogmovie.data.DataManager;
import com.irmansyah.catalogmovie.data.model.Movie;
import com.irmansyah.catalogmovie.data.model.db.MovieDb;
import com.irmansyah.catalogmovie.ui.base.BaseViewModel;
import com.irmansyah.catalogmovie.utils.rx.SchedulerProvider;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by irmansyah on 26/02/18.
 */

public class DetailMovieViewModel extends BaseViewModel<DetailMovieActivityNovigator> {

    private static final String TAG = "DetailMovieViewModel";

    public ObservableField<String> imageUrl = new ObservableField<>("");
    public ObservableField<String> title = new ObservableField<>("");
    public ObservableField<String> overview = new ObservableField<>("");
    public ObservableField<String> releaseDate = new ObservableField<>("");
    public ObservableBoolean isStarSelectedObs = new ObservableBoolean();

    private boolean isStarSelected;

    private Movie mMovie;

    public DetailMovieViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void setMovie(Movie movie) {
        this.mMovie = movie;
        imageUrl.set(BuildConfig.BASE_URL_POSTER_PATH_BIG + movie.getPosterPath());
        title.set(movie.getTitle());
        overview.set(movie.getOverview());
        releaseDate.set(movie.getReleaseDate());

        setSelectedStar();
    }

    private void setSelectedStar() {
        getCompositeDisposable().add(getDataManager().getSingleFavouriteMovie(mMovie.getId())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<MovieDb>() {
                    @Override
                    public void accept(MovieDb movieDb) throws Exception {
                        isStarSelected = movieDb.isFavourite();
                        isStarSelectedObs.set(isStarSelected);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "accept: ", throwable);
                    }
                }));
    }

    public void onShareClicked() {
        getDataManager().shareToSocialMedia(mMovie.getPosterPath());
    }

    public void onStarClicked() {
        isStarSelected = !isStarSelected;
        if (isStarSelected) {
            selectedStar();
            isStarSelectedObs.set(true);
            getNavigator().showSnackBarAdded();
        } else {
            unSelectedStar();
            isStarSelectedObs.set(false);
            getNavigator().showSnackBarDelete();
        }
    }

    private void selectedStar() {
        MovieDb movieDb = new MovieDb();
        movieDb.setId(mMovie.getId());
        movieDb.setTitle(mMovie.getTitle());
        movieDb.setOverview(mMovie.getOverview());
        movieDb.setImageUrl(mMovie.getPosterPath());
        movieDb.setFavourite(true);

        getCompositeDisposable().add(getDataManager().insertFavouriteMovie(movieDb)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        getAllApp();
                    }
                }));
    }

    private void unSelectedStar() {
        MovieDb movieDb = new MovieDb();
        movieDb.setId(mMovie.getId());
        movieDb.setFavourite(false);

        getCompositeDisposable().add(getDataManager().deleteFavouriteMovie(movieDb)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        getAllApp();
                    }
                }));

    }

    private void getAllApp() {
        getCompositeDisposable().add(getDataManager().getFavouriteMovies()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<MovieDb>>() {
                    @Override
                    public void accept(List<MovieDb> movieDbs) throws Exception {
                        for (int i = 0; i < movieDbs.size(); i++) {
                            Log.i(TAG, "getAllApp: " + movieDbs.get(i).getTitle());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "accept: ", throwable);
                    }
                }));
    }
}
