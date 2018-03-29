package com.irmansyah.catalogmovie.ui.main;

import com.irmansyah.catalogmovie.data.DataManager;
import com.irmansyah.catalogmovie.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by irmansyah on 23/02/18.
 */
@Module
public class MainActivityModule {

    @Provides
    MainViewModel provideMainViewModel(DataManager dataManager,
                                       SchedulerProvider schedulerProvider) {
        return new MainViewModel(dataManager, schedulerProvider);
    }

    @Provides
    MainPagerAdapter provideMainPagerAdapter(MainActivity activity) {
        return new MainPagerAdapter(activity.getSupportFragmentManager());
    }
}
