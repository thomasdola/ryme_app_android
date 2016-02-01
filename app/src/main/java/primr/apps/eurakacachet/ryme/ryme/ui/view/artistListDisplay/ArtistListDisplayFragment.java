package primr.apps.eurakacachet.ryme.ryme.ui.view.artistListDisplay;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Artist;
import primr.apps.eurakacachet.ryme.ryme.utils.helpers.layout.MarginDecoration;


public class ArtistListDisplayFragment extends Fragment {

    private static final String ARG_ARTIST_LIST = "artist_list";

    private ArrayList<Artist> mArtistList;


    public static ArtistListDisplayFragment newInstance(ArrayList<Artist> artistList) {
        ArtistListDisplayFragment fragment = new ArtistListDisplayFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("artists", artistList);
        fragment.setArguments(args);
        return fragment;
    }

    public ArtistListDisplayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mArtistList = getArguments().getParcelableArrayList(ARG_ARTIST_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_artist_list_display, container, false);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        RecyclerView artistListDisplayRecyclerView = (RecyclerView) rootView.findViewById(R.id.artist_list_display_recycler_view);
        artistListDisplayRecyclerView.addItemDecoration(new MarginDecoration(getActivity()));
        artistListDisplayRecyclerView.setHasFixedSize(true);
        artistListDisplayRecyclerView.setLayoutManager(layoutManager);

        ArtistListDisplayAdapter artistListDisplayAdapter = new ArtistListDisplayAdapter(this, mArtistList);
        artistListDisplayRecyclerView.setAdapter(artistListDisplayAdapter);
        return rootView;
    }

}
