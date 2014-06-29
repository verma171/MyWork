package com.example.whereto;

import java.util.ArrayList;

import com.imageslider.adapter.FullScreenImageAdapter;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class FullScreenViewActivity extends Activity {

	private FullScreenImageAdapter adapter;
	private ViewPager viewPager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen_view);
		 // Get the Bundle Object        
	    Intent intent = getIntent();
	    int position = intent.getIntExtra("position", 0);
	    ArrayList<String> urlList = intent.getStringArrayListExtra("URL_IMAGE");
	        // Get ArrayList Bundle
		adapter = new FullScreenImageAdapter(FullScreenViewActivity.this,
				urlList);
        ViewPager viewPager =(ViewPager)findViewById(R.id.pager);
		viewPager.setAdapter(adapter);

		// displaying selected image first
		viewPager.setCurrentItem(position);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_full_screen_view, menu);
		return true;
	}

}
