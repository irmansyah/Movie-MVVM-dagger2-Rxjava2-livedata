package com.irmansyah.catalogmovie.ui.widget;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

/**
 * Created by irmansyah on 26/03/18.
 */

public class FavouriteWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.i("FavouriteWidgetService", "onGetViewFactory: ");
        return new FavouriteRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
