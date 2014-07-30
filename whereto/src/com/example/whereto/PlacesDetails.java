package com.example.whereto;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.models.PlaceDetailModel;
import com.models.Placeresponse;
import com.models.PlacesPhoto;
import com.models.Reviews;

import android.location.Location;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.utils.Util;
import com.volley.AppController;

public class PlacesDetails extends Activity {
	// Google Map
	Placeresponse placedetail;
	private GoogleMap googleMap;
	LatLng lat;
	ImageLoader mImageLoader;
	LinearLayout photolayout;
	LinearLayout reviewLayout;
	TextView reviewHeader;
	ArrayList<String> networkImageUrlList;
	TextView review_name;
	TextView review_text;
	TextView review_date;
	PlaceDetailModel mPlaceDetailModel;
	TextView photoheader;
	TextView lb1;
	TextView lb2;
	TextView lb3;
	String google_url;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_places_details);
		// Initialize the image loader

		try {
			mImageLoader = AppController.getInstance().getImageLoader();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		photolayout = (LinearLayout) findViewById(R.id.photoLayout);
		reviewLayout = (LinearLayout) findViewById(R.id.reviewLayout);
		reviewHeader = (TextView) findViewById(R.id.reviewHeader);
		review_name = (TextView) findViewById(R.id.lblName);
		review_text = (TextView) findViewById(R.id.lblReview);
		review_date = (TextView) findViewById(R.id.lblTime);
		photoheader = (TextView) findViewById(R.id.photoHeader);
		lb1 = (TextView) findViewById(R.id.lblContact);
		lb1.setTag("phone");
	  
		lb2 = (TextView) findViewById(R.id.lblContact1);
		lb2.setTag("web");
		lb3 = (TextView) findViewById(R.id.lblContact2);
		lb3.setTag("google");
  
		OnClickListener listner = new OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				String tag = (String) v.getTag();

				if (tag.equals("direction")) {
					// launch map application
					Location currentLocaiton = AppController.getInstance()
							.getMlocation();
					String directionUrl = "http://maps.google.com/maps?saddr="
							+ currentLocaiton.getLatitude() + ","
							+ currentLocaiton.getLongitude() + "&daddr="
							+ placedetail.getLatitude() + ","
							+ placedetail.getLongitude();
					Intent intent = new Intent(
							android.content.Intent.ACTION_VIEW,
							Uri.parse(directionUrl));
					// Always use string resources for UI text.
					// This says something like "Share this photo with"
					String title = "Select";
					// Create intent to show chooser
					Intent.createChooser(intent, title);

					// Verify the intent will resolve to at least one activity
					if (intent.resolveActivity(getPackageManager()) != null) {
						startActivity(intent);
					}
				}  
				if (tag.equals("phone")) 
				{
						String phone = (String) lb1.getText();
						Uri ur = Uri.parse("tel:" + phone);
						Intent intent = new Intent(Intent.ACTION_DIAL);
						intent.setData(ur);
						startActivity(intent);
				}else
					if(tag.equals("google"))
						{
							Uri uri = Uri.parse(google_url);
			                startActivity(new Intent(Intent.ACTION_VIEW, uri));
						}
						else
							if(tag.equals("web"))
							{   
								Uri uri = Uri.parse(((String)lb3.getText()));
				                startActivity(new Intent(Intent.ACTION_VIEW, uri));
							}
			}
		};
		lb1.setOnClickListener(listner);
		lb2.setOnClickListener(listner);
		lb3.setOnClickListener(listner);
		
		// Click listener for Places Photos
		final OnClickListener placephotolistener = new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(getApplicationContext(),
						FullScreenViewActivity.class);
				intent.putStringArrayListExtra("URL_IMAGE", networkImageUrlList);

				int tag = Integer.parseInt(v.getTag().toString());
				intent.putExtra("position", tag);

				startActivity(intent);
			}
		};

		// review layout listener
		reviewLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						ReviewListActivity.class);

				intent.putExtra("review", mPlaceDetailModel.getReviewList());
				startActivity(intent);
			}
		});

		Intent intent = getIntent();
		Bundle extra = intent.getExtras();
		placedetail = (Placeresponse) extra.getSerializable("nearbyobj");
		try {
			new AsyncTask<String, Void, PlaceDetailModel>() {

				@Override
				protected PlaceDetailModel doInBackground(String... params) {

					GooglePlaces places = new GooglePlaces();
					PlaceDetailModel model = places.GetPlacesDetails(params[0]);
					return model;
				}

				@Override
				protected void onPostExecute(final PlaceDetailModel result) {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {

							if (result == null)
								return;
							mPlaceDetailModel = result;

							if (result.getPhone_number() != null
									&& !result.getPhone_number().equals(""))
								lb1.setText(result.getPhone_number());
							else
								lb1.setVisibility(View.GONE);

							if (result.getWebsite() != null
									&& !result.getWebsite().equals(""))
								lb2.setText(result.getWebsite());
							else
								lb2.setVisibility(View.GONE);

							if (result.getGoogleUrl() != null
									&& !result.getGoogleUrl().equals("")) {
								lb3.setText("http://plus.google.com");
								google_url = result.getGoogleUrl();
							} else
								lb3.setVisibility(View.GONE);
							ArrayList<PlacesPhoto> list = result.getPhotolist();
							int length = list.size();

							if (length > 0) {
								photolayout.setVisibility(View.VISIBLE);
								photoheader.setVisibility(View.VISIBLE);
							}

							// Set review
							List<Reviews> review = result.getReviewList();
							if (review != null && review.size() != 0) {
								// hide review header and layout
								reviewHeader.setVisibility(View.VISIBLE);
								reviewLayout.setVisibility(View.VISIBLE);
								Reviews rev = result.getReviewList().get(0);
								review_name.setText(rev.getName());
								review_text.setText(rev.getReview());
								review_date.setText(Util.createDate(rev
										.getTimespan()));
							}

							networkImageUrlList = new ArrayList<String>();
							for (int count = 0; count < length; count++) {
								String photourl = GooglePlaces.PHOTO_URL
										.replaceAll(
												"photoreference=",
												"photoreference=+"
														+ list.get(0)
																.getPhoto_reference());
								NetworkImageView imageView = getImageObject(photourl);
								imageView
										.setOnClickListener(placephotolistener);
								imageView.setTag(count);
								photolayout.addView(imageView);

								networkImageUrlList.add(photourl);
							}
						}
					});
				}
			}.execute(placedetail.getReferences());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ImageView imgView = (ImageView) findViewById(R.id.imgDirection);
		imgView.setOnClickListener(listner);

		try {
			// Loading map
			initilizeMap();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// get network image view
	public NetworkImageView getImageObject(String url) {
		NetworkImageView img = new NetworkImageView(this);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				100, 100);
		img.setLayoutParams(layoutParams);
		int paddingvalue = Util.getDpValue(this, 2);
		img.setPadding(paddingvalue, paddingvalue, paddingvalue, paddingvalue);
		img.setImageUrl(url, mImageLoader);
		img.setScaleType(ScaleType.CENTER_CROP);
		img.setBackgroundColor(Color.parseColor("#BDBDBD"));
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				Util.getDpValue(this, 50), Util.getDpValue(this, 50));
		int dpmargin = Util.getDpValue(this, 10);
		lp.setMargins(dpmargin, dpmargin, dpmargin, dpmargin);
		img.setLayoutParams(lp);
		return img;
	}

	/**
	 * function to load map. If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		if (googleMap == null) {

			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.mapview)).getMap();

			// add pin to map
			// latitude and longitude
			double latitude = placedetail.getLatitude();
			double longitude = placedetail.getLongitude();

			// create marker
			MarkerOptions marker = new MarkerOptions().position(
					new LatLng(latitude, longitude)).title("Hello Maps ");

			// adding marker
			googleMap.addMarker(marker);
			googleMap.setMyLocationEnabled(false);

			lat = new LatLng(latitude, longitude);
			googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lat, 15));
			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

}
