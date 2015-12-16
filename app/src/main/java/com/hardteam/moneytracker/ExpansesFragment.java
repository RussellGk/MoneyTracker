package com.hardteam.moneytracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by RG on 06.12.2015.
 */

@EFragment(R.layout.expenses_fragment)

public class ExpansesFragment extends Fragment { //!!! android.support.v4.app.Fragment

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
        List<Expense> adapterData = getDataList();
        ExpensesAdapter expensesAdapter = new ExpensesAdapter(adapterData);
        expensesRecycleView.setAdapter(expensesAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        expensesRecycleView.setLayoutManager(linearLayoutManager);
        if (floatingActionButton.isPressed()){
            ButtonWasClicked();
        }
        getActivity().setTitle(getString(R.string.nav_drawer_expenses));
    }

    private List<Expense> getDataList() {
        List<Expense> data = new ArrayList<>();
        data.add(new Expense("Telephone", new Date(), 500));
        data.add(new Expense("Clothes", new Date(), 3000));
        data.add(new Expense("Flat", new Date(), 26000));
        data.add(new Expense("PC", new Date(), 5000));
        data.add(new Expense("Internet", new Date(), 500));
        return data;
    }
}
