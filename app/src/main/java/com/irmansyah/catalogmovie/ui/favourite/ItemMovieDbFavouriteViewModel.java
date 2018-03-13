package com.irmansyah.catalogmovie.ui.favourite;

import android.databinding.ObservableField;
import android.util.Log;

import com.irmansyah.catalogmovie.BuildConfig;
import com.irmansyah.catalogmovie.data.model.Movie;
import com.irmansyah.catalogmovie.data.model.db.MovieDb;

/**
 * Created by irmansyah on 13/03/18.
 */

public class ItemMovieDbFavouriteViewModel {

    private static final String TAG = "ItemMovieDbFavouriteVie";

    private MovieDb mMovieDb;

    public ObservableField<String> imageUrl;
    public ObservableField<String> title;

    public ItemMovieDbFavouriteListener mListener;

    public ItemMovieDbFavouriteViewModel(MovieDb movieDb, ItemMovieDbFavouriteListener listener) {
        this.mMovieDb = movieDb;
        this.mListener = listener;

        imageUrl = new ObservableField<>(BuildConfig.BASE_URL_POSTER_PATH_SMALL + mMovieDb.imageUrl);
        title = new ObservableField<>(mMovieDb.title);
    }

    public interface ItemMovieDbFavouriteListener {
    }
}
