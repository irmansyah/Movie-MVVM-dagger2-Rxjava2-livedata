package com.irmansyah.catalogmovie.data;

import com.irmansyah.catalogmovie.data.local.db.DbHelper;
import com.irmansyah.catalogmovie.data.model.Movie;
import com.irmansyah.catalogmovie.data.remote.ApiHelper;

/**
 * Created by irmansyah on 23/02/18.
 */

public interface DataManager extends ApiHelper, DbHelper {

    void showNotification(String title, String message, int notifId, Movie item);
}
