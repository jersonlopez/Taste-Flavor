package co.edu.udea.compumovil.gr04_20172.proyecto.views.user;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;

import co.edu.udea.compumovil.gr04_20172.proyecto.R;

/**
 * Created by jerson.lopez on 23/11/17.
 */

public class SettingsScreen extends PreferenceFragment{
    public SettingsScreen() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_screen);

        SwitchPreference preference = (SwitchPreference)findPreference("switch");

    }
}
