package primr.apps.eurakacachet.com.rhyme.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import primr.apps.eurakacachet.com.rhyme.R;
import primr.apps.eurakacachet.com.rhyme.model.Track;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewReleasedTracksFragment extends Fragment {

    ArrayList<Track> mTrackList;

    public NewReleasedTracksFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mTrackList = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_new_released_tracks, container, false);

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment publicTrackListDisplayFragment = fragmentManager.findFragmentById
                (R.id.new_released_tracks_fragment_container);
        if(publicTrackListDisplayFragment == null){
            publicTrackListDisplayFragment = PublicTrackListDisplayFragment.newInstance(mTrackList);
            fragmentTransaction.add(R.id.new_released_tracks_fragment_container,
                    publicTrackListDisplayFragment).commit();
        }

        return rootView;
    }

}
