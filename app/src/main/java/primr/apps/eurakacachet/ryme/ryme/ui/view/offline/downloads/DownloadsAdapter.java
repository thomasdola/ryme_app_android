package primr.apps.eurakacachet.ryme.ryme.ui.view.offline.downloads;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.SavedTrack;

public class DownloadsAdapter extends RecyclerView.Adapter<DownloadsViewHolder> {

    Context mContext;
    List<SavedTrack> mTracks;

    @Inject
    public DownloadsAdapter(){
        mTracks = new ArrayList<>();
    }

    public void setTracks(Context context, List<SavedTrack> tracks){
        mContext = context;
        mTracks = tracks;
    }

    @Override
    public DownloadsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View downloadTrackView = layoutInflater.inflate(
//                R.layout.download_track_card,
                R.layout.download_track_list_item,
                parent, false);

        return new DownloadsViewHolder(mContext, downloadTrackView);
    }

    @Override
    public void onBindViewHolder(DownloadsViewHolder holder, int position) {
        SavedTrack track = mTracks.get(position);
        holder.bindTrack(track, position);
    }

    @Override
    public int getItemCount() {
        return mTracks.size();
    }

    public void swap(int firstPosition, int secondPosition) {
        Collections.swap(mTracks, firstPosition, secondPosition);
        notifyItemMoved(firstPosition, secondPosition);
    }

    public void remove(int position) {
        mTracks.remove(position);
        notifyItemRemoved(position);
    }
}
