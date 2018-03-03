package com.irmansyah.catalogmovie.ui.detailMovie;

import com.irmansyah.catalogmovie.data.DataManager;
import com.irmansyah.catalogmovie.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by irmansyah on 26/02/18.
 */
@Module
public class DetailMovieActivityModule {

    @Provides
    DetailMovieViewModel provideDetailMovieViewModel(DataManager dataManager,
                                                     SchedulerProvider schedulerProvider) {
        return new DetailMovieViewModel(dataManager, schedulerProvider);
    }

}
