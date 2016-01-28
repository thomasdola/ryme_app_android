package primr.apps.eurakacachet.ryme.ryme.ui.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistAllTracksFragment extends Fragment {


    private ArrayList<Track> mTrackList;

    public ArtistAllTracksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mTrackList = new ArrayList<>();
        mTrackList.add(new Track("New Track One"));
        mTrackList.add(new Track("New Track Two"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_artist_all_tracks, container, false);

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment = fragmentManager.findFragmentById(R.id.artist_all_tracks_display_container);
        if(fragment == null){
            fragment = ArtistTrackListDisplayFragment.newInstance(mTrackList);
            fragmentTransaction.add(R.id.artist_all_tracks_display_container, fragment)
                    .commit();
        }

        return rootView;
    }




}
