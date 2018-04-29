package com.victorsquarecity.app.searchresults;


import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.victorsquarecity.app.R;
import com.victorsquarecity.app.adapters.CommentsListAdapter;
import com.victorsquarecity.app.filters.ExpandableListMain;
import com.victorsquarecity.app.utils.LocationModel;
import com.victorsquarecity.app.utils.LocationsController;
import com.victorsquarecity.app.utils.VictorConstants;
import com.victorsquarecity.app.utils.VictorController;
import com.victorsquarecity.app.utils.pojo.LocationReview;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = "DetailsActivity";

    @BindView(R.id.place_name)
    TextView place_name;

    @BindView(R.id.place_name_search)
    TextView place_name_search;

    @BindView(R.id.place_priceRange)
    TextView place_priceRange;

    @BindView(R.id.place_location)
    TextView place_location;

    @BindView(R.id.place_ratings)
    TextView place_ratings;

    @BindView(R.id.pager)
    RecyclerView recyclerView;


    RecyclerView commentsRecyclerView;


    List<LocationModel> locationList;
    LocationModel location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        int position = intent.getIntExtra("position", 0);
        Log.d(TAG, "onCreate() position obtained " + position);

        locationList = LocationsController.getLocations();

        location = locationList.get(position);

        Log.d(TAG, "onCreate() location " + location);

        place_name.setText(location.getLocationName());
        place_priceRange.setText(location.getPriceRange());
        place_location.setText(location.getLocationAddress());
        place_name_search.setText(location.getLocationName());
        place_ratings.setText(location.getLocationRatings());


        init();
    }

    @OnClick(R.id.share_button)
    public void getFilters(View view) {
        Intent intent = new Intent(this, ExpandableListMain.class);
        startActivity(intent);
    }


    @OnClick(R.id.review_button)
    public void onShowPopup(View v) {

        //Send request for place reviews

        String placeid;
        if (!location.getId().isEmpty()) {
            placeid = location.getId();
        } else {
            placeid = location.getReference();
        }


        String result = VictorController.getVictorController().sendVolleyRequest(DetailsActivity.this, VictorConstants.PLACE_REVIEWS_REQUEST,placeid);

        List<LocationReview> locationReviews = VictorController.getVictorController().getLocationReview(result);


        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // inflate the custom popup layout
        final View inflatedView = layoutInflater.inflate(R.layout.comments_popup_layout, null, false);
        // find the ListView in the popup layout
        //ListView listView = (ListView) inflatedView.findViewById(R.id.commentsListView);

        // get device size
        Display display = getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);


        // fill the data to the list items
        setSimpleList(locationReviews);

        // set height depends on the device size
        PopupWindow popWindow = new PopupWindow(inflatedView, size.x - 50, size.y - 200, true);
        // set a background drawable with rounders corners
        popWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.fb_popup_bg));
        // make it focusable to show the keyboard to enter in `EditText`
        popWindow.setFocusable(true);
        // make it outside touchable to dismiss the popup window
        popWindow.setOutsideTouchable(true);

        // show the popup at bottom of the screen and set some margin at bottom ie,
        popWindow.showAtLocation(v, Gravity.TOP, 0, 150);
    }


    void setSimpleList(List<LocationReview> locationReviews) {

        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(DetailsActivity.this));
        CommentsListAdapter adapter = new CommentsListAdapter(DetailsActivity.this, locationReviews);
        commentsRecyclerView.setAdapter(adapter);
        Log.d(TAG, " pager adapter is set images " + locationReviews.size());

    }

    private void init() {

        List<String> placePhotos = location.getPlacePhotos();

        recyclerView.setLayoutManager(new LinearLayoutManager(DetailsActivity.this));
        ImageSliderAdapter adapter = new ImageSliderAdapter(DetailsActivity.this, placePhotos);
        recyclerView.setAdapter(adapter);
        Log.d(TAG, " pager adapter is set images " + placePhotos.size());

    }

}
