package com.hardteam.moneytracker;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;

/**
 * Created by RG on 16.12.2015.
 */
public class MoneyTrackerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
