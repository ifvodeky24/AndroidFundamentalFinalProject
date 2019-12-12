package com.idw.project.cataloguemovie.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.idw.project.cataloguemovie.db.FavoriteHelper;
import com.idw.project.cataloguemovie.fragment.FavoriteMovieFragment;

import java.util.Objects;

import static com.idw.project.cataloguemovie.db.DatabaseContract.AUTHORITY_MOVIE;
import static com.idw.project.cataloguemovie.db.DatabaseContract.FavoriteColumn.CONTENT_URI_MOVIE;
import static com.idw.project.cataloguemovie.db.DatabaseContract.FavoriteColumn.TABLE_FAVORITE_MOVIE;

public class FavoriteMovieProvider extends ContentProvider {

    private static final int FAVORITE = 1;
    private static final int FAVORITE_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(AUTHORITY_MOVIE, TABLE_FAVORITE_MOVIE, FAVORITE);

        sUriMatcher.addURI(AUTHORITY_MOVIE,
                TABLE_FAVORITE_MOVIE + "/#",
                FAVORITE_ID);
    }

    private FavoriteHelper favoriteHelper;

    @Override
    public boolean onCreate() {
        favoriteHelper = FavoriteHelper.getInstance(getContext());

        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        favoriteHelper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case FAVORITE:
                cursor = favoriteHelper.queryProviderMovie();
                break;
            case FAVORITE_ID:
                cursor = favoriteHelper.queryByIdProviderMovie(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        favoriteHelper.open();
        long added;
        if (sUriMatcher.match(uri) == FAVORITE) {
            added = favoriteHelper.insertProviderMovie(contentValues);
        } else {
            added = 0;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(CONTENT_URI_MOVIE, new FavoriteMovieFragment.DataObserver(new Handler(), getContext()));
        }

        return Uri.parse(CONTENT_URI_MOVIE + "/" + added);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        favoriteHelper.open();
        int updated;
        if (sUriMatcher.match(uri) == FAVORITE_ID) {
            updated = favoriteHelper.updateProviderMovie(uri.getLastPathSegment(), contentValues);
        } else {
            updated = 0;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(CONTENT_URI_MOVIE, new FavoriteMovieFragment.DataObserver(new Handler(), getContext()));
        }

        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        favoriteHelper.open();
        int deleted;
        if (sUriMatcher.match(uri) == FAVORITE_ID) {
            deleted = favoriteHelper.deleteProviderMovie(uri.getLastPathSegment());
        } else {
            deleted = 0;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(CONTENT_URI_MOVIE, new FavoriteMovieFragment.DataObserver(new Handler(), getContext()));
        }

        return deleted;
    }
}
