package com.irmansyah.catalogmoviefavourite;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.irmansyah.catalogmoviefavourite.db.DatabaseContract;

import static com.irmansyah.catalogmoviefavourite.db.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener  {

    private static final String TAG = "MainActivity";

    private MovieFavouriteAdapter mAdapter;

    ListView lvNotes;

    private final int LOAD_MOVIE_ID = 110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvNotes = findViewById(R.id.lv_movies);
        mAdapter = new MovieFavouriteAdapter(this, null, true);
        lvNotes.setAdapter(mAdapter);
        lvNotes.setOnItemClickListener(this);

        getSupportLoaderManager().initLoader(LOAD_MOVIE_ID, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOAD_MOVIE_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(LOAD_MOVIE_ID);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        Cursor cursor = (Cursor) mAdapter.getItem(i);
//
//        int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns._ID));
//        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
//        intent.setData(Uri.parse(CONTENT_URI+"/"+id));
//        startActivity(intent);
    }
}
