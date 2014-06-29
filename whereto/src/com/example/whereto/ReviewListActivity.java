package com.example.whereto;

import java.nio.BufferUnderflowException;
import java.util.List;

import com.imageslider.adapter.ReviewLoaderAdapter;
import com.models.Reviews;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ListView;

public class ReviewListActivity extends Activity {
    ListView listview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_review_list);
		listview = (ListView)findViewById(R.id.listreviewList);
		
		Intent i  = getIntent();
	    Bundle bundle =	i.getExtras();
	    @SuppressWarnings("unchecked")
		List<Reviews> review = (List<Reviews>)bundle.getSerializable("review");
	    ReviewLoaderAdapter adap = new ReviewLoaderAdapter(ReviewListActivity.this,review);
	    listview.setAdapter(adap);
	  
	}
}
