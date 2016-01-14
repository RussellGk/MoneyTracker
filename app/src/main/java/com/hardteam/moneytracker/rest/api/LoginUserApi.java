package com.hardteam.moneytracker.rest.api;

import com.hardteam.moneytracker.rest.model.UserLoginModel;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by rg on 12/29/15.
 */
public interface LoginUserApi {

    @GET("/auth")
    UserLoginModel loginUser(@Query("login") String login, @Query("password") String password);
}
