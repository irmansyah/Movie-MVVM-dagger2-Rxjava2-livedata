package com.irmansyah.catalogmovie.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.irmansyah.catalogmovie.BR;
import com.irmansyah.catalogmovie.R;
import com.irmansyah.catalogmovie.databinding.ActivityMainBinding;
import com.irmansyah.catalogmovie.ui.base.BaseActivity;
import com.irmansyah.catalogmovie.ui.favourite.FavouriteActivity;
import com.irmansyah.catalogmovie.ui.search.SearchActivity;
import com.irmansyah.catalogmovie.ui.setting.SettingActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel>
        implements HasSupportFragmentInjector, MainActivityNavigator, NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    public static final String MOVIE_ITEM = "MOVIE_ITEM";

    @Inject
    MainPagerAdapter mPagerAdapter;

    @Inject
    MainViewModel mMainViewModel;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;

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
        setNavDrawer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDrawer != null)
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    private void setNavDrawer() {
        mDrawer = mActivityMainBinding.drawerLayout;
        mToolbar = mActivityMainBinding.toolbar;
        mNavigationView = mActivityMainBinding.navigationView;

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawer,
                mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
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

            case R.id.action_settings:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (mActivityMainBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mActivityMainBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setUp() {
        setSupportActionBar(mActivityMainBinding.toolbar);

        mPagerAdapter.setCount(2);

        mActivityMainBinding.baseViewPager.setAdapter(mPagerAdapter);

        mActivityMainBinding.tabLayout.addTab(mActivityMainBinding.tabLayout.newTab().setText(getString(R.string.now_playing)));
        mActivityMainBinding.tabLayout.addTab(mActivityMainBinding.tabLayout.newTab().setText(getString(R.string.upcoming)));
        mActivityMainBinding.baseViewPager.setOffscreenPageLimit(mActivityMainBinding.tabLayout.getTabCount());
        mActivityMainBinding.baseViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mActivityMainBinding.tabLayout));

        mActivityMainBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mActivityMainBinding.baseViewPager.setCurrentItem(tab.getPosition());
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
    }

    @Override
    public void failedLoadApi() {
        Toast.makeText(this, "Gagal memuat!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawer.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.nav_home:
                Log.i(TAG, "onNavigationItemSelected: nav_home");
                mActivityMainBinding.baseViewPager.setCurrentItem(0);
                return true;
            case R.id.nav_search:
                startActivity(SearchActivity.gotoSearchActivity(this));
                Log.i(TAG, "onNavigationItemSelected: nav_search");
                return true;
            case R.id.nav_favourite:
                startActivity(FavouriteActivity.gotoFavouriteActivity(this));
                Log.i(TAG, "onNavigationItemSelected: nav_favourite");
                return true;
            case R.id.nav_setting:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                Log.i(TAG, "onNavigationItemSelected: nav_setting");
                return true;
            default:
                return false;
        }
    }
}
