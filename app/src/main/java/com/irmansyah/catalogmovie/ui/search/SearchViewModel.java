package com.irmansyah.catalogmovie.ui.search;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.util.Log;

import com.irmansyah.catalogmovie.data.DataManager;
import com.irmansyah.catalogmovie.data.model.Movie;
import com.irmansyah.catalogmovie.data.model.MovieResponse;
import com.irmansyah.catalogmovie.ui.base.BaseViewModel;
import com.irmansyah.catalogmovie.utils.rx.SchedulerProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * Created by irmansyah on 28/02/18.
 */

public class SearchViewModel extends BaseViewModel<SearchActivityNavigator> {

    private static final String TAG = "SearchViewModel";

    private final ObservableArrayList<Movie> searchMovieObservableArrayList = new ObservableArrayList<>();
    private final MutableLiveData<List<Movie>> searchMovieListLiveData;

    public SearchViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        searchMovieListLiveData = new MutableLiveData<>();
    }

    public void doSearchMovieList(String query) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().getMovieSearchApiCall(query)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<MovieResponse>() {
                    @Override
                    public void accept(MovieResponse movieResponse) throws Exception {

                        setIsLoading(false);
                        searchMovieListLiveData.setValue(movieResponse.getResults());

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "accept: ", throwable);

                        setIsLoading(false);
                        getNavigator().failedLoadApi();
                    }
                }));
    }

    public MutableLiveData<List<Movie>> getSearchMovieListLiveData() {
        return searchMovieListLiveData;
    }

    public ObservableArrayList<Movie> getSearchMovieObservableArrayList() {
        return searchMovieObservableArrayList;
    }

    public void addSearchMovieItemsToList(List<Movie> movies) {
        searchMovieObservableArrayList.clear();
        searchMovieObservableArrayList.addAll(movies);
    }
}
