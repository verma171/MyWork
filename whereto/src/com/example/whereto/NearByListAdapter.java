package com.example.whereto;

import java.util.List;
import java.util.StringTokenizer;

import com.models.Placeresponse;
import com.models.Places;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NearByListAdapter extends ArrayAdapter<Placeresponse> {
	Context context;
	List<Placeresponse> list;
	public NearByListAdapter(Context context, int textViewResourceId,
			List<Placeresponse> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		list = objects;
	}

	
	

	@Override
	public int getPosition(Placeresponse item) {
		// TODO Auto-generated method stub
		return super.getPosition(item);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		 LayoutInflater inflater = (LayoutInflater) context
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 View rowView = inflater.inflate(R.layout.nearbylistitem, parent, false);
		    TextView textView = (TextView) rowView.findViewById(R.id.lblListItem);
		    textView.setText(list.get(position).getName());
		    TextView textView1 = (TextView) rowView.findViewById(R.id.lblListAddress);
		     textView1.setText(list.get(position).getVicinity());
		    return rowView;
	}
}
