package com.irmansyah.catalogmovie.ui.detailMovie;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
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

    private boolean isEdit = false;
    public static int REQUEST_ADD = 100;
    public static int RESULT_ADD = 101;
    public static int REQUEST_UPDATE = 200;
    public static int RESULT_UPDATE = 201;
    public static int RESULT_DELETE = 301;

    @Inject
    DetailMovieViewModel mDetailMovieViewModel;

    ActivityDetailMovieBinding mActivityDetailMovieBinding;

    private Movie mMovie;

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

        setCursor();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDetailMovieViewModel.closeDbHelper();
    }

    private void displayHomeAsUpEnabled() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setCursor() {
        mDetailMovieViewModel.setDatabaseOpen();
        Uri uri = getIntent().getData();

        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null){
                if(cursor.moveToFirst()) mMovie = new Movie(cursor);
                cursor.close();
            }
        }
        if (mMovie != null) {
            mDetailMovieViewModel.setMovieImage();
        }
        else {
            mMovie = getIntent().getParcelableExtra(MOVIE_INTENT);
        }

        mDetailMovieViewModel.setMovie(mMovie);
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

    @Override
    public void selectedStar() {
        mDetailMovieViewModel.insertValue(getContentResolver());
        setResult(RESULT_ADD);
    }

    @Override
    public void unSelectedStar() {
        mDetailMovieViewModel.deleteValue(getContentResolver(), getIntent().getData());
        setResult(RESULT_DELETE, null);
    }
}
