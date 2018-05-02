package com.viaviapp.hdvideo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.AllVideosListAdapter;
import com.example.adapter.HomeLatestGridAdapter;
import com.example.item.ItemAllVideos;
import com.example.item.ItemLatest;
import com.example.util.Constant;
import com.example.util.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {



    RelativeLayout mainlay;
    ProgressBar pbar;
    List<ItemLatest> arrayofLatest;
    List<ItemLatest> arrayofLatestVideoall;
    List<ItemAllVideos> arrayofLatestVideoallCat;
    GridView gridViewallvideo,gridviewcat,gridcat_most;
    HomeLatestGridAdapter objAdapter;
    AllVideosListAdapter objAdaptercat;
    private int columnWidth;
    Button btn_more,btn_moreall,btncat;
    private FragmentManager fragmentManager;
    private ItemLatest objAllBean;
    private ItemAllVideos objAllBeancat;
    TextView txt_latest,txt_latestall,txt_cat;
    LinearLayout laymost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);

        arrayofLatest = new ArrayList<>();
        arrayofLatestVideoall = new ArrayList<>();
        arrayofLatestVideoallCat=new ArrayList<>();
        getActivity().setTitle(getString(R.string.app_name));

        mainlay = (RelativeLayout) rootView.findViewById(R.id.main);
        pbar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        fragmentManager = getActivity().getSupportFragmentManager();

        gridViewallvideo = (GridView) rootView.findViewById(R.id.gridcat_allvideo);
        gridviewcat=(GridView)rootView.findViewById(R.id.gridcat_cat);
        gridcat_most=(GridView)rootView.findViewById(R.id.gridcat);
        btn_more=(Button)rootView.findViewById(R.id.btn_more);
        btn_moreall=(Button)rootView.findViewById(R.id.btn_moreall);
        btncat=(Button)rootView.findViewById(R.id.btn_moreallcat);
        txt_latest=(TextView)rootView.findViewById(R.id.text_title_latest);
        txt_latestall=(TextView)rootView.findViewById(R.id.text_title_latestall);
        txt_cat=(TextView)rootView.findViewById(R.id.textView_cat);


        if (JsonUtils.isNetworkAvailable(getActivity())) {
            new MyTaskVideo().execute(Constant.HOME_URL);

        } else {
            showToast(getString(R.string.network_msg));
        }

        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostFragment currenthome = new MostFragment();
                fragmentManager.beginTransaction().replace(R.id.fragment1, currenthome).commit();
            }
        });

        btn_moreall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatestFragment currenthome = new LatestFragment();
                fragmentManager.beginTransaction().replace(R.id.fragment1, currenthome).commit();
            }
        });

        btncat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllVideosFragment currenthome = new AllVideosFragment();
                fragmentManager.beginTransaction().replace(R.id.fragment1, currenthome).commit();
            }
        });
        return rootView;
    }


    private class MyTaskVideo extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbar.setVisibility(View.VISIBLE);
            mainlay.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... params) {
            return JsonUtils.getJSONString(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            pbar.setVisibility(View.INVISIBLE);
            mainlay.setVisibility(View.VISIBLE);

            if (null == result || result.length() == 0) {
                showToast(getString(R.string.no_data_found));
            } else {

                try {
                    JSONObject mainJson = new JSONObject(result);
                    JSONObject mainJsonob = mainJson.getJSONObject(Constant.LATEST_ARRAY_NAME);
                    JSONArray jsonArray = mainJsonob.getJSONArray("most_viewed");

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

                        arrayofLatest.add(objItem);

                    }
                    JSONArray jsonArray2 = mainJsonob.getJSONArray("latest_video");
                    JSONObject objJson2 = null;
                    for (int i = 0; i < jsonArray2.length(); i++) {
                        objJson2 = jsonArray2.getJSONObject(i);

                        ItemLatest objItem = new ItemLatest();

                        objItem.setId(objJson2.getString(Constant.LATEST_ID));
                        objItem.setCategoryId(objJson2.getString(Constant.LATEST_CATID));
                        objItem.setCategoryName(objJson2.getString(Constant.LATEST_CAT_NAME));
                        objItem.setVideoUrl(objJson2.getString(Constant.LATEST_VIDEO_URL));
                        objItem.setVideoId(objJson2.getString(Constant.LATEST_VIDEO_ID));
                        objItem.setVideoName(objJson2.getString(Constant.LATEST_VIDEO_NAME));
                        objItem.setDuration(objJson2.getString(Constant.LATEST_VIDEO_DURATION));
                        objItem.setDescription(objJson2.getString(Constant.LATEST_VIDEO_DESCRIPTION));
                        objItem.setImageUrl(objJson2.getString(Constant.LATEST_IMAGE_URL));
                        objItem.setType(objJson2.getString(Constant.LATEST_TYPE));
                        objItem.setViewC(objJson2.getString(Constant.LATEST_VIEW));

                        arrayofLatestVideoall.add(objItem);

                    }

                    JSONArray jsonArray2cat = mainJsonob.getJSONArray("all_video_cat");
                    JSONObject objJson2cat = null;
                    for (int i = 0; i < jsonArray2cat.length(); i++) {
                        objJson2cat = jsonArray2cat.getJSONObject(i);

                        ItemAllVideos objItem = new ItemAllVideos();

                        objItem.setCategoryName(objJson2cat.getString(Constant.CATEGORY_NAME));
                        objItem.setCategoryId(objJson2cat.getInt(Constant.CATEGORY_CID));
                        objItem.setCategoryImageurl(objJson2cat.getString(Constant.CATEGORY_IMAGE));

                        arrayofLatestVideoallCat.add(objItem);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setAdapterToListview();
            }

        }
    }

    public void setAdapterToListview() {
        objAdapter = new HomeLatestGridAdapter(getActivity(), R.layout.homelatest_lsv_item,
                arrayofLatest, columnWidth);
        gridcat_most.setAdapter(objAdapter);


        objAdapter = new HomeLatestGridAdapter(getActivity(), R.layout.homelatest_lsv_item,
                arrayofLatestVideoall, columnWidth);
        gridViewallvideo.setAdapter(objAdapter);

        objAdaptercat = new AllVideosListAdapter(getActivity(), R.layout.homeallvideos_lsv_item,
                arrayofLatestVideoallCat);
        gridviewcat.setAdapter(objAdaptercat);

        gridcat_most.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                objAllBean=arrayofLatest.get(position);
                Constant.LATEST_IDD=objAllBean.getId();
                Intent intplay=new Intent(getActivity(),DetailActivity.class);
                startActivity(intplay);
            }
        });

        gridViewallvideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                objAllBean=arrayofLatestVideoall.get(position);
                Constant.LATEST_IDD=objAllBean.getId();
                Intent intplay=new Intent(getActivity(),DetailActivity.class);
                startActivity(intplay);
            }
        });

        gridviewcat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                objAllBeancat=arrayofLatestVideoallCat.get(position);

                Constant.CATEGORY_ID=objAllBeancat.getCategoryId();
                Constant.CATEGORY_TITLE=objAllBeancat.getCategoryName();

                Intent intcat=new Intent(getActivity(),CategoryItem.class);
                startActivity(intcat);
            }
        });

    }
    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }
