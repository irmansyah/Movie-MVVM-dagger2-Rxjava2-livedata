package com.irmansyah.catalogmovie.ui.nowPlaying;

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

public class NowPlayingViewModel extends BaseViewModel<NowPlayingFragmentNavigator> {

    private static final String TAG = "NowPlayingViewModel";

    private final ObservableArrayList<Movie> nowPlayingMovieObservableArrayList = new ObservableArrayList<>();
    private final MutableLiveData<List<Movie>> nowPlayingMovieListLiveData;

    public NowPlayingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        nowPlayingMovieListLiveData = new MutableLiveData<>();

        doRequestMovieNowPlaying();
    }

    private void doRequestMovieNowPlaying() {
        getCompositeDisposable().add(getDataManager().getMovieNowPlayingApiCall()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<MovieResponse>() {
                    @Override
                    public void accept(MovieResponse movieResponse) throws Exception {
                        for (int i = 0; i < movieResponse.getResults().size(); i++) {
                            Log.i(TAG, "accept: " + movieResponse.getResults().get(i).getTitle());
                        }
                        nowPlayingMovieListLiveData.setValue(movieResponse.getResults());

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "accept: ", throwable);
                    }
                }));
    }

    public MutableLiveData<List<Movie>> getNowPlayingMovieListLiveData() {
        return nowPlayingMovieListLiveData;
    }

    public ObservableArrayList<Movie> getNowPlayingMovieObservableArrayList() {
        return nowPlayingMovieObservableArrayList;
    }

    public void addNowPlayingMovieItemsToList(List<Movie> movies) {
        nowPlayingMovieObservableArrayList.clear();
        nowPlayingMovieObservableArrayList.addAll(movies);
    }
}
