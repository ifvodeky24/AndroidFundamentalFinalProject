package com.idw.project.cataloguemovie.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.idw.project.cataloguemovie.db.DatabaseContract.FavoriteColumn.AVERAGE;
import static com.idw.project.cataloguemovie.db.DatabaseContract.FavoriteColumn.OVERVIEW;
import static com.idw.project.cataloguemovie.db.DatabaseContract.FavoriteColumn.POSTER_PATH;
import static com.idw.project.cataloguemovie.db.DatabaseContract.FavoriteColumn.RELEASE_DATE;
import static com.idw.project.cataloguemovie.db.DatabaseContract.FavoriteColumn.TABLE_FAVORITE_MOVIE;
import static com.idw.project.cataloguemovie.db.DatabaseContract.FavoriteColumn.TABLE_FAVORITE_TVSHOW;
import static com.idw.project.cataloguemovie.db.DatabaseContract.FavoriteColumn.TITLE;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "catalogmovie";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_FAVORITE_MOVIE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_FAVORITE_MOVIE,
            _ID,
            RELEASE_DATE,
            TITLE,
            OVERVIEW,
            AVERAGE,
            POSTER_PATH
    );

    private static final String SQL_CREATE_TABLE_FAVORITE_TVSHOW = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_FAVORITE_TVSHOW,
            _ID,
            RELEASE_DATE,
            TITLE,
            OVERVIEW,
            AVERAGE,
            POSTER_PATH
    );

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE_MOVIE);
        db.execSQL(SQL_CREATE_TABLE_FAVORITE_TVSHOW);
    }

    /*
    Method onUpgrade akan di panggil ketika terjadi perbedaan versi
    Gunakan method onUpgrade untuk melakukan proses migrasi data
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
        Drop table tidak dianjurkan ketika proses migrasi terjadi dikarenakan data user akan hilang,
        */
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE_MOVIE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE_TVSHOW);
        onCreate(db);
    }
}
