package com.example.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.favorite.DatabaseHandler;
import com.example.favorite.Pojo;
import com.example.item.ItemLatest;
import com.example.util.Constant;
import com.squareup.picasso.Picasso;
import com.viaviapp.hdvideo.R;

public class LatestGridAdapter extends ArrayAdapter<ItemLatest>{

 	private Activity activity;
	private List<ItemLatest> itemsLatest;
	private ItemLatest objLatestBean;
	private int row;
	private ArrayList<ItemLatest> arraycat;
	public DatabaseHandler db;

	public LatestGridAdapter(Activity act, int resource, List<ItemLatest> arrayList, int columnWidth) {
		super(act, resource, arrayList);
		this.activity = act;
		this.row = resource;
		this.itemsLatest = arrayList;
		this.arraycat = new ArrayList<ItemLatest>();
		this.arraycat.addAll(itemsLatest);
		db = new DatabaseHandler(activity);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final ViewHolder holder;
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
		holder.img_fav=(ImageView)view.findViewById(R.id.image_fav);

		if(objLatestBean.getType().equals("local"))
		{
			Picasso.with(activity).load(objLatestBean.getImageUrl().toString()).into(holder.imgv_latetst);
		}
		else if (objLatestBean.getType().equals("server_url")) {
			Picasso.with(activity).load(objLatestBean.getImageUrl()).into(holder.imgv_latetst);
		}
		else if (objLatestBean.getType().equals("youtube"))
		{
			Picasso.with(activity).load(Constant.YOUTUBE_IMAGE_FRONT+objLatestBean.getVideoId().toString()+Constant.YOUTUBE_IMAGE_BACK).placeholder(R.drawable.placeholder_big).into(holder.imgv_latetst);
 		}

		holder.name.setText(objLatestBean.getVideoName().toString());
		holder.txt_time.setText(objLatestBean.getDuration().toString());
		holder.txt_category.setText(objLatestBean.getCategoryName().toString());
		holder.txt_view.setText(objLatestBean.getViewC().toString());

		List<Pojo> pojolist=db.getFavRow(Constant.LATEST_IDD);
		if(pojolist.size()==0)
		{
			holder.img_fav.setImageResource(R.drawable.fav);
		}
		else
		{
			if(pojolist.get(0).getVideoId().equals(Constant.LATEST_IDD))
			{
				holder.img_fav.setImageResource(R.drawable.fav_hover);
			}
		}
		holder.img_fav.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				List<Pojo> pojolist=db.getFavRow(Constant.LATEST_IDD);
				if(pojolist.size()==0)
				{
					db.AddtoFavorite(new Pojo(Constant.LATEST_IDD,objLatestBean.getVideoName(),objLatestBean.getImageUrl(), objLatestBean.getDuration(),objLatestBean.getCategoryName(),objLatestBean.getType(),objLatestBean.getViewC(),objLatestBean.getVideoId()));
					Toast.makeText(activity, "Added to Bookmark", Toast.LENGTH_SHORT).show();
					holder.img_fav.setImageResource(R.drawable.fav_hover);
				}
				else
				{
					if(pojolist.get(0).getVId().equals(Constant.LATEST_IDD)) {
						db.RemoveFav(new Pojo(Constant.LATEST_IDD,objLatestBean.getVideoName(),objLatestBean.getImageUrl(), objLatestBean.getDuration(),objLatestBean.getCategoryName(),objLatestBean.getType(),objLatestBean.getViewC(),objLatestBean.getVideoId()));
						Toast.makeText(activity, "Removed from Bookmark", Toast.LENGTH_SHORT).show();
						holder.img_fav.setImageResource(R.drawable.fav);
					}
				}

			}
		});

		return view;

	}


	public class ViewHolder {

		public ImageView imgv_latetst,img_fav;
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
			for (ItemLatest wp : arraycat)
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