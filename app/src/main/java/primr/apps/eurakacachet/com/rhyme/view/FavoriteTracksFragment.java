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
public class FavoriteTracksFragment extends Fragment {


    private ArrayList<Track> mFavoriteTrackList;

    public FavoriteTracksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mFavoriteTrackList = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favorite_tracks, container, false);

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment favoriteTrackListDisplayFragment = fragmentManager.findFragmentById
                (R.id.favorite_track_list_display_fragment_container);
        if(favoriteTrackListDisplayFragment == null){
            favoriteTrackListDisplayFragment = PublicTrackListDisplayFragment
                    .newInstance(mFavoriteTrackList);
            fragmentTransaction.add(R.id.favorite_track_list_display_fragment_container,
                    favoriteTrackListDisplayFragment).commit();
        }

        return rootView;
    }


}
