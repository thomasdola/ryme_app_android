package primr.apps.eurakacachet.ryme.ryme.ui.view.settings;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import primr.apps.eurakacachet.ryme.ryme.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsViewFragment extends Fragment {


    public SettingsViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


//        getActivity().getSupportFragmentManager().beginTransaction()
//                .replace(R.uuid.settings_fragment_layout, new SettingsFragment())
//                .commit();

        return inflater.inflate(R.layout.fragment_settings_view, container, false);
    }

    public static SettingsViewFragment newInstance() {
        Bundle args = new Bundle();
        SettingsViewFragment fragment = new SettingsViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
