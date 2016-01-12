package com.hardteam.moneytracker.ui.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hardteam.moneytracker.MoneyTrackerApplication;
import com.hardteam.moneytracker.R;
import com.hardteam.moneytracker.rest.RestService;
import com.hardteam.moneytracker.rest.model.UserLoginModel;
import com.hardteam.moneytracker.rest.model.UserRegistrationModel;
import com.hardteam.moneytracker.util.NetworkStatusChecker;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by RG on 25.12.2015.
 */

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {

    private static final String LOG_VIEW = LoginActivity.class.getSimpleName();

    @ViewById(R.id.login_field)
    EditText loginField;

    @ViewById(R.id.password_field)
    EditText passField;

    @ViewById(R.id.login_button_ok)
    Button buttonOk;

    @ViewById(R.id.login_activity_container)
    View loginActivityLayout;

    @Click(R.id.login_button_ok)
    void loginOk()
    {

        if(NetworkStatusChecker.isNetworkAvailable(this))
        {
            registerUser();
        }
        else
        {
            Snackbar.make(loginActivityLayout, R.string.no_internet, Snackbar.LENGTH_LONG).show();
        }

    }

    @Background
    public void registerUser()
    {
        String loginUser = loginField.getText().toString();
        String passwordUser = passField.getText().toString();

        if( loginUser.length() < 5 || passwordUser.length() < 5 )
        {
            Snackbar.make(loginActivityLayout, R.string.login_pass_length, Snackbar.LENGTH_LONG).show();
        }

        else
        {
            RestService restService = new RestService();
            UserRegistrationModel userRegistrationModel = restService.register(loginUser, passwordUser);

            if(userRegistrationModel.getStatus().equalsIgnoreCase("success"))
            {
                UserLoginModel userLoginModel = restService.login(loginUser,passwordUser);
                MoneyTrackerApplication.setAuthToken(userLoginModel.getAuthToken());

                startMainActivity();
            }
            else
            {
                Snackbar.make(loginActivityLayout, R.string.registration_busy, Snackbar.LENGTH_LONG).show();
            }

        }
    }

    public void startMainActivity()
    {
        Intent intentMainActivity = new Intent(this, MainActivity_.class);
        startActivity(intentMainActivity);
    }
}
