package com.example.whereto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;

import com.models.Places;
import com.models.Places.PartPlaces;
import com.utils.ParserApi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

public class MainActivity extends Activity {

	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, PartPlaces> listDataChild;
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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
				Toast.makeText(
						getApplicationContext(),
						listDataHeader.get(groupPosition)
								+ " : "
								+ listDataChild.get
								(
										listDataHeader.get(groupPosition)).templates.get(childPosition), Toast.LENGTH_SHORT)
						.show();
		      Intent intent = new Intent(getApplicationContext(),NearBy.class);
		      PartPlaces place =  listDataChild.get(listDataHeader.get(groupPosition));
			  intent.putExtra("type",place.search_keywords.get(childPosition));
		    
			  startActivity(intent);
				return false;
			}
		});
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
}
