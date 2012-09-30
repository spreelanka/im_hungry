package com.example.im_hungry;

import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.yelp.Yelp;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import com.yelp.YelpAuthInfo;
import android.content.Context;
import android.os.AsyncTask;
import com.yelp.*;



public class MainActivity extends Activity {
	protected GeoPoint current_location;
	//TODO: move yelp auth stuff somewhere better
	protected final static YelpAuthInfo yelp_auth_info= new YelpAuthInfo("mWYLNzQlV1jHxOWL9BCK3A",//consumerKey
			"zF5zKAg419WATpzlR4yGY7iVqWM",//consumerSecret
			"QWkjswtBialCuKtriQKNq4wkWbYeEVhR",//token
			"rqlzdngBigqKskrmAoXhc5lqKuU"//tokenSecret
			);
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        LocationListener locationListener = new MyLocationListener();
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 35000, 10, locationListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void onOpenGmapsButtonClick(View v){
    	YelpResultsTask yelp_task=new YelpResultsTask();
        yelp_task.execute();
    	
    }
    public void onFeedMeButtonClick(View v){
    	
    	YelpResultsGmapsTask yelp_task=new YelpResultsGmapsTask();
        yelp_task.execute();
        

    }
    
    public void setYelpResultTextView(String text){
    	((TextView)findViewById(R.id.yelpResultTextView)).setText(text);
    }
    
    

	public void openSettings(View v){
    	Intent i=new Intent();
    	i.setClass(this, SettingsActivity.class);
    	startActivity(i);
    }
	
	public void openMapRestaurant(View v){
		openMapRestaurant();
    }
	
	public void openMapRestaurant(){
    	Intent i=new Intent();
    	i.setClass(this, MapRestaurantActivity.class);
    	startActivity(i);
    }
	
	
	public class YelpResultsTask extends AsyncTask<Void, Integer, String> {
		@Override
		protected String doInBackground(Void... junk) {

			
	        Yelp yelp = new Yelp(	yelp_auth_info.consumerKey(), 
	        						yelp_auth_info.consumerSecret(), 
	        						yelp_auth_info.token(), 
	        						yelp_auth_info.tokenSecret()
	        						);
	        try{

//	        	return current_location.toString();

	        	
//	        	return current_location.getLatitudeE6());
	        	String response = yelp.search("fast food", 
	        			current_location.getLatitudeE6(), 
	        			current_location.getLongitudeE6());
	        	if(response!=null && response.length()>0){
	        		return response;
	        	}
	        } catch (IllegalStateException e){
	        	//maybe do something here...
	        }

			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result != null && result.length()>0){
				MainActivity.this.setYelpResultTextView(result);
//						"got something from yelp");
//				MainActivity.this.openMapRestaurant();
			}else{
				MainActivity.this.setYelpResultTextView("got NOTHING");
			}
		}
	}
	
	public class YelpResultsGmapsTask extends AsyncTask<Void, Integer, String> {
		@Override
		protected String doInBackground(Void... junk) {

	        Yelp yelp = new Yelp(	yelp_auth_info.consumerKey(), 
	        						yelp_auth_info.consumerSecret(), 
	        						yelp_auth_info.token(), 
	        						yelp_auth_info.tokenSecret()
	        						);
	        try{
//	        	30.361471, -87.164326);
	        	
	        	String response = yelp.search("fast food", 
	        			current_location.getLatitudeE6(),
	        			current_location.getLongitudeE6());
	        	if(response!=null && response.length()>0){
	        		return response;
	        	}
	        } catch (IllegalStateException e){
	        	//maybe do something here...
	        }

			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result != null && result.length()>0){
				try {
					//TODO this json parsing is horrible and will probably cause exceptions when yelp changes its stuff 
					JSONObject result_as_json= new JSONObject(result);
					JSONArray directions_lines= ((JSONArray)((JSONObject)((JSONObject)((JSONArray)result_as_json.get("businesses")).get(0)).get("location")).get("display_address"));
					String direction_string="";
					for( int i=0;i<directions_lines.length();i++) {
						direction_string+=" "+directions_lines.getString(i);
					}
					MainActivity.this.setYelpResultTextView( direction_string);
										

	//				String url = "http://maps.google.com/maps?saddr=some+address&daddr=another+Address"
					String url = "http://maps.google.com/maps?daddr="+direction_string;
					Intent intent = new Intent(android.content.Intent.ACTION_VIEW,  Uri.parse(url));
//					MainActivity.this.setYelpResultTextView(result);
					startActivity(intent);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}else{
				MainActivity.this.setYelpResultTextView("got NOTHING");
			}
		}
	}
		
	private final class MyLocationListener implements LocationListener{
		  public void onLocationChanged(Location location) {
		    // Called when a new location is found by the network location provider.
			  current_location= new GeoPoint((int)location.getLatitude(),(int)location.getLongitude());  
		    
		  }
		  public void onStatusChanged(String provider, int status, Bundle extras) {}

		  public void onProviderEnabled(String provider) {}

		  public void onProviderDisabled(String provider) {}
	}
}

