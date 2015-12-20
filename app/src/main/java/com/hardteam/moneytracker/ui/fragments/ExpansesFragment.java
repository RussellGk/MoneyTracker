package com.hardteam.moneytracker.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.activeandroid.query.Select;

import com.hardteam.moneytracker.R;
import com.hardteam.moneytracker.adapters.ExpensesAdapter;
import com.hardteam.moneytracker.database.Categories;
import com.hardteam.moneytracker.database.Expenses;
import com.hardteam.moneytracker.ui.activities.AddExpenseActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * Created by RG on 06.12.2015.
 */

@EFragment(R.layout.expenses_fragment)

public class ExpansesFragment extends Fragment { //!!! android.support.v4.app.Fragment

    private static final String LOG_VIEW = ExpansesFragment.class.getSimpleName();

//    private RecyclerView expensesRecycleViewNew;

    @ViewById(R.id.context_recyclerview)
    RecyclerView expensesRecycleView;

    @ViewById(R.id.fab)
    FloatingActionButton floatingActionButton;

    @Click(R.id.fab)
    void ButtonWasClicked() {
        Intent intent = new Intent(getActivity(), AddExpenseActivity_.class);
        getActivity().startActivity(intent);
    }

    @AfterViews
    void ready()
    {
        //List<Expense> adapterData = getDataList();
        //ExpensesAdapter expensesAdapter = new ExpensesAdapter(adapterData);
        //expensesRecycleView.setAdapter(expensesAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        expensesRecycleView.setLayoutManager(linearLayoutManager);

        //remove this code

        Categories categoryFun = new Categories("Fun");
        categoryFun.save();
        Expenses expenses = new Expenses("321","Cinema","15.12.15",categoryFun);
        expenses.save();

//        Expenses expenses1 = getExpense();
//        Log.e(LOG_VIEW, expenses1.category.toString());

        if (floatingActionButton.isPressed()){
            ButtonWasClicked();
        }
        getActivity().setTitle(getString(R.string.nav_drawer_expenses));
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData()
    {
        getLoaderManager().restartLoader(0, null, new LoaderManager.LoaderCallbacks<List<Expenses>>(){
            @Override
            public Loader<List<Expenses>> onCreateLoader(int id, Bundle args) {//import android.support.v4.content.AsyncTaskLoader;
                final AsyncTaskLoader<List<Expenses>> loader = new AsyncTaskLoader<List<Expenses>>(getActivity()) {
                    @Override
                    public List<Expenses> loadInBackground() {
                        return getDataList();
                    }
                };

                loader.forceLoad();
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<List<Expenses>> loader, List<Expenses> data) {

                expensesRecycleView.setAdapter(new ExpensesAdapter(data));
            }

            @Override
            public void onLoaderReset(Loader<List<Expenses>> loader) {

            }
        });
    }

    private List<Expenses> getDataList()
    {
        return new Select()
                .from(Expenses.class)
                .execute();
    }

}
