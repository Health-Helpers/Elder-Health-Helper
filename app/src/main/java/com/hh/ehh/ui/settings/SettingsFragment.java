package com.hh.ehh.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;

import com.hh.ehh.R;
import com.hh.ehh.ui.customdialogs.CustomDialogs;
import com.hh.ehh.utils.FragmentStackManager;

/**
 * Created by mpifa on 17/1/16.
 */
public class SettingsFragment extends PreferenceFragmentCompat implements
        Preference.OnPreferenceClickListener {
    private SharedPreferences.OnSharedPreferenceChangeListener prefListener;
    private FragmentStackManager fragmentStackManager;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getPreferenceScreen() == null) {
            addPreferencesFromResource(R.xml.preferences);
        }
        Preference about = findPreference("ABOUT");
        about.setOnPreferenceClickListener(this);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {

            }
        };
        prefs.registerOnSharedPreferenceChangeListener(prefListener);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference.getKey().equals("ABOUT")) {
            CustomDialogs.aboutDialog(getActivity()).show();
        }
        return false;
    }
}