package com.irmansyah.catalogmovie.ui;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.irmansyah.catalogmovie.R;
import com.irmansyah.catalogmovie.data.model.Movie;

public class Main2Activity extends AppCompatActivity {

    private static final String TAG = "Main2Activity";

    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Uri uri = getIntent().getData();

        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            Log.i(TAG, "setCursor: cursor: " + cursor);
            if (cursor != null){
                if(cursor.moveToFirst()) mMovie = new Movie(cursor);
                cursor.close();
            }
        }
    }
}
