package com.victorsquarecity.app.profile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.victorsquarecity.app.R;
import com.victorsquarecity.app.login.LoginActivity;
import com.victorsquarecity.app.utils.BottomNavHelper;
import com.victorsquarecity.app.utils.HelperFactory;
import com.victorsquarecity.app.utils.VictorUtil;
import com.victorsquarecity.app.utils.db.dao.UserORM;

/**
 * Created by app on 6/8/2017.
 */

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    private Context mCtx = ProfileActivity.this;
    private static int ACTIVITY_NUM = 3;
    UserORM userORM = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userORM = HelperFactory.getUserProfileData(getApplication());
        setContentView(R.layout.activity_profile);

        setupBottomNavigation();
        setToolbar();
        setupProfileImage();
        setupGridView();
        TextView mtextEditProfile = (TextView) findViewById(R.id.textEditProfile);
        TextView textViewFollowers = (TextView) findViewById(R.id.textViewFollowers);
        TextView textViewTipps = (TextView) findViewById(R.id.textViewTipps);
        TextView textViewReviews = (TextView) findViewById(R.id.textViewReviews);
        TextView userProfileName = (TextView) findViewById(R.id.userProfileName);


        TextView displayname = (TextView) findViewById(R.id.displayname);
        TextView profileDesc = (TextView) findViewById(R.id.profileDesc);
        TextView displayState = (TextView) findViewById(R.id.displayState);


        String name = userORM.getMUserName();
        String state = userORM.getMState();
        String email = userORM.getMEmail();

        String followers = Integer.toString(userORM.getFriends().size());
        String comments = Integer.toString(userORM.getComments().size());
        String checkins = Integer.toString(userORM.getCheckIns().size());

        textViewFollowers.setText(followers);
        textViewTipps.setText(checkins);
        textViewReviews.setText(comments);
        userProfileName.setText(name);

        displayname.setText(name);
        displayState.setText(state);
        profileDesc.setText(email);





        mtextEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptEditProfile();
            }
        });
    }


    private void attemptEditProfile() {
        Intent intent = new Intent(this, UpdateProfileActivity.class);
        startActivity(intent);
        this.finish();
    }

    /*
    * setup profile toolbar*/
    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.profiletoolbar);
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.changePassword:
                        attemptUpdatePassword();
                        break;
                    case R.id.profileLogout:
                        attemptLogout();
                        break;
                }
                return false;
            }
        });
    }

    /*
 add bottom navmenu
 * */
    private void setupBottomNavigation() {
        Log.d(TAG, "setting bottom navmenu");
        BottomNavigationViewEx navigationViewEx = (BottomNavigationViewEx) findViewById(R.id.navigation);
        BottomNavHelper.setBottomNavView(navigationViewEx);
        BottomNavHelper.enableNavigation(mCtx, navigationViewEx);
        Menu menu = navigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

    public void setupProfileImage() {
        ImageView profileImageView = (ImageView) findViewById(R.id.profileImage);
        String imageUrl = userORM.getMImageUrl();
        Glide.with(ProfileActivity.this).load(imageUrl).into(profileImageView);
    }


    public void setupGridView() {
        Log.d(TAG, "setupGridView() userOrm" + userORM);

        Log.d(TAG, "setupGridView() imagesList size " + userORM.getImages().size());
        final GridView listview = (GridView) findViewById(R.id.profileGridView);

        listview.setAdapter(new ImageListAdapter(ProfileActivity.this, userORM.getImages().toArray()));

    }

    private void attemptUpdatePassword() {
        Intent intent = new Intent(this, UpdateProfilePasswordActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void attemptLogout() {
        VictorUtil.clearDatabase(ProfileActivity.this);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// clear back stack
        startActivity(intent);
        this.finish();
    }

}
