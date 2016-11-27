package com.sam_chordas.android.stockhawk.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.service.StockTaskService;

public class StockWidgetProvider extends AppWidgetProvider
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        super.onReceive(context, intent);

        if (intent.getAction().equals(StockTaskService.ACTION_DATA_UPDATED))
        {
            AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
            int[] widgetIds = widgetManager.getAppWidgetIds(
                    new ComponentName(context, StockWidgetProvider.class));

            widgetManager.notifyAppWidgetViewDataChanged(widgetIds, R.id.list_view);
        }
    }

    @Override
    public void onUpdate (Context context, AppWidgetManager widgetManager, int[] ids)
    {
        context.startService(new Intent(context, StockWidgetIntentService.class));

        super.onUpdate(context, widgetManager, ids);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager widgetManager,
                                          int widgetId, Bundle newOptions)
    {
       context.startService(new Intent(context, StockWidgetIntentService.class));
    }
}
