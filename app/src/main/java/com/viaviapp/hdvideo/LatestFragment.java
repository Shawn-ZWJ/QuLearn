package com.viaviapp.hdvideo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.adapter.LatestGridAdapter;
import com.example.item.ItemLatest;
import com.example.util.AlertDialogManager;
import com.example.util.Constant;
import com.example.util.JsonUtils;

public class LatestFragment extends Fragment {



	ListView lsv_latest;
	List<ItemLatest> arrayOfLatestVideo;
	LatestGridAdapter objAdapter;
	AlertDialogManager alert = new AlertDialogManager();
	ArrayList<String> allListVideo,allListVideoCatName;
	ArrayList<String> allListVideoId,allListVideoCatId,allListVideoUrl,allListVideoName,allListVideoDuration,allListVideoDesc,allListImageUrl;
	String[] allArrayVideo,allArrayVideoCatName;
	String[] allArrayVideoId,allArrayVideoCatId,allArrayVideourl,allArrayVideoName,allArrayVideoDuration,allArrayVideoDesc,allArrayImageUrl;
	private ItemLatest objAllBean;
	private int columnWidth;
	JsonUtils util;
	int textlength = 0;
	ProgressBar pbar;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View rootView = inflater.inflate(R.layout.fragment_latest, container, false);
 		setHasOptionsMenu(true);

		lsv_latest=(ListView)rootView.findViewById(R.id.lsv_latest);
		pbar=(ProgressBar)rootView.findViewById(R.id.progressBar1);
		
		arrayOfLatestVideo=new ArrayList<ItemLatest>();
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

		util=new JsonUtils(getActivity());


		if (JsonUtils.isNetworkAvailable(getActivity())) {
			new MyTask().execute(Constant.LATEST_URL);
		} else {
			showToast("No Network Connection!!!");
			alert.showAlertDialog(getActivity(), "Internet Connection Error",
					"Please connect to working Internet connection", false);
		}


		lsv_latest.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				objAllBean=arrayOfLatestVideo.get(position);
				Constant.LATEST_IDD=objAllBean.getId();
				PopUpAds.ShowInterstitialAds(getActivity());
				Intent intplay=new Intent(getActivity(),DetailActivity.class);
 				startActivity(intplay);



			}
		});

		return rootView;
	}




	private	class MyTask extends AsyncTask<String, Void, String> {

 
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pbar.setVisibility(View.VISIBLE);
			lsv_latest.setVisibility(View.GONE);
		}

		@Override
		protected String doInBackground(String... params) {
			return JsonUtils.getJSONString(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			pbar.setVisibility(View.INVISIBLE);
			lsv_latest.setVisibility(View.VISIBLE);


			if (null == result || result.length() == 0) {
				showToast("No data found from web!!!");


			} else {

				try {
					JSONObject mainJson = new JSONObject(result);
					JSONArray jsonArray = mainJson.getJSONArray(Constant.LATEST_ARRAY_NAME);
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

				setAdapterToListview();
			}

		}
	}



	public void setAdapterToListview() {
		objAdapter = new LatestGridAdapter(getActivity(), R.layout.latest_lsv_item,
				arrayOfLatestVideo,columnWidth);
		lsv_latest.setAdapter(objAdapter);
	}

	public void showToast(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
	}

	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
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

	}

}
