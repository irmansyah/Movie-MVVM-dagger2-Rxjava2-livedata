package com.irmansyah.catalogmovie.ui.favourite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.irmansyah.catalogmovie.data.DataManager;
import com.irmansyah.catalogmovie.data.model.Movie;
import com.irmansyah.catalogmovie.databinding.ItemMovieDbFavouriteBinding;
import com.irmansyah.catalogmovie.ui.base.BaseViewHolder;
import com.irmansyah.catalogmovie.ui.detailMovie.DetailMovieActivity;
import com.irmansyah.catalogmovie.utils.rx.SchedulerProvider;

import static com.irmansyah.catalogmovie.data.local.db.sqlite.db.DatabaseContract.CONTENT_URI;

/**
 * Created by irmansyah on 13/03/18.
 */

public class MovieDbFavouriteAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final String TAG = "MovieDbFavouriteAdapter";

    private Cursor movieList;

    private final Activity mActivity;
    private final DataManager mDataManager;
    private final SchedulerProvider mSchedulerProvider;

    public MovieDbFavouriteAdapter(FavouriteActivity activity, DataManager dataManager, SchedulerProvider schedulerProvider) {
        this.mDataManager = dataManager;
        this.mSchedulerProvider = schedulerProvider;
        this.mActivity = activity;
    }

    public void setListMovieDbs(Cursor movieDbs) {
        this.movieList = movieDbs;
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
        if (movieList == null) return 0;
        return movieList.getCount();
    }

    private Movie getItem(int position){
        if (!movieList.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Movie(movieList);
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
            final Movie movie = getItem(position);
            mViewModel = new ItemMovieDbFavouriteViewModel(mDataManager, mSchedulerProvider);
            mViewModel.setMovieDb(movie, this);
            mBinding.setViewModel(mViewModel);
            mBinding.executePendingBindings();
        }

        @Override
        public void gotoDetailMovieActivity(Movie movie) {
//            Context context = mBinding.getRoot().getContext();
            Intent intent = new Intent(mActivity, DetailMovieActivity.class);
            Uri uri = Uri.parse(CONTENT_URI + "/" + movie.getId());
            intent.setData(uri);
            intent.putExtra(DetailMovieActivity.MOVIE_INTENT, movie);
            mActivity.startActivityForResult(intent, DetailMovieActivity.REQUEST_ADD);
        }
    }
}
