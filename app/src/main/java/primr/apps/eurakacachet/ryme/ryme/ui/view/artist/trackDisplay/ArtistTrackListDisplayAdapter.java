package primr.apps.eurakacachet.ryme.ryme.ui.view.artist.trackDisplay;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;


public class ArtistTrackListDisplayAdapter extends RecyclerView.Adapter<ArtistTrackDisplayViewHolder> {

    private ArtistTrackListDisplayFragment mArtistTrackListDisplayFragment;
    private List<Track> mTracksList;
    private boolean mIsArtistViewing;

    @Inject
    public ArtistTrackListDisplayAdapter(){
        mTracksList = new ArrayList<>();
    }

    public void setTracks(ArtistTrackListDisplayFragment artistTrackListDisplayFragment
            , List<Track> trackArrayList, boolean isArtistViewing) {
        mArtistTrackListDisplayFragment = artistTrackListDisplayFragment;
        mTracksList = trackArrayList;
        mIsArtistViewing = isArtistViewing;
    }



    @Override
    public ArtistTrackDisplayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mArtistTrackListDisplayFragment.getActivity());
        View view = layoutInflater.inflate(R.layout.artist_track_card_view, parent, false);
        return new ArtistTrackDisplayViewHolder(mArtistTrackListDisplayFragment, view);
    }

    @Override
    public void onBindViewHolder(ArtistTrackDisplayViewHolder holder, int position) {
        Track track = mTracksList.get(position);
        holder.bindTrack(track, mIsArtistViewing);
    }

    @Override
    public int getItemCount() {
        return mTracksList.size();
    }
}
