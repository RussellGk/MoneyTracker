package com.hardteam.moneytracker.rest;

import com.hardteam.moneytracker.MoneyTrackerApplication;
import com.hardteam.moneytracker.rest.model.CreateCategory;
import com.hardteam.moneytracker.rest.model.Data;
import com.hardteam.moneytracker.rest.model.ExpenseSynch;
import com.hardteam.moneytracker.rest.model.SynchCategory;
import com.hardteam.moneytracker.rest.model.UserLoginModel;
import com.hardteam.moneytracker.rest.model.UserRegistrationModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RG on 23.12.2015.
 */
public class RestService {

    private static final String REGISTER_FLAG = "1";

    private RestClient restClient;

    public RestService()
    {
        restClient = new RestClient();
    }

    public UserRegistrationModel register(String login, String password)
    {
        return restClient.getRegisterUserApi().registerUser(login, password, REGISTER_FLAG);
    }

    public UserLoginModel login(String login, String password)
    {
        return restClient.getLoginUserApi().loginUser(login, password);
    }

    //check if the token was gotten before launch this method
    public CreateCategory createCategory(String title)
    {
        return restClient.getCreateCategoryApi().createCategory(title,
                MoneyTrackerApplication.getAuthKey());
    }

    public SynchCategory synchCategory(String gToken, String data)
    {
       return restClient.getCategorySynchApi().dataSynch(gToken, data, MoneyTrackerApplication.getAuthKey());
    }

    public ExpenseSynch expenseSynch(String gToken,String dataExpense)
    {
        return restClient.getExpenseSynchApi().dataExpenseSynch(gToken, dataExpense, MoneyTrackerApplication.getAuthKey());
    }
}


