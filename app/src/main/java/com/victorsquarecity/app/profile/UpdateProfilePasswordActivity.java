package com.victorsquarecity.app.profile;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.victorsquarecity.app.R;
import com.victorsquarecity.app.services.VictorApp;
import com.victorsquarecity.app.utils.HelperFactory;

import java.util.ArrayList;
import java.util.List;


public class UpdateProfilePasswordActivity extends AppCompatActivity  {
    private UserChangeProfilePasswdTask mAuthTask = null;

    // UI references.
    
    private EditText mPasswordView;
    private EditText mOldPasswordView;

    private View mProgressView;
    private View mUpdateProfilePasswdFormView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);


        
        mOldPasswordView = (EditText) findViewById(R.id.oldpassword);

        mPasswordView = (EditText) findViewById(R.id.password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.password || id == EditorInfo.IME_NULL) {
                    attemptUpdateProfilePassword();
                    return true;
                }
                return false;
            }
        });

        Button mUpdateProfilePasswdButton = (Button) findViewById(R.id.reset_passwd_button);
        mUpdateProfilePasswdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptUpdateProfilePassword();
            }
        });


        mUpdateProfilePasswdFormView = findViewById(R.id.updateprofile_passwd_form);
        mProgressView = findViewById(R.id.updatepasswd_progress);
    }

    private void attemptUpdateProfilePassword() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.

        mPasswordView.setError(null);


        // Store values at the time of the reset attempt.

        String password = mPasswordView.getText().toString();
        String oldpassword = mOldPasswordView.getText().toString();
        String email = ((VictorApp) getApplication()).getEmail();


        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(oldpassword) && !isPasswordValid(oldpassword)) {
            mOldPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mOldPasswordView;
            cancel = true;
        }

        if(oldpassword == password) {
            mPasswordView.setError(getString(R.string.error_invalid_matching_password));
            focusView = mPasswordView;
            cancel = true;
        }

//        // Check for a valid email address.
//        if (!isEmailValid(email)) {
//            mPasswordView.setError(getString(R.string.error_invalid_email));
//            focusView = mPasswordView;
//            cancel = true;
//        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserChangeProfilePasswdTask(email,password,oldpassword);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }



    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
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

            mUpdateProfilePasswdFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mUpdateProfilePasswdFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mUpdateProfilePasswdFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mUpdateProfilePasswdFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
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

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserChangeProfilePasswdTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final String mOldPassword;


        UserChangeProfilePasswdTask(String email, String password, String oldpassword) {
            mEmail = email;
            mPassword = password;
            mOldPassword = oldpassword;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                boolean isSuccess = HelperFactory.updateUserProfilePassword(getApplicationContext(),getApplication(), mEmail, mPassword,mOldPassword);
                Thread.sleep(1000);
                return isSuccess;

            } catch (Exception e) {
                return false;
            }

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                Toast.makeText(getApplicationContext(), "User Profile Updated Successfully", Toast.LENGTH_LONG).show();

                startProfile();
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_failed_to_update));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}
