package com.irmansyah.catalogmovie.ui.upcoming;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by irmansyah on 28/02/18.
 */
@Module
public abstract class UpcomingFragmentProvider {

    @ContributesAndroidInjector(modules = UpcomingFragmentModule.class)
    abstract UpcomingFragment provideUpcomingFragmentFactory();
}
