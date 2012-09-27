package com.example.im_hungry;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class SettingsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
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
    
}
