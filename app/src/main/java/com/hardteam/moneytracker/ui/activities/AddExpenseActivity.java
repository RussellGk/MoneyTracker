package com.hardteam.moneytracker.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.hardteam.moneytracker.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

/**
 * Created by RG on 12.12.2015.
 */

@EActivity(R.layout.activity_add_expense)

public class AddExpenseActivity extends AppCompatActivity {

    @ViewById
    Toolbar toolbar;//With private doesn't work
    @OptionsItem(R.id.home)
    void back()
    {
      onBackPressed();
      finish();
    }

    @AfterViews
    void ready() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle("Добавить Трату");
        }
    }
}
