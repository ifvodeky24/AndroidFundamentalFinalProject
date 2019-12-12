package com.idw.project.cataloguemovie.helper;

import android.database.Cursor;

import com.idw.project.cataloguemovie.model.Movie;
import com.idw.project.cataloguemovie.model.TvShow;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.idw.project.cataloguemovie.db.DatabaseContract.FavoriteColumn.AVERAGE;
import static com.idw.project.cataloguemovie.db.DatabaseContract.FavoriteColumn.OVERVIEW;
import static com.idw.project.cataloguemovie.db.DatabaseContract.FavoriteColumn.POSTER_PATH;
import static com.idw.project.cataloguemovie.db.DatabaseContract.FavoriteColumn.RELEASE_DATE;
import static com.idw.project.cataloguemovie.db.DatabaseContract.FavoriteColumn.TITLE;

public class MappingHelper {

    public static ArrayList<Movie> mapCursorToArrayListMovie(Cursor moviesCursor) {
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        if (moviesCursor != null){
            while (moviesCursor.moveToNext()) {
                int id = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(_ID));
                String title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(TITLE));
                String release_date = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(RELEASE_DATE));
                String overview = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(OVERVIEW));
                Double vote_average = Double.valueOf(moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(AVERAGE)));
                String poster_path = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(POSTER_PATH));
                movieArrayList.add(new Movie(id, title, release_date, overview, vote_average, poster_path));
            }
        }
        return movieArrayList;
    }

    public static ArrayList<TvShow> mapCursorToArrayListTvShow(Cursor tvshowCursor) {
        ArrayList<TvShow> tvShowArrayList = new ArrayList<>();
        if (tvshowCursor != null){
            while (tvshowCursor.moveToNext()) {
                int id = tvshowCursor.getInt(tvshowCursor.getColumnIndexOrThrow(_ID));
                String title = tvshowCursor.getString(tvshowCursor.getColumnIndexOrThrow(TITLE));
                String release_date = tvshowCursor.getString(tvshowCursor.getColumnIndexOrThrow(RELEASE_DATE));
                String overview = tvshowCursor.getString(tvshowCursor.getColumnIndexOrThrow(OVERVIEW));
                Double vote_average = Double.valueOf(tvshowCursor.getString(tvshowCursor.getColumnIndexOrThrow(AVERAGE)));
                String poster_path = tvshowCursor.getString(tvshowCursor.getColumnIndexOrThrow(POSTER_PATH));
                tvShowArrayList.add(new TvShow(id, title, release_date, overview, vote_average, poster_path));
            }
        }
        return tvShowArrayList;
    }
}
