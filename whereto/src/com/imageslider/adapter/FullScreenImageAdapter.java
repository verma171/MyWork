package com.imageslider.adapter;

import com.android.volley.toolbox.NetworkImageView;
import  com.example.whereto.R;
import com.models.PlacesPhoto;
import com.volley.AppController;

import java.lang.reflect.Array;
import java.util.ArrayList;
 
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
 
public class FullScreenImageAdapter extends PagerAdapter {
 
    private Activity _activity;
    private ArrayList<String> _imagePaths;
    private LayoutInflater inflater;
    private ArrayList<String> urlList;
    // constructor
    public FullScreenImageAdapter(Activity activity,
            ArrayList<String> photoList) {
        this._activity = activity;
        this.urlList = photoList;
    }
 
    @Override
    public int getCount() {
        return this.urlList.size();
    }
 
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }
     
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imgDisplay;
        Button btnClose;
  
        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
       
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container,
                false);
  
        imgDisplay = (ImageView) viewLayout.findViewById(R.id.imgDisplay);
        btnClose = (Button) viewLayout.findViewById(R.id.btnClose);
        
        NetworkImageView imgview = (NetworkImageView)imgDisplay;
        imgview.setImageUrl(urlList.get(position).toString(), AppController.getInstance().getImageLoader());
        
       /* BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(_imagePaths.get(position), options);
        imgDisplay.setImageBitmap(bitmap);
        */
        // close button click event
        btnClose.setOnClickListener(new View.OnClickListener() {            
            @Override
            public void onClick(View v) {
                _activity.finish();
            }
        });
  
        ((ViewPager) container).addView(viewLayout);
  
        return viewLayout;
    }
     
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);
  
    }
}