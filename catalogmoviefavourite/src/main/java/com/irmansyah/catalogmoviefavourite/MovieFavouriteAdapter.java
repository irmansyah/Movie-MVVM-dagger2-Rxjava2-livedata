package com.irmansyah.catalogmoviefavourite;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.irmansyah.catalogmoviefavourite.custom.RoundedImageView;
import com.squareup.picasso.Picasso;

import static com.irmansyah.catalogmoviefavourite.db.DatabaseContract.MovieColumns.OVERVIEW;
import static com.irmansyah.catalogmoviefavourite.db.DatabaseContract.MovieColumns.POSTER_PATH;
import static com.irmansyah.catalogmoviefavourite.db.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.irmansyah.catalogmoviefavourite.db.DatabaseContract.MovieColumns.TITLE;
import static com.irmansyah.catalogmoviefavourite.db.DatabaseContract.getColumnString;

/**
 * Created by irmansyah on 21/03/18.
 */

public class MovieFavouriteAdapter extends CursorAdapter {

    private static final String TAG = "MovieFavouriteAdapter";

    public MovieFavouriteAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie_db_favourite, viewGroup, false);
        return view;
    }


    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor != null){
            ImageView image = view.findViewById(R.id.round_image);
            TextView tvTitle = view.findViewById(R.id.title_tv);
            TextView tvDate = view.findViewById(R.id.date_tv);
            TextView tvOverview = view.findViewById(R.id.overview_tv);


            String imageUrl = BuildConfig.BASE_URL_POSTER_PATH_SMALL + getColumnString(cursor, POSTER_PATH);
            Picasso.with(context).load(imageUrl).into(image);
            tvTitle.setText(getColumnString(cursor,TITLE));
            tvDate.setText(getColumnString(cursor,RELEASE_DATE));
            tvOverview.setText(getColumnString(cursor,OVERVIEW));

            Log.i(TAG, "bindView: " + imageUrl);
        }
    }
}
