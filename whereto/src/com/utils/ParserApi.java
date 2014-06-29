package com.utils;

import java.io.IOException;

import java.io.InputStream;
import org.json.JSONException;
import android.content.Context;
import com.google.gson.Gson;
import com.models.*;

import android.util.Log;
public class ParserApi {
  
	public static String AssetJSONFile(String filename, Context context) throws IOException {
		 String json = null;
		    try {
               
		        InputStream is = context.getAssets().open("Places.json");

		        int size = is.available();

		        byte[] buffer = new byte[size];

		        is.read(buffer);

		        is.close();

		        json = new String(buffer, "UTF-8");

		    } catch (IOException ex) 
		    {
		        ex.printStackTrace();
		        return null;
		    }
		    return json;
    }
	
	
	public static Places GetJsonObject(Context context) throws IOException, JSONException
	{   
		 // JSONObject jobj = null; 
	     // jobj = new JSONObject(AssetJSONFile("Places.json", context.getApplicationContext()));
	     // JSONObject travelobje = jobj.getJSONObject("Travel");
	     // JSONArray arry = travelobje.getJSONArray("templates");
	    String jsonstring = AssetJSONFile("Places.json", context.getApplicationContext());
	    Gson gson = new Gson();
	    Places places = null;
	    try
	    {
	     places =  gson.fromJson(jsonstring, Places.class);
	    }
	    catch(Exception r)
	    {
	    	Log.d("Error he bhaya",r.getMessage());
	    }
	    
	    return places;
		
	}

}
