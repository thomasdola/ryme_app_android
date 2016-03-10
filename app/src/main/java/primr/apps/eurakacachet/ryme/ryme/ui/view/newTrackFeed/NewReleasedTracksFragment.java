package primr.apps.eurakacachet.ryme.ryme.ui.view.newTrackFeed;


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
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.trackListDisplay.PublicTrackListDisplayFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewReleasedTracksFragment extends Fragment implements NewReleasedTracksMvpView{

    @Inject NewReleasedTracksPresenter mNewReleasedTracksPresenter;

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
        ((BaseActivity)getActivity()).getActivityComponent().inject(this);
        View rootView = inflater.inflate(R.layout.fragment_new_released_tracks, container, false);

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment publicTrackListDisplayFragment = fragmentManager.findFragmentById
                (R.id.new_released_tracks_fragment_container);
        if(publicTrackListDisplayFragment == null){
            publicTrackListDisplayFragment = PublicTrackListDisplayFragment
                    .newInstance(PublicTrackListDisplayFragment.NEW_RELEASE);
            fragmentTransaction.add(R.id.new_released_tracks_fragment_container,
                    publicTrackListDisplayFragment).commit();
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
