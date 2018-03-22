package com.irmansyah.catalogmovie.ui.nowPlaying;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import com.irmansyah.catalogmovie.ViewModelProviderFactory;
import com.irmansyah.catalogmovie.data.DataManager;
import com.irmansyah.catalogmovie.data.model.Movie;
import com.irmansyah.catalogmovie.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by irmansyah on 28/02/18.
 */
@Module
public class NowPlayingFragmentModule {

    @Provides
    NowPlayingViewModel provideNowPlayingViewModel(DataManager dataManager,
                                SchedulerProvider schedulerProvider) {
        return new NowPlayingViewModel(dataManager, schedulerProvider);
    }

    @Provides
    NowPlayingAdapter provideNowPlayingAdapter(NowPlayingFragment fragment,
                                               DataManager dataManager,
                                               SchedulerProvider schedulerProvider) {
        return new NowPlayingAdapter(new ArrayList<Movie>(), fragment.getActivity(), dataManager, schedulerProvider);
    }

    @Provides
    LinearLayoutManager provideNowPlayingLinearLayoutManager(NowPlayingFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }

    @Provides
    ViewModelProvider.Factory provideNowPlayingViewModelProviderFactory(NowPlayingViewModel blogViewModel) {
        return new ViewModelProviderFactory<>(blogViewModel);
    }
}
