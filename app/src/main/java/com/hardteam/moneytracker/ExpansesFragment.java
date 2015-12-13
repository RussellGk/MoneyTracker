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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by RG on 06.12.2015.
 */
public class ExpansesFragment extends Fragment { //!!! android.support.v4.app.Fragment

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View mainView = inflater.inflate(R.layout.expenses_fragment,container,false);
        RecyclerView expensesRecycleView = (RecyclerView) mainView.findViewById(R.id.context_recyclerview);
        List<Expense> adapterData = getDataList();
        ExpensesAdapter expensesAdapter = new ExpensesAdapter(adapterData);
        expensesRecycleView.setAdapter(expensesAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        expensesRecycleView.setLayoutManager(linearLayoutManager);

        FloatingActionButton floatingActionButton =(FloatingActionButton)mainView.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
//          Snackbar.make(mainView,"Nice", Snackbar.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), AddExpenseActivity.class);
            intent.putExtra("key","value");
            getActivity().startActivity(intent);

        }
        });

        getActivity().setTitle(getString(R.string.nav_drawer_expenses));
        return mainView;
    }
    private List<Expense> getDataList()
    {
        List<Expense> data = new ArrayList<>();
        data.add(new Expense("Telephone",new Date(),500));
        data.add(new Expense("Clothes",new Date(),3000));
        data.add(new Expense("Flat",new Date(),26000));
        data.add(new Expense("PC",new Date(),5000));
        data.add(new Expense("Internet",new Date(),500));
        return data;
    }
}
