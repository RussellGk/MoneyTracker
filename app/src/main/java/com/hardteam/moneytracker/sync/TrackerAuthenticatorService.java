package com.hardteam.moneytracker.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by RG on 22.01.2016.
 */
public class TrackerAuthenticatorService extends Service {

    private TrackerAuthenticator mTrackerAuthenticator;

    @Override
    public void onCreate() {
        mTrackerAuthenticator = new TrackerAuthenticator(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
