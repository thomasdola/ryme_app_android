package primr.apps.eurakacachet.ryme.ryme.ui.view.artist.allTrack;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.ui.view.artist.trackDisplay.ArtistTrackListDisplayFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistAllTracksFragment extends Fragment {

    public static final String ARG_ARTIST_ID = "artist_id";
    private static final String ARG_IS_USER_ACCOUNT = "is_user_account";
    private String mArtistId;
    private boolean mIsUserAccount;

    public static ArtistAllTracksFragment newInstance(String artistId, boolean isUserAccount) {
        ArtistAllTracksFragment fragment = new ArtistAllTracksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ARTIST_ID, artistId);
        args.putBoolean(ARG_IS_USER_ACCOUNT, isUserAccount);
        fragment.setArguments(args);
        return fragment;
    }

    public ArtistAllTracksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mArtistId = getArguments().getString(ARG_ARTIST_ID);
            mIsUserAccount = getArguments().getBoolean(ARG_IS_USER_ACCOUNT);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_artist_all_tracks, container, false);

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment = fragmentManager.findFragmentById(R.id.artist_all_tracks_display_container);
        if(fragment == null){
            fragment = ArtistTrackListDisplayFragment.newInstance(mArtistId,
                    ArtistTrackListDisplayFragment.ALL_TRACK, mIsUserAccount);
            fragmentTransaction.add(R.id.artist_all_tracks_display_container, fragment)
                    .commit();
        }

        return rootView;
    }




}
