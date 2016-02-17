package com.hardteam.moneytracker.ui.fragments;


import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hardteam.moneytracker.R;

/**
 * Created by RG on 07.12.2015.
 */


public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getString(R.string.nav_drawer_settings));

        addPreferencesFromResource(R.xml.pref_general);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Тут можно подключать наши списки настроек, например:
        //bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_frequency_of_updates_key)));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

        String stringValue= newValue.toString();
        if (preference instanceof ListPreference) {
       // For list preferences, look up the correct display value in
       // the preference's 'entries' list (since they have separate labels/values).
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
// For other preferences, set the summary to the value's simple string representation.
            preference.setSummary(stringValue);
        }
        return true;
    }

    private void bindPreferenceSummaryToValue(Preference preference){
        //Setthelistenertowatchforvaluechanges.
        preference.setOnPreferenceChangeListener(this);
        //Triggerthelistenerimmediatelywiththepreference's
        //currentvalue.
        onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(),""));
    }
}