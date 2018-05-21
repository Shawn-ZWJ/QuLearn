package com.viaviapp.hdvideo;

import android.content.Intent;
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
import android.widget.ListView;
import android.widget.TextView;

import com.example.adapter.FavGridAdapter;
import com.example.favorite.DatabaseHandler;
import com.example.favorite.Pojo;
import com.example.util.Constant;

import java.util.List;
import java.util.Locale;


public class FavoriteFragment extends Fragment {

    ListView lsv_latest;
    DatabaseHandler db;
    private DatabaseHandler.DatabaseManager dbManager;
    FavGridAdapter adapterfav;
    TextView txt_no;
    private int columnWidth;
    List<Pojo> allData;
    Pojo pojo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
        setHasOptionsMenu(true);
        getActivity().setTitle(getString(R.string.menu_favorite));
        lsv_latest = (ListView) rootView.findViewById(R.id.lsv_favorite);

        txt_no = (TextView) rootView.findViewById(R.id.textView1);
        db = new DatabaseHandler(getActivity());
        dbManager = DatabaseHandler.DatabaseManager.INSTANCE;
        dbManager.init(getActivity());

        txt_no = (TextView) rootView.findViewById(R.id.textView1);
        db = new DatabaseHandler(getActivity());
        dbManager = DatabaseHandler.DatabaseManager.INSTANCE;
        dbManager.init(getActivity());

        allData = db.getAllData();
        adapterfav = new FavGridAdapter(getActivity(), R.layout.latest_lsv_item,
                allData,columnWidth);
        lsv_latest.setAdapter(adapterfav);
        if (allData.size() == 0) {
            txt_no.setVisibility(View.VISIBLE);
        } else {
            txt_no.setVisibility(View.INVISIBLE);
        }

        lsv_latest.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                // TODO Auto-generated method stub
                pojo = allData.get(position);
                Constant.LATEST_IDD = pojo.getVId();

                Intent intplay = new Intent(getActivity(), DetailActivity.class);
                startActivity(intplay);

            }
        });

        return rootView;
    }

    public void onDestroyView() {

        if (!dbManager.isDatabaseClosed())
            dbManager.closeDatabase();
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!dbManager.isDatabaseClosed())
            dbManager.closeDatabase();
    }

    @Override
    public void onResume() {
        super.onResume();

        allData = db.getAllData();
        adapterfav = new FavGridAdapter(getActivity(), R.layout.latest_lsv_item,
                allData,columnWidth);
        lsv_latest.setAdapter(adapterfav);

        if (allData.size() == 0) {
            txt_no.setVisibility(View.VISIBLE);
        } else {
            txt_no.setVisibility(View.INVISIBLE);
        }

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
                if (!hasFocus) {
                    searchMenuItem.collapseActionView();
                    searchView.setQuery("", false);
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub
                String text = newText.toString().toLowerCase(Locale.getDefault());
                if(adapterfav!=null)
                {
                    adapterfav.filter(text);
                }

                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub
                return false;
            }
        });

    }
}