package com.victorsquarecity.app.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.victorsquarecity.app.R;
import com.victorsquarecity.app.searchresults.SearchResultsActivity;
import com.victorsquarecity.app.utils.LocationModel;
import com.victorsquarecity.app.services.LocationWebService;
import com.victorsquarecity.app.utils.LocationsController;
import com.victorsquarecity.app.utils.VictorConstants;

import java.util.List;

/**
 * Created by app on 6/8/2017.
 */

public class NightLifeSearchFragment extends Fragment {
    private static final String TAG = "NightLifeSearchFragment";
    private View mView = null;
    private static Context mCtx;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        Log.d(TAG, "onCreateView() creating NightLifeSearchFragment view");

        mCtx = getActivity().getApplicationContext();

        ImageButton mapButton = (ImageButton) mView.findViewById(R.id.displayOnMapBtn);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchResultsActivity.class);
                startActivity(intent);
            }
        });

        final EditText searchView = (EditText) mView.findViewById(R.id.search_bar);
        Button searchButton = (Button) mView.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String query = searchView.getText().toString();
                Log.d(TAG, "onCreateView() search button is clicked for night_club query : " + query);

                LocationWebService.getInstance(mCtx).refreshLocations(query, VictorConstants.RESTURANT, mCtx);

                searchView.setText("");
                setupRecyclerView();
            }
        });

        setupRecyclerView();
        Log.d(TAG, "created searched placed list view");

        return mView;
    }


    public void setupRecyclerView() {

        List<LocationModel> data = LocationsController.getLocations();
        Log.d(TAG, "creating searched view");
        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.searchResults);
        //SearchAdapter adapter = new SearchAdapter(data, getActivity().getApplication());
        //adapter.clearAdapter();
        //recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}
