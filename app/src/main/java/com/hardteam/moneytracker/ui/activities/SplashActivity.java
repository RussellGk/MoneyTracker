package com.hardteam.moneytracker.ui.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.hardteam.moneytracker.MoneyTrackerApplication;
import com.hardteam.moneytracker.R;
import com.hardteam.moneytracker.util.NetworkStatusChecker;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by rg on 1/3/16.
 */

@EActivity(R.layout.activity_splash)
public class SplashActivity extends AppCompatActivity {

    @ViewById(R.id.welcome_activity_splash)
    View welcomeActivitySplash;

    @AfterViews
    void ready()
    {
        if(NetworkStatusChecker.isNetworkAvailable(this))
        {
            launchActivity();
        }
        else
        {
            Snackbar.make(welcomeActivitySplash, R.string.no_internet, Snackbar.LENGTH_LONG).show();
        }
    }

    @Background (delay=1500)
    void launchActivity()
    {
         if(tokenIsExist().length() != 0)
            {
                startMainActivity();
            }
         else
            {
                startTokenActivity();
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

    public void startTokenActivity()
    {
        Intent intentTokenActivity = new Intent(this, TokenActivity_.class);
        startActivity(intentTokenActivity);
    }
}
