package com.idw.project.cataloguemovie.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.idw.project.cataloguemovie.model.Movie;
import com.idw.project.cataloguemovie.model.TvShow;

import static android.provider.BaseColumns._ID;
import static com.idw.project.cataloguemovie.db.DatabaseContract.FavoriteColumn.AVERAGE;
import static com.idw.project.cataloguemovie.db.DatabaseContract.FavoriteColumn.OVERVIEW;
import static com.idw.project.cataloguemovie.db.DatabaseContract.FavoriteColumn.POSTER_PATH;
import static com.idw.project.cataloguemovie.db.DatabaseContract.FavoriteColumn.RELEASE_DATE;
import static com.idw.project.cataloguemovie.db.DatabaseContract.FavoriteColumn.TABLE_FAVORITE_MOVIE;
import static com.idw.project.cataloguemovie.db.DatabaseContract.FavoriteColumn.TABLE_FAVORITE_TVSHOW;
import static com.idw.project.cataloguemovie.db.DatabaseContract.FavoriteColumn.TITLE;

public class FavoriteHelper {
    private static final String DATABASE_TABLE_MOVIE = TABLE_FAVORITE_MOVIE;
    private static final String DATABASE_TABLE_TVSHOW = TABLE_FAVORITE_TVSHOW;
    private static DatabaseHelper dataBaseHelper;
    private static FavoriteHelper INSTANCE;

    private static SQLiteDatabase database;

    public FavoriteHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public boolean Check(int id){
        Cursor cursor;
        String sql = "SELECT "+_ID+" FROM "+TABLE_FAVORITE_MOVIE+" WHERE "+_ID +"="+id;
        cursor = database.rawQuery(sql, null);
        System.out.println("cek sini"+cursor.getCount());

        if (cursor.getCount()>0){
            return true;
        }

        cursor.close();
        return false;
    }

    public boolean CheckTvShow(int id){
        Cursor cursor;
        String sql = "SELECT "+_ID+" FROM "+TABLE_FAVORITE_TVSHOW+" WHERE "+_ID +"="+id;
        cursor = database.rawQuery(sql, null);
        System.out.println("cek sini"+cursor.getCount());

        if (cursor.getCount()>0){
            return true;
        }

        cursor.close();
        return false;
    }


    public long insertMovie(Movie movie) {
        ContentValues args = new ContentValues();
        args.put(_ID, movie.getId());
        args.put(TITLE, movie.getTitle());
        args.put(OVERVIEW, movie.getOverview());
        args.put(RELEASE_DATE, movie.getReleaseDate());
        args.put(AVERAGE, movie.getVoteAverage());
        args.put(POSTER_PATH, movie.getPosterPath());
        return database.insert(DATABASE_TABLE_MOVIE, null, args);
    }

    /**
     * Gunakan method ini untuk getAllNotes insertNote
     *
     * @param tvShow model note yang akan dimasukkan
     * @return id dari data yang baru saja dimasukkan
     */
    public long insertTvShow(TvShow tvShow) {
        ContentValues args = new ContentValues();
        args.put(_ID, tvShow.getId());
        args.put(TITLE, tvShow.getOriginalName());
        args.put(OVERVIEW, tvShow.getOverview());
        args.put(RELEASE_DATE, tvShow.getFirstAirDate());
        args.put(AVERAGE, tvShow.getVoteAverage());
        args.put(POSTER_PATH, tvShow.getPosterPath());
        return database.insert(DATABASE_TABLE_TVSHOW, null, args);
    }


    public int deleteMovie(int id) {
        return database.delete(TABLE_FAVORITE_MOVIE, _ID + " = '" + id + "'", null);
    }

    public int deleteTvShow(int id) {
        return database.delete(TABLE_FAVORITE_TVSHOW, _ID + " = '" + id + "'", null);
    }

    public Cursor queryByIdProviderMovie(String id) {
        return database.query(DATABASE_TABLE_MOVIE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryByIdProviderTvShow(String id) {
        return database.query(DATABASE_TABLE_TVSHOW, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProviderMovie() {
        return database.query(DATABASE_TABLE_MOVIE
                , null
                , null
                , null
                , null
                , null
                , _ID + " ASC");
    }

    public Cursor queryProviderTvshow() {
        return database.query(DATABASE_TABLE_TVSHOW
                , null
                , null
                , null
                , null
                , null
                , _ID + " ASC");
    }

    public long insertProviderMovie(ContentValues values) {
        return database.insert(DATABASE_TABLE_MOVIE, null, values);
    }

    public long insertProviderTvShow(ContentValues values) {
        return database.insert(DATABASE_TABLE_TVSHOW, null, values);
    }

    public int updateProviderMovie(String id, ContentValues values) {
        return database.update(DATABASE_TABLE_MOVIE, values, _ID + " = ?", new String[]{id});
    }

    public int updateProviderTvShow(String id, ContentValues values) {
        return database.update(DATABASE_TABLE_TVSHOW, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProviderMovie(String id) {
        return database.delete(DATABASE_TABLE_MOVIE, _ID + " = ?", new String[]{id});
    }

    public int deleteProviderTvShow(String id) {
        return database.delete(DATABASE_TABLE_TVSHOW, _ID + " = ?", new String[]{id});
    }



}
