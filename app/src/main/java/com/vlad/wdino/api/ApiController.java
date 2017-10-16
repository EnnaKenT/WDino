package com.vlad.wdino.api;

import com.vlad.wdino.utils.Constants;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiController {

    private static String accessToken;
    private static Request accessTokenRequest;
    private static final DinoTestAPI apiInterfaceInstance = getApi();

    public static DinoTestAPI getInstance() {
        return apiInterfaceInstance;
    }

    private ApiController() {
    }

    private static DinoTestAPI getApi() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(DinoTestAPI.class);
    }
}
