package com.hardteam.moneytracker;

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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_VIEW = MainActivity.class.getSimpleName();
    private DrawerLayout drawerLayout;
    private Fragment fragment;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        setupDrawer();

        if(savedInstanceState == null)// by default for fragments show
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new ExpansesFragment()).commit();
        }
        Log.d(LOG_VIEW, "onCreate()");
    }

    private void setupToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();//check the import android.support.v7.app.ActionBar
        if(actionBar != null)
        {
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Fragment findingFragment = getSupportFragmentManager().findFragmentById(R.id.main_container);

        if(findingFragment != null)

            if(findingFragment instanceof ExpansesFragment)
        {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            navigationView.getMenu().findItem(R.id.drawer_expenses).setCheckable(true);

        }
            if(findingFragment instanceof CategoryFragment)
        {
            navigationView.getMenu().findItem(R.id.drawer_categories).setCheckable(true);
        }
            if(findingFragment instanceof StatisticsFragment)
        {
            navigationView.getMenu().findItem(R.id.drawer_statistics).setCheckable(true);
        }
            if(findingFragment instanceof SettingsFragment)
        {
            navigationView.getMenu().findItem(R.id.drawer_settings).setCheckable(true);
        }
    }

    private void setupDrawer()
    {
        drawerLayout =(DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.drawer_expenses:
                        fragment = new ExpansesFragment();
                        break;
                    case R.id.drawer_categories:
                        fragment = new CategoryFragment();
                        break;
                    case R.id.drawer_statistics:
                        fragment = new StatisticsFragment();
                        break;
                    case R.id.drawer_settings:
                        fragment = new SettingsFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).addToBackStack(null).commit();
                item.setChecked(true);
                drawerLayout.closeDrawers();
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //call the Drawer by push Button in Toolbar
        int id = item.getItemId();
        if(id == android.R.id.home)// burger button on Toolbar
        {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
