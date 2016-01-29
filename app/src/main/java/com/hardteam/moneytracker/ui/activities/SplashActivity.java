package com.hardteam.moneytracker.ui.activities;

import android.accounts.AccountManager;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.hardteam.moneytracker.MoneyTrackerApplication;
import com.hardteam.moneytracker.R;
import com.hardteam.moneytracker.rest.RestClient;
import com.hardteam.moneytracker.rest.model.GoogleTokenStatusModel;
import com.hardteam.moneytracker.util.Constants;
import com.hardteam.moneytracker.util.NetworkStatusChecker;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by rg on 1/3/16.
 */

@EActivity(R.layout.activity_splash)
public class SplashActivity extends AppCompatActivity {

    private String gToken;
    private RestClient restClient;

    @ViewById(R.id.welcome_activity_splash)
    View welcomeActivitySplash;

    @AfterViews
    void ready()
    {
        restClient = new RestClient();
        gToken = MoneyTrackerApplication.getGoogleToken(this);
        if(gToken.equalsIgnoreCase("2"))
        {
            if(NetworkStatusChecker.isNetworkAvailable(this))
            {
                launchActivity();
            }
            else
            {
                Snackbar.make(welcomeActivitySplash, Constants.NO_INTERNET, Snackbar.LENGTH_LONG).show();
            }
        }
        else
        {
            checkTokenValid();
        }

    }

    @Background
    void checkTokenValid()
    {
        restClient.getCheckGoogleTokenApi().tokenStatus(gToken, new Callback<GoogleTokenStatusModel>() {
            @Override
            public void success(GoogleTokenStatusModel googleTokenStatusModel, Response response) {
                Log.e("LOG_VIEW", googleTokenStatusModel.getStatus());
                if(googleTokenStatusModel.getStatus().equalsIgnoreCase("error"))
                {
                    doubleTokenExc();
                }
                else
                {
                    Intent regIntent = new Intent(SplashActivity.this,MainActivity_.class);
                    startActivity(regIntent);
                    finish();
                }
            }


            @Override
            public void failure(RetrofitError error) {

                doubleTokenExc();
            }
        });
    }

    private void doubleTokenExc()
    {
        Intent intent = AccountPicker.newChooseAccountIntent(null, null, new String[]{"com.google"}, false, null, null, null, null);
        startActivityForResult(intent, 123);
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
            token = GoogleAuthUtil.getToken(SplashActivity.this, accountName, LoginActivity.SCOPES);
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
    }


    @Background (delay=1500)
    void launchActivity()
    {
         if(tokenIsExist().length() != 0)
            {
                startMainActivity();
                finish();
            }
         else
            {
                startLoginActivity();
                finish();
            }
    }

    public String tokenIsExist()
    {
        String token = MoneyTrackerApplication.getAuthKey();
        return token;
    }

    public void startMainActivity()
    {
        Intent intentMainActivity = new Intent(this, MainActivity_.class);
        startActivity(intentMainActivity);
    }

    public void startLoginActivity()
    {
        Intent intentLoginActivity = new Intent(this, LoginActivity_.class);
        startActivity(intentLoginActivity);
    }
}
