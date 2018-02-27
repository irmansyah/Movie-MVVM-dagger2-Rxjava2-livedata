package com.irmansyah.catalogmovie.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.irmansyah.catalogmovie.data.model.Movie;
import com.irmansyah.catalogmovie.ui.main.MovieAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by irmansyah on 23/02/18.
 */

public class BindingUtils {

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        if (url != null && !url.isEmpty()) {
            Picasso.with(imageView.getContext()).load(url).into(imageView);
        }
    }

    @BindingAdapter({"movieAdapter"})
    public static void addMovieAdapter(RecyclerView recyclerView,
                                     ArrayList<Movie> movies) {
        MovieAdapter adapter = (MovieAdapter) recyclerView.getAdapter();
        if(adapter != null) {
            adapter.clearItems();
            adapter.addItems(movies);
        }
    }
}
