package com.irmansyah.catalogmovie.ui.detailMovie;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.irmansyah.catalogmovie.BR;
import com.irmansyah.catalogmovie.R;
import com.irmansyah.catalogmovie.data.model.Movie;
import com.irmansyah.catalogmovie.databinding.ActivityDetailMovieBinding;
import com.irmansyah.catalogmovie.ui.base.BaseActivity;
import com.irmansyah.catalogmovie.ui.favourite.FavouriteActivity;

import javax.inject.Inject;

public class DetailMovieActivity extends BaseActivity<ActivityDetailMovieBinding, DetailMovieViewModel>
        implements DetailMovieActivityNovigator {

    public static final String TAG = "DetailMovieActivity";

    public static final String MOVIE_INTENT = "MOVIE_INTENT";

    @Inject
    DetailMovieViewModel mDetailMovieViewModel;

    ActivityDetailMovieBinding mActivityDetailMovieBinding;

    public static Intent gotoDetailMovieActivity(Context context, Movie movie) {
        Intent intent = new Intent(context, DetailMovieActivity.class);
        intent.putExtra(MOVIE_INTENT, movie);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityDetailMovieBinding = getViewDataBinding();
        mDetailMovieViewModel.setNavigator(this);

        setSupportActionBar(mActivityDetailMovieBinding.toolbar);
        displayHomeAsUpEnabled();

        setUp();
    }

    private void displayHomeAsUpEnabled() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setUp() {
        Movie movie = getIntent().getParcelableExtra(MOVIE_INTENT);
        mDetailMovieViewModel.setMovie(movie);
    }

    @Override
    public DetailMovieViewModel getViewModel() {
        return mDetailMovieViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_detail_movie;
    }

    @Override
    public void showSnackBarAdded() {
        Snackbar snackbar = Snackbar
                .make(mActivityDetailMovieBinding.baseLayout, getString(R.string.favourite_added), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.favourite_btn), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(FavouriteActivity.gotoFavouriteActivity(getApplicationContext()));
                    }
                });

        snackbar.setActionTextColor(Color.RED);

        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);

        snackbar.show();
    }

    @Override
    public void showSnackBarDelete() {
        Snackbar snackbar = Snackbar
                .make(mActivityDetailMovieBinding.baseLayout, getString(R.string.favourite_remove), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.favourite_btn), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(FavouriteActivity.gotoFavouriteActivity(getApplicationContext()));
                    }
                });

        snackbar.setActionTextColor(Color.RED);

        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);

        snackbar.show();
    }
}
