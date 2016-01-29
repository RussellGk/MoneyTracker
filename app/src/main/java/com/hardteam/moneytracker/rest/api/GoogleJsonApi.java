package com.hardteam.moneytracker.rest.api;

import com.hardteam.moneytracker.rest.model.GooglePlusModel;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by RG on 28.01.2016.
 */
public interface GoogleJsonApi {

    @GET("/gjson")
    GooglePlusModel googleJson(@Query("google_token") String gToken);
}
