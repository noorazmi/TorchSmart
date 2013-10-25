package droid.torch.smart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

public class ConfigurationActivity extends FragmentActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.configuration_activity);
	FragmentManager fragmentManager = getSupportFragmentManager();
	FragmentTransaction ftr = fragmentManager.beginTransaction();
	
	Fragment fragment = new ConfigurationFragment();
	ftr.add(R.id.fragment_container, fragment);
	ftr.commit();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//	// Inflate the menu; this adds items to the action bar if it is present.
//	getMenuInflater().inflate(R.menu.configuration, menu);
//	return true;
//    }

}
