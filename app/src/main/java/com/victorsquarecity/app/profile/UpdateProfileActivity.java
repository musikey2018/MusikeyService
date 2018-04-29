package com.victorsquarecity.app.profile;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.victorsquarecity.app.R;
import com.victorsquarecity.app.utils.HelperFactory;
import com.victorsquarecity.app.utils.ImageHelper;
import com.victorsquarecity.app.utils.db.dao.UserORM;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class UpdateProfileActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private UpdateProfileTask mAuthTask = null;

    // UI references.
    private ImageView profileimg;
    private AutoCompleteTextView mEmailView;
    private EditText mUsernameView;
    private EditText mNumberView;
    private EditText mAgeView;
    private EditText mCityView;
    private EditText mStateView;
    private int PICK_IMAGE_REQUEST = 111;
    private Bitmap mBitmap;
    private ProgressDialog progressDialog;
    private View mProgressView;
    private View mUpdateProfileFormView;
    private UserORM userProfile = null;

    private static final String TAG = "UpdateProfileActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mUsernameView = (EditText) findViewById(R.id.username);
        mNumberView = (EditText) findViewById(R.id.mobileNumber);
        mStateView = (EditText) findViewById(R.id.state);
        mAgeView = (EditText) findViewById(R.id.age);
        mCityView = (EditText) findViewById(R.id.city);
        mCityView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.regiter || id == EditorInfo.IME_NULL) {
                    attemptUpdateProfile();
                    return true;
                }
                return false;
            }
        });

        setupProfileImage();
        setProfieFeilds();
        profileimg = (ImageView) findViewById(R.id.profileImage);
        profileimg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toasty.success(getApplicationContext(), "User Profile image clicked", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });

        Button mUpdateProfileButton = (Button) findViewById(R.id.update_profile_button);
        mUpdateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptUpdateProfile();
            }
        });


        mUpdateProfileFormView = findViewById(R.id.updateprofile_form);
        mProgressView = findViewById(R.id.updateprofile_progress);
    }

    private void attemptUpdateProfile() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mAgeView.setError(null);
        mCityView.setError(null);
        mStateView.setError(null);
        mUsernameView.setError(null);
        mNumberView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();

        String age = mAgeView.getText().toString();
        String city = mCityView.getText().toString();
        String state = mStateView.getText().toString();
        String username = mUsernameView.getText().toString();
        String number = mNumberView.getText().toString();


        boolean cancel = false;
        View focusView = null;


        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        // Check for a valid username, if the user entered one.
        if (TextUtils.isEmpty(username) && username.length() < 6) {
            mUsernameView.setError(getString(R.string.error_invalid_username));
            focusView = mUsernameView;
            cancel = true;
        }
        // Check for a valid age 0-99, if the user entered one.
        if (!TextUtils.isEmpty(age) && !isAgeValid(age)) {
            mNumberView.setError(getString(R.string.error_invalid_number));
            focusView = mNumberView;
            cancel = true;
        }
        // Check for a valid number, if the user entered one.
        if (!TextUtils.isEmpty(number) && !isNumberValid(number)) {
            mNumberView.setError(getString(R.string.error_invalid_number));
            focusView = mNumberView;
            cancel = true;
        }
        // Check for a valid city, if the user entered one.
        if (TextUtils.isEmpty(city)) {
            mCityView.setError(getString(R.string.error_invalid_city));
            focusView = mCityView;
            cancel = true;
        }
        // Check for a valid state, if the user entered one.
        if (TextUtils.isEmpty(state)) {
            mStateView.setError(getString(R.string.error_invalid_state));
            focusView = mStateView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UpdateProfileTask(email, username, number, age, city, state);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isNumberValid(String number) {

        return Patterns.PHONE.matcher(number).matches();
    }

    private boolean isAgeValid(String number) {
        int age = Integer.parseInt(number);
        if (age > 12 && age < 110)
            return true;
        else
            return false;
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mUpdateProfileFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mUpdateProfileFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mUpdateProfileFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mUpdateProfileFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    public void setupProfileImage() {
        profileimg = (ImageView) findViewById(R.id.profileImage);
        Uri profileuri = Uri.parse("android.resource://" + UpdateProfileActivity.this.getPackageName() + "/" + R.drawable.profile);
        //Log.d("profile");
        Glide.with(UpdateProfileActivity.this).load(profileuri).into(profileimg);
    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(UpdateProfileActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startProfile();
        this.finish();
    }

    public void startProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UpdateProfileTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mUserName;
        private final String mNumber;
        private final String mAge;
        private final String mCity;
        private final String mState;


        UpdateProfileTask(String email, String username, String number, String age, String city, String state) {
            mEmail = email;
            mUserName = username;
            mNumber = number;
            mAge = age;
            mCity = city;
            mState = state;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                boolean isSuccess = HelperFactory.updateUserProfile(getApplicationContext(),getApplication(), mEmail, mUserName, mNumber, mAge, mCity, mState);
                return isSuccess;

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                Toasty.success(getApplicationContext(), "User Profile Updated Successfully", Toast.LENGTH_LONG).show();

                //startProfile();
                //finish();
            } else {
                mUsernameView.setError(getString(R.string.error_failed_to_update));
                mUsernameView.requestFocus();
            }
        }


        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();

            try {
                //getting image from gallery
                mBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                final String imageString = ImageHelper.bitmapToBase64(mBitmap);

                Thread.sleep(100);

                ImageUploadTask imageUploadTask = new ImageUploadTask(mEmailView.getText().toString(), imageString);
                imageUploadTask.execute((Void) null);


            } catch (Exception e) {
                Log.e(TAG,"onActivityResult() e"+e.getMessage());
                e.printStackTrace();
            }
        }
    }


    /* new Response.Listener<String>(){
     @Override
     public void onResponse(String s) {
         //progressDialog.dismiss();

     }
 },new Response.ErrorListener(){
     @Override
     public void onErrorResponse(VolleyError volleyError) {

         // progressDialog.dismiss();
     }
 }*/
    public void setProfieFeilds() {


        userProfile = HelperFactory.getUserProfileData(getApplication());
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mEmailView.setText(userProfile.getMEmail());
        mUsernameView = (EditText) findViewById(R.id.username);
        mUsernameView.setText(userProfile.getMUserName());
        mNumberView = (EditText) findViewById(R.id.mobileNumber);
        mNumberView.setText(userProfile.getMMobileNumber());
        mStateView = (EditText) findViewById(R.id.state);
        mStateView.setText(userProfile.getMState());
        mAgeView = (EditText) findViewById(R.id.age);
        mAgeView.setText(userProfile.getMAge());
        mCityView = (EditText) findViewById(R.id.city);
        mCityView.setText(userProfile.getMCity());

    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class ImageUploadTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mImageString;


        public ImageUploadTask(String email, String imageString) {
            mEmail = email;
            mImageString = imageString;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                //showProgress(true);
                String success = HelperFactory.getInstance().uploadProfileImage(mEmail, mImageString, UpdateProfileActivity.this);
                return Boolean.parseBoolean(success);

            } catch (Exception e) {
                //showProgress(false);
                throw new RuntimeException(e);
            }

        }

        @Override
        protected void onPostExecute(final Boolean result) {
            mAuthTask = null;
            //showProgress(false);

            if (result) {
                Log.d(TAG, "onPostExecute() success" + result);
                //Toasty.success(UpdateProfileActivity.this, "Uploaded Successful", Toast.LENGTH_LONG).show();
                profileimg.setImageBitmap(mBitmap);

            } else {
                Log.d(TAG, "onPostExecute() error " + result);
                // Toasty.error(UpdateProfileActivity.this, "Uploaded unSuccessful!", Toast.LENGTH_LONG).show();
            }
        }


        @Override
        protected void onCancelled() {
            mAuthTask = null;
            //showProgress(false);
        }
    }
}
