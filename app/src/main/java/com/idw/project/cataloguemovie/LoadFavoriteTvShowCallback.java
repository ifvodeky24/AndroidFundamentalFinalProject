package com.idw.project.cataloguemovie;

import android.database.Cursor;

public interface LoadFavoriteTvShowCallback {
    void preExecute();

    void postExecute(Cursor tvShows);
}
