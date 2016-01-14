package com.hardteam.moneytracker;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;
import com.hardteam.moneytracker.util.Constants;

/**
 * Created by RG on 16.12.2015.
 */
public class MoneyTrackerApplication extends Application {

    private static SharedPreferences preferences;//save the Token here

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public static void setAuthToken(String token)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.TOKEN_KEY, token);
//        editor.commit(); save immediatly in UA thread
        editor.apply();
        editor.commit();

    }

    public static String getAuthKey() {
        return preferences.getString(Constants.TOKEN_KEY,"");//"" - default value, it needs for Splash Activity
    }
}
