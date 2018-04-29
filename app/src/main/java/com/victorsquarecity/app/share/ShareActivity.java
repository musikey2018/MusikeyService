package com.victorsquarecity.app.share;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.victorsquarecity.app.R;
import com.victorsquarecity.app.utils.BottomNavHelper;

/**
 * Created by app on 6/8/2017.
 */

public class ShareActivity extends AppCompatActivity {
    private static final String TAG = "ShareActivity";
    private Context mCtx = ShareActivity.this;
    private static int ACTIVITY_NUM =5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setupBottomNavigation();
    }

    /*
 add bottom navmenu
 * */
    private void setupBottomNavigation(){
        Log.d(TAG,"setting bottom navmenu");
        BottomNavigationViewEx navigationViewEx =(BottomNavigationViewEx) findViewById(R.id.navigation);
        BottomNavHelper.setBottomNavView(navigationViewEx);
        BottomNavHelper.enableNavigation(mCtx,navigationViewEx);
        Menu menu = navigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }
}
