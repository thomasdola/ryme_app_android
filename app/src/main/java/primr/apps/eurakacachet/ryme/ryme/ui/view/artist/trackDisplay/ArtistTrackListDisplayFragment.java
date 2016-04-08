package primr.apps.eurakacachet.ryme.ryme.ui.view.artist.trackDisplay;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;

public class ArtistTrackListDisplayFragment extends Fragment implements ArtistTrackListDisplayFragmentMvpView{

    private static final String ARG_IS_ARTIST_VIEWING = "is_artist_viewing";
    @Inject ArtistTrackListDisplayFragmentPresenter mPresenter;
    @Inject ArtistTrackListDisplayAdapter mAdapter;
    public static final int NEW_RELEASE_TRACK= 1;
    public static final int ALL_TRACK= 0;
    private static final String ARG_ARTIST_ID = "artistId";
    private static final String ARG_TRACK_TYPE = "track_type";
    RecyclerView mArtistTracksDisplayRecyclerView;
    RelativeLayout mAllTracksEmptyState;
    RelativeLayout mNewTracksEmptyState;
    ProgressBar mLoadingBar;
    private int mTrackType;
    private String mArtistId;
    private boolean mIsArtistViewing;


    public static ArtistTrackListDisplayFragment newInstance(String artistId, int trackType,
                                                             boolean isArtistViewing) {
        ArtistTrackListDisplayFragment fragment = new ArtistTrackListDisplayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ARTIST_ID, artistId);
        args.putInt(ARG_TRACK_TYPE, trackType);
        args.putBoolean(ARG_IS_ARTIST_VIEWING, isArtistViewing);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTrackType = getArguments().getInt(ARG_TRACK_TYPE);
            mArtistId = getArguments().getString(ARG_ARTIST_ID);
            mIsArtistViewing = getArguments().getBoolean(ARG_IS_ARTIST_VIEWING);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_artist_tracks_display, container, false);
        mArtistTracksDisplayRecyclerView = (RecyclerView) rootView
                .findViewById(R.id.artist_tracks_display_recycler_view);
        mLoadingBar = (ProgressBar) rootView.findViewById(R.id.loading_progress_bar);
        mNewTracksEmptyState = (RelativeLayout) rootView.findViewById(R.id.new_track_list_empty_state);
        mAllTracksEmptyState = (RelativeLayout) rootView.findViewById(R.id.all_track_list_empty_state);
        mArtistTracksDisplayRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((BaseActivity)getActivity()).getActivityComponent().inject(this);
        mPresenter.attachView(this);
        mPresenter.downloadTracks(mArtistId, mTrackType);
        mArtistTracksDisplayRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void showLoading() {
        mLoadingBar.setVisibility(View.VISIBLE);
        mLoadingBar.setIndeterminate(true);
    }

    @Override
    public void hideLoading() {
        mLoadingBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setTrackList(List<Track> trackList) {
        mArtistTracksDisplayRecyclerView.setVisibility(View.VISIBLE);
        mAdapter.setTracks(this, trackList, mIsArtistViewing);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), R.string.please_try_again_text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmptyState() {
        mArtistTracksDisplayRecyclerView.setVisibility(View.INVISIBLE);
        mAllTracksEmptyState.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNewEmptyState(){
        mArtistTracksDisplayRecyclerView.setVisibility(View.INVISIBLE);
        mNewTracksEmptyState.setVisibility(View.VISIBLE);
    }
}
