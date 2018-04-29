package com.victorsquarecity.app.home;


/**
 * Created by app on 6/4/2017.
 */

import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.victorsquarecity.app.R;
import com.victorsquarecity.app.utils.BottomNavHelper;
import com.victorsquarecity.app.utils.HelperFactory;
import com.victorsquarecity.app.utils.LocationHelper;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    private static int ACTIVITY_NUM = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate() activity started");

        setupBottomNavigation();
        setFragsPager();

    }

    /*
    * accountable for search type fragments*/
    private void setFragsPager() {
        Log.d(TAG, "setFragsPager() setFragsPager");
        SearchFragsAdapter fragsAdapter = new SearchFragsAdapter(getSupportFragmentManager());


        fragsAdapter.addFragment(new HomeFragment());
        fragsAdapter.addFragment(new LunchSearchFragment());
        fragsAdapter.addFragment(new DinnerSearchFragment());
        fragsAdapter.addFragment(new NightLifeSearchFragment());

        ViewPager fragsPager = (ViewPager) findViewById(R.id.container);
        fragsPager.setAdapter(fragsAdapter);

        Log.d(TAG,"setFragsPager() tablayout setting start");

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(fragsPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_breakfast);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_lunch);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_dinner);
        Log.d(TAG,"setFragsPager() tablayout setting done");
//        tabLayout.getTabAt(3).setIcon(R.drawable.ic_coffeebar);
//        tabLayout.getTabAt(4).setIcon(R.drawable.ic_mocktails);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_party);
    }

    /*
    add bottom navmenu
    * */
    private void setupBottomNavigation() {
        Log.d(TAG, "setupBottomNavigation() setting bottom navmenu");
        BottomNavigationViewEx navigationViewEx = (BottomNavigationViewEx) findViewById(R.id.navigation);
        BottomNavHelper.setBottomNavView(navigationViewEx);
        BottomNavHelper.enableNavigation(HomeActivity.this, navigationViewEx);
        Menu menu = navigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }



}
