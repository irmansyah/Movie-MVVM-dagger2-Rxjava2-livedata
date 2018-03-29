package com.irmansyah.catalogmovie.ui.upcoming;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.irmansyah.catalogmovie.data.model.Movie;
import com.irmansyah.catalogmovie.databinding.ItemMovieUpcomingBinding;
import com.irmansyah.catalogmovie.ui.base.BaseViewHolder;
import com.irmansyah.catalogmovie.ui.detailMovie.DetailMovieActivity;

import java.util.List;

/**
 * Created by irmansyah on 28/02/18.
 */

public class UpcomingAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Movie> mMovieList;

    private UpcomingAdapterListener mListener;

    public UpcomingAdapter(List<Movie> movies) {
        this.mMovieList = movies;
    }

    public void setListener(UpcomingAdapterListener listener) {
        this.mListener = listener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMovieUpcomingBinding binding = ItemMovieUpcomingBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);

        return new UpcomingViewHolder(binding);
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

    public class UpcomingViewHolder extends BaseViewHolder
            implements ItemMovieUpcomingViewModel.MovieUpcomingItemViewModelListener {

        private ItemMovieUpcomingBinding mBinding;

        private ItemMovieUpcomingViewModel mViewModel;

        public UpcomingViewHolder(ItemMovieUpcomingBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            final Movie movie = mMovieList.get(position);

            mViewModel = new ItemMovieUpcomingViewModel(movie, this);
            mBinding.setViewModel(mViewModel);

            mBinding.executePendingBindings();
        }

        @Override
        public void gotoDetailMovieActivity(Movie movie) {
            Context context = mBinding.getRoot().getContext();
            context.startActivity(DetailMovieActivity.gotoDetailMovieActivity(context, movie));
        }
    }

    public interface UpcomingAdapterListener {
        void onRetryClick();
    }
}
