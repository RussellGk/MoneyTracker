package com.hardteam.moneytracker;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RG on 06.12.2015.
 */
public class OtherFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.other_fragment,container,false);
        ListView expensesListView = (ListView) mainView.findViewById(R.id.list_view);
        List<Expense> adapterData = getDataList();
        ExpensesAdapter expensesAdapter = new ExpensesAdapter(getActivity(),adapterData);
        expensesListView.setAdapter(expensesAdapter);
        getActivity().setTitle("Не траты");
        return mainView;
    }
    private List<Expense> getDataList()
    {
        List<Expense> data = new ArrayList<>();
        data.add(new Expense("Food","4000"));
        data.add(new Expense("Servers","500"));
        data.add(new Expense("TV","350"));
        return data;
    }
}
