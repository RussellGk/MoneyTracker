package com.hardteam.moneytracker.rest;

import com.hardteam.moneytracker.rest.api.CategorySynchApi;
import com.hardteam.moneytracker.rest.api.CreateCategoryApi;
import com.hardteam.moneytracker.rest.api.ExpenseSynchApi;
import com.hardteam.moneytracker.rest.api.LoginUserApi;
import com.hardteam.moneytracker.rest.api.RegisterUserApi;
import com.hardteam.moneytracker.rest.model.CreateCategory;
import com.hardteam.moneytracker.util.Constants;

import retrofit.RestAdapter;

/**
 * Created by RG on 23.12.2015.
 */
public class RestClient {
    private static final String BASE_URL = Constants.baseURL;
    private RegisterUserApi registerUserApi;
    // var1 for next request

    private LoginUserApi loginUserApi;
    private CreateCategoryApi createCategoryApi;
    private CategorySynchApi categorySynchApi;
    private ExpenseSynchApi expenseSynchApi;

    public RestClient()
    {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        registerUserApi = restAdapter.create(RegisterUserApi.class);
        // init for var1 for next Adapter

        loginUserApi = restAdapter.create(LoginUserApi.class);

        createCategoryApi = restAdapter.create(CreateCategoryApi.class);

        categorySynchApi = restAdapter.create(CategorySynchApi.class);

        expenseSynchApi = restAdapter.create(ExpenseSynchApi.class);
    }

    public RegisterUserApi getRegisterUserApi() {
        return registerUserApi;
    }

    public LoginUserApi getLoginUserApi()
    {
        return loginUserApi;
    }

    public CreateCategoryApi getCreateCategoryApi() {
        return createCategoryApi;
    }

    public CategorySynchApi getCategorySynchApi() {
        return categorySynchApi;
    }

    public ExpenseSynchApi getExpenseSynchApi() {
        return expenseSynchApi;
    }
}
