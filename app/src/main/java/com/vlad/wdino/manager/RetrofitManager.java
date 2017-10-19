package com.vlad.wdino.manager;

import android.util.Log;

import com.vlad.wdino.WDinoApp;
import com.vlad.wdino.api.DinoTestAPI;
import com.vlad.wdino.api.ApiController;
import com.vlad.wdino.api.model.formErrors.Errors;
import com.vlad.wdino.api.model.formErrors.FormErrors;
import com.vlad.wdino.api.model.playload.CreateImagePlayload;
import com.vlad.wdino.api.model.playload.LoginPlayload;
import com.vlad.wdino.api.model.playload.RegisterUserPlayload;
import com.vlad.wdino.api.model.playload.createDino.CreateDinoPlayload;
import com.vlad.wdino.api.model.response.CreateDinoResponse;
import com.vlad.wdino.api.model.response.CreateImageResponse;
import com.vlad.wdino.api.model.response.RegisterUserResponse;
import com.vlad.wdino.api.model.response.login.LoginResponse;
import com.vlad.wdino.background.BackgroundManager;
import com.vlad.wdino.background.DefaultBackgroundCallback;
import com.vlad.wdino.background.IBackgroundTask;
import com.vlad.wdino.model.Dino;
import com.vlad.wdino.model.Dinos;
import com.vlad.wdino.utils.Constants;
import com.vlad.wdino.utils.SharedPreferenceHelper;

import java.util.List;

