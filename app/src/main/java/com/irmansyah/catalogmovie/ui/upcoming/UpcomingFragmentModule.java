package com.irmansyah.catalogmovie.ui.upcoming;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import com.irmansyah.catalogmovie.ViewModelProviderFactory;
import com.irmansyah.catalogmovie.data.DataManager;
import com.irmansyah.catalogmovie.data.model.Movie;
import com.irmansyah.catalogmovie.ui.nowPlaying.NowPlayingFragment;
import com.irmansyah.catalogmovie.ui.nowPlaying.NowPlayingViewModel;
import com.irmansyah.catalogmovie.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by irmansyah on 28/02/18.
 */
@Module
public class UpcomingFragmentModule {

    @Provides
    UpcomingViewModel provideUpcomingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new UpcomingViewModel(dataManager, schedulerProvider);
    }

    @Provides
    UpcomingAdapter provideUpcomingAdapter() {
        return new UpcomingAdapter(new ArrayList<Movie>());
    }

    @Provides
    LinearLayoutManager provideUpcomingLinearLayoutManager(NowPlayingFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }

    @Provides
    ViewModelProvider.Factory provideUpcomingViewModelProviderFactory(UpcomingViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }
}
