package primr.apps.eurakacachet.ryme.ryme.ui.view.settings;


import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import javax.inject.Inject;


public class SettingsFragment extends PreferenceFragmentCompat implements SettingsFragmentMvpView{

    @Inject SettingsFragmentPresenter mSettingsFragmentPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
//        addPreferencesFromResource(R.xml.preferences);
    }
}
