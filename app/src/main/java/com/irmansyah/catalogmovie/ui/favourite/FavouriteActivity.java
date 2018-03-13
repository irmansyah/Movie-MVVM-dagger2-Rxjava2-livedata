package com.irmansyah.catalogmovie.ui.favourite;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import com.irmansyah.catalogmovie.BR;
import com.irmansyah.catalogmovie.R;
import com.irmansyah.catalogmovie.data.model.db.MovieDb;
import com.irmansyah.catalogmovie.databinding.ActivityFavouriteBinding;
import com.irmansyah.catalogmovie.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

public class FavouriteActivity extends BaseActivity<ActivityFavouriteBinding, FavouriteViewModel> implements FavouriteNavigator {

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    @Inject
    FavouriteViewModel mFavouriteViewModel;

    @Inject
    MovieDbFavouriteAdapter mMovieDbFavouriteAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    ActivityFavouriteBinding mActivityFavouriteBinding;

    public static Intent gotoFavouriteActivity(Context context) {
        Intent intent = new Intent(context, FavouriteActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityFavouriteBinding = getViewDataBinding();
        mFavouriteViewModel.setNavigator(this);

        setSupportActionBar(mActivityFavouriteBinding.toolbar);
        displayHomeAsUpEnabled();

        setUp();
        subscribeToLiveData();
    }

    private void displayHomeAsUpEnabled() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setUp() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityFavouriteBinding.movieListRecyclerview.setLayoutManager(mLayoutManager);
        mActivityFavouriteBinding.movieListRecyclerview.setItemAnimator(new DefaultItemAnimator());
        mActivityFavouriteBinding.movieListRecyclerview.setAdapter(mMovieDbFavouriteAdapter);
    }

    private void subscribeToLiveData() {
        mFavouriteViewModel.getMovieDbListLiveData().observe(this, new Observer<List<MovieDb>>() {
            @Override
            public void onChanged(@Nullable List<MovieDb> movies) {
                mFavouriteViewModel.addMovieDbItemsToList(movies);
            }
        });
    }

    @Override
    public FavouriteViewModel getViewModel() {
        mFavouriteViewModel = ViewModelProviders.of(this, mViewModelFactory).get(FavouriteViewModel.class);
        return mFavouriteViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_favourite;
    }
}
