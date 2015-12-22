package com.hardteam.moneytracker.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hardteam.moneytracker.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

/**
 * Created by RG on 07.12.2015.
 */

@EFragment(R.layout.statistics_fragment)

public class StatisticsFragment extends Fragment {

    @AfterViews
    void ready()
    {
       getActivity().setTitle(getString(R.string.nav_drawer_statistics));
    }

}