//    public void Most()
//    {
//        laymost.removeAllViews();
//        int i=0;
//        do
//        {
//            if(i>=arrayofLatest.size())
//            {
//                return;
//            }
//
//            View view = getActivity().getLayoutInflater().inflate(R.layout.homelatest_lsv_item, null,false);
//            RelativeLayout lay=(RelativeLayout)view.findViewById(R.id.imgsec);
//            //lay.setLayoutParams(new LinearLayout.LayoutParams(getActivity().getResources().getDimensionPixelSize(R.dimen.item_size), getActivity().getResources().getDimensionPixelSize(R.dimen.item_size)));
//            final ImageView imgv_latetst=(ImageView)view.findViewById(R.id.image);
//            TextView name=(TextView)view.findViewById(R.id.text_title);
//            TextView txt_time=(TextView)view.findViewById(R.id.text_time);
//            TextView txt_category=(TextView)view.findViewById(R.id.text_cat);
//            TextView txt_view=(TextView)view.findViewById(R.id.text_view);
//            laymost.addView(view);
//            objAllBean=arrayofLatest.get(i);
//            if(objAllBean.getType().equals("local"))
//            {
//                Picasso.with(getActivity()).load(objAllBean.getImageUrl().toString()).into(imgv_latetst);
//            }
//            else
//            {
//                Picasso.with(getActivity()).load(Constant.YOUTUBE_IMAGE_FRONT+objAllBean.getVideoId().toString()+Constant.YOUTUBE_IMAGE_BACK).placeholder(R.drawable.icon).into(imgv_latetst);
//            }
//
//            name.setText(objAllBean.getVideoName().toString());
//            txt_time.setText(objAllBean.getDuration().toString());
//            txt_category.setText(objAllBean.getCategoryName().toString());
//            txt_view.setText(objAllBean.getViewC().toString());
//
//
//            imgv_latetst.setId(i);
//            imgv_latetst.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    // TODO Auto-generated method stub
//                    objAllBean=arrayofLatest.get(imgv_latetst.getId());
//                     Constant.LATEST_IDD=objAllBean.getId();
//
//                    Intent intplay=new Intent(getActivity(),DetailActivity.class);
//                    intplay.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intplay);
//
//                }
//            });
//            i++;
//        }while(true);
//    }

}

