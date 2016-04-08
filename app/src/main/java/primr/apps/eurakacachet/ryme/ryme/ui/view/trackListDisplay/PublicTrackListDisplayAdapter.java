package primr.apps.eurakacachet.ryme.ryme.ui.view.trackListDisplay;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;


public class PublicTrackListDisplayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Two view types which will be used to determine whether a row should be displaying
    // user or a Progressbar
    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_TRACK = 1;
    private PublicTrackListDisplayFragment mPublicTrackListDisplayFragment;
    private List<Track> mTrackList;

    @Inject
    public PublicTrackListDisplayAdapter() {
        mTrackList = new ArrayList<>();
    }

    public void setTracks(List<Track> tracks, PublicTrackListDisplayFragment fragment){
        mTrackList = tracks;
        mPublicTrackListDisplayFragment = fragment;
    }

    @Override
    public int getItemViewType(int position){
        return mTrackList.get(position) != null
                ? VIEW_TYPE_TRACK : VIEW_TYPE_LOADING;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if( viewType == VIEW_TYPE_TRACK){
            LayoutInflater layoutInflater = LayoutInflater.from(mPublicTrackListDisplayFragment.getActivity());
            View trackItemView = layoutInflater.inflate(
//                    R.layout.track_item_no_back,
                    R.layout.track_card_view,
                    parent, false);
            viewHolder = new PublicTrackDisplayViewHolder(mPublicTrackListDisplayFragment,
                    trackItemView);
        }else {
            View loadingView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.progress_bar_item, parent, false);
            viewHolder = new LoadingViewHolder(loadingView);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if( holder instanceof PublicTrackDisplayViewHolder){
            Track track = mTrackList.get(position);
            ((PublicTrackDisplayViewHolder)holder).bindTrack(track);
        }else {
            ((LoadingViewHolder)holder).mProgressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        return mTrackList.size();
    }

    public void clear(){
        mTrackList.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Track> tracks){
        mTrackList.addAll(tracks);
        notifyDataSetChanged();
    }
}
