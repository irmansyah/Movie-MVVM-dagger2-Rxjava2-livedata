package com.irmansyah.catalogmovie.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.irmansyah.catalogmovie.ui.nowPlaying.NowPlayingFragment;
import com.irmansyah.catalogmovie.ui.upcoming.UpcomingFragment;

/**
 * Created by irmansyah on 28/02/18.
 */

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private int mTabCount;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
        mTabCount = 0;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return NowPlayingFragment.newInstance();
            case 1:
                return UpcomingFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mTabCount;
    }

    public void setCount(int count) {
        mTabCount = count;
    }
}
