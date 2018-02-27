package com.irmansyah.catalogmovie.ui.detailMovie;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.irmansyah.catalogmovie.BR;
import com.irmansyah.catalogmovie.R;
import com.irmansyah.catalogmovie.data.model.Movie;
import com.irmansyah.catalogmovie.databinding.ActivityDetailMovieBinding;
import com.irmansyah.catalogmovie.ui.base.BaseActivity;

import javax.inject.Inject;

public class DetailMovieActivity extends BaseActivity<ActivityDetailMovieBinding, DetailMovieViewModel> {

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
}
