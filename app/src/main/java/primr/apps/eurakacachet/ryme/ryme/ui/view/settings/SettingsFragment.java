package primr.apps.eurakacachet.ryme.ryme.ui.view.settings;


import android.app.Fragment;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import primr.apps.eurakacachet.ryme.ryme.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
