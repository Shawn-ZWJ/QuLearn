package com.example.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.item.ItemLatest;
import com.example.util.Constant;
import com.squareup.picasso.Picasso;
import com.viaviapp.hdvideo.R;

public class CategoryItemGridAdapter extends ArrayAdapter<ItemLatest>{

	private Activity activity;
	private List<ItemLatest> itemsCategory;
	private ItemLatest objCategoryBean;
	private int row;
	private ArrayList<ItemLatest> arraycat;

	public CategoryItemGridAdapter(Activity act, int resource, List<ItemLatest> arrayList, int columnWidth) {
		super(act, resource, arrayList);
		this.activity = act;
		this.row = resource;
		this.itemsCategory = arrayList;
		this.arraycat = new ArrayList<ItemLatest>();
		this.arraycat.addAll(itemsCategory);

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

		if ((itemsCategory == null) || ((position + 1) > itemsCategory.size()))
			return view;

		objCategoryBean = itemsCategory.get(position);


		holder.imgv_latetst=(ImageView)view.findViewById(R.id.image);
		holder.name=(TextView)view.findViewById(R.id.text_title);
		holder.txt_time=(TextView)view.findViewById(R.id.text_time);
		holder.txt_category=(TextView)view.findViewById(R.id.text_cat);
		holder.txt_view=(TextView)view.findViewById(R.id.text_view);


		if(objCategoryBean.getType().equals("local"))
		{
			Picasso.with(activity).load(objCategoryBean.getImageUrl().toString()).into(holder.imgv_latetst);
		}
		else if (objCategoryBean.getType().equals("server_url")) {
			Picasso.with(activity).load(objCategoryBean.getImageUrl()).into(holder.imgv_latetst);
		}
		else if (objCategoryBean.getType().equals("youtube"))
		{
			Picasso.with(activity).load(Constant.YOUTUBE_IMAGE_FRONT+objCategoryBean.getVideoId().toString()+Constant.YOUTUBE_IMAGE_BACK).into(holder.imgv_latetst);
		}

		holder.name.setText(objCategoryBean.getVideoName().toString());
		holder.txt_time.setText(objCategoryBean.getDuration().toString());
		holder.txt_category.setText(objCategoryBean.getCategoryName().toString());
		holder.txt_view.setText(objCategoryBean.getViewC().toString());

		return view;

	}

	public class ViewHolder {

		public ImageView imgv_latetst;
		public  TextView name,txt_time,txt_category,txt_view;

	}
	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		itemsCategory.clear();
		if (charText.length() == 0) {
			itemsCategory.addAll(arraycat);
		}
		else
		{
			for (ItemLatest wp : arraycat)
			{
				if (wp.getVideoName().toLowerCase(Locale.getDefault()).contains(charText))
				{
					itemsCategory.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}
}