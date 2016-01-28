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

public class TrendingTracksFragment extends Fragment {

    private ArrayList<Track> mTrackList;

    public TrendingTracksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mTrackList = new ArrayList<>();
        mTrackList.add(new Track("New Track One"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_trending_tracks, container, false);

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment publicTrackListDisplayFragment = fragmentManager
                .findFragmentById(R.id.trending_tracks_fragment_container);
        if(publicTrackListDisplayFragment == null){
            publicTrackListDisplayFragment = PublicTrackListDisplayFragment.newInstance(mTrackList);
            fragmentTransaction.add(R.id.trending_tracks_fragment_container,
                    publicTrackListDisplayFragment).commit();
        }

        return rootView;
    }


}
