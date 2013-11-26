package foi.youcandoit;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


import foi.youcandoit.sucelja.ILokacija;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;

public class AktivnostPrati extends Activity
{
	public int time = 0;
	public boolean zaust = false;
	private LatLng trenutnaLokacija;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aktivnost_prati);
		Button pokreni = (Button) findViewById(R.id.gmbPokreni);
		Button zaustavi = (Button) findViewById(R.id.gmbZaustavi);
		//EditText lok = (EditText) findViewById(R.id.urediDuljina);
		
		LocationManager mlocManager=null;  
        ILokacija mlocListener;  
        mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);  
        mlocListener = new ILokacija();  
       mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
       
       if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) 
       {
            Log.d("Latitude:- ", "" + ILokacija.duzina + '\n');  
            Log.d("Longitude:- ", "" + ILokacija.sirina + '\n');  
       }
		
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

}
