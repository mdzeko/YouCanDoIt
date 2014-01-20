package foi.youcandoit;

import foi.youcandoit.MySupportMapFragment;
import foi.youcandoit.OnMyLongClickListener;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.conn.scheme.PlainSocketFactory;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class AktivnostPlaniraj extends FragmentActivity implements OnMyLongClickListener{
	private long startTime;
	private long stopTime;
	private long WAIT_TIME = 1500;
	private LatLng clickedPoint;
	private GoogleMap map;
	private Dialog addPoiDialog;
	private LatLng poiPosition;
	private Context c;
	public static int groupId;
	
	// NOVO 3
	EditText ptTrajanje;
	KalkulatorKalorija kalkulatorKalorija;
	int pozicijaItema;
	Spinner mySpinner;
	float udaljenost = 0;
	float crta;
	Boolean prva = false;
	Location zadnjaLokacija;
	Location novaLokacija;
	int time;
	// NOVO 3
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prikaz_mape);
		c = this;
		//groupId = getIntent().getExtras().getInt("intGroupId");
		
		//NOVO 3
		Button Izracunaj = (Button) findViewById(R.id.gmbIzracunaj);
		ptTrajanje = (EditText)findViewById(R.id.ptTrajanje);
		
		//NOVO 4
		ptTrajanje.setText("60");
		//NOVO 4
		
		kalkulatorKalorija = new KalkulatorKalorija();
		mySpinner = (Spinner)findViewById(R.id.spAktivnost);
		
		Izracunaj.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) 
			{
				pozicijaItema = mySpinner.getSelectedItemPosition();
				time = Integer.parseInt(ptTrajanje.getText().toString())*60;
				Toast.makeText(AktivnostPlaniraj.this, "broj kalorija: " + kalkulatorKalorija.Izracun(pozicijaItema, (double)time/3600, ((udaljenost/time)*3.6)) + "\nudaljenost: " + udaljenost, Toast.LENGTH_LONG).show();	
			}
		});
		//NOVO 3
		
		
		//prikaz podataka na mapu
				MySupportMapFragment mapFragment = 
						(MySupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa);
				map = mapFragment.getMap();
				mapFragment.setParent(this);
		
		setOnMyLongClickListener(this);
	}

	public void onMyTouchEvent(MotionEvent event) {
		Log.d("AIR", "Event: " + event.getAction());
		switch (event.getAction()){
		case MotionEvent.ACTION_DOWN:
			startTime = event.getEventTime();
			Point point = new Point((int)event.getX(), (int)event.getY());
			clickedPoint = map.getProjection().fromScreenLocation(point);
			handler.postDelayed(r, WAIT_TIME);
			//pokreni odgodeni prikaz dijaloga
			break;
			
		case MotionEvent.ACTION_UP:
			stopTime = event.getEventTime();
			if(stopTime - startTime < WAIT_TIME){
				handler.removeCallbacks(r);
			}
			break;
			
		case MotionEvent.ACTION_MOVE:
			handler.removeCallbacks(r);
			break;
		}
	}

	Handler handler = new Handler();
	Runnable r = new Runnable(){
		@Override
		public void run(){
			myLongClick(clickedPoint);
		}};
	/*
	private void showPois() {
		//dohvat podataka
		IPoiSource poiSource = PoiLoaderFactory.getInstance(this);
		List<PoiInfo> pois = poiSource.getPois(1);
		
		//èištenje mape
		map.clear();
		
		for(int i = 0; i < pois.size(); i++){
			PoiInfo poi = pois.get(i);
			MarkerOptions marker = new MarkerOptions();
			marker.title(poi.getName());
			marker.snippet(poi.getDescription());
			marker.position(pois.get(i).getPosition());
			marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.nesto));
			map.addMarker(marker);
		}
		
		//MOJE
		Polyline line = map.addPolyline(new PolylineOptions()
	     .add(new LatLng(51.5, -0.1), new LatLng(40.7, -74.0))
	     .width(3)
	     .color(Color.RED));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.menu_map, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.opt_map_settings:
			Intent i = new Intent(this, MapSettingsActivity.class);
			startActivity(i);
			break;

		case R.id.opt_map_refresh:
			//Toast.makeText(this, "Refresh clicked!", Toast.LENGTH_SHORT).show();
			onResume();
			break;
			
		case R.id.opt_map_localDataActivity:
			Intent j = new Intent(this, LocalDataActivity.class);
			startActivity(j);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onResume() {
		showPois();
		super.onResume();
	}
	*/
	//elementi kod generatora dogadaja
		private List<OnMyLongClickListener> myLongClickListeners;
		
		public void setOnMyLongClickListener(OnMyLongClickListener listener)
		{
			if(myLongClickListeners == null)
				myLongClickListeners = new ArrayList<OnMyLongClickListener>();
			myLongClickListeners.add(listener);
		}
		
		private void myLongClick(LatLng clickedPoint)
		{
			if(myLongClickListeners != null)
				for(OnMyLongClickListener listener : myLongClickListeners)
				{
					listener.onMyLongClick(clickedPoint);
				}
		}
		
		
		//elementi kod pretplatnika na dogadaj
		@Override
		public void onMyLongClick(LatLng clickedPoint) {
			//Log.d("AIR","Long click accepted");
			//Dialog myDialog = new Dialog(this);
			/*final Dialog myDialog = new Dialog(this);
			addPoiDialog = myDialog;
			poiPosition = clickedPoint;
			
			myDialog.setContentView(R.layout.dialog_add_poi);
			myDialog.setTitle(getResources().getString(R.string.poi_add));
			myDialog.setCancelable(true);
			myDialog.show();*/
			
			//Toast.makeText(this, "bravo" + clickedPoint.latitude + " " + clickedPoint.longitude, Toast.LENGTH_LONG).show();
			Toast.makeText(this, "Tocka dodana", Toast.LENGTH_LONG).show();
			
			//NOVO 3
			Location location = new Location("Test");
			location.setLatitude(clickedPoint.latitude);
			location.setLongitude(clickedPoint.longitude);
			
			if(prva == false)
			{
				zadnjaLokacija = location;
				prva = true;
			}
			else
			{
				novaLokacija = location;	
				
				map.addPolyline(new PolylineOptions()
			     .add(new LatLng(zadnjaLokacija.getLatitude(), zadnjaLokacija.getLongitude()), new LatLng(location.getLatitude(), location.getLongitude()))
			     .width(3).color(Color.RED));
				
				udaljenost += location.distanceTo(zadnjaLokacija);
				zadnjaLokacija = novaLokacija;
			}
			//NOVO 3
			
			
			//MOJE
			//Toast.makeText(this, "bravo" + clickedPoint.latitude + " " + clickedPoint.longitude, Toast.LENGTH_LONG).show();
			//Toast.makeText(this, "da" + String.format("%.2f", clickedPoint.latitude), Toast.LENGTH_LONG).show();
			
			//Button btnCancel = (Button)myDialog.findViewById(R.id.btnCancel);
			//Button btnAdd = (Button)myDialog.findViewById(R.id.btnAdd);
			
			//MOJE
			//btnAdd.setEnabled(false);
			/*
			btnCancel.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					myDialog.dismiss();
				}
			});*/
			
			/*btnAdd.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					EditText txtName = (EditText)addPoiDialog.findViewById(R.id.txtName);
					EditText txtDesc = (EditText)addPoiDialog.findViewById(R.id.txtDescription);
					
					String name = txtName.getText().toString();
					String desc = txtDesc.getText().toString();
					
					PoiInfo newPoi = new PoiInfo(name, desc, poiPosition);
					
					PoisAdapter poisAdapter = new PoisAdapter(c);
					long r = poisAdapter.insertNewPoi(newPoi);
					Toast.makeText(c, 
							r!=-1 ? "Poi Added!" : "Error occured!",
									Toast.LENGTH_LONG)
									.show();
					addPoiDialog.dismiss();
				}
			});*/
		};
}

