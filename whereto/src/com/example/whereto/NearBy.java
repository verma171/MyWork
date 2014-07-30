package com.example.whereto;

import java.util.List;
import java.util.StringTokenizer;

import com.models.Placeresponse;
import com.utils.AlertDialogManager;
import com.utils.ConnectionDetector;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.location.*;
public class NearBy extends Activity {
	// flag for Internet connection status
	Boolean isInternetPresent = false;

	// Connection detector class
	ConnectionDetector cd;

	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();

	// Google Places
	GooglePlaces googlePlaces;

	List<Placeresponse> nearplaceslist;
	// GPS Location
	GPSTracker gps;
   
	ListView lv ;
	
	NearByListAdapter adapter;
	// Progress dialog
	ProgressDialog pDialog;
	
    Location location;
	 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nearbylist);
		 cd = new ConnectionDetector(getApplicationContext());
		 
	        // Check if Internet present
	        isInternetPresent = cd.isConnectingToInternet();
	        if (!isInternetPresent) {
	            // Internet Connection is not present
	            alert.showAlertDialog(NearBy.this, "Internet Connection Error",
	                    "Please connect to working Internet connection", false);
	            // stop executing code by return
	            return;
	        }
	        
	        //get location from intent
	        Intent intent = getIntent();
		    Bundle bundle =	intent.getExtras();
		    String string = (String)bundle.get("loc");
	        
		    StringTokenizer tok  = new StringTokenizer(string,",");
		    
		    String latitude = tok.nextToken();
		    String longitube = tok.nextToken();
		    
		    location = new Location("");
		    location.setLatitude(Double.parseDouble(latitude));
		    location.setLongitude(Double.parseDouble(longitube));
		    
		    
		/*    
		    // creating GPS Class object
	        gps = new GPSTracker(this);
	 
	        // check if GPS location can get
	        if (gps.canGetLocation()) {
	            Log.d("Your Location", "latitude:" + gps.getLatitude() + ", longitude: " + gps.getLongitude());
	        } else {
	            // Can't get user's current location
	            alert.showAlertDialog(NearBy.this, "GPS Status",
	                    "Couldn't get location information. Please enable GPS",
	                    false);
	            // stop executing code by return
	            return;
	        }
	 */
		
		
		Log.d("Latitube----->",String.valueOf(location.getLatitude())+" , " + String.valueOf(location.getLongitude()));
		  LoadList();
	}
	
	public void LoadList()
	{
		lv =(ListView) findViewById(R.id.listnearby);
		   
		Load lo = new Load();
		Intent intent = getIntent();
	    Bundle bundle =	intent.getExtras();
	    String string = (String)bundle.get("type");
		lo.execute(string);
		
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
			   Intent intent = new Intent(getApplicationContext(), PlacesDetails.class);
			   intent.putExtra("nearbyobj", nearplaceslist.get(arg2));
			   startActivity(intent);
			}
			});
	}

	
   


	class Load extends AsyncTask<String, String, String> {
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(NearBy.this);
			pDialog.setMessage(Html
					.fromHtml("<b>Search</b><br/>Loading Places..."));
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... type) {
			googlePlaces = new GooglePlaces();
			try {
				double radius = 1000; // in meter
				
				// get nearest places
				nearplaceslist = googlePlaces.findPlaces(location.getLatitude(),
						location.getLongitude(), type[0]);
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			return null;
		}
		
		
		@Override
		protected void onPostExecute(String result) {
			// dismiss the dialog 
			pDialog.dismiss();
			
			if(nearplaceslist != null)
			{
				   // updating UI from Background Thread
	          runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					
					adapter = new NearByListAdapter(NearBy.this, R.layout.nearbylistitem, nearplaceslist);
				
					lv.setAdapter(adapter);
				}
			});
			}
			else
			{
	    alert.showAlertDialog(NearBy.this , "Error" ,"Sorry no places found. Try to change the types of places",
						false);
			}
			
			super.onPostExecute(result);
		}

	}
}
