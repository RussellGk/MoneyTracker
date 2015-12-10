package com.hardteam.moneytracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RG on 07.12.2015.
 */
public class CategoryFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.expenses_fragment,container,false);
        ListView categoryListView = (ListView) mainView.findViewById(R.id.list_view);
        List<Category> adapterData = getDataList();
        CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity(),adapterData);
        categoryListView.setAdapter(categoryAdapter);
        getActivity().setTitle(getString(R.string.nav_drawer_categories));
        return mainView;
    }
    private List<Category> getDataList()
    {
        List<Category> data = new ArrayList<>();
        data.add(new Category("Телефон"));
        data.add(new Category("Еда"));
        data.add(new Category("Книги"));
        return data;
    }
}
