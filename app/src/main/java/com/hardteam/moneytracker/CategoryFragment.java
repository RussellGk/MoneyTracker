package com.hardteam.moneytracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RG on 07.12.2015.
 */
public class CategoryFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View mainView = inflater.inflate(R.layout.category_fragment,container,false);


        RecyclerView categoryRecycleView = (RecyclerView) mainView.findViewById(R.id.category_recyclerview);
        List<Category> adapterData = getDataList();
        CategoryAdapter categoryAdapter = new CategoryAdapter(adapterData);
        categoryRecycleView.setAdapter(categoryAdapter);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecycleView.setLayoutManager(linearLayoutManager);

        FloatingActionButton floatingActionButton = (FloatingActionButton)mainView.findViewById(R.id.fab_category);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(mainView, "Work", Snackbar.LENGTH_SHORT).show();
            }
        });

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
