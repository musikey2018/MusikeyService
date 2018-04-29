package com.victorsquarecity.app.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.sdsmdg.tastytoast.TastyToast;
import com.victorsquarecity.app.R;
import com.victorsquarecity.app.home.MainActivity;
import com.victorsquarecity.app.profile.ProfileActivity;
import com.victorsquarecity.app.profile.UpdateProfileActivity;
import com.victorsquarecity.app.utils.HelperFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 */
public class ForgetActivity extends AppCompatActivity {


    /**
     * Keep track of the forget task to ensure we can cancel it if requested.
     */
    private ForgetPasswordTask mAuthTask = null;


    @BindView(R.id.email)
    AutoCompleteTextView mEmailView;


    @BindView(R.id.send_forgotpasswd_mail_form)
    View mSendforgotpasswdFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.forget_password_button)
    public void forgetPassword(View view){
        String email = mEmailView.getText().toString();
        if(isEmailValid(email)){
            mAuthTask = new ForgetPasswordTask(email);
            mAuthTask.execute((Void) null);
        }else{
            TastyToast.makeText(getBaseContext(), "Enter valid email.", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
        }
    }
    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    private void startMain() {
       Intent intent = new Intent(this, ResetPasswordActivity.class);
        intent.putExtra("email",mEmailView.getText().toString());
        startActivity(intent);
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

            mSendforgotpasswdFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mSendforgotpasswdFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mSendforgotpasswdFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });


        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mSendforgotpasswdFormView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class ForgetPasswordTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;

        ForgetPasswordTask(String email) {
            mEmail = email;
        }

        @Override
        protected Boolean doInBackground(Void... params) {


            try {
                boolean isSuccess = HelperFactory.sendUserForgotPasswordMail(getApplicationContext(), mEmail);
                return isSuccess;

            } catch (Exception e){
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                startMain();
                finish();
            }else{
                TastyToast.makeText(getBaseContext(), "Update password not successful", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    @Override
    public void onBackPressed() {
        startLogin();
    }

    private void startLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finish();
    }
}

