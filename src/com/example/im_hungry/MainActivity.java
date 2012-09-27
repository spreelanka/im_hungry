package com.example.im_hungry;

import java.util.concurrent.ExecutionException;

import com.yelp.Yelp;

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
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void onFeedMeButtonClick(View v){
    	
//    	YelpAuthInfo auth_info= new YelpAuthInfo("mWYLNzQlV1jHxOWL9BCK3A",//consumerKey
//				"zF5zKAg419WATpzlR4yGY7iVqWM",//consumerSecret
//				"QWkjswtBialCuKtriQKNq4wkWbYeEVhR",//token
//				"rqlzdngBigqKskrmAoXhc5lqKuU"//tokenSecret
//				);
    	YelpResultsTask yelp_task=new YelpResultsTask();
        yelp_task.execute();
        
//        String alertdialog_message="blahblahblah";
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
// 
//			// set title
//			alertDialogBuilder.setTitle("Your Title");
// 
//			// set dialog message
//			alertDialogBuilder
//				.setMessage(alertdialog_message)
//				.setCancelable(false)
//				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog,int id) {
//						// if this button is clicked, close
//						// current activity
//						MainActivity.this.finish();
//					}
//				  })
//				.setNegativeButton("No",new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog,int id) {
//						// if this button is clicked, just close
//						// the dialog box and do nothing
//						dialog.cancel();
//					}
//				});
// 
//				// create alert dialog
//				AlertDialog alertDialog = alertDialogBuilder.create();
// 
//				// show it
//				alertDialog.show();
    }
    
    public void setYelpResultTextView(String text){
    	((TextView)findViewById(R.id.yelpResultTextView)).setText(text);
    }
    
    

	public void openSettings(View v){
    	Intent i=new Intent();
    	i.setClass(this, SettingsActivity.class);
    	startActivity(i);
    }
	
	
	public class YelpResultsTask extends AsyncTask<Void, Integer, Boolean> {
		@Override
		protected Boolean doInBackground(Void... junk) {
			//TODO: move yelp auth stuff somewhere better
			YelpAuthInfo auth_info= new YelpAuthInfo("mWYLNzQlV1jHxOWL9BCK3A",//consumerKey
					"zF5zKAg419WATpzlR4yGY7iVqWM",//consumerSecret
					"QWkjswtBialCuKtriQKNq4wkWbYeEVhR",//token
					"rqlzdngBigqKskrmAoXhc5lqKuU"//tokenSecret
					);
	        Yelp yelp = new Yelp(	auth_info.consumerKey(), 
	        						auth_info.consumerSecret(), 
	        						auth_info.token(), 
	        						auth_info.tokenSecret()
	        						);
	        try{
	        	String response = yelp.search("burritos", 30.361471, -87.164326);
	        	if(response!=null && response.length()>0){
	        		return true;
	        	}
	        } catch (IllegalStateException e){
	        	//maybe do something here...
	        }

			return false;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result){
				MainActivity.this.setYelpResultTextView("got something from yelp");
			}else{
				MainActivity.this.setYelpResultTextView("got NOTHING");
			}
		}
	}
}
