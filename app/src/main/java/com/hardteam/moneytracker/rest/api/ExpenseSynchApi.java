package com.hardteam.moneytracker.rest.api;

import com.hardteam.moneytracker.rest.model.ExpenseSynch;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by RG on 24.01.2016.
 */
public interface ExpenseSynchApi {

    @GET("/transactions/synch")
    ExpenseSynch dataExpenseSynch(@Query("google_token") String gToken, @Query("data") String data,
                              @Query("auth_token") String token);

}
