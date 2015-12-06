package com.hardteam.moneytracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RG on 06.12.2015.
 */
public class ExpansesFragment extends Fragment { //!!! android.support.v4.app.Fragment

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.expenses_fragment,container,false);
        ListView expensesListView = (ListView) mainView.findViewById(R.id.list_view);
        List<Expense> adapterData = getDataList();
        ExpensesAdapter expensesAdapter = new ExpensesAdapter(getActivity(),adapterData);
        expensesListView.setAdapter(expensesAdapter);
        getActivity().setTitle("Траты");
        return mainView;
    }
    private List<Expense> getDataList()
    {
        List<Expense> data = new ArrayList<>();
        data.add(new Expense("Telephone","500"));
        data.add(new Expense("Clothes","3000"));
        data.add(new Expense("Flat","26000"));
        data.add(new Expense("PC","5000"));
        data.add(new Expense("Internet","500"));
        return data;
    }
}
