package primr.apps.eurakacachet.ryme.ryme.ui.view.offline.downloads;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.DownloadedTrack;

public class DownloadsAdapter extends RecyclerView.Adapter<DownloadsViewHolder> {

    DownloadsFragment mDownloadsFragment;
    List<DownloadedTrack> mTracks;
    DownloadedTrack mTrack;

    public DownloadsAdapter(DownloadsFragment downloadsFragment, List<DownloadedTrack> tracks){
        mDownloadsFragment = downloadsFragment;
        mTracks = tracks;
    }

    @Override
    public DownloadsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mDownloadsFragment.getActivity());
        View downloadTrackView = layoutInflater.inflate(R.layout.download_track_card,
                parent, false);

        return new DownloadsViewHolder(mDownloadsFragment, downloadTrackView, mTrack);
    }

    @Override
    public void onBindViewHolder(DownloadsViewHolder holder, int position) {
        mTrack = mTracks.get(position);
//        holder.mTrackCover.setImageResource();
        holder.mTrackArtist.setText(mTrack.artist.toUpperCase());
        holder.mTrackDuration.setText((int) mTrack.duration);
        holder.mTrackTitle.setText(mTrack.title.toUpperCase());
    }

    @Override
    public int getItemCount() {
        return mTracks.size();
    }
}
