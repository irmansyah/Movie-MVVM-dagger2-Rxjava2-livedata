package com.irmansyah.catalogmovie.di.builder;

import com.irmansyah.catalogmovie.ui.detailMovie.DetailMovieActivity;
import com.irmansyah.catalogmovie.ui.detailMovie.DetailMovieActivityModule;
import com.irmansyah.catalogmovie.ui.main.MainActivity;
import com.irmansyah.catalogmovie.ui.main.MainActivityModule;
import com.irmansyah.catalogmovie.ui.nowPlaying.NowPlayingFragmentProvider;
import com.irmansyah.catalogmovie.ui.search.SearchActivity;
import com.irmansyah.catalogmovie.ui.search.SearchActivityModule;
import com.irmansyah.catalogmovie.ui.upcoming.UpcomingFragmentProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by irmansyah on 13/12/17.
 */

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {
            MainActivityModule.class,
            NowPlayingFragmentProvider.class,
            UpcomingFragmentProvider.class
    })
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = SearchActivityModule.class)
    abstract SearchActivity bindSearchActivity();

    @ContributesAndroidInjector(modules = DetailMovieActivityModule.class)
    abstract DetailMovieActivity bindDetailMovieActivity();
}
