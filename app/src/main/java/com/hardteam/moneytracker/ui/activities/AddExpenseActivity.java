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

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

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
            setTitle("Добавить Трату");
        }

        SpinnerCategoryAdapter spinnerAdapter = new SpinnerCategoryAdapter(getDataList());
        listSpinner.setAdapter(spinnerAdapter);
    }

    @Click(R.id.add_button_expense)
    public void clickButton()
    {
        EditText sumField = (EditText)findViewById(R.id.sum_digit);
        EditText noteField = (EditText)findViewById(R.id.note_text);
        EditText dateField = (EditText)findViewById(R.id.date_number);

        if (sumField.getText().toString().equals(""))
        {
            Toast.makeText(this, "Fill the Sum field!", Toast.LENGTH_LONG).show();
        }
        if (noteField.getText().toString().equals(""))
        {
            Toast.makeText(this, "Fill the Note field!", Toast.LENGTH_LONG).show();
        }
        if (dateField.getText().toString().equals(""))
        {
            Toast.makeText(this, "Fill the Date field!", Toast.LENGTH_LONG).show();
        }
        else
        {
            Categories category = (Categories)listSpinner.getSelectedItem();
            Expenses newExpense = new Expenses(sumField.getText().toString(),noteField.getText().toString(),dateField.getText().toString(),category);
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
