package com.sam_chordas.android.stockhawk.widget;

import android.content.Context;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;

public class StockRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory
{
    private Context context;

    public StockRemoteViewsFactory(Context context)
    {
        this.context = context;
    }

    @Override
    public int getCount()
    {
        Cursor cursor = context.getContentResolver()
                               .query(QuoteProvider.Quotes.CONTENT_URI,
                                new String[] { "Distinct " + QuoteColumns.SYMBOL },
                                null,
                                null,
                                null);

        int count = cursor.getCount();
        cursor.moveToFirst();
        for (int i = 0; i < count; i++)
        {
           System.out.println("stockremoteviewfac: symbol " + cursor.getString(0));
            cursor.moveToNext();
        }

        cursor.close();

        return count;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public RemoteViews getLoadingView()
    {
        return null;
    }

    @Override
    public RemoteViews getViewAt(int position)
    {
        Cursor cursor = context.getContentResolver()
                               .query(QuoteProvider.Quotes.CONTENT_URI,
                                new String[] {QuoteColumns.SYMBOL, QuoteColumns.BIDPRICE},
                                QuoteColumns.ISCURRENT + " = ?",
                                new String[]{"1"},
                                null);

        cursor.moveToPosition(position);

        RemoteViews itemView = new RemoteViews(this.context.getPackageName(), R.layout.widget_item);

        itemView.setTextViewText(R.id.widget_item_symbol, cursor.getString(0));
        itemView.setTextViewText(R.id.widget_item_price, cursor.getString(1));

        cursor.close();

        return itemView;
    }

    @Override
    public int getViewTypeCount()
    {
        return 1;
    }

    @Override
    public boolean hasStableIds()
    {
        return true;
    }

    @Override
    public void onCreate(){}

    @Override
    public void onDataSetChanged(){}

    @Override
    public void onDestroy(){}
}
