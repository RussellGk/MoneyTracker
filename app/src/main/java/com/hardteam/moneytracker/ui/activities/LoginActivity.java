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

    @ViewById(R.id.login_field_login)
    EditText loginFieldLogin;

    @ViewById(R.id.password_field_login)
    EditText passFieldLogin;

    @ViewById(R.id.login_button_get)
    Button buttonLogin;

    @ViewById(R.id.login_activity)
    View loginActivityLayout;

    @Click(R.id.login_button_get)
    void buttonLogin()
    {

        if(NetworkStatusChecker.isNetworkAvailable(this))
        {
            LogIn();
        }
        else
        {
            Snackbar.make(loginActivityLayout, Constants.noInternet, Snackbar.LENGTH_LONG).show();
        }
    }

    @Click(R.id.registration_text)
    void textClickRegistration()
    {
        startRegistrationActivity();
    }

    @Background
    void LogIn()
    {

        String loginUserToken = loginFieldLogin.getText().toString();
        String passwordUserToken = passFieldLogin.getText().toString();

        if( loginUserToken.length() < 5 || passwordUserToken.length() < 5 )
        {
            Snackbar.make(loginActivityLayout, Constants.loginPassLength, Snackbar.LENGTH_LONG).show();
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
                Snackbar.make(loginActivityLayout, Constants.wrongPassword, Snackbar.LENGTH_LONG).show();
            }
            else if(userLoginModel.getStatus().equalsIgnoreCase(Constants.wrongLogin))
            {
                Snackbar.make(loginActivityLayout, Constants.wrongLogin, Snackbar.LENGTH_LONG).show();
            }
            else if(userLoginModel.getStatus().equalsIgnoreCase(Constants.error))
            {
                Snackbar.make(loginActivityLayout, Constants.error, Snackbar.LENGTH_LONG).show();
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
        Intent intentRegistrationActivity = new Intent(this, RegisterActivity_.class);
        startActivity(intentRegistrationActivity);
    }


}
