package primr.apps.eurakacachet.com.rhyme.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import primr.apps.eurakacachet.com.rhyme.R;
import primr.apps.eurakacachet.com.rhyme.model.Track;

public class PublicTrackListDisplayFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TRACKS = "tracks";

    private ArrayList<Track> mTracks;
    private RecyclerView mPublicTrackListDisplayRecyclerView;
    private PublicTrackListDisplayAdapter mPublicTrackListDisplayAdapter;

//    private OnFragmentInteractionListener mListener;


    public static PublicTrackListDisplayFragment newInstance(ArrayList<Track> mTracks) {
        PublicTrackListDisplayFragment fragment = new PublicTrackListDisplayFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_TRACKS, mTracks);
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

        mPublicTrackListDisplayRecyclerView = (RecyclerView) rootView.
                findViewById(R.id.public_track_list_display_recycler_view);
        mPublicTrackListDisplayRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mPublicTrackListDisplayAdapter = new PublicTrackListDisplayAdapter(mTracks);
        mPublicTrackListDisplayRecyclerView.setAdapter(mPublicTrackListDisplayAdapter);

        return rootView;
    }


    private class PublicTrackListDisplayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView mTrackTitleTextView;
        private Track mTrack;

        public PublicTrackListDisplayViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTrackTitleTextView = (TextView) itemView.findViewById(R.id.track_title_text_view);
        }

        @Override
        public void onClick(View v) {
            Intent intent = PublicTrackDisplayActivity.newIntent(getActivity());
            startActivity(intent);
        }
    }



    private class PublicTrackListDisplayAdapter extends RecyclerView.Adapter<PublicTrackListDisplayViewHolder>{

        private ArrayList<Track> mTrackList;

        public PublicTrackListDisplayAdapter(ArrayList<Track> trackArrayList){
            mTrackList = trackArrayList;
        }

        @Override
        public PublicTrackListDisplayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View publicTrackDisplayCardView = layoutInflater.inflate(R.layout.track_card_view,
                    parent, false);
            return new PublicTrackListDisplayViewHolder(publicTrackDisplayCardView);
        }

        @Override
        public void onBindViewHolder(PublicTrackListDisplayViewHolder holder, int position) {
            Track track = mTrackList.get(position);
            holder.mTrackTitleTextView.setText(track.getTrackTitle());
        }

        @Override
        public int getItemCount() {
            return mTrackList.size();
        }
    }

}
