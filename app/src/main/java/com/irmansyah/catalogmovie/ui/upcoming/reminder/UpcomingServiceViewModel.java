package com.irmansyah.catalogmovie.ui.upcoming.reminder;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.irmansyah.catalogmovie.R;
import com.irmansyah.catalogmovie.data.DataManager;
import com.irmansyah.catalogmovie.data.model.Movie;
import com.irmansyah.catalogmovie.data.model.MovieResponse;
import com.irmansyah.catalogmovie.ui.base.BaseViewModel;
import com.irmansyah.catalogmovie.ui.main.MainActivity;
import com.irmansyah.catalogmovie.utils.rx.SchedulerProvider;

import java.util.List;
import java.util.Random;

import io.reactivex.functions.Consumer;

public class UpcomingServiceViewModel extends BaseViewModel {

    private static final String TAG = "UpcomingServiceViewMode";

    private Context context;

    public UpcomingServiceViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, Context context) {
        super(dataManager, schedulerProvider);
        this.context = context;
    }
}
