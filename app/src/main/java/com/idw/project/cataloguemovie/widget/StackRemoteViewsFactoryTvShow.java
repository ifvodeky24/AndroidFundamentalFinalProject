package com.idw.project.cataloguemovie.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.idw.project.cataloguemovie.R;
import com.idw.project.cataloguemovie.model.TvShow;

import java.util.concurrent.ExecutionException;

import static com.idw.project.cataloguemovie.config.Config.IMAGE_W342;
import static com.idw.project.cataloguemovie.db.DatabaseContract.FavoriteColumn.CONTENT_URI_MOVIE;
import static com.idw.project.cataloguemovie.db.DatabaseContract.FavoriteColumn.CONTENT_URI_TVSHOW;

public class StackRemoteViewsFactoryTvShow implements RemoteViewsService.RemoteViewsFactory{
    private int mAppWidgetId;
    private final Context mContext;
    private Cursor cursor;

    StackRemoteViewsFactoryTvShow(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null) {
            cursor.close();
        }

        final long identitiyToken = Binder.clearCallingIdentity();

        cursor = mContext.getContentResolver().query(CONTENT_URI_TVSHOW, null, null, null, null);

        Binder.restoreCallingIdentity(identitiyToken);
    }

    @Override
    public void onDestroy() {
        if (cursor != null) cursor.close();
    }

    @Override
    public int getCount() {
        if (cursor == null) return 0;
        else return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        TvShow tvShow = getItem(position);

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        Bitmap bmp = null;
        String poster_path = IMAGE_W342 + tvShow.getPosterPath();
        String id = String.valueOf(tvShow.getId());
//        String date = dateFormat(movie.getReleaseDate());
        try {

            bmp = Glide.with(mContext)
                    .asBitmap()
                    .load(poster_path)
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();

        } catch (InterruptedException | ExecutionException e) {
            Log.d("Widget Load Error", "error");
        }

        rv.setImageViewBitmap(R.id.imageView, bmp);

        Bundle extras = new Bundle();
        extras.putString(ImageBannerWidget.EXTRA_ITEM, id);

        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);

        return rv;
    }

    private TvShow getItem(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position Invalid");
        }
        return new TvShow(cursor);
    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        if (cursor.moveToPosition(position)) {
            return cursor.getLong(0);
        } else return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}
