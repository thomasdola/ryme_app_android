package primr.apps.eurakacachet.ryme.ryme.ui.view.artist.newTrack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.ui.view.artist.trackDisplay.ArtistTrackListDisplayFragment;


public class ArtistNewTracksFragment extends Fragment {

    public static final String ARG_ARTIST_ID = "artist_id";
    private static final String ARG_USER_ACCOUNT = "is_user_account";
    private String mArtistId;
    private boolean mIsUserAccount;

    public ArtistNewTracksFragment(){}

    public static ArtistNewTracksFragment newInstance(String artistId, boolean isUserAccount) {
        Bundle args = new Bundle();
        args.putString(ARG_ARTIST_ID, artistId);
        args.putBoolean(ARG_USER_ACCOUNT, isUserAccount);
        ArtistNewTracksFragment fragment = new ArtistNewTracksFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mArtistId = getArguments().getString(ARG_ARTIST_ID);
            mIsUserAccount= getArguments().getBoolean(ARG_USER_ACCOUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = layoutInflater.inflate(R.layout.fragment_artist_new_tracks, container, false);

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment = fragmentManager.findFragmentById(R.id.artist_new_tracks_display_container);
        if(fragment == null){
            fragment = ArtistTrackListDisplayFragment.newInstance(mArtistId, ArtistTrackListDisplayFragment
                    .NEW_RELEASE_TRACK, mIsUserAccount);
            fragmentTransaction.add(R.id.artist_new_tracks_display_container, fragment)
                    .commit();
        }

        return rootView;
    }
}
