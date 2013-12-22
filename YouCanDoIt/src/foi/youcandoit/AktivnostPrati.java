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
import android.widget.Spinner;
import android.widget.TextView;

import android.location.LocationListener;

public class AktivnostPrati extends Activity implements LocationListener
{
	
	// Minimalna udaljenost prije azuriranja lokacije u metrima
	private static final long MINIMALNA_UDALJENOST = 10; // 10 metara
	// Minimalno vrijeme izmedju dva azuriranja lokacije (milisekunde)
	private static final long MINIMALNI_VREMENSKI_PERIOD = 1000 * 1; // 10 sek
	protected LocationManager locationManager;
	
	protected Context context;
	protected boolean gps_enabled, network_enabled;
	
	TextView txtDuljina;
	TextView txtProsBrzina;
	TextView txtBrojKalorija;
	float udaljenost = 0;
	float crta;
	
	Location zadnjaLokacija;
	Location novaLokacija;
	
	
	//NOVO
	KalkulatorKalorija kalkulatorKalorija;
	int pozicijaItema;
	Spinner mySpinner;
	//NOVO
	
	public int time = 0;
	public boolean zaust = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aktivnost_prati);
		Button pokreni = (Button) findViewById(R.id.gmbPokreni);
		Button zaustavi = (Button) findViewById(R.id.gmbZaustavi);
		/*
		Criteria kriterij = new Criteria();
		kriterij.setAccuracy(Criteria.ACCURACY_FINE);
		kriterij.setAltitudeRequired(false);
		kriterij.setBearingRequired(false);
		kriterij.setCostAllowed(false);
		kriterij.setPowerRequirement(Criteria.POWER_LOW);
		*/
		
		//NOVO
		kalkulatorKalorija = new KalkulatorKalorija();
		//NOVO
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		//String izvorLokacije = locationManager.getBestProvider(kriterij, true);
		
		//locationManager.requestLocationUpdates(izvorLokacije, MINIMALNI_VREMENSKI_PERIOD, MINIMALNA_UDALJENOST, this);
		// getting GPS status
		gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// getting network status
		network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		if (gps_enabled) 
		{
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINIMALNI_VREMENSKI_PERIOD, MINIMALNA_UDALJENOST, this);
		} else if (network_enabled) 
		{
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MINIMALNI_VREMENSKI_PERIOD, MINIMALNA_UDALJENOST, this);
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
		Boolean prva = false;
		
		if(prva == false)
		{
			zadnjaLokacija = location;
			prva = true;
		}
		else
		{
			novaLokacija = location;
			udaljenost += location.distanceTo(zadnjaLokacija);
			zadnjaLokacija = novaLokacija;
		}
		txtDuljina = (EditText) findViewById(R.id.urediDuljina);
		txtDuljina.setText("" + udaljenost);
		txtProsBrzina = (EditText) findViewById(R.id.urediProsBrzina);
		if(time!=0)
			txtProsBrzina.setText("" + udaljenost/time);
		//NOVO
		mySpinner = (Spinner)findViewById(R.id.spAktivnost);
		pozicijaItema = mySpinner.getSelectedItemPosition();
		
		txtBrojKalorija = (EditText) findViewById(R.id.urediBrKalorija);
		if(time!=0)
			txtBrojKalorija.setText("" + kalkulatorKalorija.Izracun(pozicijaItema, (double)time/3600, (udaljenost/time)*3.6));
		//NOVO
		
		//txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
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
