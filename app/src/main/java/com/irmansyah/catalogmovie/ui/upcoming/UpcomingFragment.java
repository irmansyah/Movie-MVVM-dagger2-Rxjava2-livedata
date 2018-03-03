package com.irmansyah.catalogmovie.ui.upcoming;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.irmansyah.catalogmovie.BR;
import com.irmansyah.catalogmovie.R;
import com.irmansyah.catalogmovie.data.model.Movie;
import com.irmansyah.catalogmovie.databinding.FragmentUpcomingBinding;
import com.irmansyah.catalogmovie.ui.base.BaseFragment;
import com.irmansyah.catalogmovie.ui.nowPlaying.NowPlayingFragment;

import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends BaseFragment<FragmentUpcomingBinding, UpcomingViewModel>
        implements UpcomingFragmentNavigator, UpcomingAdapter.UpcomingAdapterListener {

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    @Inject
    UpcomingAdapter mUpcomingAdapter;

//    @Inject
//    LinearLayoutManager mLayoutManager;

    private UpcomingViewModel mUpcomingViewModel;

    FragmentUpcomingBinding mFragmentUpcomingBinding;

    public static UpcomingFragment newInstance() {
        Bundle args = new Bundle();
        UpcomingFragment fragment = new UpcomingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUpcomingViewModel.setNavigator(this);
        mUpcomingAdapter.setListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentUpcomingBinding = getViewDataBinding();
        setUp();
        subscribeToLiveData();
    }

    private void setUp() {
        GridLayoutManager gm = new GridLayoutManager(getActivity(), 3);
        mFragmentUpcomingBinding.upcomingRecyclerView.setLayoutManager(gm);
        mFragmentUpcomingBinding.upcomingRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mFragmentUpcomingBinding.upcomingRecyclerView.setAdapter(mUpcomingAdapter);
    }

    private void subscribeToLiveData() {
        mUpcomingViewModel.getUpcomingMovieListLiveData().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                mUpcomingViewModel.addUpcomingMovieItemsToList(movies);
            }
        });
    }

    @Override
    public UpcomingViewModel getViewModel() {
        mUpcomingViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UpcomingViewModel.class);
        return mUpcomingViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_upcoming;
    }

    @Override
    public void onRetryClick() {

    }
}
