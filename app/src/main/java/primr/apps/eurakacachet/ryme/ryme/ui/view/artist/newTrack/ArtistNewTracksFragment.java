package primr.apps.eurakacachet.ryme.ryme.ui.view.artist.newTrack;

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
import primr.apps.eurakacachet.ryme.ryme.ui.view.artist.trackDisplay.ArtistTrackListDisplayFragment;


public class ArtistNewTracksFragment extends Fragment {

    private ArrayList<Track> mTrackList;

    public ArtistNewTracksFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mTrackList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = layoutInflater.inflate(R.layout.fragment_artist_new_tracks, container, false);

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment = fragmentManager.findFragmentById(R.id.artist_new_tracks_display_container);
        if(fragment == null){
            fragment = ArtistTrackListDisplayFragment.newInstance(mTrackList);
            fragmentTransaction.add(R.id.artist_new_tracks_display_container, fragment)
                    .commit();
        }

        return rootView;
    }
}
