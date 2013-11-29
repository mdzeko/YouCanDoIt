package foi.youcandoit;

import java.util.Timer;
import java.util.TimerTask;


import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.location.LocationListener;

public class AktivnostPrati extends Activity implements LocationListener
{
	
	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 10; // 1 minute
	protected LocationManager locationManager;
	protected Context context;
	protected boolean gps_enabled, network_enabled;
	TextView txtLat;
	String proba = "proba";
	
	
	public int time = 0;
	public boolean zaust = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aktivnost_prati);
		Button pokreni = (Button) findViewById(R.id.gmbPokreni);
		Button zaustavi = (Button) findViewById(R.id.gmbZaustavi);
		
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// getting GPS status
		gps_enabled = locationManager
		.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// getting network status
		network_enabled = locationManager
		.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		if (gps_enabled) 
		{
			locationManager.requestLocationUpdates(
			LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
			MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
		} else if (network_enabled) 
		{
			locationManager.requestLocationUpdates(
			LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
			MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
		};
		
		pokreni.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				Timer t = new Timer();
		        //Set the schedule function and rate
		        t.scheduleAtFixedRate(new TimerTask() {

					@Override
					public void run() {
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								if (zaust == false)
								{
									EditText tv = (EditText) findViewById(R.id.urediVrijeme);
									tv.setText(String.valueOf(time));
									time += 1;
								}
								else
									Thread.currentThread().interrupt();
							}
							
						});
					}
		        	
		        }, 0, 1000);
			}
		});
		zaustavi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				zaust = true;
			}
		});
	}
	@Override
	public void onLocationChanged(Location location)
	{
		txtLat = (TextView) findViewById(R.id.txtDuljina);
		txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:"
		+ location.getLongitude());
	}

	@Override
	public void onProviderDisabled(String provider) 
	{
		Log.d("Latitude", "disable");
	}

	@Override
	public void onProviderEnabled(String provider)
	{
		Log.d("Latitude", "enable");
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{
		Log.d("Latitude", "status");
	}

}
