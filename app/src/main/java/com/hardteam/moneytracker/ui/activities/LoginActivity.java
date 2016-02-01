package com.hardteam.moneytracker.ui.activities;

import android.accounts.AccountManager;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
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

import java.io.IOException;

/**
 * Created by rg on 12/29/15.
 */

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity{

//    private final static String LOG_VIEW = LoginActivity.class.getSimpleName();

    private final static String G_PLUS_SCOPE = "oauth2:https://www.googleapis.com/auth/plus.me";
    private final static String USERINFO_SCOPE = "https://www.googleapis.com/auth/userinfo.profile";
    private final static String EMAIL_SCOPE = "https://www.googleapis.com/auth/userinfo.email";
    public final static String SCOPES = G_PLUS_SCOPE + " " + USERINFO_SCOPE + " " + EMAIL_SCOPE;

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
            Snackbar.make(loginActivityLayout, Constants.NO_INTERNET, Snackbar.LENGTH_LONG).show();
        }
    }

    @Click(R.id.registration_text)
    void textClickRegistration()
    {
        startRegistrationActivity();
    }

    @Click(R.id.sign_in_button)
    void btnGplusLogin()
    {
        Intent intent = AccountPicker.newChooseAccountIntent(null,null, new String[]{"com.google"}, false, null, null,null,null);
        startActivityForResult(intent, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 10 && resultCode == RESULT_OK )
        {
         getToken(data);
        }
    }

    @Background
    void getToken(Intent data)
    {
        String token = null;
        final String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        try
        {
            token = GoogleAuthUtil.getToken(LoginActivity.this, accountName, SCOPES);
        }
        catch (UserRecoverableAuthException userAuthEx)
        {
            startActivityForResult(userAuthEx.getIntent(), 10);
        }
        catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
        catch (GoogleAuthException fatakAuthEx) {
            Log.e("LOG_VIEW", "Fatal Exception" + fatakAuthEx.getLocalizedMessage());
        }

        MoneyTrackerApplication.setGoogleToken(this, token);
        Log.e("LOG_VIEW_TOKEN", "GOOGLE_TOKEN" + " " + MoneyTrackerApplication.getGoogleToken(this));

        if(!MoneyTrackerApplication.getGoogleToken(this).equalsIgnoreCase("2"))
        {
            Intent regIntent = new Intent(LoginActivity.this, MainActivity_.class);
            startActivity(regIntent);
            finish();
        }

    }

    @Background
    void LogIn()
    {

        String loginUserToken = loginFieldLogin.getText().toString();
        String passwordUserToken = passFieldLogin.getText().toString();

        if( loginUserToken.length() < 5 || passwordUserToken.length() < 5 )
        {
            Snackbar.make(loginActivityLayout, Constants.LOGIN_PASS_LENGTH, Snackbar.LENGTH_LONG).show();
        }

        else
        {
            RestService restService = new RestService();
            UserLoginModel userLoginModel = restService.login(loginUserToken,passwordUserToken);

            if(userLoginModel.getStatus().equalsIgnoreCase(Constants.SUCCESS))
            {
                MoneyTrackerApplication.setAuthToken(userLoginModel.getAuthToken());
                startMainActivity();
            }
            else if(userLoginModel.getStatus().equalsIgnoreCase(Constants.WRONG_PASSWORD))
            {
                Snackbar.make(loginActivityLayout, Constants.WRONG_PASSWORD, Snackbar.LENGTH_LONG).show();
            }
            else if(userLoginModel.getStatus().equalsIgnoreCase(Constants.WRONG_LOGIN))
            {
                Snackbar.make(loginActivityLayout, Constants.WRONG_LOGIN, Snackbar.LENGTH_LONG).show();
            }
            else if(userLoginModel.getStatus().equalsIgnoreCase(Constants.ERROR))
            {
                Snackbar.make(loginActivityLayout, Constants.ERROR, Snackbar.LENGTH_LONG).show();
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
