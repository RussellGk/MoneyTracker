package com.hardteam.moneytracker.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.activeandroid.query.Select;
import com.google.gson.Gson;
import com.hardteam.moneytracker.R;
import com.hardteam.moneytracker.database.Categories;
import com.hardteam.moneytracker.rest.RestService;
import com.hardteam.moneytracker.rest.model.CreateCategory;
import com.hardteam.moneytracker.rest.model.Data;
import com.hardteam.moneytracker.rest.model.SynchCategory;
import com.hardteam.moneytracker.ui.activities.LoginActivity_;
import com.hardteam.moneytracker.util.Constants;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RG on 22.01.2016.
 */
public class TrackerSyncAdapter extends AbstractThreadedSyncAdapter {

    public static final String LOG_VIEW = TrackerSyncAdapter.class.getSimpleName();

    public TrackerSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

        // different requests
        Log.e(LOG_VIEW, "Syncing method was called!");

        RestService restService = new RestService();
        SynchCategory synchCategory = restService.synchCategory(addCategoriesToServerSynch());

        if(synchCategory.getStatus().equalsIgnoreCase(Constants.success)) {
            Log.d(LOG_VIEW, "Status: " + synchCategory.getStatus());
        }
        else if(synchCategory.getStatus().equalsIgnoreCase(Constants.error))
        {
            //startLoginActivity();
            System.out.println("EEERRROOORRRR!!!!!!!!!!");
        }
    }

    public static void syncImmediately(Context context)
    {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED,true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);

    }

    public static Account getSyncAccount(Context context){
        AccountManager accountManager = (AccountManager)context.getSystemService(Context.ACCOUNT_SERVICE);
        Account newAccount = new Account(context.getString(R.string.app_name),context.getString(R.string.sync_account_type));
        if(null==accountManager.getPassword(newAccount)){
            if(!accountManager.addAccountExplicitly(newAccount,"",null)){
                return null;
            }
            onAccountCreated(newAccount,context);
        }
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context)
    {
        final int SYNC_INTERVAL = 60*60*24;
        final int SYNC_FLEXTIME = SYNC_INTERVAL/3;
        TrackerSyncAdapter.configurePeriodicSync(context,SYNC_INTERVAL,SYNC_FLEXTIME);
        ContentResolver.setSyncAutomatically(newAccount,context.getString(R.string.content_authority),true);
        ContentResolver.addPeriodicSync(newAccount,context.getString(R.string.content_authority),Bundle.EMPTY,SYNC_INTERVAL);
        syncImmediately(context);
    }

    public static void configurePeriodicSync(Context context,int syncInterval,int flexTime)
    {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
   //we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account,authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        }else{
            ContentResolver.addPeriodicSync(account,
                    authority,new Bundle(),syncInterval);
        }
    }

    public static void initializeSyncAdapter(Context context)
    {
        getSyncAccount(context);
    }

    private List<Categories> getDataList()
    {
        return new Select()
                .from(Categories.class)
                .execute();
    }

    public String addCategoriesToServerSynch()
    {
        List<Categories> catList = getDataList();

        ArrayList<String> data = new ArrayList<>();
        Data eachData = new Data();
        Gson gson = new Gson();

        for(Categories itemCatList : catList)

        {
            eachData.setTitle(itemCatList.toString());
            eachData.setId(itemCatList.hashCode());
            data.add(gson.toJson(eachData));
//            String nameData = itemCatList.name;

//            Integer idData = itemCatList.getId().intValue();

//            Data eachData = new Data();
//            eachData.setId(idData);
//            eachData.setId(i++);
//            eachData.setTitle(nameData);
//            data.add(eachData);
        }

        return data.toString();
    }

//    public void startLoginActivity()
//    {
//        Intent intentLoginActivity = new Intent(getContext(),LoginActivity_.class);
//        getContext().startActivity(intentLoginActivity);
//    }

}
