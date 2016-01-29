package com.hardteam.moneytracker.rest.api;

import com.hardteam.moneytracker.rest.model.GoogleTokenStatusModel;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by RG on 27.01.2016.
 */
public interface CheckGoogleTokenApi {
    @GET("/gcheck")
    void tokenStatus(@Query("google_token") String gToken, Callback<GoogleTokenStatusModel> modelCallback);

//    @GET("/gjson")
//    GoogleTokenStatusModel googleJson(@Query("google_token") String gToken);
}
