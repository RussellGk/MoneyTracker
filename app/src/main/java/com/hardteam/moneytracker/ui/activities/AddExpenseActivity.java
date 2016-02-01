package com.hardteam.moneytracker.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.hardteam.moneytracker.R;
import com.hardteam.moneytracker.adapters.CategoryAdapter;
import com.hardteam.moneytracker.adapters.SpinnerCategoryAdapter;
import com.hardteam.moneytracker.database.Categories;
import com.hardteam.moneytracker.database.Expenses;
import com.hardteam.moneytracker.rest.RestService;
import com.hardteam.moneytracker.rest.model.UserRegistrationModel;
import com.hardteam.moneytracker.util.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by RG on 12.12.2015.
 */

@EActivity(R.layout.activity_add_expense)

public class AddExpenseActivity extends AppCompatActivity {

    @ViewById
    Toolbar toolbar;//With private doesn't work

    @ViewById(R.id.spinner_category)
    Spinner listSpinner;

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
            setTitle(Constants.EXPENSE_ADD);
        }

        SpinnerCategoryAdapter spinnerAdapter = new SpinnerCategoryAdapter(getDataList());
        listSpinner.setAdapter(spinnerAdapter);
    }


    @Click(R.id.add_button_expense)
    public void clickButton()
    {
        EditText sumField = (EditText)findViewById(R.id.sum_digit);
        EditText noteField = (EditText)findViewById(R.id.note_text);

        Date date = new Date();
        SimpleDateFormat data_human = new SimpleDateFormat("dd.MM.yyyy");
        String currentDate = data_human.format(date);


        if (sumField.getText().toString().equals(""))
        {
            Toast.makeText(this, Constants.FILL_SUM_FIELD, Toast.LENGTH_LONG).show();
        }
        if (noteField.getText().toString().equals(""))
        {
            Toast.makeText(this, Constants.FILL_NOTE_FIELD, Toast.LENGTH_LONG).show();
        }

        else
        {
            Categories category = (Categories)listSpinner.getSelectedItem();
            Expenses newExpense = new Expenses(sumField.getText().toString(),noteField.getText().toString(),currentDate,category);
            newExpense.save();
            back();
        }

    }


    private List<Categories> getDataList()
    {
        return new Select()
                .from(Categories.class)
                .execute();
    }
}
