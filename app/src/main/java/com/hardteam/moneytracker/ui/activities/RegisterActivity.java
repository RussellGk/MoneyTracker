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
import com.hardteam.moneytracker.rest.model.UserRegistrationModel;
import com.hardteam.moneytracker.util.Constants;
import com.hardteam.moneytracker.util.NetworkStatusChecker;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by RG on 25.12.2015.
 */

@EActivity(R.layout.activity_register)
public class RegisterActivity extends AppCompatActivity {

    private static final String LOG_VIEW = RegisterActivity.class.getSimpleName();

    @ViewById(R.id.register_field)
    EditText registerField;

    @ViewById(R.id.password_field)
    EditText passField;

    @ViewById(R.id.register_button_ok)
    Button buttonOk;

    @ViewById(R.id.register_activity_container)
    View registerActivityLayout;

    @Click(R.id.register_button_ok)
    void loginOk()
    {

        if(NetworkStatusChecker.isNetworkAvailable(this))
        {
            registerUser();
        }
        else
        {
            Snackbar.make(registerActivityLayout, Constants.noInternet, Snackbar.LENGTH_LONG).show();
        }

    }

    @Background
    public void registerUser()
    {
        String loginUser = registerField.getText().toString();
        String passwordUser = passField.getText().toString();

        if( loginUser.length() < 5 || passwordUser.length() < 5 )
        {
            Snackbar.make(registerActivityLayout, Constants.loginPassLength, Snackbar.LENGTH_LONG).show();
        }

        else
        {
            RestService restService = new RestService();
            UserRegistrationModel userRegistrationModel = restService.register(loginUser, passwordUser);

            if(userRegistrationModel.getStatus().equalsIgnoreCase(Constants.success))
            {
                UserLoginModel userLoginModel = restService.login(loginUser,passwordUser);
                MoneyTrackerApplication.setAuthToken(userLoginModel.getAuthToken());

                startMainActivity();
            }
            else
            {
                Snackbar.make(registerActivityLayout, Constants.registrationBusy, Snackbar.LENGTH_LONG).show();
            }

        }
    }

    public void startMainActivity()
    {
        Intent intentMainActivity = new Intent(this, MainActivity_.class);
        startActivity(intentMainActivity);
    }
}
