package com.hardteam.moneytracker.ui.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hardteam.moneytracker.MoneyTrackerApplication;
import com.hardteam.moneytracker.R;
import com.hardteam.moneytracker.rest.RestService;
import com.hardteam.moneytracker.rest.model.CreateCategory;
import com.hardteam.moneytracker.rest.model.UserLoginModel;
import com.hardteam.moneytracker.util.NetworkStatusChecker;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by rg on 12/29/15.
 */

@EActivity(R.layout.activity_token)
public class TokenActivity extends AppCompatActivity{

    private final static String LOG_VIEW = TokenActivity.class.getSimpleName();

    @ViewById(R.id.login_field_token)
    EditText loginFieldToken;

    @ViewById(R.id.password_field_token)
    EditText passFieldToken;

    @ViewById(R.id.token_button_get)
    Button buttonToken;

    @ViewById(R.id.token_activity)
    View tokenActivityLayout;

    //@ViewById(R.id.registration_token_text)
    //TextView tokenText;

    @Click(R.id.token_button_get)
    void buttonToken()
    {

        if(NetworkStatusChecker.isNetworkAvailable(this))
        {
            tokenIn();
        }
        else
        {
            Snackbar.make(tokenActivityLayout, R.string.no_internet, Snackbar.LENGTH_LONG).show();
        }
    }

    @Click(R.id.registration_token_text)
    void textClickRegistration()
    {
        startRegistrationActivity();
    }

//    @AfterViews
//    void ready()
//    {
//        //Check all cases for Login, Password, Internet and etc...
//        login();
//    }

    @Background
    void tokenIn()
    {

        String loginUserToken = loginFieldToken.getText().toString();
        String passwordUserToken = passFieldToken.getText().toString();

        if( loginUserToken.length() < 5 || passwordUserToken.length() < 5 )
        {
            Snackbar.make(tokenActivityLayout, R.string.login_pass_length, Snackbar.LENGTH_LONG).show();
        }

        else
        {
            RestService restService = new RestService();
            UserLoginModel userLoginModel = restService.login(loginUserToken,passwordUserToken);

            if(userLoginModel.getStatus().equalsIgnoreCase("success"))
            {
                MoneyTrackerApplication.setAuthToken(userLoginModel.getAuthToken());
                startMainActivity();
            }
            else if(userLoginModel.getStatus().equalsIgnoreCase("Wrong password"))
            {
                Snackbar.make(tokenActivityLayout, "Wrong password", Snackbar.LENGTH_LONG).show();
            }
            else if(userLoginModel.getStatus().equalsIgnoreCase("Wrong login"))
            {
                Snackbar.make(tokenActivityLayout, "Wrong login", Snackbar.LENGTH_LONG).show();
            }
            else if(userLoginModel.getStatus().equalsIgnoreCase("Error"))
            {
                Snackbar.make(tokenActivityLayout, "Error", Snackbar.LENGTH_LONG).show();
            }

//            MoneyTrackerApplication.setAuthToken(userLoginModel.getAuthToken());

//            Log.d(LOG_VIEW, "Status: " + userLoginModel.getStatus() + ", token: "
//                    + MoneyTrackerApplication.getAuthKey());
//            CreateCategory createCategory = restService.createCategory("Test1");

//            Log.d(LOG_VIEW,"Status: " + createCategory.getStatus() + ", Title: "
//                    + createCategory.getData().getTitle() + ", Id: "
//                    + createCategory.getData().getId());
        }


//        Log.d(LOG_VIEW, "Status: " + userLoginModel.getStatus() + ", token: "
//                + userLoginModel.getAuthToken());
    }

    public void startMainActivity()
    {
        Intent intentMainActivity = new Intent(this, MainActivity_.class);
        startActivity(intentMainActivity);
    }

    public void startRegistrationActivity()
    {
        Intent intentRegistrationActivity = new Intent(this, LoginActivity_.class);
        startActivity(intentRegistrationActivity);
    }


}
