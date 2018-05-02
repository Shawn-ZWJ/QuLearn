
package com.viaviapp.hdvideo;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.favorite.DatabaseHandler;
import com.example.favorite.Pojo;
import com.example.item.ItemLatest;
import com.example.play.OpenYouTubePlayerActivity;
import com.example.util.Constant;
import com.example.util.JsonUtils;
import com.example.youtube.YoutubePlay;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class DetailActivity extends AppCompatActivity {

    List<ItemLatest> arrayOfLatestVideo;
    private ItemLatest objAllBean;
    ImageView img_video,img_play;
    TextView txt_title,txt_cat,txt_time,txt_view,text_num_like;//********************
    WebView web_desc;
    String vid,video_pid,videoname,videourl,videoduration,videocatname,videocatid,videodesc,videoimageurl,videotype,videoview;

    String video_num_like;//***点赞数量*******************
    ImageView like;
    ImageView dislike;

    private Menu menu;
    public DatabaseHandler db;
    private AdView mAdView;
    int scrollRange = -1;
    boolean isShow = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        arrayOfLatestVideo=new ArrayList<>();
        final  CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });

        db = new DatabaseHandler(this);
        img_video=(ImageView)findViewById(R.id.backdrop);
        txt_title=(TextView)findViewById(R.id.text_title);
        txt_cat=(TextView)findViewById(R.id.txt_cat);
        txt_time=(TextView)findViewById(R.id.txt_time);
        web_desc=(WebView)findViewById(R.id.desweb);
        txt_view=(TextView)findViewById(R.id.txt_view);
        text_num_like = (TextView)findViewById(R.id.text_num_like);//***点赞数量***********
        like = (ImageView) findViewById(R.id.like);//********点赞图片按钮******
        dislike = (ImageView) findViewById(R.id.dislike);//********踩图片按钮******

        img_play=(ImageView)findViewById(R.id.imageView3);
        mAdView = (AdView) findViewById(R.id.adView);
        mAdView.loadAd(new AdRequest.Builder().build());
        if (JsonUtils.isNetworkAvailable(DetailActivity.this)) {
            new MyTask().execute(Constant.CATEGORY_ITEM_SINGLE_URL+Constant.LATEST_IDD);
        } else {
            showToast("No Network Connection!!!");
        }

        like.setOnClickListener( new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View v) {
                                                      // 长toast
                                                      //Toast.makeText(DetailActivity.this, "This is a long toast!", Toast.LENGTH_SHORT).show();
                                                      like.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_red_hdpi));
                                                  }
                                              }
        );

        dislike.setOnClickListener( new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         // 长toast
                                         //Toast.makeText(DetailActivity.this, "This is a long toast!", Toast.LENGTH_SHORT).show();
                                         like.setImageDrawable(getResources().getDrawable(R.drawable.dislike_darkgreen));
                                     }
                                 }
        );



    }

    private	class MyTask extends AsyncTask<String, Void, String> {

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
                        objItem.setViewC(objJson.getString(Constant.LATEST_VIEW));//观看人数
                        objItem.setVideoNumLike(objJson.getString(Constant.LATEST_NUM_LIKE));//点赞数量************
                        arrayOfLatestVideo.add(objItem);



                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                setAdapterToListview();
            }

        }
    }

    public void setAdapterToListview() {

        objAllBean = arrayOfLatestVideo.get(0);
        vid=objAllBean.getId();
        video_pid=objAllBean.getVideoId();
        videocatid=objAllBean.getCategoryId();
        videocatname=objAllBean.getCategoryName();
        videodesc=objAllBean.getDescription();
        videoduration=objAllBean.getDuration();
        videoimageurl=objAllBean.getImageUrl();
        videoname=objAllBean.getVideoName();
        videourl=objAllBean.getVideoUrl();
        videotype=objAllBean.getType();
        videoview=objAllBean.getViewC();

        video_num_like=objAllBean.getVideoNumLike();//******************

        txt_title.setText(videoname);
        txt_time.setText("时间:"+ videoduration);
        txt_cat.setText("分类:"+ videocatname);
        txt_view.setText("观看:"+ videoview);
        text_num_like.setText(video_num_like);//***点赞数量*****************

        web_desc.setBackgroundColor(0);
        web_desc.setFocusableInTouchMode(false);
        web_desc.setFocusable(false);
        web_desc.getSettings().setDefaultTextEncodingName("UTF-8");

        String mimeType = "text/html";
        String encoding = "utf-8";
        String htmlText = videodesc;

        String text = "<html><head>"
                + "<style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/myfonts/Roboto-Regular.ttf\")}body{font-family: MyFont;color: #545454;text-align:justify}"
                + "</style></head>"
                + "<body>"
                + htmlText
                + "</body></html>";

        web_desc.loadDataWithBaseURL(null,text, mimeType, encoding,null);

        if(videotype.equals("local"))
        {
            Picasso.with(DetailActivity.this).load(videoimageurl).into(img_video);
        }
        else if (videotype.equals("server_url")) {
            Picasso.with(DetailActivity.this).load(videoimageurl).into(img_video);
        }
        else if (videotype.equals("youtube"))
        {
            Picasso.with(DetailActivity.this).load(Constant.YOUTUBE_IMAGE_FRONT+video_pid+Constant.YOUTUBE_IMAGE_BACK).into(img_video);
        }

        img_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(videotype.equals("local"))
                {
                    Intent lVideoIntent = new Intent(null, Uri.parse("file://" + videourl), DetailActivity.this, OpenYouTubePlayerActivity.class);
                    startActivity(lVideoIntent);
                }
                else if (videotype.equals("server_url")) {
                    Intent lVideoIntent = new Intent(null, Uri.parse("file://" + videourl), DetailActivity.this, OpenYouTubePlayerActivity.class);
                    startActivity(lVideoIntent);
                } else if (videotype.equals("youtube"))
                {
                    Intent i = new Intent(DetailActivity.this,YoutubePlay.class);
                    i.putExtra("id", video_pid);
                    startActivity(i);                }
            }
        });
    }

    public void showToast(String msg) {
        Toast.makeText(DetailActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.video_menu, menu);
        this.menu = menu;
        FirstFav();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;


            case R.id.menu_fav:
                List<Pojo> pojolist = db.getFavRow(Constant.LATEST_IDD);
                if (pojolist.size() == 0) {
                    AddtoFav();//if size is zero i.e means that record not in database show add to favorite
                } else {
                    if (pojolist.get(0).getVId().equals(Constant.LATEST_IDD)) ;
                    {
                        RemoveFav();
                    }

                }

                return true;

            case R.id.menu_share:

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Download this app from this link"+"https://play.google.com/store/apps/details?id="+getPackageName());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }

    }

    public void AddtoFav() {
        //***************************
        db.AddtoFavorite(new Pojo(Constant.LATEST_IDD, videoname, videoimageurl, videoduration, videocatname,videotype,videoview,video_pid));
        Toast.makeText(getApplicationContext(), getString(R.string.add_favorite_msg), Toast.LENGTH_SHORT).show();
        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.fav_hover));
    }

    public void RemoveFav() {

        db.RemoveFav(new Pojo(Constant.LATEST_IDD));
        Toast.makeText(getApplicationContext(), getString(R.string.remove_favorite_msg), Toast.LENGTH_SHORT).show();
        menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.fav));

    }

    public void FirstFav() {

        List<Pojo> pojolist = db.getFavRow(Constant.LATEST_IDD);
        if (pojolist.size() == 0) {
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.fav));

        } else {
            if (pojolist.get(0).getVId().equals(Constant.LATEST_IDD)) {
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.fav_hover));
            }
        }
    }













}
