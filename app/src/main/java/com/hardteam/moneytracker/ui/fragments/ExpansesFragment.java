package com.hardteam.moneytracker.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.activeandroid.query.Select;

import com.hardteam.moneytracker.R;
import com.hardteam.moneytracker.adapters.ExpensesAdapter;
import com.hardteam.moneytracker.database.Categories;
import com.hardteam.moneytracker.database.Expenses;
import com.hardteam.moneytracker.rest.RestService;
import com.hardteam.moneytracker.rest.model.UserRegistrationModel;
import com.hardteam.moneytracker.ui.activities.AddExpenseActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.api.BackgroundExecutor;

import java.util.List;

/**
 * Created by RG on 06.12.2015.
 */

@EFragment(R.layout.expenses_fragment)
@OptionsMenu(R.menu.search_menu)

public class ExpansesFragment extends Fragment { //!!! android.support.v4.app.Fragment

    private static final String LOG_VIEW = ExpansesFragment.class.getSimpleName();

    private ExpensesAdapter adapter;
    private ActionModeCallback actionModeCallback = new ActionModeCallback();
    private ActionMode actionMode;

    private static final String FILTER_ID = "filter_id";

    @ViewById(R.id.context_recyclerview)
    RecyclerView expensesRecycleView;

    @ViewById(R.id.fab)
    FloatingActionButton floatingActionButton;

    @ViewById(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @OptionsMenuItem(R.id.search_action)
    MenuItem menuItem;

    @Click(R.id.fab)
    void ButtonWasClicked() {
        Intent intent = new Intent(getActivity(), AddExpenseActivity_.class);
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
    }

    @AfterViews
    void ready() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        expensesRecycleView.setLayoutManager(linearLayoutManager);

        if (floatingActionButton.isPressed()) {
            ButtonWasClicked();
        }
        getActivity().setTitle(getString(R.string.nav_drawer_expenses));
    }

    @Override
    public void onResume() {
        super.onResume();

        swipeRefreshLayout.setColorSchemeColors(R.color.colorAccent, R.color.selected_color, R.color.white);

        //expensesRecycleView.setAdapter(null);
        loadData("Swipe");

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData("");
            }
        });

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.removeItem(viewHolder.getAdapterPosition());
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(expensesRecycleView);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        SearchView searchView = (SearchView)menuItem.getActionView();
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
                delayedQuery(newText);
                return false;
            }
        });
    }

    @Background(delay = 700, id = FILTER_ID )
    void delayedQuery(String filter)
    {
        loadData(filter);
    }

    private void loadData(final String filter) {
        getLoaderManager().restartLoader(0, null, new LoaderManager.LoaderCallbacks<List<Expenses>>() {
            @Override
            public Loader<List<Expenses>> onCreateLoader(int id, Bundle args) {//import android.support.v4.content.AsyncTaskLoader;
                final AsyncTaskLoader<List<Expenses>> loader = new AsyncTaskLoader<List<Expenses>>(getActivity()) {
                    @Override
                    public List<Expenses> loadInBackground() {
                        return getDataList(filter);
                    }
                };

                loader.forceLoad();
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<List<Expenses>> loader, List<Expenses> data) {

                swipeRefreshLayout.setRefreshing(false);

                adapter = new ExpensesAdapter(getActivity(), data, new ExpensesAdapter.CardViewHolder.ClickListener() {
                    @Override
                    public void onItemClicked(int position) {

                        if(actionMode != null)
                        {
                         toggleSelection(position);
                        }
                    }

                    @Override
                    public boolean onItemLongClicked(int position)
                    {
                        if(actionMode == null)
                        {
                            AppCompatActivity activity = (AppCompatActivity)getActivity();
                            actionMode = activity.startSupportActionMode(actionModeCallback);
                        }

                        toggleSelection(position);

                        return true;
                    }
                });

                expensesRecycleView.setAdapter(adapter);
            }

            @Override
            public void onLoaderReset(Loader<List<Expenses>> loader) {

            }
        });
    }

    private List<Expenses> getDataList(String filter) {
        return new Select()
                .from(Expenses.class)
                .where("Name LIKE ?", new String[]{'%' + filter + '%'} )
                .execute();
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
