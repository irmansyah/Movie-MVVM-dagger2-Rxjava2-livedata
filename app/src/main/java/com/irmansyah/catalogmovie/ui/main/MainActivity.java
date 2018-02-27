package com.irmansyah.catalogmovie.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.irmansyah.catalogmovie.BR;
import com.irmansyah.catalogmovie.R;
import com.irmansyah.catalogmovie.data.model.Movie;
import com.irmansyah.catalogmovie.databinding.ActivityMainBinding;
import com.irmansyah.catalogmovie.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel>
        implements MainActivityNavigator {

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    MovieAdapter mMovieAdapter;

    @Inject
    MainViewModel mMainViewModel;

    ActivityMainBinding mActivityMainBinding;

    public static Intent totoMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = getViewDataBinding();
        mMainViewModel.setNavigator(this);
        setUp();
        subscribeToLiveData();
    }

    private void setUp() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityMainBinding.movieListRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mActivityMainBinding.movieListRecyclerview.setItemAnimator(new DefaultItemAnimator());
        mActivityMainBinding.movieListRecyclerview.setAdapter(mMovieAdapter);
    }

    private void subscribeToLiveData() {
        mMainViewModel.getMovieListLiveData().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                mMainViewModel.addMovieItemsToList(movies);
            }
        });
    }

    @Override
    public MainViewModel getViewModel() {
        mMainViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainViewModel.class);
        return mMainViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void actionSearch() {
        String query = mActivityMainBinding.searchQueryEdt.getText().toString().trim();
        mMainViewModel.getMovieList(query);
        mActivityMainBinding.searchQueryEdt.setText("");
    }

    @Override
    public void failedLoadApi() {
        Toast.makeText(this, "Gagal memuat!", Toast.LENGTH_SHORT).show();
    }
}
