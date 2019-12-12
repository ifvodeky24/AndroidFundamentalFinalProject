package com.idw.project.cataloguemovie;

import android.database.Cursor;

public interface LoadFavoriteMovieCallback {
    void preExecute();

    void postExecute(Cursor movies);
}
