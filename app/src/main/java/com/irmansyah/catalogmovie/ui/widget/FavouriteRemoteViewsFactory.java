package com.irmansyah.catalogmovie.ui.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.irmansyah.catalogmovie.BuildConfig;
import com.irmansyah.catalogmovie.R;
import com.irmansyah.catalogmovie.data.model.Movie;

import java.util.concurrent.ExecutionException;

import static com.irmansyah.catalogmovie.data.local.db.sqlite.db.DatabaseContract.CONTENT_URI;

/**
 * Created by irmansyah on 26/03/18.
 */

public class FavouriteRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = "FavouriteRemoteViewsFac";

    private Context mContext;
    private int mAppWidgetId;

    public Cursor list;

    public FavouriteRemoteViewsFactory(Context context, Intent intent) {
        this.mContext = context;
        this.mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        list = mContext.getContentResolver().query(
                CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onDataSetChanged() {
        Log.i(TAG, "onDataSetChanged: ");
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return list.getCount();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        Movie item = getItem(i);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.item_widget_favourite);

        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(BuildConfig.BASE_URL_POSTER_PATH_SMALL + item.getPosterPath())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        rv.setImageViewBitmap(R.id.imageView, bitmap);

        Bundle extras = new Bundle();
        extras.putInt(FavouriteWidget.EXTRA_ITEM, i);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private Movie getItem(int position) {
        if (!list.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid!");
        }

        return new Movie(list);
    }
}
