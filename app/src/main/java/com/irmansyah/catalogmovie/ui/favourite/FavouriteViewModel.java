package com.irmansyah.catalogmovie.ui.favourite;


import android.arch.lifecycle.MutableLiveData;
import android.database.Cursor;
import android.databinding.ObservableArrayList;
import android.util.Log;

import com.irmansyah.catalogmovie.data.DataManager;
import com.irmansyah.catalogmovie.data.local.db.sqlite.entity.MovieDb;
import com.irmansyah.catalogmovie.ui.base.BaseViewModel;
import com.irmansyah.catalogmovie.utils.rx.SchedulerProvider;

import java.util.List;

/**
 * Created by irmansyah on 13/03/18.
 */

public class FavouriteViewModel extends BaseViewModel<FavouriteNavigator> {

    private static final String TAG = "FavouriteViewModel";

    public static final int LOADER_MOVIE_DB = 1;

    private final ObservableArrayList<MovieDb> movieDbObservableArrayList = new ObservableArrayList<>();
    private final MutableLiveData<List<MovieDb>> movieDbListLiveData;

    public FavouriteViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

        movieDbListLiveData = new MutableLiveData<>();
    }

    public void fetchFavourite(Cursor cursor) {
//        Log.i(TAG, "fetchFavourite: " + getColumnString(cursor, IS_FAVOURITE));
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
