package com.victorsquarecity.app.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.victorsquarecity.app.R;
import com.victorsquarecity.app.profile.ProfileActivity;
import com.victorsquarecity.app.services.VictorApp;
import com.victorsquarecity.app.utils.HelperFactory;


public class ResetPasswordActivity extends AppCompatActivity  {
    private ResetPasswordTask mAuthTask = null;

    // UI references.
    
    private EditText mPasswordView;
    private EditText mVerifcationCodeView;

    private View mProgressView;
    private View mVerificationCodeFormView;
    private String userEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        userEmail = bundle.getString("email");

        
        mVerifcationCodeView = (EditText) findViewById(R.id.verification_code);

        mPasswordView = (EditText) findViewById(R.id.verification_code_password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.password || id == EditorInfo.IME_NULL) {
                    attemptVerifyCode();
                    return true;
                }
                return false;
            }
        });

        Button mVerificationCodeButton = (Button) findViewById(R.id.verification_code_button);
        mVerificationCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptVerifyCode();
            }
        });


        mVerificationCodeFormView = findViewById(R.id.verification_code_form);
        mProgressView = findViewById(R.id.verification_code_progress);
    }

    private void attemptVerifyCode() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.

        mPasswordView.setError(null);


        // Store values at the time of the reset attempt.

        String password = mPasswordView.getText().toString();
        String verificationCode = mVerifcationCodeView.getText().toString();
       // String email = ((VictorApp) getApplication()).getEmail();
        //String email =userEmail;


        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
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
            mAuthTask = new ResetPasswordTask(userEmail,password,verificationCode);
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

            mVerificationCodeFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mVerificationCodeFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mVerificationCodeFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mVerificationCodeFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startLogin();
        this.finish();
    }

    public void startLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class ResetPasswordTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final String mVerifcationCode;


        ResetPasswordTask(String email, String password, String verificationCode) {
            mEmail = email;
            mPassword = password;
            mVerifcationCode = verificationCode;
        }

        @Override
        protected Boolean doInBackground(Void... params){
            // TODO: attempt authentication against a network service.

            try {
                boolean isSuccess = HelperFactory.resetPassword(getApplicationContext(), mEmail, mPassword,mVerifcationCode);
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
                Toast.makeText(getApplicationContext(), "Code Verified Successfully", Toast.LENGTH_LONG).show();

                startLogin();
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
