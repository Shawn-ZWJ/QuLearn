package com.example.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.item.ItemAllVideos;
 import com.squareup.picasso.Picasso;
import com.viaviapp.hdvideo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllVideosListAdapter extends ArrayAdapter<ItemAllVideos> {
	
	private Activity activity;
	private List<ItemAllVideos> itemsAllphotos;
	private ItemAllVideos objAllBean;
	private ArrayList<ItemAllVideos> arraycat;
	private int row;

	
	public AllVideosListAdapter(Activity act, int resource, List<ItemAllVideos> arrayList) {
		super(act, resource, arrayList);
		this.activity = act;
		this.row = resource;
		this.itemsAllphotos = arrayList;
  		this.arraycat = new ArrayList<ItemAllVideos>();
		this.arraycat.addAll(itemsAllphotos);
	 
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(row, null);

			holder = new ViewHolder();
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		if ((itemsAllphotos == null) || ((position + 1) > itemsAllphotos.size()))
			return view;

		objAllBean = itemsAllphotos.get(position);
		
		holder.txt=(TextView)view.findViewById(R.id.txt_allvideos_categty);
		holder.img_cat=(CircleImageView)view.findViewById(R.id.img_cat);
		holder.txt.setText(objAllBean.getCategoryName().toString());

 		Picasso.with(activity).load(objAllBean.getCategoryImageurl().toString()).into(holder.img_cat);

		return view;
		
	}

	public class ViewHolder {
	 
		public TextView txt;
		public CircleImageView img_cat;
	}

	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		itemsAllphotos.clear();
		if (charText.length() == 0) {
			itemsAllphotos.addAll(arraycat);
		} 
		else 
		{
			for (ItemAllVideos wp : arraycat) 
			{
				if (wp.getCategoryName().toLowerCase(Locale.getDefault()).contains(charText)) 
				{
					itemsAllphotos.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}
}