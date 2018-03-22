package com.irmansyah.catalogmovie.di.module;

import android.app.Application;
import android.content.Context;

import com.irmansyah.catalogmovie.R;
import com.irmansyah.catalogmovie.data.AppDataManager;
import com.irmansyah.catalogmovie.data.DataManager;
import com.irmansyah.catalogmovie.data.local.db.AppDbHelper;
import com.irmansyah.catalogmovie.data.local.db.DbHelper;
import com.irmansyah.catalogmovie.data.remote.ApiHelper;
import com.irmansyah.catalogmovie.data.remote.AppApiHelper;
import com.irmansyah.catalogmovie.di.DatabaseInfo;
import com.irmansyah.catalogmovie.di.scope.CatalogMovieScope;
import com.irmansyah.catalogmovie.utils.AppConstants;
import com.irmansyah.catalogmovie.utils.rx.AppSchedulerProvider;
import com.irmansyah.catalogmovie.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by irmansyah on 23/02/18.
 */
@Module
public class AppModule {

    @Provides
    @CatalogMovieScope
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @CatalogMovieScope
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @CatalogMovieScope
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @CatalogMovieScope
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

//    @Provides
//    @CatalogMovieScope
//    MovieDatabase provideMovieDatabase(Context context, @DatabaseInfo String dbName) {
//        return Room.databaseBuilder(context, MovieDatabase.class, dbName).fallbackToDestructiveMigration()
//                .build();
//    }

    @Provides
    @CatalogMovieScope
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

//    @Provides
//    @CatalogMovieScope
//    MovieDbContentProvider provideMovieDbContentProvider(MovieDatabase movieDatabase) {
//        return new MovieDbContentProvider(movieDatabase);
//    }

    @Provides
    @CatalogMovieScope
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/source-sans-pro/SourceSansPro-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }
}
