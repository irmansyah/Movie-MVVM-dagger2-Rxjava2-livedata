package com.irmansyah.catalogmovie.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.irmansyah.catalogmovie.BR;
import com.irmansyah.catalogmovie.R;
import com.irmansyah.catalogmovie.data.model.Movie;
import com.irmansyah.catalogmovie.databinding.ActivityMainBinding;
import com.irmansyah.catalogmovie.ui.base.BaseActivity;
import com.irmansyah.catalogmovie.ui.search.MovieSearchAdapter;
import com.irmansyah.catalogmovie.ui.search.SearchActivity;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel>
        implements HasSupportFragmentInjector, MainActivityNavigator {

    @Inject
    MainPagerAdapter mPagerAdapter;

    @Inject
    MainViewModel mMainViewModel;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    ActivityMainBinding mActivityMainBinding;

    public static Intent gotoMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = getViewDataBinding();
        mMainViewModel.setNavigator(this);
        setUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                startActivity(SearchActivity.gotoSearchActivity(this));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void setUp() {
        setSupportActionBar(mActivityMainBinding.toolbar);

        mPagerAdapter.setCount(2);

        mActivityMainBinding.feedViewPager.setAdapter(mPagerAdapter);

        mActivityMainBinding.tabLayout.addTab(mActivityMainBinding.tabLayout.newTab().setText(getString(R.string.now_playing)));
        mActivityMainBinding.tabLayout.addTab(mActivityMainBinding.tabLayout.newTab().setText(getString(R.string.upcoming)));

        mActivityMainBinding.feedViewPager.setOffscreenPageLimit(mActivityMainBinding.tabLayout.getTabCount());

        mActivityMainBinding.feedViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mActivityMainBinding.tabLayout));

        mActivityMainBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mActivityMainBinding.feedViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    @Override
    public MainViewModel getViewModel() {
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
//        String query = mActivityMainBinding.searchQueryEdt.getText().toString().trim();
//        mMainViewModel.getMovieList(query);
//        mActivityMainBinding.searchQueryEdt.setText("");
    }

    @Override
    public void failedLoadApi() {
        Toast.makeText(this, "Gagal memuat!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
