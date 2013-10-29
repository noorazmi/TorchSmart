package droid.torch.smart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ConfigurationActivity extends ActionBarActivity
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
	
	getActionBar().setDisplayHomeAsUpEnabled(true);
	//getActionBar().setTitle("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	getMenuInflater().inflate(R.menu.configuration, menu);
	return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
	Toast.makeText(getApplicationContext(), "adfadfasd :: "+ item.getItemId(), Toast.LENGTH_LONG).show();
	switch (item.getItemId())
	{
	case android.R.id.home:
	    finish();
	    return true;

	default:
	    return super.onOptionsItemSelected(item);
	}
        
    }

}
