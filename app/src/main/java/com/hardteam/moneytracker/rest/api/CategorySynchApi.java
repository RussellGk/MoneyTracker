package com.hardteam.moneytracker.rest.api;

import com.hardteam.moneytracker.rest.model.Data;
import com.hardteam.moneytracker.rest.model.SynchCategory;

import java.util.ArrayList;
import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by RG on 22.01.2016.
 */
public interface CategorySynchApi
{
    @GET("/categories/synch")
    SynchCategory dataSynch(@Query("data") String data,
                            @Query("auth_token") String token);
}
