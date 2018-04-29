package com.victorsquarecity.app.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.victorsquarecity.app.R;
import com.victorsquarecity.app.searchresults.SearchResultsActivity;
import com.victorsquarecity.app.utils.HelperFactory;
import com.victorsquarecity.app.utils.LocationModel;
import com.victorsquarecity.app.utils.LocationsController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by app on 6/8/2017.
 */

public class HomeFragmentTemp extends Fragment {
    private static final String TAG = "HomeFragmentTemp";
    private View mView= null;
    private  SupportPlaceAutocompleteFragment autocompleteFragment = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home,container,false);
        Log.d(TAG,"onCreateView() creating Home view");

        Button displayOnMapBtn =(Button)mView.findViewById(R.id.displayOnMapBtn);
        displayOnMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResultsOnMap();
            }
        });

        setupRecyclerView();
        Log.d(TAG,"onCreateView() created searched placed list view");



        FragmentManager fm = getChildFragmentManager();
        SupportPlaceAutocompleteFragment autocompleteFragment = (SupportPlaceAutocompleteFragment) fm.findFragmentByTag("autocompleteFragment");
        if (autocompleteFragment == null) {
            autocompleteFragment = new SupportPlaceAutocompleteFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.place_autocomplete_fragment, autocompleteFragment, "autocompleteFragment");
            ft.commit();
            fm.executePendingTransactions();

            //getActivity().getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
            //getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);


                 }

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(com.google.android.gms.location.places.Place place) {
                Log.i(TAG, "Place: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });


        return mView;
    }


    public void setupRecyclerView(){

        List<LocationModel> data = LocationsController.getLocations();
        Log.d(TAG,"onCreateView() creating searched view");
        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.searchResults);
        //SearchAdapter adapter = new SearchAdapter(data, getActivity().getApplication());
       // recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    public void showResultsOnMap(){
        Intent intent = new Intent(getActivity(), SearchResultsActivity.class);
        startActivity(intent);

    }

}
