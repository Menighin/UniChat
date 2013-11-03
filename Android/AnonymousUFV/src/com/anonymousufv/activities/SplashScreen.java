package com.anonymousufv.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

import com.anonymousufv.settings.Settings;

/*
 * Activity que mostra a tela inicial do aplicativo. O tempo est� definido na classe Settings.java
 */

public class SplashScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_splash_screen);
		
		 new Handler().postDelayed(new Runnable() {
	 
	            @Override
	            public void run() {
	                Intent i = new Intent(SplashScreen.this, Login.class);
	                startActivity(i);
	                finish();
	            }
	        }, Settings.SPLASH_TIME);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

}