package com.example.im_hungry;

import java.util.List;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MapRestaurantActivity extends MapActivity {

	protected GeoPoint current_location;
	protected MyItemizedOverlay itemizedOverlay;
	protected List<Overlay> mapOverlays;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_restaurant);
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.ic_launcher);
        itemizedOverlay = new MyItemizedOverlay(drawable, this);
        
        
     // Acquire a reference to the system Location Manager
        

        // Define a listener that responds to location updates

//
//        // Register the listener with the Location Manager to receive location updates
//          // .NETWORK_PROVIDER
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        
        
        LocationListener locationListener = new MyLocationListener();
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 35000, 10, locationListener);

        
//        GeoPoint point2 = new GeoPoint(19240000,-99120000);
        GeoPoint point = new GeoPoint(19240000,-99120000);
        
        OverlayItem overlayItem = new OverlayItem(point, "Hola, Mundo!", "I'm in Mexico City!");
        itemizedOverlay.addOverlay(overlayItem);
        
        mapOverlays.add(itemizedOverlay);
        
//        GeoPoint point = new GeoPoint(19240000,-99120000);
//        mapView.
    }

  
private final class MyLocationListener implements LocationListener{
  public void onLocationChanged(Location location) {
    // Called when a new location is found by the network location provider.
//    makeUseOfNewLocation(location);
//    mapOverlays.remove(itemizedOverlay);
    String alertdialog_message="blahblahblah";
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MapRestaurantActivity.this);

		// set title
		alertDialogBuilder.setTitle("Your Title");

		// set dialog message
		alertDialogBuilder
			.setMessage(alertdialog_message)
			.setCancelable(false)
			.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, close
					// current activity
					MapRestaurantActivity.this.finish();
				}
			  })
			.setNegativeButton("No",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, just close
					// the dialog box and do nothing
					dialog.cancel();
				}
			});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
    if(current_location==null){
  	  current_location= new GeoPoint((int)location.getLatitude(),(int)location.getLongitude());  
    }
    
    MapView mapView = (MapView) findViewById(R.id.mapview);
    mapOverlays = mapView.getOverlays();
    mapOverlays.remove(itemizedOverlay);
    OverlayItem overlayItem = new OverlayItem(current_location, "current location", "current location");
    itemizedOverlay.addOverlay(overlayItem);
    
    mapOverlays.add(itemizedOverlay);
  }

  public void onStatusChanged(String provider, int status, Bundle extras) {}

  public void onProviderEnabled(String provider) {}

  public void onProviderDisabled(String provider) {}
}
    
    protected void makeUseOfNewLocation(Location location) {
		// TODO Auto-generated method stub
		
	}

	public void openMain(View v){
    	Intent i=new Intent();
    	i.setClass(this, MainActivity.class);
    	startActivity(i);
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_settings, menu);
//        return true;
//    }
//    public void openSettings(){
//    	Intent i=new Intent();
//    	i.setClass(this, )
//    	
//    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
    
}
