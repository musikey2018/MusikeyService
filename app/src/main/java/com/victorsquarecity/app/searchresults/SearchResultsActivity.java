package com.victorsquarecity.app.searchresults;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.otto.Subscribe;
import com.victorsquarecity.app.R;
import com.victorsquarecity.app.utils.AddLocationEvent;
import com.victorsquarecity.app.utils.LocationModel;
import com.victorsquarecity.app.utils.LocationsController;


import java.util.ArrayList;


public class SearchResultsActivity extends FragmentActivity implements GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    public static final String TAG = "SearchResultsActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            //LocationWebService.getInstance(this).refreshLocations(this);

            //LoginWebService.getInstance().loginUser(this,"wixtor@mail.com","0123Aziz$","wixtor@mail.com");
        }
        setContentView(R.layout.activity_searchresults);
        setUpMapIfNeeded();

//        addLocationButton = (Button)findViewById(1);
        // We can't call this yet since web services hasn't loaded out data
//        LocationsController.getLocations();

        LocationsController.startLocationReporting(this);
    }


    @Subscribe
    public void locationsUpdated(AddLocationEvent addLocationEvent) {
        Log.d("TAG", "AddLocationEvent");
        ArrayList<LocationModel> currentLocations = LocationsController.getLocations();

        mMap.clear();

        for (int index = 0; index < currentLocations.size(); index++) {
            LocationModel currentLocation = currentLocations.get(index);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.title(currentLocation.getLocationName());
            markerOptions.snippet("Ratings :" + currentLocation.getLocationRatings());
            markerOptions.position(currentLocation.getLocationCoordinates());

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation.getLocationCoordinates(), 13.0f));
        }


        mMap.setMyLocationEnabled(true);
        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();

        // BusProvider.getInstance().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //BusProvider.getInstance().unregister(this);
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    // .getMap();
                    .getMapAsync(this);
            // Check if we were successful in obtaining the map.

        } else {
            locationsUpdated(null);
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        locationsUpdated(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.d(TAG, "Item Id" + id);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent detailsIntent = new Intent(this, DetailsActivity.class);
            startActivity(detailsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, marker.getTitle() + "is clicked",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mMap != null) {
            setUpMap();
            // locationsUpdated( null );
        }
    }
}
