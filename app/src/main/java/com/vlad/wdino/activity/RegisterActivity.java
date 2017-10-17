package com.vlad.wdino.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vlad.wdino.R;
import com.vlad.wdino.api.ApiController;
import com.vlad.wdino.api.DinoTestAPI;
import com.vlad.wdino.api.model.formErrors.FormErrors;
import com.vlad.wdino.api.model.playload.LoginPlayload;
import com.vlad.wdino.api.model.playload.RegisterUserPlayload;
import com.vlad.wdino.api.model.response.RegisterUserResponse;
import com.vlad.wdino.api.model.response.login.LoginResponse;
import com.vlad.wdino.background.BackgroundManager;
import com.vlad.wdino.background.DefaultBackgroundCallback;
import com.vlad.wdino.background.IBackgroundCallback;
import com.vlad.wdino.background.IBackgroundTask;
import com.vlad.wdino.manager.RetrofitManager;
import com.vlad.wdino.utils.Constants;
import com.vlad.wdino.utils.InternetUtil;
import com.vlad.wdino.view.dialog.RegisterUserError;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private AutoCompleteTextView mLoginView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mLoginView = (AutoCompleteTextView) findViewById(R.id.login);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mLoginView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String login = mLoginView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

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

        // Check for a valid login.
        if (TextUtils.isEmpty(login)) {
            mLoginView.setError(getString(R.string.error_field_required));
            focusView = mLoginView;
            cancel = true;

        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            if (InternetUtil.isInternetTurnOn(this)) {
                showProgress(true);
                registerNewUser(login, email, password);
            } else {
                showCustomDialog(getString(R.string.no_internet_connection));
            }
        }
    }

    private void registerNewUser(final String login, String email, final String password) {
        final RegisterUserPlayload registerUserPlayload = new RegisterUserPlayload();
        registerUserPlayload.setName(login);
        registerUserPlayload.setMail(email);
        registerUserPlayload.setPass(password);

        RetrofitManager.getInstance().registerUser(registerUserPlayload, new DefaultBackgroundCallback<RegisterUserResponse>() {
            @Override
            public void doOnSuccess(RegisterUserResponse result) {
                showProgress(false);

                if (result != null) {
                    if (result.getFormErrors() == null) {
                        showProgress(true);
                        loginUser(login, password);
                    } else {
                        clearInputFields();

                        // name/email already taken or wrong requested data
                        FormErrors formError = result.getFormErrors();
                        String error = formError.getMail();
                        error += formError.getName();
                        showCustomDialog(error);
                    }
                } else {
                    showCustomDialog(getString(R.string.no_internet_connection));
                }
            }
        });
    }

    private void clearInputFields() {
        mLoginView.setText("");
        mEmailView.setText("");
        mPasswordView.setText("");
        mLoginView.requestFocus();
    }

    private void loginUser(final String login, String password) {
        LoginPlayload loginPlayload = new LoginPlayload();
        loginPlayload.setUsername(login);
        loginPlayload.setPassword(password);
        RetrofitManager.getInstance().loginUser(this, loginPlayload, new DefaultBackgroundCallback<LoginResponse>() {
            @Override
            public void doOnSuccess(LoginResponse result) {
                showProgress(false);

                if (result != null) {
                    if (result.getError() == null) {
                        startDinosActivity();
                    } else {
                        showCustomDialog(getString(R.string.wrong_username_or_password));
                    }
                } else {
                    showCustomDialog(getString(R.string.no_internet_connection));
                }
            }
        });
    }

    private void startDinosActivity() {
        Intent intent = new Intent(this, DinosViewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void showCustomDialog(String string) {
        new RegisterUserError(RegisterActivity.this, string, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        })
                .showDialog();
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
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

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}

