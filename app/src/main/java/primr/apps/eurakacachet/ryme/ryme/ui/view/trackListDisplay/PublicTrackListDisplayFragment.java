package primr.apps.eurakacachet.ryme.ryme.ui.view.trackListDisplay;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;
import primr.apps.eurakacachet.ryme.ryme.utils.helpers.layout.DividerItemDecorator;
import primr.apps.eurakacachet.ryme.ryme.utils.helpers.layout.EndlessRecyclerViewScrollListener;

public class PublicTrackListDisplayFragment extends Fragment implements PublicTrackListFragmentMvpView {

    @Inject PublicTrackListFragmentPresenter mPresenter;
    @Inject PublicTrackListDisplayAdapter mDisplayAdapter;

    public static final String ARG_TRACK_TYPE = "type";
    public static final int NEW_RELEASE = 1;
    public static final int FAVORITE = 2;
    public static final int TRENDING = 3;

    private SwipeRefreshLayout mSwipeContainer;
    private RelativeLayout mNewTracksEmptyState;
    private RelativeLayout mTrendingTracksEmptyState;
    private RelativeLayout mFavoriteTracksEmptyState;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    int mType;
    private List<Track> mTracks;
    private LinearLayoutManager mLayoutManager;

    public static PublicTrackListDisplayFragment newInstance(int type) {
        PublicTrackListDisplayFragment fragment = new PublicTrackListDisplayFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TRACK_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }


    public PublicTrackListDisplayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Icepick.restoreInstanceState(this, savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getInt(ARG_TRACK_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_public_track_list_display, container, false);
        initViews(rootView);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecorator(getContext(),
                R.drawable.track_list_divider);
        mRecyclerView.addItemDecoration(itemDecoration);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        Icepick.saveInstanceState(this, outState);
    }

    public void initListeners() {
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                mPresenter.loadMore(page);
            }
        });
    }

    public void initViews(View rootView) {
        mNewTracksEmptyState = (RelativeLayout) rootView.findViewById(R.id.track_list_empty_state);
        mTrendingTracksEmptyState = (RelativeLayout) rootView.findViewById(R.id.trending_track_list_empty_state);
        mFavoriteTracksEmptyState = (RelativeLayout) rootView.findViewById(R.id.favorite_track_list_empty_state);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.loading_progress_bar);
        mSwipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        mSwipeContainer.setColorSchemeResources(
                R.color.redPink,
                R.color.blueBlack,
                R.color.lightBlueBlack,
                R.color.lighterBlueBlack
        );
        mRecyclerView = (RecyclerView) rootView.
                findViewById(R.id.public_track_list_display_recycler_view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((BaseActivity)getActivity()).getActivityComponent().inject(this);
        mPresenter.attachView(this);
        mPresenter.downloadTracks(mType, true);
        mRecyclerView.setAdapter(mDisplayAdapter);
        initListeners();
    }

    public void showFullState() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mNewTracksEmptyState.setVisibility(View.GONE);
    }

    public void showEmpty() {
        mRecyclerView.setVisibility(View.GONE);
        mNewTracksEmptyState.setVisibility(View.VISIBLE);
    }

    private void refresh() {
        mPresenter.refreshTrackStore(mType);
    }

    // Append more user into the adapter
    // This method probably sends out a network request and appends new user items to your adapter.
    private void loadMoreTracks(int page) {
        // Send an API request to retrieve appropriate user using the offset value as a parameter.
        // Deserialize API response and then construct new objects to append to the adapter
        // Add the new objects to the user source for the adapter
//        items.addAll(moreItems);
        // For efficiency purposes, notify the adapter of only the elements that got changed
        // curSize will equal to the index of the first element inserted because the list is 0-indexed
        int curSize = mDisplayAdapter.getItemCount();
        mDisplayAdapter.notifyItemRangeInserted(curSize, mTracks.size() - 1);
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setIndeterminate(true);
    }

    @Override
    public void setTrackListAdapter(List<Track> tracks) {
        mTracks = tracks;
        mDisplayAdapter.setTracks(tracks, this);
        mDisplayAdapter.notifyDataSetChanged();
        showFullState();
    }

    @Override
    public void showNewTracksEmptyState() {
//        mDisplayAdapter.setTracks(Collections.<Track>emptyList(), this);
//        mDisplayAdapter.notifyDataSetChanged();
        showEmpty();
    }

    @Override
    public void showTrendingTracksEmptyState() {
        mRecyclerView.setVisibility(View.GONE);
        mTrendingTracksEmptyState.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setMoreTracks(List<Track> moreTracks) {
        mDisplayAdapter.addAll(moreTracks);
        int curSize = mDisplayAdapter.getItemCount();
        mDisplayAdapter.notifyItemRangeInserted(curSize, moreTracks.size() - 1);
    }

    @Override
    public void showFavoriteTracksEmptyState() {
        mRecyclerView.setVisibility(View.GONE);
        mFavoriteTracksEmptyState.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRefreshLoading() {
        mSwipeContainer.setRefreshing(false);
    }
}
