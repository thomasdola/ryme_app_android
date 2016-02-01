package primr.apps.eurakacachet.ryme.ryme.ui.view.trackListDisplay;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;


public class PublicTrackListDisplayAdapter extends RecyclerView.Adapter<PublicTrackListDisplayViewHolder> {

    private PublicTrackListDisplayFragment mPublicTrackListDisplayFragment;
    private List<Track> mTrackList;
    private Track mTrack;

    public PublicTrackListDisplayAdapter(PublicTrackListDisplayFragment
                                                 publicTrackListDisplayFragment, List<Track> trackList) {
        mPublicTrackListDisplayFragment = publicTrackListDisplayFragment;
        mTrackList = trackList;
    }

    @Override
    public PublicTrackListDisplayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mPublicTrackListDisplayFragment.getActivity());
        View publicTrackDisplayCardView = layoutInflater.inflate(R.layout.track_card_view,
                parent, false);
        return new PublicTrackListDisplayViewHolder(mPublicTrackListDisplayFragment,
                publicTrackDisplayCardView, mTrack);
    }

    @Override
    public void onBindViewHolder(PublicTrackListDisplayViewHolder holder, int position) {
        mTrack = mTrackList.get(position);
        holder.mTrackTitleTextView.setText(mTrack.title.toUpperCase());
    }

    @Override
    public int getItemCount() {
        return mTrackList.size();
    }
}
