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

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.google.gson.Gson;
import com.irmansyah.catalogmovie.R;
import com.irmansyah.catalogmovie.data.model.Movie;
import com.irmansyah.catalogmovie.data.model.MovieResponse;
import com.irmansyah.catalogmovie.data.remote.ApiEndPoint;
import com.irmansyah.catalogmovie.ui.main.MainActivity;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.List;
import java.util.Random;

import dagger.android.AndroidInjection;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UpcomingSchedulerService extends GcmTaskService {

    private static final String TAG = "UpcomingSchedulerServic";

    public static String TAG_TASK_UPCOMING = "TAG_TASK_UPCOMING";

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public int onRunTask(TaskParams taskParams) {
        int result = 0;
        if (taskParams.getTag().equals(TAG_TASK_UPCOMING)) {

            compositeDisposable.add(loadData()
                    .observeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<MovieResponse>() {
                        @Override
                        public void accept(MovieResponse movieResponse) throws Exception {
                            Log.i(TAG, "accept: " + movieResponse.getResults().size());

                            List<Movie> items = movieResponse.getResults();
                            int index = new Random().nextInt(items.size());

                            Movie item = items.get(index);
                            String title = items.get(index).getTitle();
                            String message = items.get(index).getOverview();
                            int notifId = 200;

                            showNotification(title, message, notifId, item);
                        }
                    }));

            result = GcmNetworkManager.RESULT_SUCCESS;
        }
        return result;
    }

    public void showNotification(String title, String message, int notifId, Movie item) {
        NotificationManager notificationManagerCompat = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.MOVIE_ITEM, new Gson().toJson(item));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(this, android.R.color.transparent))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        notificationManagerCompat.notify(notifId, builder.build());
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "onCreate: ");
    }

    private Single<MovieResponse> loadData() {
        return Rx2AndroidNetworking.get(ApiEndPoint.END_POINT_MOVIE_UPCOMING_API)
                .addQueryParameter("api_key", ApiEndPoint.API_KEY)
                .addQueryParameter("language", "en-US")
                .build()
                .getObjectSingle(MovieResponse.class);
    }

    private void unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void reset() {
        unSubscribeFromObservable();
        compositeDisposable = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        reset();
    }
}
