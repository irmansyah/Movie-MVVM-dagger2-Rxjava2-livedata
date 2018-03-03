package com.irmansyah.catalogmovie.ui.upcoming;

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
 * Created by irmansyah on 28/02/18.
 */

public class UpcomingViewModel extends BaseViewModel<UpcomingFragmentNavigator> {

    private static final String TAG = "UpcomingViewModel";

    private final ObservableArrayList<Movie> upcomingMovieObservableArrayList = new ObservableArrayList<>();
    private final MutableLiveData<List<Movie>> upcomingMovieListLiveData;

    public UpcomingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        upcomingMovieListLiveData = new MutableLiveData<>();

        doRequestMovieUpcoming();
    }

    private void doRequestMovieUpcoming() {
        getCompositeDisposable().add(getDataManager().getMovieUpcomingApiCall()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<MovieResponse>() {
                    @Override
                    public void accept(MovieResponse movieResponse) throws Exception {
                        for (int i = 0; i < movieResponse.getResults().size(); i++) {
                            Log.i(TAG, "accept: " + movieResponse.getResults().get(i).getTitle());
                        }

                        upcomingMovieListLiveData.setValue(movieResponse.getResults());

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "accept: ", throwable);
                    }
                }));
    }

    public MutableLiveData<List<Movie>> getUpcomingMovieListLiveData() {
        return upcomingMovieListLiveData;
    }

    public ObservableArrayList<Movie> getUpcomingMovieObservableArrayList() {
        return upcomingMovieObservableArrayList;
    }

    public void addUpcomingMovieItemsToList(List<Movie> movies) {
        upcomingMovieObservableArrayList.clear();
        upcomingMovieObservableArrayList.addAll(movies);
    }
}
