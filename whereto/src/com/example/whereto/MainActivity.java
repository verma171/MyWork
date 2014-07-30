package com.example.whereto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;

import com.models.Places;
import com.models.Places.PartPlaces;
import com.utils.LocationHelper;
import com.utils.ParserApi;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;
import com.utils.*;
import com.volley.AppController;

import android.app.AlertDialog;
public class MainActivity extends Activity {

	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, PartPlaces> listDataChild;
    LocationHelper locationHelper;
    LocationManager locationManager;
    String LOG_TAG;
    Handler handler;
    Location location = null;
   // Alert Dialog Manager
 	AlertDialogManager alert = new AlertDialogManager();
    Context context;
    final int DURATION = 30;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this.getApplicationContext();
		handler = new Handler() {
			public void handleMessage(Message m) {
				Log.d(LOG_TAG, "Handler returned with message: " + m.toString());
				if (m.what == LocationHelper.MESSAGE_CODE_LOCATION_FOUND) {
                   location = (Location)m.obj;
                   AppController.getInstance().setMlocation(location);
				} else if (m.what == LocationHelper.MESSAGE_CODE_LOCATION_NULL) {
					alert.showAlertDialog(MainActivity.this, "GPS Error", "No location available", false);
				} else if (m.what == LocationHelper.MESSAGE_CODE_PROVIDER_NOT_PRESENT) {
                    // launch setting;
					   showSettingsAlert();
				}
			}
		};

		// Init location Helper 
		locationManager = (LocationManager) this
				.getSystemService(LOCATION_SERVICE);
		locationHelper = new LocationHelper(locationManager, handler, LOG_TAG);
		if(locationHelper != null)
		      locationHelper.getCurrentLocation(DURATION);
		
		// get the listview
		expListView = (ExpandableListView) findViewById(R.id.lvExp);
		expListView.setGroupIndicator(null);
    
		// preparing list data
		Places plc = null;
		try {
		 plc =	prepareListData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


        listAdapter = new ExpandableListAdapter(getApplicationContext(), listDataHeader, listDataChild, plc);
		
        // setting list adapter
		expListView.setAdapter(listAdapter);

		// Listview Group click listener
		expListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// Toast.makeText(getApplicationContext(),
				// "Group Clicked " + listDataHeader.get(groupPosition),
				// Toast.LENGTH_SHORT).show();
				return false;
			}
		});

		// Listview Group expanded listener
		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
			/*	Toast.makeText(getApplicationContext(),
						listDataHeader.get(groupPosition) + " Expanded",
						Toast.LENGTH_SHORT).show();*/
			}
		});

		// Listview Group collasped listener
		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
				
			}
		});

		// Listview on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
			
				if(location == null)
				{
					alert.showAlertDialog(MainActivity.this, "Error", "We are fetching your location please wait...", false);
					if(locationHelper != null)
					      locationHelper.getCurrentLocation(DURATION);
					return false;
				}
				
		      Intent intent = new Intent(getApplicationContext(),NearBy.class);
		      PartPlaces place =  listDataChild.get(listDataHeader.get(groupPosition));
			  intent.putExtra("type",place.search_keywords.get(childPosition));
		      
			  StringBuilder string = new StringBuilder();
			  string.append(location.getLatitude());
			  string.append(",");
			  string.append(location.getLongitude());
			  intent.putExtra("loc",string.toString());
			  startActivity(intent);
				return false;
			}
		});
	}
	
	

	@Override
	protected void onResume() {
		super.onResume();
		
	}
	/*
	 * Preparing the list data
	 */
	private Places prepareListData() throws IOException, JSONException {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, Places.PartPlaces>();
      
		Places places = ParserApi.GetJsonObject(getApplicationContext());
		
		// Adding child data
		int leght = places.master.size();
		for(int i=0; i < leght; i++)  
		{
			listDataHeader.add(places.master.get(i).placename);
		}
		
		for(int i=0; i< listDataHeader.size(); i++)
		{
		   listDataChild.put(listDataHeader.get(i), places.master.get(i));
		}
		return places;
	}
   
    private void showSettingsAlert() {
	        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
	 
	        // Setting Dialog Title
	        alertDialog.setTitle("GPS is settings");
	 
	        // Setting Dialog Message
	        alertDialog
	                .setMessage("Not All location provider enable. Turn on GPS and wireless network in setting.");
	 
	        // On pressing Settings button
	        alertDialog.setPositiveButton("Settings",
	                new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) {
	                        Intent intent = new Intent(
	                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	                        context.startActivity(intent);
	                    }
	                });
	 
	        // on pressing cancel button
	        alertDialog.setNegativeButton("Cancel",
	                new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) {
	                        dialog.cancel();
	                    }
	                });
	 
	        // Showing Alert Message
	        alertDialog.show();
	    }
	
}
