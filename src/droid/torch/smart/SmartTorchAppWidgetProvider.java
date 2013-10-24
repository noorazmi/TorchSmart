package droid.torch.smart;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

public class SmartTorchAppWidgetProvider extends AppWidgetProvider
{
    private static final String TAG = SmartTorchAppWidgetProvider.class.getSimpleName();
    private static final String TORCH_ON_OFF_ACTION = "com.smarttorch.intent.action.TORCH_ON_OFF_ACTION";
    private static final String URI_SCHEME = "torch_wiget";
    @Override
    public void onReceive(Context context, Intent intent)
    {
	super.onReceive(context, intent);
	if (intent.getAction().equals(TORCH_ON_OFF_ACTION))
	{
	    updateTorchStatus(context);
	}
	Log.d(TAG, "onReceive called :: " + intent.getAction());
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
	final int numberOfActiveWidgets = appWidgetIds.length;
	Log.d(TAG, "onUpdate called");
	for (int i = 0; i < numberOfActiveWidgets; i++)
	{
	    super.onUpdate(context, appWidgetManager, appWidgetIds);

	    int appWidgetId = appWidgetIds[i];
	    Log.d(TAG, "appWidgetId " + appWidgetId + " appWidgetIds.length : " + appWidgetIds.length);

	    // Register an onClickListener
	    RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.torch_layout);
	    if(getTorchActiveStatus(context)){
		remoteViews.setImageViewResource(R.id.torch_image, R.drawable.bulb_on);
	    }
	    remoteViews.setOnClickPendingIntent(R.id.parent_layout, createPendingIntent(context));
	    
	    
	    Intent configIntent = new Intent(context, ConfigurationActivity.class);
	    configIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
	    
	    //Make this appwidget unique
	    configIntent.setData(Uri.withAppendedPath(Uri.parse(SmartTorchAppWidgetProvider.URI_SCHEME + "://widget/id/"), String.valueOf(appWidgetId)));
	    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, configIntent, PendingIntent.FLAG_UPDATE_CURRENT);
	    remoteViews.setOnClickPendingIntent(R.id.setting, pendingIntent);
	    
	    appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
	}
    }

    private void updateTorchStatus(Context context)
    {

	RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.torch_layout);
	if (getTorchActiveStatus(context))
	{
	    remoteViews.setImageViewResource(R.id.torch_image, R.drawable.bulb_off);
	}
	else
	{
	    remoteViews.setImageViewResource(R.id.torch_image, R.drawable.bulb_on);
	}
	setTorchActiveStatus(context);
	remoteViews.setOnClickPendingIntent(R.id.parent_layout, createPendingIntent(context));
	AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
	ComponentName componentName = new ComponentName(context, SmartTorchAppWidgetProvider.class);
	appWidgetManager.updateAppWidget(componentName, remoteViews);

    }

    private boolean getTorchActiveStatus(Context context)
    {
	SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
	return sharedPreferences.getBoolean("isTorchOn", false);
    }

    private void setTorchActiveStatus(Context context)
    {
	SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
	SharedPreferences.Editor editor = sharedPreferences.edit();
	editor.putBoolean("isTorchOn", !sharedPreferences.getBoolean("isTorchOn", false));
	editor.commit();
    }

    public PendingIntent createPendingIntent(Context context)
    {
	Intent intent = new Intent(context, SmartTorchAppWidgetProvider.class);
	intent.setAction(TORCH_ON_OFF_ACTION);
	return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

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
	//Delete the shared preferences so that it could not take the previous saved value.
	context.getSharedPreferences(TAG, 0).edit().clear().commit(); 
	Log.d(TAG, "Widget Provider disabled.");
    }

}
