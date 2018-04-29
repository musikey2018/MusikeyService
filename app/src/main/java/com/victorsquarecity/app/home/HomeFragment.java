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
import android.widget.TextView;

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

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private View mView = null;
    private static TextView tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        Log.d(TAG, "onCreateView() creating Home view" + (getActivity() != null) + (getContext() != null));

        Log.d(TAG, "onCreateView() context " + (getContext() != null));

        tv = (TextView) mView.findViewById(R.id.resultCount);
        tv.setText("");

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
                Log.d(TAG, "onCreateView() search button is clicked for query : " + query);
                //searchView.clearFocus();
                //HelperFactory.getInstance().hideSoftKeyboard(getActivity());
                Context mCtx = getContext();
                if (!query.isEmpty()) {
                    //TODO make dynamic request type
                    LocationWebService.getInstance(mCtx).refreshLocations(query, VictorConstants.RESTURANT,mCtx);
                    searchView.setText("");
                    tv.invalidate();
                    setupRecyclerView(true);
                } else {
                    tv.setText("Please type some place");
                }
            }
        });

        setupRecyclerView(false);
        Log.d(TAG, "created searched placed list view");

        return mView;
    }


    public void setupRecyclerView(boolean is_clicked) {

        List<LocationModel> data = LocationsController.getLocations();

        Log.d(TAG, "setupRecyclerView() creating searched view");
        Log.d(TAG, "setupRecyclerView() result match count is :" + data.size());
        showResultCount(data.size(), is_clicked);

        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.searchResults);
        //SearchAdapter adapter = new SearchAdapter(data, getActivity().getApplication());
        //adapter.clearAdapter();
        //recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    public void showResultsOnMap() {
        Intent intent = new Intent(getActivity(), SearchResultsActivity.class);
        startActivity(intent);

    }

    public void showResultCount(int count, boolean is_clicked) {
        Log.d(TAG, "showResultCount() result count is :" + count);
        tv = (TextView) mView.findViewById(R.id.resultCount);
        if (!is_clicked)
            tv.setText("");
        else {
            if (count > 0)
                tv.setText(count + " showResultCount() places matche criteria.");
            else tv.setText("showResultCount() no place matches such criteria.");
        }
        tv.postInvalidate();
    }


}
