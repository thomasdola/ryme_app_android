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

public class ArtistTrackListDisplayFragment extends Fragment{

    private static final String ARG_TRACKS = "trackList";
    RecyclerView mArtistTracksDisplayRecyclerView;
    ArtistTrackListDisplayAdapter mArtistTrackListDisplayAdapter;
    private ArrayList<Track> mTracks;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_artist_tracks_display, container, false);

        mArtistTracksDisplayRecyclerView = (RecyclerView) rootView.findViewById(R.id.artist_tracks_display_recycler_view);
        mArtistTracksDisplayRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mArtistTrackListDisplayAdapter = new ArtistTrackListDisplayAdapter(mTracks);
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

    private class ArtistTrackDisplayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTrackTitleTextView;
        private TextView mTrackStreamTextView;
        private TextView mTrackDownloadTextView;
        private TextView mTrackFavoriteTextView;
        private TextView mTrackReleasedDateTextView;
        private Track mTrack;

        public ArtistTrackDisplayViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTrackTitleTextView = (TextView) itemView.findViewById(R.id.artist_track_title_text_view);
            mTrackStreamTextView = (TextView) itemView.findViewById(R.id.artist_track_stream_text);
            mTrackDownloadTextView = (TextView) itemView.findViewById(R.id.artist_track_download_text);
            mTrackFavoriteTextView = (TextView) itemView.findViewById(R.id.artist_track_favorite_text);
            mTrackReleasedDateTextView = (TextView) itemView.findViewById(R.id.artist_track_released_date);
        }

        @Override
        public void onClick(View v) {
            Intent intent = PublicTrackDisplayActivity.newIntent(getActivity());
            startActivity(intent);
        }
    }


    private class ArtistTrackListDisplayAdapter extends RecyclerView.Adapter<ArtistTrackDisplayViewHolder>{

        private ArrayList<Track> mTracksList;

        public ArtistTrackListDisplayAdapter(ArrayList<Track> trackArrayList){
            mTracksList = trackArrayList;
        }

        @Override
        public ArtistTrackDisplayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.artist_track_card_view, parent, false);

            return new ArtistTrackDisplayViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ArtistTrackDisplayViewHolder holder, int position) {
            holder.mTrackTitleTextView.setText(mTracksList.get(position).getTrackTitle());
//            holder.mTrackFavoriteTextView.setText(mTracksList.get(position).g);

        }

        @Override
        public int getItemCount() {
            return mTracksList.size();
        }
    }
}
