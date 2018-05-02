package com.example.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.favorite.Pojo;
import com.example.item.ItemLatest;
import com.example.util.Constant;
import com.squareup.picasso.Picasso;
import com.viaviapp.hdvideo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FavGridAdapter extends ArrayAdapter<Pojo>{

 	private Activity activity;
	private List<Pojo> itemsLatest;
	private Pojo objLatestBean;
	private int row;
	private ArrayList<Pojo> arraycat;

	public FavGridAdapter(Activity act, int resource, List<Pojo> arrayList, int columnWidth) {
		super(act, resource, arrayList);
		this.activity = act;
		this.row = resource;
		this.itemsLatest = arrayList;
		this.arraycat = new ArrayList<Pojo>();
		this.arraycat.addAll(itemsLatest);

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

		if ((itemsLatest == null) || ((position + 1) > itemsLatest.size()))
			return view;

		objLatestBean = itemsLatest.get(position);


		holder.imgv_latetst=(ImageView)view.findViewById(R.id.image);
		holder.name=(TextView)view.findViewById(R.id.text_title);
		holder.txt_time=(TextView)view.findViewById(R.id.text_time);
		holder.txt_category=(TextView)view.findViewById(R.id.text_cat);
		holder.txt_view=(TextView)view.findViewById(R.id.text_view);

		if(objLatestBean.getVtype().equals("local"))
		{
			Picasso.with(activity).load(objLatestBean.getImageUrl().toString()).into(holder.imgv_latetst);
		}
		else if (objLatestBean.getVtype().equals("server_url")) {
			Picasso.with(activity).load(objLatestBean.getImageUrl()).into(holder.imgv_latetst);
		}
		else if (objLatestBean.getVtype().equals("youtube"))
		{
			Picasso.with(activity).load(Constant.YOUTUBE_IMAGE_FRONT+objLatestBean.getVideoId().toString()+Constant.YOUTUBE_IMAGE_BACK).into(holder.imgv_latetst);
		}

		holder.name.setText(objLatestBean.getVideoName().toString());
		holder.txt_time.setText(objLatestBean.getDuration().toString());
		holder.txt_category.setText(objLatestBean.getCategoryName().toString());
		holder.txt_view.setText(objLatestBean.getVview().toString());

		return view;

	}

	public class ViewHolder {

		public ImageView imgv_latetst;
		public  TextView name,txt_time,txt_category,txt_view;

	}

	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		itemsLatest.clear();
		if (charText.length() == 0) {
			itemsLatest.addAll(arraycat);
		}
		else
		{
			for (Pojo wp : arraycat)
			{
				if (wp.getVideoName().toLowerCase(Locale.getDefault()).contains(charText))
				{
					itemsLatest.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}
}