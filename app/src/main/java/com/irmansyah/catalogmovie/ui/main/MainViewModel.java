package com.irmansyah.catalogmovie.ui.main;

import com.irmansyah.catalogmovie.data.DataManager;
import com.irmansyah.catalogmovie.ui.base.BaseViewModel;
import com.irmansyah.catalogmovie.utils.rx.SchedulerProvider;

/**
 * Created by irmansyah on 23/02/18.
 */

public class MainViewModel extends BaseViewModel<MainActivityNavigator> {

    private static final String TAG = "MainViewModel";

    public MainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
