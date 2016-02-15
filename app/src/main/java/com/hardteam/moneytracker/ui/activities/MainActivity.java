package com.hardteam.moneytracker.ui.activities;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.hardteam.moneytracker.MoneyTrackerApplication;
import com.hardteam.moneytracker.R;
import com.hardteam.moneytracker.database.Categories;
import com.hardteam.moneytracker.rest.RestClient;
import com.hardteam.moneytracker.rest.RestService;
import com.hardteam.moneytracker.rest.model.CreateCategory;
import com.hardteam.moneytracker.rest.model.GooglePlusModel;
import com.hardteam.moneytracker.sync.TrackerSyncAdapter;
import com.hardteam.moneytracker.ui.fragments.CategoryFragment_;
import com.hardteam.moneytracker.ui.fragments.ExpansesFragment_;
import com.hardteam.moneytracker.ui.fragments.SettingsFragment;
import com.hardteam.moneytracker.ui.fragments.StatisticsFragment_;
import com.hardteam.moneytracker.util.Constants;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_main)

public class MainActivity extends AppCompatActivity {

    private final static String LOG_VIEW = MainActivity.class.getSimpleName();

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

        if(getDataList().isEmpty()) {
            createCategories();
        }
        if(savedInstanceState == null)
        {
            getFragmentManager().beginTransaction().replace(R.id.main_container, new ExpansesFragment_()).commit();
        }

        String gToken1 = MoneyTrackerApplication.getGoogleToken(this);

        if(!gToken1.equals("2")) //"2" - the default value when Google plus token equals null(used a normal Auth token)
        {
            googleJsonDataForDrawer();

        }

//        addCategoriesToServer(getDataList());

        TrackerSyncAdapter.initializeSyncAdapter(this); // Init of SyncAdapter



    }

    @Background
    void googleJsonDataForDrawer()
    {
        String gToken = MoneyTrackerApplication.getGoogleToken(this);
        RestClient restGoogleClient = new RestClient();
        GooglePlusModel googlePlusModel = restGoogleClient.getGoogleJsonApi().googleJson(gToken);
        String nameUser = googlePlusModel.getName();
        String emailUser = googlePlusModel.getEmail();
        String pictureUser = googlePlusModel.getPicture();

        try {
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(pictureUser).getContent());

            setPictureToDrawer(bitmap);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setInfoToDrawer(nameUser, emailUser);

    }


    @UiThread
    void setInfoToDrawer(String nameUser, String emailUser)
    {
        TextView googleNameDrawer = (TextView)findViewById(R.id.iname);
        TextView googleMailDrawer = (TextView)findViewById(R.id.imail);
        googleNameDrawer.setText(nameUser);
        googleMailDrawer.setText(emailUser);
    }


    @UiThread
    void setPictureToDrawer(Bitmap bitmap)
    {
//        ImageView googlepPicDrawer = (ImageView) findViewById(R.id.photo);
        CircularImageView googlepPicDrawer = (CircularImageView) findViewById(R.id.photo_new);
        googlepPicDrawer.setImageBitmap(bitmap);
    }

    private void createCategories()
    {
        Categories categoryFun = new Categories(Constants.FUN,0);
        categoryFun.save();
        Categories categoryPhone = new Categories(Constants.PHONE,0);
        categoryPhone.save();
        Categories categoryFood = new Categories(Constants.FOOD,0);
        categoryFood.save();
        Categories categoryBooks = new Categories(Constants.BOOKS,0);
        categoryBooks.save();
    }

    private List<Categories> getDataList()
    {
        return new Select()
                .from(Categories.class)
                .execute();
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
        Fragment findingFragment = getFragmentManager().findFragmentById(R.id.main_container);

        if(findingFragment != null && findingFragment instanceof ExpansesFragment_)
        {
            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            menuItems.findItem(R.id.drawer_expenses).setCheckable(true);

        }
        if(drawerLayout.isEnabled())
        {
            drawerLayout.closeDrawer(navigationView);
        }
    }

    @Background
    void addCategoriesToServer(List<Categories> catList)
    {

        RestService restService = new RestService();

        for(Categories itemCatList : catList)
        {
            CreateCategory createCategory = restService.createCategory(itemCatList.name);
            if(createCategory.getStatus().equalsIgnoreCase(Constants.SUCCESS))
            {
                Log.d(LOG_VIEW, "Status: " + createCategory.getStatus() + ", Title: "
                        + createCategory.getData().getTitle() + ", Id: "
                        + createCategory.getData().getId());
            }
            else if(createCategory.getStatus().equalsIgnoreCase(Constants.UNAUTHORIZED))
            {
                startLoginActivity();
            }
        }
    }

    public void startLoginActivity()
    {
        Intent intentLoginActivity = new Intent(this, LoginActivity_.class);
        startActivity(intentLoginActivity);
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
                        fragment = new SettingsFragment();
                        break;
                }

                getFragmentManager().beginTransaction().replace(R.id.main_container, fragment).addToBackStack(null).commit();
                item.setChecked(true);
                drawerLayout.closeDrawers();
                return false;
            }
        });
    }
}
