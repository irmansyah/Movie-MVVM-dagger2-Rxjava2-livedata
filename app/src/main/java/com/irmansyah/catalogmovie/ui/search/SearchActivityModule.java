package com.irmansyah.catalogmovie.ui.search;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import com.irmansyah.catalogmovie.ViewModelProviderFactory;
import com.irmansyah.catalogmovie.data.DataManager;
import com.irmansyah.catalogmovie.data.model.Movie;
import com.irmansyah.catalogmovie.ui.nowPlaying.NowPlayingFragment;
import com.irmansyah.catalogmovie.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by irmansyah on 28/02/18.
 */
@Module
public class SearchActivityModule {

    @Provides
    SearchViewModel provideSearchViewModel(DataManager dataManager,
                                                   SchedulerProvider schedulerProvider) {
        return new SearchViewModel(dataManager, schedulerProvider);
    }

    @Provides
    MovieSearchAdapter provideMovieSearchAdapter() {
        return new MovieSearchAdapter(new ArrayList<Movie>());
    }

    @Provides
    LinearLayoutManager provideSearchLinearLayoutManager(NowPlayingFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }

    @Provides
//    @SearchViewModelProviderFactoryScope
    ViewModelProvider.Factory provideSearchViewModelProviderFactory(SearchViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }
}
