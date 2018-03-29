package com.irmansyah.catalogmovie.ui.detailMovie;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.irmansyah.catalogmovie.BuildConfig;
import com.irmansyah.catalogmovie.data.DataManager;
import com.irmansyah.catalogmovie.data.model.Movie;
import com.irmansyah.catalogmovie.ui.base.BaseViewModel;
import com.irmansyah.catalogmovie.utils.rx.SchedulerProvider;

import static com.irmansyah.catalogmovie.data.local.db.sqlite.db.DatabaseContract.CONTENT_URI;
import static com.irmansyah.catalogmovie.data.local.db.sqlite.db.DatabaseContract.MovieColumns.IS_FAVOURITE;
import static com.irmansyah.catalogmovie.data.local.db.sqlite.db.DatabaseContract.MovieColumns.OVERVIEW;
import static com.irmansyah.catalogmovie.data.local.db.sqlite.db.DatabaseContract.MovieColumns.POSTER_PATH;
import static com.irmansyah.catalogmovie.data.local.db.sqlite.db.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.irmansyah.catalogmovie.data.local.db.sqlite.db.DatabaseContract.MovieColumns.TITLE;
import static com.irmansyah.catalogmovie.data.local.db.sqlite.db.DatabaseContract.MovieColumns._ID;

/**
 * Created by irmansyah on 26/02/18.
 */

public class DetailMovieViewModel extends BaseViewModel<DetailMovieActivityNovigator> {

    private static final String TAG = "DetailMovieViewModel";

    public ObservableField<String> imageUrl = new ObservableField<>("");
    public ObservableField<String> title = new ObservableField<>("");
    public ObservableField<String> overview = new ObservableField<>("");
    public ObservableField<String> releaseDate = new ObservableField<>("");
    public ObservableBoolean isStarSelectedObs = new ObservableBoolean();
    public ObservableInt starSelectedMsgObs = new ObservableInt(View.INVISIBLE);

    private boolean isStarSelected;

    private Movie mMovie;

    public DetailMovieViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

        getDataManager().open();
    }

    public void setDatabaseOpen() {
        getDataManager().open();
    }

    public void setMovie(Movie movie) {
        this.mMovie = movie;

        imageUrl.set(BuildConfig.BASE_URL_POSTER_PATH_BIG + movie.getPosterPath());
        title.set(movie.getTitle());
        overview.set(movie.getOverview());
        releaseDate.set(movie.getReleaseDate());
//        setIsFavourite();
    }

    public void setMovieImage() {
        isStarSelected = true;
        isStarSelectedObs.set(isStarSelected);
        starSelectedMsgObs.set(View.VISIBLE);
    }

    private void setIsFavourite() {
        isStarSelected = mMovie.isFavourite();
        Log.i(TAG, "setIsFavourite: " + isStarSelected);
        if (isStarSelected) {
            isStarSelectedObs.set(true);
            starSelectedMsgObs.set(View.VISIBLE);
        } else {
            isStarSelectedObs.set(false);
            starSelectedMsgObs.set(View.INVISIBLE);
        }
    }

    public void onShareClicked() {
        getDataManager().shareToSocialMedia(mMovie.getPosterPath());
    }

    public void onStarClicked() {
        isStarSelected = !isStarSelected;
        if (isStarSelected) {
            getNavigator().selectedStar();
            isStarSelectedObs.set(true);
            starSelectedMsgObs.set(View.VISIBLE);
            getNavigator().showSnackBarAdded();
        } else {
            getNavigator().unSelectedStar();
            isStarSelectedObs.set(false);
            starSelectedMsgObs.set(View.INVISIBLE);
            getNavigator().showSnackBarDelete();
        }

        Log.i(TAG, "setIsFavourite: " + isStarSelected);
    }

    public void setUpCursor(Cursor cursor) {
        Movie movie = null;
        Log.i(TAG, "setUpCursor: " + cursor);
        if (cursor != null){
            if(cursor.moveToFirst()) movie = new Movie(cursor);
            cursor.close();
            Log.i(TAG, "setUpCursor: " + movie.isFavourite());
        }
    }

    public void insertValue(ContentResolver resolver) {
        ContentValues values = new ContentValues();
        values.put(_ID, mMovie.getId());
        values.put(TITLE, mMovie.getTitle());
        values.put(OVERVIEW, mMovie.getOverview());
        values.put(RELEASE_DATE, mMovie.getReleaseDate());
        values.put(POSTER_PATH, mMovie.getPosterPath());
        values.put(IS_FAVOURITE, true);

        resolver.insert(CONTENT_URI, values);
    }

    public void updateValue(ContentResolver resolver, Uri uri) {
        ContentValues values = new ContentValues();
        values.put(_ID, mMovie.getId());
        values.put(TITLE, mMovie.getTitle());
        values.put(OVERVIEW, mMovie.getOverview());
        values.put(RELEASE_DATE, mMovie.getReleaseDate());
        values.put(POSTER_PATH, mMovie.getPosterPath());
        values.put(IS_FAVOURITE, mMovie.isFavourite());

        resolver.update(uri, values, null, null);
    }

    public void deleteValue(ContentResolver resolver, Uri data) {
        resolver.delete(data, null, null);
    }

    public void closeDbHelper() {
        getDataManager().close();
    }
}
