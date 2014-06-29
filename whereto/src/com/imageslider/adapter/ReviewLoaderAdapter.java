
package com.imageslider.adapter;
import java.util.List;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.whereto.R;
import com.models.Reviews;
import com.utils.Util;
public class ReviewLoaderAdapter extends ArrayAdapter<Reviews>{
	Context context;
	List<Reviews> list;

	public ReviewLoaderAdapter(Context context,
			List<Reviews> objects) {
		super(context, R.layout.review_item, objects);
		this.context = context;
		this.list = objects;
	}

	@Override
	public int getPosition(Reviews item) {
		// TODO Auto-generated method stub
		return super.getPosition(item);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{  
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = null;
		try {
			rowView = inflater.inflate(R.layout.review_item, parent, false);
		} catch (Exception er) {
			er.printStackTrace();
		}

		TextView textView = (TextView) rowView.findViewById(R.id.lblName1);
		textView.setText(list.get(position).getName());
		TextView textView1 = (TextView) rowView.findViewById(R.id.lblReview1);
		textView1.setText(list.get(position).getReview());
		TextView textView2 = (TextView) rowView.findViewById(R.id.lblTime1);
		textView2.setText(Util.createDate(list.get(position).getTimespan()));
		ImageView imgView = (ImageView)rowView.findViewById(R.id.imageViewGold);
	    Integer width = imgView.getWidth();
	    imgView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
	    int widht = imgView.getMeasuredWidth();
	   
	    Integer intg = Integer.valueOf(widht);
	    Log.d("Image widht--->",intg.toString());
	    Integer ratingwidth = intg / 5;
	    imgView.getLayoutParams().width = (int)(ratingwidth * list.get(position).getRating());
		return rowView;
	}
}
