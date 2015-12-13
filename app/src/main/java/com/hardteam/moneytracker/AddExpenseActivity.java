package com.hardteam.moneytracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

/**
 * Created by RG on 12.12.2015.
 */
public class AddExpenseActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        toolbar = (Toolbar) findViewById(R.id.toolbar); // from import android.support.v7.widget.Toolbar;
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String value = intent.getStringExtra("key");
        Log.e("Value", value);

        if (getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home)// burger button on Toolbar
        {
            onBackPressed();
            finish();// add finish() method for Destroy the current Activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
