package com.irmansyah.catalogmovie.ui.favourite;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.irmansyah.catalogmovie.data.DataManager;
import com.irmansyah.catalogmovie.data.model.db.MovieDb;
import com.irmansyah.catalogmovie.databinding.ItemMovieDbFavouriteBinding;
import com.irmansyah.catalogmovie.ui.base.BaseViewHolder;
import com.irmansyah.catalogmovie.utils.rx.SchedulerProvider;

import java.util.List;

/**
 * Created by irmansyah on 13/03/18.
 */

public class MovieDbFavouriteAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<MovieDb> movieDbList;

    public MovieDbFavouriteAdapter(List<MovieDb> movieDbs, DataManager dataManager, SchedulerProvider schedulerProvider) {
        this.movieDbList = movieDbs;
    }

    public void addItems(List<MovieDb> movieDbs) {
        movieDbList.addAll(movieDbs);
        notifyDataSetChanged();
    }

    public void clearItems() {
        movieDbList.clear();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMovieDbFavouriteBinding binding = ItemMovieDbFavouriteBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new MovieDbFavouriteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return movieDbList.size();
    }

    public class MovieDbFavouriteViewHolder extends BaseViewHolder implements
            ItemMovieDbFavouriteViewModel.ItemMovieDbFavouriteListener {

        private ItemMovieDbFavouriteBinding mBinding;
        private ItemMovieDbFavouriteViewModel mViewModel;

        public MovieDbFavouriteViewHolder(ItemMovieDbFavouriteBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            final MovieDb movieDb = movieDbList.get(position);
            mViewModel = new ItemMovieDbFavouriteViewModel(movieDb, this);
            mBinding.setViewModel(mViewModel);
            mBinding.executePendingBindings();
        }
    }
}
