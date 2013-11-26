package foi.youcandoit.sucelja;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class ILokacija implements LocationListener
{
	public static double sirina;
	public static double duzina;
	
	
	@Override
	public void onLocationChanged(Location location) 
	{
		location.getLatitude();
		location.getLongitude();
		sirina = location.getLatitude();
		duzina = location.getLongitude();
	}
	@Override
	public void onProviderDisabled(String provider)
	{
		
	}
	@Override
	public void onProviderEnabled(String provider) 
	{
		
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) 
	{
		
	}
	
	
}
