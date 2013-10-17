package droid.torch.smart;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.RemoteViews;
import android.widget.Toast;

public class SmartTorchAppWidgetProvider extends AppWidgetProvider
{
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
	final int numberOfActiveWidgets = appWidgetIds.length;
	Toast.makeText(context, "appWidgetIds.length :: " + appWidgetIds.length, Toast.LENGTH_LONG).show();
	for (int i = 0; i < numberOfActiveWidgets; i++)
	{
	    int appWidgetId = appWidgetIds[i];

	    // Create a PendingIntent to send a broadcast.
	    Intent intent = new Intent(context, getClass());
	    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

	    // Get the layout for the App Widget and attach an on-click listener
	    // to the button
	    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.torch_layout);
	    views.setOnClickPendingIntent(R.id.torch_image, pendingIntent);

	    // Tell the AppWidgetManager to perform an update on the current app
	    // widget
	    appWidgetManager.updateAppWidget(appWidgetId, views);

	    // Update The clock label using a shared method
	    updateAppWidget(context, appWidgetManager, appWidgetId);
	}
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId)
    {
	RemoteViews updateViews = new RemoteViews(context.getPackageName(), appWidgetId);
	Drawable myDrawable = context.getResources().getDrawable(R.drawable.bulb_on);
	Bitmap bulb_bitmap = ((BitmapDrawable) myDrawable).getBitmap();

	updateViews.setImageViewBitmap(R.id.torch_image, bulb_bitmap);
	appWidgetManager.updateAppWidget(appWidgetId, updateViews);
    }

}
