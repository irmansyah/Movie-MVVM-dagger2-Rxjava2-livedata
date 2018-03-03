package com.irmansyah.catalogmovie.ui.nowPlaying;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.irmansyah.catalogmovie.data.DataManager;
import com.irmansyah.catalogmovie.data.model.Movie;
import com.irmansyah.catalogmovie.databinding.ItemMovieNowPlayingBinding;
import com.irmansyah.catalogmovie.ui.base.BaseViewHolder;
import com.irmansyah.catalogmovie.ui.detailMovie.DetailMovieActivity;
import com.irmansyah.catalogmovie.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by irmansyah on 28/02/18.
 */

public class NowPlayingAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Movie> mMovieList;

    private DataManager mDataManager;
    private SchedulerProvider mSchedulerProvider;

    private NowPlayingAdapterListener mListener;

    public NowPlayingAdapter(List<Movie> movies, DataManager dataManager, SchedulerProvider schedulerProvider) {
        this.mMovieList = movies;
        this.mDataManager = dataManager;
        this.mSchedulerProvider = schedulerProvider;
    }

    public void setListener(NowPlayingAdapterListener listener) {
        this.mListener = listener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemMovieNowPlayingBinding binding = ItemMovieNowPlayingBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);

        return new NowPlayingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public void addItems(List<Movie> movies) {
        mMovieList.addAll(movies);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mMovieList.clear();
    }

    public class NowPlayingViewHolder extends BaseViewHolder implements
            ItemMovieNowPlayingViewModel.MovieNowPlayingItemViewModelListener {

        private ItemMovieNowPlayingBinding mBinding;

        private ItemMovieNowPlayingViewModel mViewModel;

        public NowPlayingViewHolder(ItemMovieNowPlayingBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            final Movie movie = mMovieList.get(position);

            mViewModel = new ItemMovieNowPlayingViewModel(mDataManager, mSchedulerProvider);
            mViewModel.setMovie(movie, this);
            mBinding.setViewModel(mViewModel);

            mBinding.executePendingBindings();
        }

        @Override
        public void gotoDetailMovieActivity(Movie movie) {
            Context context = mBinding.getRoot().getContext();
            context.startActivity(DetailMovieActivity.gotoDetailMovieActivity(context, movie));
        }
    }

    public interface NowPlayingAdapterListener {
        void onRetryClick();
    }
}
