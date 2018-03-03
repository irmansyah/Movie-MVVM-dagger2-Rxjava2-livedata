package com.irmansyah.catalogmovie.ui.nowPlaying;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by irmansyah on 28/02/18.
 */
@Module
public abstract class NowPlayingFragmentProvider {

    @ContributesAndroidInjector(modules = NowPlayingFragmentModule.class)
    abstract NowPlayingFragment provideNowPlayingFragmentFactory();
}
