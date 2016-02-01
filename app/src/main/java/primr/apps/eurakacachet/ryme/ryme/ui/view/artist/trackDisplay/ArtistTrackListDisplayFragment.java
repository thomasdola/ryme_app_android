package primr.apps.eurakacachet.ryme.ryme.ui.view.artist.trackDisplay;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;

public class ArtistTrackListDisplayFragment extends Fragment{

    private static final String ARG_TRACKS = "trackList";
    RecyclerView mArtistTracksDisplayRecyclerView;
    ArtistTrackListDisplayAdapter mArtistTrackListDisplayAdapter;
    private ArrayList<Track> mTracks;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_artist_tracks_display, container, false);

        mArtistTracksDisplayRecyclerView = (RecyclerView) rootView.findViewById(R.id.artist_tracks_display_recycler_view);
        mArtistTracksDisplayRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mArtistTrackListDisplayAdapter = new ArtistTrackListDisplayAdapter(this, mTracks);
        mArtistTracksDisplayRecyclerView.setAdapter(mArtistTrackListDisplayAdapter);

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTracks = getArguments().getParcelableArrayList(ARG_TRACKS);
        }
    }

    public static ArtistTrackListDisplayFragment newInstance(ArrayList<Track> mTracks) {
        ArtistTrackListDisplayFragment fragment = new ArtistTrackListDisplayFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_TRACKS, mTracks);
        fragment.setArguments(args);
        return fragment;
    }


}
