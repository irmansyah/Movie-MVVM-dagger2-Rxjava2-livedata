package com.irmansyah.catalogmovie.di.component;

import android.app.Application;

import com.irmansyah.catalogmovie.MovieApp;
import com.irmansyah.catalogmovie.di.builder.ActivityBuilder;
import com.irmansyah.catalogmovie.di.module.AppModule;
import com.irmansyah.catalogmovie.di.scope.CatalogMovieScope;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by irmansyah on 13/12/17.
 */
@CatalogMovieScope
@Component(modules = {AndroidInjectionModule.class, AppModule.class, ActivityBuilder.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(MovieApp app);
}