import okhttp3.ResponseBody;
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
                        int code = response.code();
                        if (response.isSuccessful()) {
                            Log.i(Constants.LOG_TAG, "register response is success");
                            return response.body();
                        } else if (code == 400) {
                            RegisterUserResponse r = new RegisterUserResponse();
                            FormErrors f = new FormErrors();
                            f.setMail("Wrong request data");
                            r.setFormErrors(f);
                            return r;
                        } else if (code == 406) {
                            return response.body();
                        } else {
                            Log.i(Constants.LOG_TAG, "register response not success, cause code: " + Integer.toString(response.code()));
                        }
                        return null;
                    }
                }, callback);

            }

            @Override
            public void onFailure(Call<RegisterUserResponse> call, Throwable t) {
                Log.e(Constants.LOG_TAG, "register response failed, cause: " + t.toString());
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
                        LoginResponse loginResponse;
                        if (response.isSuccessful()) {
                            loginResponse = response.body();
                            savedLoginResponse(loginResponse);
                            Log.i(Constants.LOG_TAG, "login response is success");
                        } else {
                            Log.i(Constants.LOG_TAG, "login response not success, cause code: " + response.code());
                            loginResponse = new LoginResponse();
                            loginResponse.setError(response.raw().toString());
                        }
                        return loginResponse;
                    }
                }, callback);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e(Constants.LOG_TAG, "login response failed, cause: " + t.toString());
                BackgroundManager.getInstance().doBackgroundTask(new IBackgroundTask<LoginResponse>() {
                    @Override
                    public LoginResponse execute() {
                        return null;
                    }
                }, callback);
            }
        });
    }

    public void getDinos(final DefaultBackgroundCallback callback) {
        DinoTestAPI dinoTestAPI = ApiController.getInstance();
        Call<Dinos> myCall = dinoTestAPI.getDinos();
        myCall.enqueue(new Callback<Dinos>() {
            @Override
            public void onResponse(Call<Dinos> call, final Response<Dinos> response) {
                if (response.isSuccessful()) {
                    Log.i(Constants.LOG_TAG, "getDinos response is success");
                    BackgroundManager.getInstance().doBackgroundTask(new IBackgroundTask<List<Dino>>() {
                        @Override
                        public List<Dino> execute() {
                            return response.body().getDinos();
                        }
                    }, callback);
                } else {
                    Log.i(Constants.LOG_TAG, "getDinos response isn't success, cause code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Dinos> call, Throwable t) {
                Log.e(Constants.LOG_TAG, "getDinos response failed, cause :" + t.toString());
            }
        });
    }

    public void createImage(CreateImagePlayload playload, LoginResponse user, final DefaultBackgroundCallback callback) {
        DinoTestAPI dinoTestAPI = ApiController.getInstance();
        String XCSRFToken = user.getToken();
        String cookie = user.getSessionName() + "=" + user.getSessid();
        Call<CreateImageResponse> myCall = dinoTestAPI.createImage(playload, XCSRFToken, cookie);
        myCall.enqueue(new Callback<CreateImageResponse>() {
            @Override
            public void onResponse(Call<CreateImageResponse> call, final Response<CreateImageResponse> response) {
                if (response.isSuccessful()) {
                    Log.i(Constants.LOG_TAG, "createImage response is success");
                    BackgroundManager.getInstance().doBackgroundTask(new IBackgroundTask<CreateImageResponse>() {
                        @Override
                        public CreateImageResponse execute() {
                            String fid = response.body().getFid();
                            if (fid != null) {
                                saveFID(fid);
                            }
                            return response.body();
                        }
                    }, callback);
                } else {
                    Log.i(Constants.LOG_TAG, "createImage response isn't success, cause code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<CreateImageResponse> call, Throwable t) {
                Log.e(Constants.LOG_TAG, "createImage response failed, cause :" + t.toString());
            }
        });
    }

    public void createDino(CreateDinoPlayload playload, LoginResponse user, final DefaultBackgroundCallback callback) {
        DinoTestAPI dinoTestAPI = ApiController.getInstance();
        String XCSRFToken = user.getToken();
        String cookie = user.getSessionName() + "=" + user.getSessid();
        Call<CreateDinoResponse> myCall = dinoTestAPI.createDino(playload, XCSRFToken, cookie);
        myCall.enqueue(new Callback<CreateDinoResponse>() {
            @Override
            public void onResponse(Call<CreateDinoResponse> call, final Response<CreateDinoResponse> response) {
                if (response.isSuccessful()) {
                    Log.i(Constants.LOG_TAG, "createDino response is success");
                    BackgroundManager.getInstance().doBackgroundTask(new IBackgroundTask<CreateDinoResponse>() {
                        @Override
                        public CreateDinoResponse execute() {
                            String nid = response.body().getNid();
                            if (nid != null) {
                                saveNID(nid);
                            }
                            return response.body();
                        }
                    }, callback);
                } else {
                    Log.i(Constants.LOG_TAG, "createDino response isn't success, cause code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<CreateDinoResponse> call, Throwable t) {
                Log.e(Constants.LOG_TAG, "createDino response failed, cause :" + t.toString());
            }
        });
    }

    public void logout(LoginResponse user, final DefaultBackgroundCallback callback) {
        DinoTestAPI dinoTestAPI = ApiController.getInstance();
        String XCSRFToken = user.getToken();
        String cookie = user.getSessionName() + "=" + user.getSessid();
        Call<ResponseBody> myCall = dinoTestAPI.logout(XCSRFToken, cookie);
        myCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.i(Constants.LOG_TAG, "logout response is success");
                    BackgroundManager.getInstance().doBackgroundTask(new IBackgroundTask<String>() {
                        @Override
                        public String execute() {
                            String result = response.body().toString();
                            return result;
                        }
                    }, callback);
                } else {
                    Log.i(Constants.LOG_TAG, "logout response isn't success, cause code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(Constants.LOG_TAG, "logout response failed, cause :" + t.toString());
            }
        });

    }

    private void savedLoginResponse(LoginResponse loginResponse) {
        SharedPreferenceHelper.putObject(WDinoApp.getCurrentActivity().getApplicationContext(), SharedPreferenceHelper.KEY_LOGIN_USER, loginResponse);
    }

    private void saveFID(String fid) {
        SharedPreferenceHelper.putObject(WDinoApp.getCurrentActivity().getApplicationContext(), SharedPreferenceHelper.KEY_FID, fid);
    }

    private void saveNID(String nid) {
        SharedPreferenceHelper.putObject(WDinoApp.getCurrentActivity().getApplicationContext(), SharedPreferenceHelper.KEY_FID, nid);
    }
}
