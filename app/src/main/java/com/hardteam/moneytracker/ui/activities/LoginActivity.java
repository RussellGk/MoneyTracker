package com.hardteam.moneytracker.ui.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hardteam.moneytracker.MoneyTrackerApplication;
import com.hardteam.moneytracker.R;
import com.hardteam.moneytracker.rest.RestService;
import com.hardteam.moneytracker.rest.model.UserLoginModel;
import com.hardteam.moneytracker.util.Constants;
import com.hardteam.moneytracker.util.NetworkStatusChecker;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by rg on 12/29/15.
 */

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity{

    @ViewById(R.id.login_field_token)
    EditText loginFieldToken;

    @ViewById(R.id.password_field_token)
    EditText passFieldToken;

    @ViewById(R.id.token_button_get)
    Button buttonToken;//Login

    @ViewById(R.id.token_activity)
    View tokenActivityLayout;

    @Click(R.id.token_button_get)
    void buttonToken()//Login
    {

        if(NetworkStatusChecker.isNetworkAvailable(this))
        {
            tokenIn();
        }
        else
        {
            Snackbar.make(tokenActivityLayout, Constants.noInternet, Snackbar.LENGTH_LONG).show();
        }
    }

    @Click(R.id.registration_token_text)
    void textClickRegistration()
    {
        startRegistrationActivity();
    }

    @Background
    void tokenIn()
    {

        String loginUserToken = loginFieldToken.getText().toString();
        String passwordUserToken = passFieldToken.getText().toString();

        if( loginUserToken.length() < 5 || passwordUserToken.length() < 5 )
        {
            Snackbar.make(tokenActivityLayout, Constants.loginPassLength, Snackbar.LENGTH_LONG).show();
        }

        else
        {
            RestService restService = new RestService();
            UserLoginModel userLoginModel = restService.login(loginUserToken,passwordUserToken);

            if(userLoginModel.getStatus().equalsIgnoreCase(Constants.success))
            {
                MoneyTrackerApplication.setAuthToken(userLoginModel.getAuthToken());
                startMainActivity();
            }
            else if(userLoginModel.getStatus().equalsIgnoreCase(Constants.wrongPassword))
            {
                Snackbar.make(tokenActivityLayout, Constants.wrongPassword, Snackbar.LENGTH_LONG).show();
            }
            else if(userLoginModel.getStatus().equalsIgnoreCase(Constants.wrongLogin))
            {
                Snackbar.make(tokenActivityLayout, Constants.wrongLogin, Snackbar.LENGTH_LONG).show();
            }
            else if(userLoginModel.getStatus().equalsIgnoreCase(Constants.error))
            {
                Snackbar.make(tokenActivityLayout, Constants.error, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    public void startMainActivity()
    {
        Intent intentMainActivity = new Intent(this, MainActivity_.class);
        startActivity(intentMainActivity);
    }

    public void startRegistrationActivity()
    {
        Intent intentRegistrationActivity = new Intent(this, RegistrActivity_.class);
        startActivity(intentRegistrationActivity);
    }


}
