package com.sam_chordas.android.stockhawk.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.sam_chordas.android.stockhawk.R;


public class StockWidgetIntentService extends IntentService
{
    public StockWidgetIntentService()
    {
        super("StockWidgetIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(this);

        int[] widgetIds = widgetManager.getAppWidgetIds(
            new ComponentName(this, StockWidgetProvider.class));

        for (int widgetId : widgetIds)
        {
            Intent adapterIntent = new Intent(this, StockWidgetService.class);
            adapterIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
            adapterIntent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget);
            views.setRemoteAdapter(R.id.list_view, adapterIntent);

            widgetManager.updateAppWidget(widgetId, views);
        }
    }
}

