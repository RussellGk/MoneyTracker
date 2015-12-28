package com.hardteam.moneytracker.rest.api;

import com.hardteam.moneytracker.rest.model.UserRegistrationModel;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by RG on 23.12.2015.
 */
public interface RegisterUserApi {

    @GET("/auth")
    UserRegistrationModel registerUser(@Query("login") String login,
                                       @Query("password") String password,
                                       @Query("register") String register);
}
