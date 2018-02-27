package com.irmansyah.catalogmovie.ui.main;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.util.Log;

import com.irmansyah.catalogmovie.data.DataManager;
import com.irmansyah.catalogmovie.data.model.Movie;
import com.irmansyah.catalogmovie.data.model.MovieResponse;
import com.irmansyah.catalogmovie.ui.base.BaseViewModel;
import com.irmansyah.catalogmovie.utils.rx.SchedulerProvider;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by irmansyah on 23/02/18.
 */

public class MainViewModel extends BaseViewModel<MainActivityNavigator> {

    private static final String TAG = "MainViewModel";

    private final ObservableArrayList<Movie> movieObservableArrayList = new ObservableArrayList<>();
    private final MutableLiveData<List<Movie>> movieListLiveData;

    public MainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        movieListLiveData = new MutableLiveData<>();
    }

    public void getMovieList(String query) {
        getCompositeDisposable().add(getDataManager().getMovieApiCall(query)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<MovieResponse>() {
                    @Override
                    public void accept(MovieResponse movieResponse) throws Exception {

                        movieListLiveData.setValue(movieResponse.getResults());

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "accept: ", throwable);

                        getNavigator().failedLoadApi();
                    }
                }));
    }

    public void onSearchClicked() {
        getNavigator().actionSearch();
    }

    public MutableLiveData<List<Movie>> getMovieListLiveData() {
        return movieListLiveData;
    }

    public ObservableArrayList<Movie> getMovieObservableArrayList() {
        return movieObservableArrayList;
    }

    public void addMovieItemsToList(List<Movie> movies) {
        movieObservableArrayList.clear();
        movieObservableArrayList.addAll(movies);
    }
}
