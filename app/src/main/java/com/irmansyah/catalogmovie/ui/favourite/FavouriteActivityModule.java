package com.irmansyah.catalogmovie.ui.favourite;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import com.irmansyah.catalogmovie.ViewModelProviderFactory;
import com.irmansyah.catalogmovie.data.DataManager;
import com.irmansyah.catalogmovie.data.model.db.MovieDb;
import com.irmansyah.catalogmovie.ui.nowPlaying.NowPlayingFragment;
import com.irmansyah.catalogmovie.utils.rx.SchedulerProvider;

import java.util.ArrayList;

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
    MovieDbFavouriteAdapter provideMovieDbFavouriteAdapter(DataManager dataManager,
                                               SchedulerProvider schedulerProvider) {
        return new MovieDbFavouriteAdapter(new ArrayList<MovieDb>(), dataManager, schedulerProvider);
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
