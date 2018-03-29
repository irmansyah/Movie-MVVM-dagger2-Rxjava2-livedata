package com.irmansyah.catalogmovie.ui.nowPlaying;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.irmansyah.catalogmovie.data.DataManager;
import com.irmansyah.catalogmovie.data.model.Movie;
import com.irmansyah.catalogmovie.databinding.ItemMovieNowPlayingBinding;
import com.irmansyah.catalogmovie.ui.base.BaseViewHolder;
import com.irmansyah.catalogmovie.ui.detailMovie.DetailMovieActivity;
import com.irmansyah.catalogmovie.utils.rx.SchedulerProvider;

import java.util.List;

import static com.irmansyah.catalogmovie.data.local.db.sqlite.db.DatabaseContract.CONTENT_URI;

/**
 * Created by irmansyah on 28/02/18.
 */

public class NowPlayingAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Movie> mMovieList;

    private final Activity mActivity;
    private final DataManager mDataManager;
    private final SchedulerProvider mSchedulerProvider;

    private NowPlayingAdapterListener mListener;

    public NowPlayingAdapter(List<Movie> movies, Activity activity, DataManager dataManager, SchedulerProvider schedulerProvider) {
        this.mMovieList = movies;
        this.mDataManager = dataManager;
        this.mSchedulerProvider = schedulerProvider;
        this.mActivity = activity;
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
            Intent intent = new Intent(context, DetailMovieActivity.class);
//            Intent intent = new Intent(context, Main2Activity.class);
            Uri uri = Uri.parse(CONTENT_URI + "/" + movie.getId());
            intent.setData(uri);
            intent.putExtra(DetailMovieActivity.MOVIE_INTENT, movie);
            mActivity.startActivityForResult(intent, DetailMovieActivity.REQUEST_UPDATE);
//            mActivity.startActivity(DetailMovieActivity.gotoDetailMovieActivity(mActivity, movie));
        }
    }

    public interface NowPlayingAdapterListener {
        void onRetryClick();
    }
}
