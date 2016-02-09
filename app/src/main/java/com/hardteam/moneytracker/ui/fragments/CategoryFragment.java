package com.hardteam.moneytracker.ui.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.hardteam.moneytracker.Category;
import com.hardteam.moneytracker.adapters.CategoryAdapter;
import com.hardteam.moneytracker.R;
import com.hardteam.moneytracker.adapters.ExpensesAdapter;
import com.hardteam.moneytracker.database.Categories;
import com.hardteam.moneytracker.database.Expenses;
import com.hardteam.moneytracker.util.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.api.BackgroundExecutor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RG on 07.12.2015.
 */

@EFragment(R.layout.category_fragment)
@OptionsMenu(R.menu.search_menu)

public class CategoryFragment extends Fragment {

    private static final String LOG_VIEW = CategoryFragment.class.getSimpleName();

    private CategoryAdapter adapter;
    private ActionModeCallback actionModeCallback = new ActionModeCallback();
    private ActionMode actionMode;


    private static final String FILTER_ID = "filter_id";

    @ViewById(R.id.category_recyclerview)
    RecyclerView categoryRecycleView;

    @ViewById(R.id.fab_category)
    FloatingActionButton floatingActionButton;

    @OptionsMenuItem(R.id.search_action)
    MenuItem menuItemCategory;

    @Click(R.id.fab_category)
    void myButtonWasClicked() {
        alertDialog();
//        Snackbar.make(getView(), Constants.WORK, Snackbar.LENGTH_SHORT).show();
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
        loadData("");
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        SearchView searchView = (SearchView)menuItemCategory.getActionView();
        searchView.setQueryHint(getString(R.string.search_title));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(LOG_VIEW, "Full query: " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(LOG_VIEW, "Current text: " + newText);

                BackgroundExecutor.cancelAll(FILTER_ID, true);
                delayedQueryCategory(newText);
                return false;
            }
        });
    }

    @Background(delay = 700, id = FILTER_ID )
    void delayedQueryCategory(String filter)
    {
        loadData(filter);
    }

    private void loadData(final String filter)
    {
        getLoaderManager().restartLoader(0, null, new LoaderManager.LoaderCallbacks<List<Categories>>() {
            @Override
            public Loader<List<Categories>> onCreateLoader(int id, Bundle args) {//import android.support.v4.content.AsyncTaskLoader;
                final AsyncTaskLoader<List<Categories>> loader = new AsyncTaskLoader<List<Categories>>(getActivity()) {
                    @Override
                    public List<Categories> loadInBackground() {
                        return getDataList(filter);
                    }
                };

                loader.forceLoad();
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<List<Categories>> loader, List<Categories> data) {

                adapter = new CategoryAdapter(getActivity(), data, new CategoryAdapter.CardViewHolder.ClickListener() {
                    @Override
                    public void onItemClicked(int position) {

                        if (actionMode != null) {
                            toggleSelection(position);
                        }
                    }

                    @Override
                    public boolean onItemLongClicked(int position) {
                        if (actionMode == null) {
                            AppCompatActivity activity = (AppCompatActivity) getActivity();
                            actionMode = activity.startSupportActionMode(actionModeCallback);
                        }

                        toggleSelection(position);

                        return true;
                    }
                });

                categoryRecycleView.setAdapter(adapter);
            }

            @Override
            public void onLoaderReset(Loader<List<Categories>> loader) {

            }
        });
    }

    private List<Categories> getDataList(String filter)
    {
        return new Select()
                .from(Categories.class)
                .where("Name LIKE ?", new String[]{'%' + filter + '%'})
                .execute();
    }

    private void alertDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_window);
        TextView textView = (TextView) dialog.findViewById(R.id.title_dialog);
        final EditText editText = (EditText) dialog.findViewById(R.id.edittext);

//        dialog.getWindow().setWindowAnimations(R.style.);
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);


        Button okButton = (Button) dialog.findViewById(R.id.okButton);
        Button cancelButton = (Button) dialog.findViewById(R.id.cancelButton);

        textView.setText(getString(R.string.nav_drawer_categories));
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable text = editText.getText();
//                if (!TextUtils.isEmpty(text)) {
//                    dialog.dismiss();
//                }
                if (text.length() == 0) {
                    dialog.dismiss();
                }
                else
                {
                    String textCategory = editText.getText().toString();
                    Categories categoryNew = new Categories(textCategory,0);
                    categoryNew.save();
                    loadData("");
                    dialog.dismiss();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    private void toggleSelection(int position)
    {
        adapter.toggleSelection(position);
        int count = adapter.getSelectedItemCount();
        if(count == 0)
        {
            actionMode.finish();
        }
        else
        {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    private class ActionModeCallback implements ActionMode.Callback
    {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.contextual_action_bar, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId())
            {
                case R.id.menu_remove:
                    adapter.removeItems(adapter.getSelectedItems());
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

            adapter.clearSelection();
            actionMode = null;
        }
    }

}