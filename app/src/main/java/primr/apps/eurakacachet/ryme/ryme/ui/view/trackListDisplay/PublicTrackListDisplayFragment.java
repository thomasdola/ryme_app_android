package primr.apps.eurakacachet.ryme.ryme.ui.view.trackListDisplay;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;

public class PublicTrackListDisplayFragment extends Fragment implements PublicTrackListDisplayFragmentMvpView{

    @Inject PublicTrackListDisplayFragmentPresenter mPublicTrackListDisplayFragmentPresenter;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TRACKS = "tracks";

    private List<Track> mTracks;

//    private OnFragmentInteractionListener mListener;


    public static PublicTrackListDisplayFragment newInstance(ArrayList<Track> trackList) {
        PublicTrackListDisplayFragment fragment = new PublicTrackListDisplayFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_TRACKS, trackList);
        fragment.setArguments(args);
        return fragment;
    }

    public PublicTrackListDisplayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTracks = getArguments().getParcelableArrayList(ARG_TRACKS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_public_track_list_display, container, false);

        RecyclerView publicTrackListDisplayRecyclerView = (RecyclerView) rootView.
                findViewById(R.id.public_track_list_display_recycler_view);
        publicTrackListDisplayRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mTracks = new ArrayList<>();
        PublicTrackListDisplayAdapter publicTrackListDisplayAdapter = new PublicTrackListDisplayAdapter(this, mTracks);
        publicTrackListDisplayRecyclerView.setAdapter(publicTrackListDisplayAdapter);

        return rootView;
    }
}
