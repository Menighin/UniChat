package br.com.unichat.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import br.com.unichat.classes.SaveSharedPreferences;
import br.com.unichat.settings.Settings;

import br.com.unichat.activities.R;

/*
 * Activity que mostra a tela inicial do aplicativo. O tempo est� definido na classe Settings.java
 */

public class SplashScreen extends Activity {
	
	private Handler hand;
	private Runnable r;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_splash_screen);
		
		hand = new Handler();
		r = new Runnable () {
			@Override
            public void run() {
            	Intent i;
            	if (SaveSharedPreferences.getLoggedState(SplashScreen.this)) {
            		SaveSharedPreferences.setUser(SplashScreen.this);
            		i = new Intent(SplashScreen.this, MainMenu.class);
            	}
            	else
            		i = new Intent(SplashScreen.this, Login.class);
                startActivity(i);
                finish();
            }
		};
		hand.postDelayed(r, Settings.SPLASH_TIME);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}
	
	@Override
	public void onBackPressed () {
		super.onBackPressed();
		if (r != null)
			hand.removeCallbacks(r);
	}	

}