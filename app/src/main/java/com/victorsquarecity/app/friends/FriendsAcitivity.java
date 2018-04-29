package com.victorsquarecity.app.friends;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.victorsquarecity.app.R;
import com.victorsquarecity.app.utils.HelperFactory;
import com.victorsquarecity.app.utils.db.dao.UserORM;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by app on 6/8/2017.
 */

public class FriendsAcitivity extends AppCompatActivity {
    private static final String TAG = "FriendsAcitivity";
    private Context mCtx = FriendsAcitivity.this;

    FriendListAdapter adapter;

    @BindView(R.id.friendlist)
    RecyclerView recyclerView;

    UserORM user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Friends");

        setContentView(R.layout.friendlist);
        ButterKnife.bind(this);

        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mCtx));
        user = HelperFactory.getUserProfileData(getApplication());
        adapter = new FriendListAdapter(mCtx,user);
        recyclerView.setAdapter(adapter);



        setupFriendFragments(false);
        //setupBottomNavigation();

        /*Button addNewFriendBtn = (Button) findViewById(R.id.add_new_friend_button);
        addNewFriendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onCreateView() search button is clicked for  looking new friend: " );
                setupFriendFragments(true);
            }
        });*/
    }


    /*
 add bottom navmenu
 * */
    private void setupBottomNavigation(){
        Log.d(TAG,"setting bottom navmenu");
  //      BottomNavigationViewEx navigationViewEx =(BottomNavigationViewEx) findViewById(R.id.navigation);
//        BottomNavHelper.setBottomNavView(navigationViewEx);
//        BottomNavHelper.enableNavigation(mCtx,navigationViewEx);
 //       Menu menu = navigationViewEx.getMenu();
 //       MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
 //       menuItem.setChecked(true);
    }

    /*
   * accountable for search type fragments*/
    private void setupFriendFragments(boolean showSearchFragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(showSearchFragment){
            SearchFriendFragment searchFriendFragment = new SearchFriendFragment();
            fragmentTransaction.replace(android.R.id.content, searchFriendFragment);

        }
        fragmentTransaction.commit();
    }
}
