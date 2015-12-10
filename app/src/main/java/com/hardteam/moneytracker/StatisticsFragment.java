package com.hardteam.moneytracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by RG on 07.12.2015.
 */
public class StatisticsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.statistics_fragment,container,false);
        getActivity().setTitle(getString(R.string.nav_drawer_statistics));
        return mainView;
    }
}
