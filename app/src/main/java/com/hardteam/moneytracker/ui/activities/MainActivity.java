package com.hardteam.moneytracker.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.hardteam.moneytracker.R;
import com.hardteam.moneytracker.database.Categories;
import com.hardteam.moneytracker.ui.fragments.CategoryFragment_;
import com.hardteam.moneytracker.ui.fragments.ExpansesFragment_;
import com.hardteam.moneytracker.ui.fragments.SettingsFragment_;
import com.hardteam.moneytracker.ui.fragments.StatisticsFragment_;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_main)

public class MainActivity extends AppCompatActivity {

    private Fragment fragment;

    @ViewById
    Toolbar toolbar;

    @ViewById(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @ViewById(R.id.navigation_view)
    NavigationView navigationView;

    @InstanceState
    Bundle savedInstanceState;

    @AfterViews
    void ready() {

        setupToolbar();
        setupDrawer();

        createCategories();

        if(savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new ExpansesFragment_()).commit();
        }

    }

    private void createCategories()
    {
        Categories categoryFun = new Categories("Fun");
        categoryFun.save();
        Categories categoryPhone = new Categories("Phone");
        categoryPhone.save();
        Categories categoryFood = new Categories("Food");
        categoryFood.save();
        Categories categoryBooks = new Categories("Books");
        categoryBooks.save();
    }

    private void setupToolbar()
    {

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();//check the import android.support.v7.app.ActionBar
        if(actionBar != null)
        {
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @OptionsItem(android.R.id.home)
    void openDrawerByButton() {
    drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Menu menuItems = navigationView.getMenu();
        Fragment findingFragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if(findingFragment != null && findingFragment instanceof ExpansesFragment_)
        {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            menuItems.findItem(R.id.drawer_expenses).setCheckable(true);

        }
        if(drawerLayout.isEnabled())
        {
            drawerLayout.closeDrawer(navigationView);
        }
    }

    private void setupDrawer()
    {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.drawer_expenses:
                        fragment = new ExpansesFragment_();
                        break;
                    case R.id.drawer_categories:
                        fragment = new CategoryFragment_();
                        break;
                    case R.id.drawer_statistics:
                        fragment = new StatisticsFragment_();
                        break;
                    case R.id.drawer_settings:
                        fragment = new SettingsFragment_();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).addToBackStack(null).commit();
                item.setChecked(true);
                drawerLayout.closeDrawers();
                return false;
            }
        });
    }
}