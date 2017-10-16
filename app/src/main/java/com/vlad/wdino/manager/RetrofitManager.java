package com.vlad.wdino.manager;

import android.util.Log;

import com.vlad.wdino.api.DinoTestAPI;
import com.vlad.wdino.api.ApiController;
import com.vlad.wdino.api.model.formErrors.Errors;
import com.vlad.wdino.api.model.playload.LoginPlayload;
import com.vlad.wdino.api.model.playload.RegisterUserPlayload;
import com.vlad.wdino.api.model.response.RegisterUserResponse;
import com.vlad.wdino.api.model.response.login.LoginResponse;
import com.vlad.wdino.background.BackgroundManager;
import com.vlad.wdino.background.DefaultBackgroundCallback;
import com.vlad.wdino.background.IBackgroundTask;
import com.vlad.wdino.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitManager {


    private RetrofitManager() {
    }

    private static final RetrofitManager ourInstance = new RetrofitManager();

    public static RetrofitManager getInstance() {
        return ourInstance;
    }

    public void registerUser(final RegisterUserPlayload registerUser, final DefaultBackgroundCallback callback) {
        DinoTestAPI dinoTestAPI = ApiController.getInstance();
        final Call<RegisterUserResponse> myCall = dinoTestAPI.registerUserAccount(registerUser);
        myCall.enqueue(new Callback<RegisterUserResponse>() {
            @Override
            public void onResponse(Call<RegisterUserResponse> call, final Response<RegisterUserResponse> response) {
                BackgroundManager.getInstance().doBackgroundTask(new IBackgroundTask<Errors>() {
                    @Override
                    public Errors execute() {
                        if (response.isSuccessful()) {
                            Log.i(Constants.LOG_TAG, "register response is success");
                        } else {
                            Log.i(Constants.LOG_TAG, "register response not success, cause code: " + Integer.toString(response.code()));
                        }
                        return response.body();
                    }
                }, callback);

            }

            @Override
            public void onFailure(Call<RegisterUserResponse> call, Throwable t) {
                Log.e(Constants.LOG_TAG, "register response error: " + t.toString());
                BackgroundManager.getInstance().doBackgroundTask(new IBackgroundTask<RegisterUserResponse>() {
                    @Override
                    public RegisterUserResponse execute() {
                        return null;
                    }
                }, callback);
            }
        });
    }

    public void loginUser(final LoginPlayload loginPlayload, final DefaultBackgroundCallback callback) {
        DinoTestAPI dinoTestAPI = ApiController.getInstance();
        final Call<LoginResponse> myCall = dinoTestAPI.loginWithCredentials(loginPlayload);
        myCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, final Response<LoginResponse> response) {
                BackgroundManager.getInstance().doBackgroundTask(new IBackgroundTask<LoginResponse>() {
                    @Override
                    public LoginResponse execute() {
                        if (response.isSuccessful()) {
                            Log.i(Constants.LOG_TAG, "login response is success");
                        } else {
                            Log.i(Constants.LOG_TAG, "login response not success, cause code: " + Integer.toString(response.code()));
                            String s = response.raw().toString();
                        }
                        return response.body();
                    }
                }, callback);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }
}
