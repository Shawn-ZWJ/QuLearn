package com.viaviapp.hdvideo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.adapter.CategoryItemGridAdapter;
import com.example.item.ItemLatest;
import com.example.util.AlertDialogManager;
import com.example.util.Constant;
import com.example.util.JsonUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CategoryItem extends ActionBarActivity {

	ListView lsv_cat;
	List<ItemLatest> arrayOfLatestVideo;
	CategoryItemGridAdapter objAdapter;
	AlertDialogManager alert = new AlertDialogManager();
	ArrayList<String> allListVideo,allListVideoCatName;
	ArrayList<String> allListVideoId,allListVideoCatId,allListVideoUrl,allListVideoName,allListVideoDuration,allListVideoDesc,allListImageUrl;
	String[] allArrayVideo,allArrayVideoCatName;
	String[] allArrayVideoId,allArrayVideoCatId,allArrayVideourl,allArrayVideoName,allArrayVideoDuration,allArrayVideoDesc,allArrayImageUrl;
	private ItemLatest objAllBean;
	private int columnWidth;
	JsonUtils util;
	int textlength = 0;
	private AdView mAdView;
	Toolbar toolbar;
	ProgressBar pbar;
	private Button btnLoadMore;
	int page=1;
	int TOTAL_LIST_ITEMS;
	public int NUM_ITEMS_PAGE;
	private int noOfBtns;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
 		setContentView(R.layout.category_item_grid);
		toolbar = (Toolbar) this.findViewById(R.id.toolbar);
		toolbar.setTitle(Constant.CATEGORY_TITLE);
		this.setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
		}
 		mAdView = (AdView) findViewById(R.id.adView);
		mAdView.loadAd(new AdRequest.Builder().build());
 	 
		lsv_cat=(ListView)findViewById(R.id.lsv_cat_item);
		pbar=(ProgressBar)findViewById(R.id.progressBar1);

		arrayOfLatestVideo=new ArrayList<ItemLatest>();

		btnLoadMore = new Button(CategoryItem.this);
		btnLoadMore.setText(getString(R.string.loadmore));
		btnLoadMore.setBackgroundResource(R.drawable.loadmore_border);
		btnLoadMore.setTextColor(CategoryItem.this.getResources().getColor(R.color.white));
		NUM_ITEMS_PAGE=Integer.parseInt(getString(R.string.numofitem));
		lsv_cat.addFooterView(btnLoadMore);

		allListVideo=new ArrayList<String>();
		allListVideoCatName=new ArrayList<String>();
		allListVideoCatId=new ArrayList<String>();
		allListVideoId=new ArrayList<String>();
		allListVideoName=new ArrayList<String>();
		allListVideoUrl=new ArrayList<String>();
		allListVideoDuration=new ArrayList<String>();
		allListVideoDesc=new ArrayList<String>();
		allListImageUrl=new ArrayList<String>();

		allArrayVideo=new String[allListVideo.size()];
		allArrayVideoCatName=new String[allListVideoCatName.size()];
		allArrayVideoId=new String[allListVideoId.size()];
		allArrayVideoCatId=new String[allListVideoCatId.size()];
		allArrayVideourl=new String[allListVideoUrl.size()];
		allArrayVideoName=new String[allListVideoName.size()];
		allArrayVideoDuration=new String[allListVideoDuration.size()];
		allArrayVideoDesc=new String[allListVideoDesc.size()];
		allArrayImageUrl=new String[allListImageUrl.size()];

		util=new JsonUtils(getApplicationContext());


		if (JsonUtils.isNetworkAvailable(CategoryItem.this)) {
			new MyTask().execute(Constant.CATEGORY_ITEM_URL+Constant.CATEGORY_ID+"&page="+page);
		} else {
			showToast("No Network Connection!!!");

		}

		lsv_cat.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub

				objAllBean=arrayOfLatestVideo.get(position);
				Constant.LATEST_IDD=objAllBean.getId();
				Intent intplay=new Intent(getApplicationContext(),DetailActivity.class);
 				startActivity(intplay);
			}
		});

 		btnLoadMore.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(page >= noOfBtns)
				{
					showToast("No More Data");
					btnLoadMore.setVisibility(Button.GONE);
				}
				else
				{
					page=page+1;

					new MyTask().execute(Constant.CATEGORY_ITEM_URL+Constant.CATEGORY_ID+"&page="+page);

				}
			}
		});

	}



	private	class MyTask extends AsyncTask<String, Void, String> {

 
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pbar.setVisibility(View.VISIBLE);
			lsv_cat.setVisibility(View.GONE);
		}

		@Override
		protected String doInBackground(String... params) {
			return JsonUtils.getJSONString(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			pbar.setVisibility(View.INVISIBLE);
			lsv_cat.setVisibility(View.VISIBLE);


			if (null == result || result.length() == 0) {
				showToast("No data found from web!!!");

			} else {

				try {
					JSONObject mainJson = new JSONObject(result);
					JSONArray jsonArray = mainJson.getJSONArray(Constant.CATEGORY_ITEM_ARRAY_NAME);
					JSONObject objJson = null;
					for (int i = 0; i < jsonArray.length(); i++) {
						objJson = jsonArray.getJSONObject(i);

						ItemLatest objItem = new ItemLatest();

						objItem.setId(objJson.getString(Constant.LATEST_ID));
						objItem.setCategoryId(objJson.getString(Constant.LATEST_CATID));
						objItem.setCategoryName(objJson.getString(Constant.LATEST_CAT_NAME));
						objItem.setVideoUrl(objJson.getString(Constant.LATEST_VIDEO_URL));
						objItem.setVideoId(objJson.getString(Constant.LATEST_VIDEO_ID));
						objItem.setVideoName(objJson.getString(Constant.LATEST_VIDEO_NAME));
						objItem.setDuration(objJson.getString(Constant.LATEST_VIDEO_DURATION));
						objItem.setDescription(objJson.getString(Constant.LATEST_VIDEO_DESCRIPTION));
						objItem.setImageUrl(objJson.getString(Constant.LATEST_IMAGE_URL));
						objItem.setType(objJson.getString(Constant.LATEST_TYPE));
						objItem.setViewC(objJson.getString(Constant.LATEST_VIEW));
						TOTAL_LIST_ITEMS=Integer.parseInt(objJson.getString("num"));
						arrayOfLatestVideo.add(objItem);


					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
				for(int j=0;j<arrayOfLatestVideo.size();j++)
				{

					objAllBean=arrayOfLatestVideo.get(j);

					allListVideo.add(objAllBean.getVideoId());
					allArrayVideo=allListVideo.toArray(allArrayVideo);

					allListVideoCatName.add(objAllBean.getCategoryName());
					allArrayVideoCatName=allListVideoCatName.toArray(allArrayVideoCatName);

					allListVideoId.add(String.valueOf(objAllBean.getId()));
					allArrayVideoId=allListVideoId.toArray(allArrayVideoId);

					allListVideoCatId.add(String.valueOf(objAllBean.getCategoryId()));
					allArrayVideoCatId=allListVideoCatId.toArray(allArrayVideoCatId);


					allListVideoUrl.add(String.valueOf(objAllBean.getVideoUrl()));
					allArrayVideourl=allListVideoUrl.toArray(allArrayVideourl);

					allListVideoName.add(String.valueOf(objAllBean.getVideoName()));
					allArrayVideoName=allListVideoName.toArray(allArrayVideoName);

					allListVideoDuration.add(String.valueOf(objAllBean.getDuration()));
					allArrayVideoDuration=allListVideoDuration.toArray(allArrayVideoDuration);

					allListVideoDesc.add(objAllBean.getDescription());
					allArrayVideoDesc=allListVideoDesc.toArray(allArrayVideoDesc);

					allListImageUrl.add(objAllBean.getImageUrl());
					allArrayImageUrl=allListImageUrl.toArray(allArrayImageUrl);
				}
				int val = TOTAL_LIST_ITEMS%NUM_ITEMS_PAGE;
				val = val==0?0:1;
				noOfBtns=TOTAL_LIST_ITEMS/NUM_ITEMS_PAGE+val;
				setAdapterToListview();
			}

		}
	}



	public void setAdapterToListview() {
		objAdapter = new CategoryItemGridAdapter(CategoryItem.this, R.layout.latest_lsv_item,
				arrayOfLatestVideo,columnWidth);
		lsv_cat.setAdapter(objAdapter);
		if(page==1)
		{
			lsv_cat.setSelection(0);
		}
		else
		{
			int v = lsv_cat.getCount();
			lsv_cat.setSelection(v-NUM_ITEMS_PAGE);

		}

		if(page >= noOfBtns)
		{
			btnLoadMore.setVisibility(View.GONE);
		}
	}

	public void showToast(String msg) {
		Toast.makeText(CategoryItem.this, msg, Toast.LENGTH_LONG).show();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_search, menu);


		final SearchView searchView = (SearchView) menu.findItem(R.id.search)
				.getActionView();

		final MenuItem searchMenuItem = menu.findItem(R.id.search);
		searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!hasFocus) {
					searchMenuItem.collapseActionView();
					searchView.setQuery("", false);
				}
			}
		});

		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextChange(String newText) {

				String text = newText.toString().toLowerCase(Locale.getDefault());
				if(objAdapter!=null)
				{
					objAdapter.filter(text);
				} 
 
				
				return false;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				// Do something
				return true;
			}
		});


		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem)
	{       
		switch (menuItem.getItemId()) 
		{
		case android.R.id.home: 
			onBackPressed();
			break;

		default:
			return super.onOptionsItemSelected(menuItem);
		}
		return true;
	}


}
