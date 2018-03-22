package com.irmansyah.catalogmovie.ui.favourite;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import com.irmansyah.catalogmovie.ViewModelProviderFactory;
import com.irmansyah.catalogmovie.data.DataManager;
import com.irmansyah.catalogmovie.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by irmansyah on 13/03/18.
 */
@Module
public class FavouriteActivityModule {

    @Provides
    FavouriteViewModel provideFavouriteVIewModel(DataManager dataManager,
                                                 SchedulerProvider schedulerProvider) {
        return new FavouriteViewModel(dataManager, schedulerProvider);
    }

    @Provides
    MovieDbFavouriteAdapter provideMovieDbFavouriteAdapter(FavouriteActivity activity,
                                                           DataManager dataManager,
                                                           SchedulerProvider schedulerProvider) {
        return new MovieDbFavouriteAdapter(activity, dataManager, schedulerProvider);
    }

    @Provides
    LinearLayoutManager provideFavouriteLinearLayoutManager(FavouriteActivity activity) {
        return new LinearLayoutManager(activity);
    }

    @Provides
    ViewModelProvider.Factory provideFavouriteViewModelProviderFactory(FavouriteViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }
}
