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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vlad.wdino.R;
import com.vlad.wdino.api.model.playload.LoginPlayload;
import com.vlad.wdino.api.model.response.login.LoginResponse;
import com.vlad.wdino.background.DefaultBackgroundCallback;
import com.vlad.wdino.manager.RetrofitManager;
import com.vlad.wdino.utils.Constants;
import com.vlad.wdino.utils.InternetUtil;
import com.vlad.wdino.view.dialog.RegisterUserError;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    // UI references.
    private AutoCompleteTextView mLoginView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
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

        Button mLoginButton = (Button) findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegisterActivity();
            }
        });

        mLoginFormView = findViewById(R.id.login_form_scroll);
        mProgressView = findViewById(R.id.login_progress);
        restoreFieldValues(savedInstanceState);
    }

    private void restoreFieldValues(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(Constants.LOGIN_FIELD)) {
            mLoginView.setText(savedInstanceState.getString(Constants.LOGIN_FIELD));
            mPasswordView.setText(savedInstanceState.getString(Constants.PASS_FIELD));
        }
    }

    private void startRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mLoginView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
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
                loginUser(login, password);
            } else {
                showCustomDialog(getString(R.string.no_internet_connection));
            }
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 4;
    }

    private void loginUser(String login, final String password) {
        LoginPlayload loginPlayload = new LoginPlayload();
        loginPlayload.setUsername(login);
        loginPlayload.setPassword(password);
        RetrofitManager.getInstance().loginUser(loginPlayload, new DefaultBackgroundCallback<LoginResponse>() {
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

    private void showCustomDialog(final String string) {
        new RegisterUserError(LoginActivity.this, string, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (string.equals(getString(R.string.wrong_username_or_password))) {
                    mPasswordView.requestFocus();
                }
            }
        })
                .showDialog();
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
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
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(Constants.LOGIN_FIELD, String.valueOf(mLoginView.getText()));
        outState.putString(Constants.PASS_FIELD, String.valueOf(mPasswordView.getText()));
    }
}

