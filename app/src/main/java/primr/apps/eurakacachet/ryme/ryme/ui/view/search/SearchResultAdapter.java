package primr.apps.eurakacachet.ryme.ryme.ui.view.search;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Artist;
import primr.apps.eurakacachet.ryme.ryme.data.model.ArtistRequest;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.ui.view.artist.trackDisplay.ArtistTrackDisplayViewHolder;

public class SearchResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int VIEW_TYPE_ARTIST_REQUEST = 1;
    private static final int VIEW_TYPE_ARTIST = 2;
    private static final int VIEW_TYPE_TRACK = 3;
    private List<Object> mResultData;
    private SearchableActivity mActivity;
    private String mViewerId;

    @Inject
    public SearchResultAdapter(){
        mResultData = new ArrayList<>();
    }

    public void setResultData(List<Object> resultData, SearchableActivity activity, String viewerId){
        mResultData = resultData;
        mActivity = activity;
        mViewerId = viewerId;
    }

    @Override
    public int getItemViewType(int position) {
        if(mResultData.get(position) instanceof ArtistRequest){
            return VIEW_TYPE_ARTIST_REQUEST;
        }else if(mResultData.get(position) instanceof Track){
            return VIEW_TYPE_TRACK;
        }else if(mResultData.get(position) instanceof Artist){
            return VIEW_TYPE_ARTIST;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType){
            case VIEW_TYPE_ARTIST_REQUEST:
                View requestView = inflater.inflate(R.layout.artist_request_view, parent, false);
                viewHolder =  new ArtistRequestViewHolder(requestView, mActivity, mViewerId);
                break;
            case VIEW_TYPE_ARTIST:
                View trackView = inflater.inflate(R.layout.artist_track_card_view, parent, false);
                viewHolder = new ArtistTrackDisplayViewHolder(mActivity, trackView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object item = mResultData.get(position);
        switch (holder.getItemViewType()){
            case VIEW_TYPE_ARTIST_REQUEST:
                ((ArtistRequestViewHolder)holder).bindResult((ArtistRequest)item);
                break;
            case VIEW_TYPE_TRACK:
                ((ArtistTrackDisplayViewHolder)holder).bindTrack((Track)item);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mResultData.size();
    }
}
