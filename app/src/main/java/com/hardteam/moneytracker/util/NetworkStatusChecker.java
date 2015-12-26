package com.hardteam.moneytracker.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by RG on 25.12.2015.
 */
public class NetworkStatusChecker {

/**
 *Returnstrueifthenetworkisavailableoraboutbecomeavailable.
 *@paramcontextusedtogettheConnectivityManager
 *@return
 ***/
        public static boolean isNetworkAvailable(Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
}