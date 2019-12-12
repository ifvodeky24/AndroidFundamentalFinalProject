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
import com.idw.project.cataloguemovie.fragment.FavoriteTvShowFragment;

import java.util.Objects;

import static com.idw.project.cataloguemovie.db.DatabaseContract.AUTHORITY_TVSHOW;
import static com.idw.project.cataloguemovie.db.DatabaseContract.FavoriteColumn.CONTENT_URI_TVSHOW;
import static com.idw.project.cataloguemovie.db.DatabaseContract.FavoriteColumn.TABLE_FAVORITE_TVSHOW;

public class FavoriteTvShowProvider extends ContentProvider {

    private static final int FAVORITE = 1;
    private static final int FAVORITE_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(AUTHORITY_TVSHOW, TABLE_FAVORITE_TVSHOW, FAVORITE);

        sUriMatcher.addURI(AUTHORITY_TVSHOW,
                TABLE_FAVORITE_TVSHOW + "/#",
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
                cursor = favoriteHelper.queryProviderTvshow();
                break;
            case FAVORITE_ID:
                cursor = favoriteHelper.queryByIdProviderTvShow(uri.getLastPathSegment());
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
            added = favoriteHelper.insertProviderTvShow(contentValues);
        } else {
            added = 0;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(CONTENT_URI_TVSHOW, new FavoriteTvShowFragment.DataObserver(new Handler(), getContext()));
        }

        return Uri.parse(TABLE_FAVORITE_TVSHOW + "/" + added);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        favoriteHelper.open();
        int updated;
        if (sUriMatcher.match(uri) == FAVORITE_ID) {
            updated = favoriteHelper.updateProviderTvShow(uri.getLastPathSegment(), contentValues);
        } else {
            updated = 0;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(CONTENT_URI_TVSHOW, new FavoriteTvShowFragment.DataObserver(new Handler(), getContext()));
        }

        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        favoriteHelper.open();
        int deleted;
        if (sUriMatcher.match(uri) == FAVORITE_ID) {
            deleted = favoriteHelper.deleteProviderTvShow(uri.getLastPathSegment());
        } else {
            deleted = 0;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(CONTENT_URI_TVSHOW, new FavoriteTvShowFragment.DataObserver(new Handler(), getContext()));
        }

        return deleted;
    }
}
