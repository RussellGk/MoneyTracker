package com.hardteam.moneytracker.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hardteam.moneytracker.MoneyTrackerApplication;
import com.hardteam.moneytracker.R;
import com.hardteam.moneytracker.rest.RestService;
import com.hardteam.moneytracker.rest.model.CreateCategory;
import com.hardteam.moneytracker.rest.model.UserLoginModel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;

/**
 * Created by rg on 12/29/15.
 */

@EActivity(R.layout.activity_token)
public class TokenActivity extends AppCompatActivity{

    private final static String LOG_VIEW = TokenActivity.class.getSimpleName();

    @AfterViews
    void ready()
    {
        //Check all cases for Login, Password, Internet and etc...
        login();
    }

    @Background
    void login()
    {
        RestService restService = new RestService();
        UserLoginModel userLoginModel = restService.login("user2","test1");
        MoneyTrackerApplication.setAuthToken(userLoginModel.getAuthToken());

        Log.d(LOG_VIEW, "Status: " + userLoginModel.getStatus() + ", token: "
                + MoneyTrackerApplication.getAuthKey());

        CreateCategory createCategory = restService.createCategory("Test1");
        Log.d(LOG_VIEW,"Status: " + createCategory.getStatus() + ", Title: "
                + createCategory.getData().getTitle() + ", Id: "
                + createCategory.getData().getId());

//        Log.d(LOG_VIEW, "Status: " + userLoginModel.getStatus() + ", token: "
//                + userLoginModel.getAuthToken());
    }

}
