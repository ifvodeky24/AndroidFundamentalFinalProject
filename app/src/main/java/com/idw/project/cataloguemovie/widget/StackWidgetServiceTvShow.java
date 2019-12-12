package com.idw.project.cataloguemovie.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class StackWidgetServiceTvShow extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactoryTvShow(this.getApplicationContext(), intent);
    }
}
