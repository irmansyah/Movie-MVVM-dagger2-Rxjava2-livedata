package com.irmansyah.catalogmovie.ui.search;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.irmansyah.catalogmovie.BR;
import com.irmansyah.catalogmovie.R;
import com.irmansyah.catalogmovie.data.model.Movie;
import com.irmansyah.catalogmovie.databinding.ActivitySearchBinding;
import com.irmansyah.catalogmovie.ui.base.BaseActivity;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

import javax.inject.Inject;

public class SearchActivity extends BaseActivity<ActivitySearchBinding, SearchViewModel>
        implements SearchActivityNavigator {

    private static final String TAG = "SearchActivity";

    private static final String SEARCH_QUERY = "SEARCH_QUERY";

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

//    @Inject
//    LinearLayoutManager mLayoutManager;

    @Inject
    MovieSearchAdapter mMovieSearchAdapter;

    @Inject
    SearchViewModel mSearchViewModel;

    ActivitySearchBinding mActivitySearchBinding;

    public static Intent gotoSearchActivity(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivitySearchBinding = getViewDataBinding();
        mSearchViewModel.setNavigator(this);
        setSupportActionBar(mActivitySearchBinding.toolbar);
        setUp();
        subscribeToLiveData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        mActivitySearchBinding.searchView.setMenuItem(item);

        setSearchView();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                mActivitySearchBinding.searchView.setVisibility(View.VISIBLE);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setSearchView() {
        mActivitySearchBinding.searchView.showSearch();
        mActivitySearchBinding.searchView.setVoiceSearch(false);
        mActivitySearchBinding.searchView.setEllipsize(true);
        mActivitySearchBinding.searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i(TAG, "onQueryTextSubmit: " + query);
                mActivitySearchBinding.searchView.closeSearch();
                return true;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                Log.i(TAG, "onQueryTextChange: " + newText);
                if (newText.length() >= 2) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mSearchViewModel.doSearchMovieList(newText);
                        }
                    }, 500);
                }
                return true;
            }
        });

        mActivitySearchBinding.searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                Log.i(TAG, "onSearchViewShown: ");
            }

            @Override
            public void onSearchViewClosed() {
                Log.i(TAG, "onSearchViewClosed: ");
                finish();
            }
        });
    }

    private void setUp() {
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        mActivitySearchBinding.movieListRecyclerview.setLayoutManager(lm);
        mActivitySearchBinding.movieListRecyclerview.setItemAnimator(new DefaultItemAnimator());
        mActivitySearchBinding.movieListRecyclerview.setAdapter(mMovieSearchAdapter);
    }

    private void subscribeToLiveData() {
        mSearchViewModel.getSearchMovieListLiveData().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                mSearchViewModel.addSearchMovieItemsToList(movies);
            }
        });
    }

    @Override
    public SearchViewModel getViewModel() {
        mSearchViewModel = ViewModelProviders.of(this, mViewModelFactory).get(SearchViewModel.class);
        return mSearchViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void actionSearch() {

    }

    @Override
    public void failedLoadApi() {

    }
}
