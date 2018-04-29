package com.victorsquarecity.app.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import android.util.Log;
import android.view.MenuItem;


import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.victorsquarecity.app.friends.FriendsAcitivity;
import com.victorsquarecity.app.home.HomeActivity;
import com.victorsquarecity.app.messages.ChatActivity;

import com.victorsquarecity.app.history.HistoryAcitivity;
import com.victorsquarecity.app.profile.ProfileActivity;
import com.victorsquarecity.app.R;

/**
 * Created by app on 6/8/2017.
 */

public class BottomNavHelper {
    private static final String TAG = "BottomNavHelper";

    /*
    * setting up bottom navmenu*/
    public static void setBottomNavView(BottomNavigationViewEx bottomNavView){
        Log.d(TAG,"setting up navmenu");
        bottomNavView.enableAnimation(false);
        bottomNavView.enableShiftingMode(false);
        bottomNavView.enableItemShiftingMode(false);
        bottomNavView.setTextVisibility(false);

    }

    public static void enableNavigation(final Context context, BottomNavigationViewEx viewEx){
        viewEx.setOnNavigationItemSelectedListener(new BottomNavigationViewEx.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_dashboard:
                        Intent intent1 = new Intent(context, HomeActivity.class); //ACTIVITY_NUM =0
                        context.startActivity(intent1);
                        break;
               /*     case R.id.navigation_history :
                        Intent intent2 = new Intent(context, HistoryAcitivity.class); //ACTIVITY_NUM =1
                        context.startActivity(intent2);
                        break;*/
//                    case R.id.navigation_friends:
//                        Intent intent3 = new Intent(context, FriendsAcitivity.class); //ACTIVITY_NUM =2
//                        context.startActivity(intent3);
//                        break;
                    case R.id.navigation_chat:
                        Intent intent4 = new Intent(context, ChatActivity.class); //ACTIVITY_NUM =3
                        context.startActivity(intent4);
                        break;
                    case R.id.nearby_friends:
                        Intent intent5 = new Intent(context, FriendsAcitivity.class); //ACTIVITY_NUM =4
                        context.startActivity(intent5);
                        break;
                    case R.id.navigation_me:
                        Intent intent6 = new Intent(context, ProfileActivity.class); //ACTIVITY_NUM =5
                        context.startActivity(intent6);
                        break;

                }
                return false;
            }
        });
    }

}
