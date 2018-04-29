package com.victorsquarecity.app.home;


/**
 * Created by app on 6/4/2017.
 */
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.sdsmdg.tastytoast.TastyToast;
import com.victorsquarecity.app.R;
import com.victorsquarecity.app.login.LoginActivity;
import com.victorsquarecity.app.utils.BottomNavHelper;
import com.victorsquarecity.app.utils.HelperFactory;
import com.victorsquarecity.app.utils.LocationHelper;
import com.victorsquarecity.app.utils.db.dao.UserORM;
import com.victorsquarecity.app.utils.shake.ShakeIt;
import com.victorsquarecity.app.utils.shake.ShakeListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG ="MainActivity";

    private Context mCtx = MainActivity.this;
    private static int ACTIVITY_NUM =0;
   // private static int ENABLE_GPS = 100;
    //private static boolean isGPSenabled = false;
    private UserORM user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate() activity started");

        user = HelperFactory.getUserProfileData(getApplication());
        if(user == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }else{
            setContentView(R.layout.activity_main);
            setupBottomNavigation();
            setFragsPager();
        }

    }

        /*
        * accountable for search type fragments*/
    private void setFragsPager(){
        Log.d(TAG,"setFragsPager() setting FragePager start");
        SearchFragsAdapter fragsAdapter = new SearchFragsAdapter(getSupportFragmentManager());
        fragsAdapter.addFragment(new BreakFastSearchFragment());
        fragsAdapter.addFragment(new LunchSearchFragment());
        fragsAdapter.addFragment(new DinnerSearchFragment());
        fragsAdapter.addFragment(new NightLifeSearchFragment());
        ViewPager fragsPager = (ViewPager) findViewById(R.id.container);
        fragsPager.setAdapter(fragsAdapter);

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
    private void setupBottomNavigation(){
        Log.d(TAG,"setupBottomNavigation()");
        BottomNavigationViewEx navigationViewEx =(BottomNavigationViewEx) findViewById(R.id.navigation);
        BottomNavHelper.setBottomNavView(navigationViewEx);
        BottomNavHelper.enableNavigation(mCtx,navigationViewEx);
        Menu menu = navigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();  // Always call the superclass method first
//
//        // The activity is either being restarted or started for the first time
//        // so this is where we should make sure that GPS is enabled
//       checkforGPS();
//
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();  // Always call the superclass method first
//
//        // Activity being restarted from stopped state
//    }

//    private void checkforGPS(){
//        LocationManager locationManager =
//                (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        isGPSenabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//
//        if (!isGPSenabled) {
//            Log.d("mainactivity","gps not enabled");
//            TastyToast.makeText(mCtx,"Please enable gps settings for app to work",TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
//            startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), ENABLE_GPS);
//
//        }
//
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode,resultCode,data);
//        if (requestCode == ENABLE_GPS) {
//            Log.d("mainactivity","resultcode"+resultCode+"requestCode"+requestCode);
//            if (resultCode == RESULT_OK) {
//                Log.d("mainactivity","resultcode"+resultCode);
//                startActivity(new Intent(MainActivity.this, MainActivity.class));
//                finish();
//            }
//        }
//    }

}



