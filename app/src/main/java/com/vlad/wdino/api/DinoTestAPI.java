package com.vlad.wdino.api;

import com.vlad.wdino.api.model.playload.LoginPlayload;
import com.vlad.wdino.api.model.playload.RegisterUserPlayload;
import com.vlad.wdino.api.model.response.login.LoginResponse;
import com.vlad.wdino.api.model.response.RegisterUserResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface DinoTestAPI {

    @POST("rest/user")
    Call<RegisterUserResponse> registerUserAccount(@Body RegisterUserPlayload registerUser);

    @POST("rest/user/login")
    Call<LoginResponse> loginWithCredentials (@Body LoginPlayload loginUser);

    @POST("rest/user/logout")
    Call<ResponseBody> logout (@Header("X-CSRF-Token") String xCSRFToken, @Header("Cookie") String credentials);
}
