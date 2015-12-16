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
        Snackbar.make(getView(), "Work", Snackbar.LENGTH_SHORT).show();
    }

    @AfterViews
    void ready(){
        List<Category> adapterData = getDataList();
        CategoryAdapter categoryAdapter = new CategoryAdapter(adapterData);

        categoryRecycleView.setAdapter(categoryAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecycleView.setLayoutManager(linearLayoutManager);
        if (floatingActionButton.isPressed()){
            myButtonWasClicked();
        }

        getActivity().setTitle(getString(R.string.nav_drawer_categories));
    }

    private List<Category> getDataList() {
        List<Category> data = new ArrayList<>();
        data.add(new Category("Телефон"));
        data.add(new Category("Еда"));
        data.add(new Category("Книги"));
        return data;
    }

}