package com.hardteam.moneytracker.rest;

import com.hardteam.moneytracker.rest.api.RegisterUserApi;

import retrofit.RestAdapter;

/**
 * Created by RG on 23.12.2015.
 */
public class RestClient {
    private static final String BASE_URL = "http://lmt.loftblog.tmweb.ru";
    private RegisterUserApi registerUserApi;
    // var1 for next request

    public RestClient()
    {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        registerUserApi = restAdapter.create(RegisterUserApi.class);
        // init for var1 for next Adapter
    }

    public RegisterUserApi getRegisterUserApi() {
        return registerUserApi;
    }
}
