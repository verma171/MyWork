package com.ui.control;

import java.lang.reflect.TypeVariable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Label1 extends TextView {
    	
	public Label1(Context context)
	{
		super(context);
		//InitUI();
	}
	protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
        
       
    }
	public void InitUI()
   {  
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		Resources r = this.getResources();
		int left_margin = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics());
		int top_margin = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 13, r.getDisplayMetrics());

		params.setMargins(left_margin, top_margin, 0, 0);
		setLayoutParams(params);
		setTextColor(Color.rgb(0, 0, 0));
		setVisibility(2);
		setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
	}
}
