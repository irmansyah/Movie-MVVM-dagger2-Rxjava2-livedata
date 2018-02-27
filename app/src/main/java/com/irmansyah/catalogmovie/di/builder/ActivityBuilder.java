package com.irmansyah.catalogmovie.di.builder;

import com.irmansyah.catalogmovie.ui.detailMovie.DetailMovieActivity;
import com.irmansyah.catalogmovie.ui.detailMovie.DetailMovieActivityModule;
import com.irmansyah.catalogmovie.ui.main.MainActivity;
import com.irmansyah.catalogmovie.ui.main.MainActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by irmansyah on 13/12/17.
 */

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = DetailMovieActivityModule.class)
    abstract DetailMovieActivity bindDetailMovieActivity();
}
