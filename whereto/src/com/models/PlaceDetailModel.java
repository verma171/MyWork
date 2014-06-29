package com.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.whereto.PlacesDetails;
import com.google.android.gms.ads.a;
import com.google.gson.JsonArray;



public class PlaceDetailModel {
    
	
	private String name;
	private String phone_number;
	private String googleUrl;
	private String website;
	private ArrayList<PlacesPhoto> photolist;
	private ArrayList<Reviews> reviewList;
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getGoogleUrl() {
		return googleUrl;
	}
	public void setGoogleUrl(String googleUrl) {
		this.googleUrl = googleUrl;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public PlaceDetailModel()
	{
		photolist = new ArrayList<PlacesPhoto>();
		setReviewList(new ArrayList<Reviews>());
	}
	
	
	public ArrayList<PlacesPhoto> getPhotolist() {
		return photolist;
	}
	public void setPhotolist(ArrayList<PlacesPhoto> photolist) {
		this.photolist = photolist;
	}
	public static PlaceDetailModel JsontoPlaceModel(JSONObject object) 
	{ PlaceDetailModel model = null;
		try
		{
		
	    model = new PlaceDetailModel();
		model.phone_number = (String)object.getString("international_phone_number");
		model.name = (String)object.getString("name");
		model.googleUrl = (String)object.getString("url");
		model.website = (String)object.getString("website");
		JSONArray jsonarray =  object.getJSONArray("photos");
		JSONArray reviewArray = object.getJSONArray("reviews");
		if(jsonarray != null && jsonarray.length()>0)
		{  
			for(int count=0; count<jsonarray.length(); count++)
			{
			    JSONObject json = jsonarray.getJSONObject(count);
			    String height = json.getString("height");
			    String width = json.getString("width");
			    String photref = json.getString("photo_reference");
			    PlacesPhoto photo = new PlacesPhoto(height, width, photref);
			    model.photolist.add(photo);
			}
			for(int count=0; count<reviewArray.length(); count++)
			{
			    JSONObject json = reviewArray.getJSONObject(count);
			    Reviews rev = new Reviews();
			    rev.setName(json.getString("author_name"));
			    rev.setRating(json.getDouble("rating"));
			    rev.setReview(json.getString("text"));
			    rev.setTimespan(json.getLong("time"));
			    model.reviewList.add(rev);
			}
		}
	   }
		catch (Exception e) {
			// TODO: handle exception
		}
		return model;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Reviews> getReviewList() {
		return reviewList;
	}
	public void setReviewList(ArrayList<Reviews> reviewList) {
		this.reviewList = reviewList;
	}

}
