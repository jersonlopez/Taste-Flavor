package co.edu.udea.compumovil.gr04_20172.proyecto.views.user;


import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v14.preference.SwitchPreference;
import android.support.annotation.Nullable;

import co.edu.udea.compumovil.gr04_20172.proyecto.R;


public class Settings extends PreferenceFragmentCompat {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        SwitchPreference preference = (SwitchPreference)findPreference("switch");
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    }
}
