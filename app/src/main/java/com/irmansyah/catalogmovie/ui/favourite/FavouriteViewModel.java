package com.irmansyah.catalogmovie.ui.favourite;


import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.util.Log;

import com.irmansyah.catalogmovie.data.DataManager;
import com.irmansyah.catalogmovie.data.model.db.MovieDb;
import com.irmansyah.catalogmovie.ui.base.BaseViewModel;
import com.irmansyah.catalogmovie.utils.rx.SchedulerProvider;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by irmansyah on 13/03/18.
 */

public class FavouriteViewModel extends BaseViewModel<FavouriteNavigator> {

    private static final String TAG = "FavouriteViewModel";

    private final ObservableArrayList<MovieDb> movieDbObservableArrayList = new ObservableArrayList<>();
    private final MutableLiveData<List<MovieDb>> movieDbListLiveData;

    public FavouriteViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

        movieDbListLiveData = new MutableLiveData<>();

        fetchFavourite();
    }

    private void fetchFavourite() {
        getCompositeDisposable().add(getDataManager().getFavouriteMovies()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<MovieDb>>() {
                    @Override
                    public void accept(List<MovieDb> movieDbs) throws Exception {
                        movieDbListLiveData.setValue(movieDbs);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "accept: ", throwable);
                    }
                }));
    }

    public MutableLiveData<List<MovieDb>> getMovieDbListLiveData() {
        return movieDbListLiveData;
    }

    public ObservableArrayList<MovieDb> getMovieDbObservableArrayList() {
        return movieDbObservableArrayList;
    }

    public void addMovieDbItemsToList(List<MovieDb> movieDbs) {
        movieDbObservableArrayList.clear();
        movieDbObservableArrayList.addAll(movieDbs);
    }
}
