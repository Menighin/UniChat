package com.anonymousufv.activities;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.anonymousufv.settings.Settings;

public class MainMenu extends Activity {
	
	//TODO Fill spinner with data from Database
	//TODO Implement users choices on sex and course
	
	private LinearLayout paid;
	private Button whateverBtn;
	private Button femaleBtn;
	private Button maleBtn;
	private Spinner courses;
	private int selectedSex = 0;
	private Handler handler;
	private Toast toast;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main_menu);
		
		whateverBtn = (Button) findViewById(R.id.whatever_btn);
		femaleBtn = (Button) findViewById(R.id.female_btn);
		maleBtn = (Button) findViewById(R.id.male_btn);
		courses = (Spinner)findViewById(R.id.courses_spinner);
		paid = (LinearLayout) findViewById(R.id.paid_part);
		
		if (Settings.FREE_VERSION)
			paid.setVisibility(View.GONE);
		
		handler = new Handler (new Handler.Callback() {
			@Override
			public boolean handleMessage(Message msg) {
				toast = Toast.makeText(MainMenu.this, (String) msg.obj, Toast.LENGTH_SHORT);
				toast.show();
				return true;
			}
		});
		
		Settings.CURSOS = new ArrayList<String>();
		Settings.CURSOS.add("Qualquer");
		Settings.CURSOS.add("Agronomia");
		Settings.CURSOS.add("Arquitetura");
		Settings.CURSOS.add("Ci�ncia da Computa��o");
		Settings.CURSOS.add("Dan�a");
		Settings.CURSOS.add("Direito");
		Settings.CURSOS.add("Engenharia Civil");
		Settings.CURSOS.add("Engenharia de Agrimensura");
		Settings.CURSOS.add("Engenharia El�trica");
		Settings.CURSOS.add("Engenharia Qu�mica");
		Settings.CURSOS.add("Engenharia Mec�nica");
		Settings.CURSOS.add("Engenharia de Alimentos");
		Settings.CURSOS.add("F�sica");
		Settings.CURSOS.add("Medicina");
		Settings.CURSOS.add("Matem�tica");
		Settings.CURSOS.add("Nutri��o");
		Settings.CURSOS.add("Zootecnia");
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Settings.CURSOS);
		courses.setAdapter(adapter);
	}
	
	//Function to make the 3 buttons work like a RadioButton
	public void btnSelected (View v) {
		switch (v.getId()) {
			case R.id.female_btn:
				femaleBtn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.female_pressed, 0, 0);
				whateverBtn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.whatever, 0, 0);
				maleBtn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.male, 0, 0);
				selectedSex = 1;
				break;
			case R.id.male_btn:
				femaleBtn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.female, 0, 0);
				whateverBtn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.whatever, 0, 0);
				maleBtn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.male_pressed, 0, 0);
				selectedSex = 2;
				break;
			case R.id.whatever_btn:
				femaleBtn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.female, 0, 0);
				whateverBtn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.whatever_pressed, 0, 0);
				maleBtn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.male, 0, 0);
				selectedSex = 0;
				break;
		}
	}
	
	//Connect button clicked
	public void connect (View v) {
		new ConnectAsync().execute();
	}
	
	//AsyncTask to manage the connection with the API
	private class ConnectAsync extends AsyncTask<Void, Void, Integer> {
		
		@Override
		protected Integer doInBackground (Void... params) {
			Message msg = new Message();
			
			ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		   
			if (networkInfo != null && networkInfo.isConnected()) {
		        try {
		        	return doConnect();
		        } catch (Exception e) {
		        	Log.e("doConnectException", e.getMessage());
		        	return -2;
		        }
		    } else {
		    	msg.obj = "Preciso de uma conex�o com a internet pra logar!";
		    	handler.sendMessage(msg);
		    	return -2;
		    }
		}
		
		@Override
		protected void onPostExecute (Integer result) {
			if (result == 0 || result == 1) {
				Intent intent = new Intent(MainMenu.this, Chat.class);
				intent.putExtra("type", result);
				startActivity(intent);
			} else {
				Toast.makeText(MainMenu.this, "Deu merda. Que que c fez?", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	
	private int doConnect() throws Exception {
		String urlParameters = "user=" + Settings.me.getUserID() + "&sex=" + Settings.me.getSex() + "&course=" + Settings.me.getCourseID() + 
					"&wantssex=w&wantscourse=0";
		URL url = new URL(Settings.API_URL + "/connect");
		
		//Connection parameters
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
	    conn.setDoOutput(true);
	    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	    
	    //Send request
  		DataOutputStream wr = new DataOutputStream (conn.getOutputStream ());
  		wr.writeBytes (urlParameters);
  		wr.flush ();
  		wr.close ();
  		
  		//Get Response	
	    InputStream is = conn.getInputStream();
	    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	    String line;
	    StringBuffer response = new StringBuffer(); 
	    while((line = rd.readLine()) != null) {
	    	response.append(line);
	    	response.append('\r');
	    }
	    rd.close();
	    
	    JSONObject json = new JSONObject(response.toString());
	    
	    //Set new global id conversation
	    if (json.getInt("response") == 1 || json.getInt("response") == 0)
	    	Settings.CONVERSATION_ID = json.getInt("conversation_id");
		
		return json.getInt("response");
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

}