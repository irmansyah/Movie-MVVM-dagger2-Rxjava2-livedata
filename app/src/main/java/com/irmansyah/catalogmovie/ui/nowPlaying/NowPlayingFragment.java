package com.irmansyah.catalogmovie.ui.nowPlaying;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.irmansyah.catalogmovie.BR;
import com.irmansyah.catalogmovie.R;
import com.irmansyah.catalogmovie.data.model.Movie;
import com.irmansyah.catalogmovie.databinding.FragmentNowPlayingBinding;
import com.irmansyah.catalogmovie.ui.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends BaseFragment<FragmentNowPlayingBinding, NowPlayingViewModel>
        implements NowPlayingFragmentNavigator, NowPlayingAdapter.NowPlayingAdapterListener {

    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    @Inject
    NowPlayingAdapter mNowPlayingAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    private NowPlayingViewModel mNowPlayingViewModel;

    FragmentNowPlayingBinding mFragmentNowPlayingBinding;

    public static NowPlayingFragment newInstance() {
        Bundle args = new Bundle();
        NowPlayingFragment fragment = new NowPlayingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNowPlayingViewModel.setNavigator(this);
        mNowPlayingAdapter.setListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentNowPlayingBinding = getViewDataBinding();
        setUp();
        subscribeToLiveData();
    }

    private void setUp() {
//        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentNowPlayingBinding.nowPlayingRecyclerView.setLayoutManager(mLayoutManager);
        mFragmentNowPlayingBinding.nowPlayingRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mFragmentNowPlayingBinding.nowPlayingRecyclerView.setAdapter(mNowPlayingAdapter);
    }

    private void subscribeToLiveData() {
        mNowPlayingViewModel.getNowPlayingMovieListLiveData().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                mNowPlayingViewModel.addNowPlayingMovieItemsToList(movies);
            }
        });
    }

    @Override
    public NowPlayingViewModel getViewModel() {
        mNowPlayingViewModel = ViewModelProviders.of(this, mViewModelFactory).get(NowPlayingViewModel.class);
        return mNowPlayingViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_now_playing;
    }

    @Override
    public void onRetryClick() {

    }
}
