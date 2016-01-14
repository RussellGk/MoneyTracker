package com.hardteam.moneytracker.rest.api;

import com.hardteam.moneytracker.rest.model.CreateCategory;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by rg on 12/29/15.
 */
public interface CreateCategoryApi {

    @GET("/categories/add")
    CreateCategory createCategory(@Query("title") String title,
                                  @Query("auth_token") String token);

}
