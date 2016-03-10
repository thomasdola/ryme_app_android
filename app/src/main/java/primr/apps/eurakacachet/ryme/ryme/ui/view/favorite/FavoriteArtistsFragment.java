package primr.apps.eurakacachet.ryme.ryme.ui.view.favorite;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.ui.view.artistListDisplay.ArtistListDisplayFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteArtistsFragment extends Fragment implements FavoriteMvpView{

    @Inject FavoriteArtistsPresenter mFavoriteArtistsPresenter;


    public static FavoriteArtistsFragment newInstance() {
        Bundle args = new Bundle();
        FavoriteArtistsFragment fragment = new FavoriteArtistsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public FavoriteArtistsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favorite_artists, container, false);

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment artistListDisplayFragment = fragmentManager.findFragmentById
                (R.id.favorite_artist_list_display_fragment_container);
        if(artistListDisplayFragment == null){
            artistListDisplayFragment = ArtistListDisplayFragment.newInstance();
            fragmentTransaction.add(R.id.favorite_artist_list_display_fragment_container,
                    artistListDisplayFragment).commit();
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
