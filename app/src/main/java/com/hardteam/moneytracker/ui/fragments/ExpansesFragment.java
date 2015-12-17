package com.hardteam.moneytracker.ui.fragments;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.activeandroid.query.Select;

import com.hardteam.moneytracker.Expense;
import com.hardteam.moneytracker.adapters.ExpensesAdapter;
import com.hardteam.moneytracker.R;
import com.hardteam.moneytracker.database.Categories;
import com.hardteam.moneytracker.database.Expenses;
import com.hardteam.moneytracker.ui.activities.AddExpenseActivity_;

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

    private static final String LOG_VIEW = ExpansesFragment.class.getSimpleName();

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

        Categories categoryFun = new Categories("Fun");
        categoryFun.save();
        Expenses expenses = new Expenses("123","Cinema","15.12.15",categoryFun);
        expenses.save();

        Expenses expenses1 = getExpense();
        Log.e(LOG_VIEW, expenses1.category.toString());

//        expenses.price = "123";
//        expenses.name = "cinema";
//        expenses.category = "Fun";
//        expenses.date = "12.12.15";
//        expenses.save();
//
//        Expenses expenses1 = getExpense();

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

//    public Expenses getExpense()
//    {
//        return new Select()
//                .from(Expenses.class)
//                .executeSingle();
//    }

//    private List<Expenses> getExpense()
//    {
//        return new Select()
//                .from(Expenses.class)
//                .execute();
//    }

    private Expenses getExpense()
    {
        return new Select()
                .from(Expenses.class)
                .executeSingle();
    }
}
