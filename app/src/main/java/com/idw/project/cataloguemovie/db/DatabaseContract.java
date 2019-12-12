package com.idw.project.cataloguemovie.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String AUTHORITY_MOVIE= "com.idw.project.cataloguemovie.provider.favoritemovieprovider";
    public static final String AUTHORITY_TVSHOW= "com.idw.project.cataloguemovie.provider.favoritetvshowprovider";
    private static final String SCHEME = "content";

    private DatabaseContract(){}

    public static final class FavoriteColumn implements BaseColumns {
        public static String TABLE_FAVORITE_MOVIE = "favorite_movie";
        public static String TABLE_FAVORITE_TVSHOW = "favorite_tvshow";
        public static String TITLE = "title";
        public static String RELEASE_DATE = "release_date";
        public static String OVERVIEW = "overview";
        public static String AVERAGE = "average";
        public static String POSTER_PATH = "poster_path";

        public static final Uri CONTENT_URI_MOVIE = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY_MOVIE)
                .appendPath(TABLE_FAVORITE_MOVIE)
                .build();

        public static final Uri CONTENT_URI_TVSHOW = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY_TVSHOW)
                .appendPath(TABLE_FAVORITE_TVSHOW)
                .build();
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
    public static double getColumnDouble(Cursor cursor, String columnName) {
        return cursor.getDouble(cursor.getColumnIndex(columnName));
    }
}
