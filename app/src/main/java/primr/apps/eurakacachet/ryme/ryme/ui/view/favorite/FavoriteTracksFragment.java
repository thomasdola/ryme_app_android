package primr.apps.eurakacachet.ryme.ryme.ui.view.favorite;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.ui.view.trackListDisplay.PublicTrackListDisplayFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTracksFragment extends Fragment implements FavoriteMvpView{


    @Inject FavoriteTracksPresenter mFavoriteTracksPresenter;

    private ArrayList<Track> mTrackList;

    public FavoriteTracksFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_favorite_tracks, container, false);

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment favoriteTrackListDisplayFragment = fragmentManager.findFragmentById
                (R.id.favorite_track_list_display_fragment_container);
        if(favoriteTrackListDisplayFragment == null){
            favoriteTrackListDisplayFragment = PublicTrackListDisplayFragment
                    .newInstance(mTrackList);
            fragmentTransaction.add(R.id.favorite_track_list_display_fragment_container,
                    favoriteTrackListDisplayFragment).commit();
        }

        return rootView;
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
