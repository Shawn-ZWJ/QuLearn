package com.viaviapp.hdvideo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.item.ItemAbout;
import com.example.util.Constant;
import com.example.util.JsonUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements View.OnClickListener  {

	private DrawerLayout mDrawerLayout;
	private FragmentManager fragmentManager;
	NavigationView navigationView;
	private AdView mAdView;
 	Toolbar toolbar;
	LinearLayout lay_dev;
	ArrayList<ItemAbout> mListItem;
 	TextView txt_hader,textView_latest,textView_cat,textView_fav,textView_home,textView_share,textView_rate,textView_more,textView_about,textView_privacy, textView_developedby;
	LinearLayout ll_latest,ll_cat,ll_fav,ll_home;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
 		setContentView(R.layout.activity_main);
		toolbar = (Toolbar) this.findViewById(R.id.toolbar);
		toolbar.setTitle(getString(R.string.app_name));
		this.setSupportActionBar(toolbar);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
		}
		mAdView = (AdView) findViewById(R.id.adView);
		mAdView.loadAd(new AdRequest.Builder().build());

 		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		navigationView = (NavigationView) findViewById(R.id.nav_view);



		mListItem = new ArrayList<>();
		if (JsonUtils.isNetworkAvailable(MainActivity.this)) {
			new MyTaskDev().execute(Constant.ABOUT_US_URL);
		}else {
			showToast(getString(R.string.network_msg));
		}
		ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}
		};
		mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
		actionBarDrawerToggle.syncState();



		fragmentManager = getSupportFragmentManager();


		textView_latest = (TextView)findViewById(R.id.textView_latest);
		textView_cat = (TextView)findViewById(R.id.textView_cat);
		textView_fav= (TextView)findViewById(R.id.textView_fav);
		textView_rate = (TextView)findViewById(R.id.textView_rate);
		textView_more = (TextView)findViewById(R.id.textView_more);
		textView_about = (TextView)findViewById(R.id.textView_ab);
		textView_privacy = (TextView)findViewById(R.id.textView_priv);
		textView_home = (TextView)findViewById(R.id.textView_home);
		textView_share = (TextView)findViewById(R.id.textView_share);
		textView_developedby = (TextView)findViewById(R.id.textView_developedby);
		txt_hader=(TextView)findViewById(R.id.title_head);


		ll_latest = (LinearLayout)findViewById(R.id.ll_latest);
		ll_cat = (LinearLayout)findViewById(R.id.ll_cat);
		ll_fav = (LinearLayout)findViewById(R.id.ll_fav);
		ll_home = (LinearLayout)findViewById(R.id.ll_home);

		textView_latest.setOnClickListener(this);
		textView_cat.setOnClickListener(this);
		textView_fav.setOnClickListener(this);
		textView_rate.setOnClickListener(this);
		textView_more.setOnClickListener(this);
		textView_about.setOnClickListener(this);
		textView_privacy.setOnClickListener(this);
		textView_share.setOnClickListener(this);
		textView_home.setOnClickListener(this);

		Typeface tf = Typeface.createFromAsset(this.getAssets(),"Roboto-Regular.ttf");
		txt_hader.setTypeface(tf);
		//******初始显示************************************************************************************************************
		/*HomeFragment f1 = new HomeFragment();
		loadFrag(f1,getString(R.string.menu_home),fragmentManager);
		toolbar.setTitle(getString(R.string.menu_home));*/

		LatestFragment f1 = new LatestFragment();
		loadFrag(f1,getString(R.string.menu_latest),fragmentManager);
		toolbar.setTitle(getString(R.string.menu_latest));
	}

	public void loadFrag(Fragment f1, String name, FragmentManager fm) {
		FragmentTransaction ft = fm.beginTransaction();
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.replace(R.id.fragment1, f1, name);
		ft.commit();
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				mDrawerLayout.openDrawer(GravityCompat.START);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();

		if (id == R.id.textView_home) {
/*			HomeFragment f1 = new HomeFragment();
			loadFrag(f1,getString(R.string.menu_home),fragmentManager);
			toolbar.setTitle(getString(R.string.menu_home));

			changeNavItemBG(getString(R.string.menu_home));*/

		} else if (id == R.id.textView_latest) {
			LatestFragment f1 = new LatestFragment();
			loadFrag(f1,getString(R.string.menu_latest),fragmentManager);
			toolbar.setTitle(getString(R.string.menu_latest));

			changeNavItemBG(getString(R.string.menu_latest));

		} else if (id == R.id.textView_cat) {
			AllVideosFragment f1 = new AllVideosFragment();
			loadFrag(f1,getString(R.string.menu_category),fragmentManager);
			toolbar.setTitle(getString(R.string.menu_category));

			changeNavItemBG(getString(R.string.menu_category));

		} else if(id == R.id.textView_fav) {
			FavoriteFragment f1 = new FavoriteFragment();
			loadFrag(f1,getString(R.string.menu_favorite),fragmentManager);
			toolbar.setTitle(getString(R.string.menu_favorite));

			changeNavItemBG(getString(R.string.menu_favorite));

		}
		else if(id == R.id.textView_ab) {
			Intent intent = new Intent(MainActivity.this,AboutUsActivity.class);
			startActivity(intent);
		}
		else if(id == R.id.textView_share) {
		/*	Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, "Download this app from this link"+"https://play.google.com/store/apps/details?id="+getPackageName());
			sendIntent.setType("text/plain");
			startActivity(sendIntent);*/

		} else if(id == R.id.textView_rate) {
/*			final String appName = getPackageName();//your application package name i.e play store application url
			Log.e("package:", appName);
			try {
				startActivity(new Intent(Intent.ACTION_VIEW,
						Uri.parse("market://details?id="
								+ appName)));
			} catch (android.content.ActivityNotFoundException anfe) {
				startActivity(new Intent(
						Intent.ACTION_VIEW,
						Uri.parse("http://play.google.com/store/apps/details?id="
								+ appName)));
			}*/
		}
		else if(id == R.id.textView_more) {
			startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(getString(R.string.play_more_apps))));

		} else if(id == R.id.textView_priv) {
		/*	Intent intentpr = new Intent(MainActivity.this,Privacy_Activity.class);
			startActivity(intentpr);*/
		}
		mDrawerLayout.closeDrawer(GravityCompat.START);

	}
	public void changeNavItemBG(String abc) {
		if(abc.equals(getString(R.string.menu_home))) {
			ll_home.setBackground(getResources().getDrawable(R.drawable.bg_nav_text_white));
			ll_cat.setBackground(null);
			ll_fav.setBackground(null);
			ll_latest.setBackground(null);
		} else if(abc.equals(getString(R.string.menu_latest))) {
			ll_latest.setBackground(getResources().getDrawable(R.drawable.bg_nav_text_white));
			ll_cat.setBackground(null);
			ll_fav.setBackground(null);
			ll_home.setBackground(null);
		} else if(abc.equals(getString(R.string.menu_category))) {
			ll_cat.setBackground(getResources().getDrawable(R.drawable.bg_nav_text_white));
			ll_latest.setBackground(null);
			ll_home.setBackground(null);
			ll_fav.setBackground(null);
		} else if(abc.equals(getString(R.string.menu_favorite))) {
			ll_fav.setBackground(getResources().getDrawable(R.drawable.bg_nav_text_white));
			ll_latest.setBackground(null);
			ll_cat.setBackground(null);
			ll_home.setBackground(null);
		}
	}
	private class MyTaskDev extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... params) {
			return JsonUtils.getJSONString(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (null == result || result.length() == 0) {
				showToast(getString(R.string.no_data_found));
			} else {

				try {
					JSONObject mainJson = new JSONObject(result);
					JSONArray jsonArray = mainJson.getJSONArray(Constant.APP_Array);
					JSONObject objJson;
					for (int i = 0; i < jsonArray.length(); i++) {
						objJson = jsonArray.getJSONObject(i);
						ItemAbout itemAbout = new ItemAbout();

						itemAbout.setappDevelop(objJson.getString(Constant.APP_DEVELOP));
						mListItem.add(itemAbout);
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
				setResult();
			}
		}
	}

	private void setResult() {

		ItemAbout itemAbout = mListItem.get(0);
		textView_developedby.setText(getString(R.string.dev_by)+itemAbout.getappDevelop());


	}

	public void showToast(String msg) {
		Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// Toast.makeText(appContext, "BAck", Toast.LENGTH_LONG).show();
			AlertDialog.Builder alert = new AlertDialog.Builder(
					MainActivity.this);
			alert.setTitle(getString(R.string.app_name));
			alert.setIcon(R.drawable.icon);
			alert.setMessage("Are You Sure You Want To Quit?");

			alert.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {

					finish();
				}
			});

			alert.setNegativeButton("No",
					new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

				}
			});
			alert.show();
			return true;
		}

		return super.onKeyDown(keyCode, event);

	}


}
