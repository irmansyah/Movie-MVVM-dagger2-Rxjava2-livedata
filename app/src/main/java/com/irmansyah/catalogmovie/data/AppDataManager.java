package com.irmansyah.catalogmovie.data;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.google.gson.Gson;
import com.irmansyah.catalogmovie.R;
import com.irmansyah.catalogmovie.data.local.db.DbHelper;
import com.irmansyah.catalogmovie.data.model.Movie;
import com.irmansyah.catalogmovie.data.model.MovieResponse;
import com.irmansyah.catalogmovie.data.remote.ApiHelper;
import com.irmansyah.catalogmovie.di.scope.CatalogMovieScope;
import com.irmansyah.catalogmovie.ui.main.MainActivity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by irmansyah on 23/02/18.
 */
@CatalogMovieScope
public class AppDataManager implements DataManager {

    private final Context mContext;
    private final ApiHelper mAPiHelper;
    private final DbHelper mDbHelper;

    @Inject
    public AppDataManager(Context mContext, ApiHelper apiHelper, DbHelper dbHelper) {
        this.mContext = mContext;
        this.mAPiHelper = apiHelper;
        this.mDbHelper = dbHelper;
    }

    @Override
    public Single<MovieResponse> getMovieSearchApiCall(String query) {
        return mAPiHelper.getMovieSearchApiCall(query);
    }

    @Override
    public Single<MovieResponse> getMovieNowPlayingApiCall() {
        return mAPiHelper.getMovieNowPlayingApiCall();
    }

    @Override
    public Single<MovieResponse> getMovieUpcomingApiCall() {
        return mAPiHelper.getMovieUpcomingApiCall();
    }

    @Override
    public void shareToSocialMedia(String imageUrl) {
       mAPiHelper.shareToSocialMedia(imageUrl);
    }

    @Override
    public DbHelper open() throws SQLException {
        return mDbHelper.open();
    }

    @Override
    public void close() {
        mDbHelper.close();
    }

    @Override
    public List<Movie> query() {
        return mDbHelper.query();
    }

    @Override
    public long insert(Movie movie) {
        return insert(movie);
    }

    @Override
    public int update(Movie movie) {
        return mDbHelper.update(movie);
    }

    @Override
    public int delete(int id) {
        return mDbHelper.delete(id);
    }

    @Override
    public Cursor queryByIdProvider(String id) {
        return mDbHelper.queryByIdProvider(id);
    }

    @Override
    public Cursor queryProvider() {
        return mDbHelper.queryProvider();
    }

    @Override
    public long insertProvider(ContentValues values) {
        return mDbHelper.insertProvider(values);
    }

    @Override
    public int updateProvider(String id, ContentValues values) {
        return mDbHelper.updateProvider(id, values);
    }

    @Override
    public int deleteProvider(String id) {
        return mDbHelper.deleteProvider(id);
    }

    @Override
    public void showNotification(String title, String message, int notifId, Movie item) {
        NotificationManager notificationManagerCompat = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(mContext, MainActivity.class);
        intent.putExtra(MainActivity.MOVIE_ITEM, new Gson().toJson(item));
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(mContext, android.R.color.transparent))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        notificationManagerCompat.notify(notifId, builder.build());
    }
}
