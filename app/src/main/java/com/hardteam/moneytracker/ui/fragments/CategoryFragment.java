package com.hardteam.moneytracker.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.activeandroid.query.Select;
import com.hardteam.moneytracker.Category;
import com.hardteam.moneytracker.adapters.CategoryAdapter;
import com.hardteam.moneytracker.R;
import com.hardteam.moneytracker.adapters.ExpensesAdapter;
import com.hardteam.moneytracker.database.Categories;
import com.hardteam.moneytracker.database.Expenses;
import com.hardteam.moneytracker.util.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RG on 07.12.2015.
 */

@EFragment(R.layout.category_fragment)

public class CategoryFragment extends Fragment {

    @ViewById(R.id.category_recyclerview)
    RecyclerView categoryRecycleView;

    @ViewById(R.id.fab_category)
    FloatingActionButton floatingActionButton;

    @Click(R.id.fab_category)
    void myButtonWasClicked() {
        Snackbar.make(getView(), Constants.work, Snackbar.LENGTH_SHORT).show();
    }

    @AfterViews
    void ready(){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecycleView.setLayoutManager(linearLayoutManager);
        if (floatingActionButton.isPressed()){
            myButtonWasClicked();
        }

        getActivity().setTitle(getString(R.string.nav_drawer_categories));
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData()
    {
        getLoaderManager().restartLoader(0, null, new LoaderManager.LoaderCallbacks<List<Categories>>() {
            @Override
            public Loader<List<Categories>> onCreateLoader(int id, Bundle args) {//import android.support.v4.content.AsyncTaskLoader;
                final AsyncTaskLoader<List<Categories>> loader = new AsyncTaskLoader<List<Categories>>(getActivity()) {
                    @Override
                    public List<Categories> loadInBackground() {
                        return getDataList();
                    }
                };

                loader.forceLoad();
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<List<Categories>> loader, List<Categories> data) {

                categoryRecycleView.setAdapter(new CategoryAdapter(data));
            }

            @Override
            public void onLoaderReset(Loader<List<Categories>> loader) {

            }
        });
    }

    private List<Categories> getDataList()
    {
        return new Select()
                .from(Categories.class)
                .execute();
    }
}