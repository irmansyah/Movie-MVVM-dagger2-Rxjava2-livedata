package com.irmansyah.catalogmovie.ui.favourite;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.irmansyah.catalogmovie.BR;
import com.irmansyah.catalogmovie.R;
import com.irmansyah.catalogmovie.data.local.db.sqlite.entity.MovieDb;
import com.irmansyah.catalogmovie.databinding.ActivityFavouriteBinding;
import com.irmansyah.catalogmovie.ui.base.BaseActivity;
import com.irmansyah.catalogmovie.ui.detailMovie.DetailMovieActivity;

import java.util.List;

import javax.inject.Inject;

import static com.irmansyah.catalogmovie.data.local.db.sqlite.db.DatabaseContract.CONTENT_URI;
import static com.irmansyah.catalogmovie.ui.detailMovie.DetailMovieActivity.REQUEST_UPDATE;

public class FavouriteActivity extends BaseActivity<ActivityFavouriteBinding, FavouriteViewModel>
        implements FavouriteNavigator {

    private static final String TAG = "FavouriteActivity";

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    @Inject
    FavouriteViewModel mFavouriteViewModel;

    @Inject
    MovieDbFavouriteAdapter mMovieDbFavouriteAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    ActivityFavouriteBinding mActivityFavouriteBinding;

    private Cursor list;

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

        new LoadMovieDbAsync().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DetailMovieActivity.REQUEST_ADD){
            if (resultCode == DetailMovieActivity.RESULT_ADD){
                new LoadMovieDbAsync().execute();
            }
        }
        else if (requestCode == REQUEST_UPDATE) {
            if (resultCode == DetailMovieActivity.RESULT_UPDATE) {
                new LoadMovieDbAsync().execute();
            }

            else if (resultCode == DetailMovieActivity.RESULT_DELETE) {
                new LoadMovieDbAsync().execute();
            }
        }
    }

    private void displayHomeAsUpEnabled() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setUp() {
        mMovieDbFavouriteAdapter.setListMovieDbs(list);

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityFavouriteBinding.movieListRecyclerview.setLayoutManager(mLayoutManager);
        mActivityFavouriteBinding.movieListRecyclerview.setItemAnimator(new DefaultItemAnimator());
        mActivityFavouriteBinding.movieListRecyclerview.setHasFixedSize(true);
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

    @Override
    public void setActionQuery() {
//        mFavouriteViewModel.setActionQuery(getContentResolver());
    }

    private class LoadMovieDbAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(CONTENT_URI,null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor notes) {
            super.onPostExecute(notes);

            list = notes;
            mMovieDbFavouriteAdapter.setListMovieDbs(list);
            mMovieDbFavouriteAdapter.notifyDataSetChanged();

            Log.i(TAG, "onPostExecute: " + list.getCount());

            if (list.getCount() == 0){
            }
        }
    }
}
