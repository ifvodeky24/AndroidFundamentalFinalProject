package com.idw.project.cataloguemovie.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.idw.project.cataloguemovie.R;

/**
 * Implementation of App Widget functionality.
 */
public class ImageBannerWidget extends AppWidgetProvider {

    private static final String TOAST_ACTION = "com.idw.project.cataloguemovie.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.idw.project.cataloguemovie.EXTRA_ITEM";
    public static final String UPDATE_WIDGET = "com.idw.project.cataloguemovie.UPDATE_WIDGET";

    static void updateAppWidgetMovie(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, StackWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.image_banner_widget);
        views.setRemoteAdapter(R.id.stack_view, intent);
        views.setEmptyView(R.id.stack_view, R.id.empty_view);

        Intent toastIntent = new Intent(context, ImageBannerWidget.class);
        toastIntent.setAction(ImageBannerWidget.TOAST_ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    static void updateAppWidgetTvShow(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, StackWidgetServiceTvShow.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.image_banner_widget);
        views.setRemoteAdapter(R.id.stack_view2, intent);
        views.setEmptyView(R.id.stack_view2, R.id.empty_view);

        Intent toastIntent = new Intent(context, ImageBannerWidget.class);
        toastIntent.setAction(ImageBannerWidget.TOAST_ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_view2, toastPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidgetMovie(context, appWidgetManager, appWidgetId);
            updateAppWidgetTvShow(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (intent.getAction() != null) {
            if (intent.getAction().equals(TOAST_ACTION)) {
                String id = intent.getStringExtra(EXTRA_ITEM);
                Toast.makeText(context, "Touched view " + id , Toast.LENGTH_SHORT).show();

//                Intent intent1 = new Intent(context, DetailMovieActivity.class)
//                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent1.putExtra(DetailMovieActivity.TAG_ID, id);
//                context.startActivity(intent1);

                System.out.println("nilai dari " +id);

            }

            if (intent.getAction().equals(UPDATE_WIDGET)){
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                int[] ids = appWidgetManager.getAppWidgetIds(new ComponentName(context, ImageBannerWidget.class));

                appWidgetManager.notifyAppWidgetViewDataChanged(ids ,R.id.stack_view);
                appWidgetManager.notifyAppWidgetViewDataChanged(ids ,R.id.stack_view2);
            }
        }
    }
}

