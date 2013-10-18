package droid.torch.smart;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class SmartTorchAppWidgetProvider extends AppWidgetProvider
{
    private static final String TAG = SmartTorchAppWidgetProvider.class.getSimpleName();
    private boolean isTorchOn = false;
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
	final int numberOfActiveWidgets = appWidgetIds.length;
	Log.d(TAG, "onUpdate called");
	for (int i = 0; i < numberOfActiveWidgets; i++)
	{
	    super.onUpdate(context, appWidgetManager, appWidgetIds);
	    
	    int appWidgetId = appWidgetIds[i];
	    Log.d(TAG, "appWidgetId "+appWidgetId+" appWidgetIds.length : "+appWidgetIds.length);
	    // Register an onClickListener
	    Intent intent = new Intent(context, SmartTorchAppWidgetProvider.class);

	    intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//	    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetId);
//
	    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	    
	    RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.torch_layout);
	    
	    changeTorchStatusLayout(context,appWidgetId,remoteViews);
	    remoteViews.setOnClickPendingIntent(R.id.torch_image, pendingIntent);
	    appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

	    //
	    // // Create a PendingIntent to send a broadcast.
	    // Intent intent = new Intent(context,
	    // SmartTorchAppWidgetProvider.class);
	    // intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
	    // intent.putExtra(appWidgetManager.EXTRA_APPWIDGET_ID,
	    // appWidgetId);
	    // PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
	    // 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
	    //
	    // // Get the layout for the App Widget and attach an on-click
	    // listener
	    // // to the button
	    // RemoteViews views = new RemoteViews(context.getPackageName(),
	    // R.layout.torch_layout);
	    // Log.d(TAG, "before setOnClickPendingIntent");
	    // views.setOnClickPendingIntent(R.id.torch_image, pendingIntent);
	    // Log.d(TAG, "after setOnClickPendingIntent");
	    //
	    // // Tell the AppWidgetManager to perform an update on the current
	    // app
	    // // widget
	    // appWidgetManager.updateAppWidget(appWidgetId, views);
	    //
	    // // Update The clock label using a shared method
	    // updateAppWidget(context, appWidgetManager, appWidgetId);
	}
    }
    
    private void changeTorchStatusLayout(Context context,int appWidgetId,RemoteViews remoteViews)
    {
	
	SharedPreferences  sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
	isTorchOn = sharedPreferences.getBoolean("isTorchOn", false);
	Log.d(TAG, "isTorchOn11 :: "+isTorchOn);
	//RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.torch_layout);
	if(isTorchOn)
	{
	    
	    remoteViews.setImageViewResource(R.id.torch_image, R.drawable.bulb_off); 
	    Log.d(TAG, "isTorchOn11 :: "+isTorchOn+" bulb_off");
	}
	else
	{
	    remoteViews.setImageViewResource(R.id.torch_image, R.drawable.bulb_on); 
	    Log.d(TAG, "isTorchOn11 :: "+isTorchOn+" bulb_on");
	}
	//AppWidgetManager.getInstance(context).updateAppWidget(appWidgetId, remoteViews);
	isTorchOn = !isTorchOn;
	SharedPreferences.Editor editor = sharedPreferences.edit();
	editor.putBoolean("isTorchOn", isTorchOn);
	editor.commit();
	
	Log.d(TAG, "isTorchOn22 :: "+isTorchOn);
	
    }
    
    @Override
    public void onEnabled(Context context)
    {

	Log.d(TAG, "Torch Widget Provider enabled.");
	super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context)
    {
	super.onDisabled(context);
	Log.d(TAG, "Widget Provider disabled.");
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId)
    {
	Log.d(TAG, "inside updateAppWidget.");
	RemoteViews updateViews = new RemoteViews(context.getPackageName(), appWidgetId);
	Drawable myDrawable = context.getResources().getDrawable(R.drawable.ic_launcher);
	Bitmap bulb_bitmap = ((BitmapDrawable) myDrawable).getBitmap();

	updateViews.setImageViewBitmap(R.id.torch_image, bulb_bitmap);
	appWidgetManager.updateAppWidget(appWidgetId, updateViews);
	Log.d(TAG, "end of  updateAppWidget.");
    }

}
