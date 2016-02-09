package primr.apps.eurakacachet.ryme.ryme.ui.view.trackListDisplay;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.utils.helpers.layout.EndlessRecyclerViewScrollListener;

public class PublicTrackListDisplayFragment extends Fragment implements PublicTrackListDisplayFragmentMvpView{

    @Inject PublicTrackListDisplayFragmentPresenter mPresenter;

    private static final String ARG_TRACKS = "tracks";

    private List<Track> mTracks;
    private PublicTrackListDisplayAdapter mAdapter;
    private SwipeRefreshLayout mSwipeContainer;

//    private OnFragmentInteractionListener mListener;


    public static PublicTrackListDisplayFragment newInstance(ArrayList<Track> trackList) {
        PublicTrackListDisplayFragment fragment = new PublicTrackListDisplayFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_TRACKS, trackList);
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

        TextView emptyState = (TextView) rootView.findViewById(R.id.track_list_empty_state);
        mSwipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        mSwipeContainer.setColorSchemeColors(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        RecyclerView recyclerView = (RecyclerView) rootView.
                findViewById(R.id.public_track_list_display_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadMoreTracks(page);
            }
        });

        mTracks = new ArrayList<>();
        mAdapter = new PublicTrackListDisplayAdapter(this, mTracks);
        recyclerView.setAdapter(mAdapter);

        if( mTracks.isEmpty() ){
            recyclerView.setVisibility(View.GONE);
            emptyState.setVisibility(View.VISIBLE);
        }else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyState.setVisibility(View.GONE);
        }

        return rootView;
    }

    private void refresh() {
        List<Track> tracks = mPresenter.refreshTrackStore();
        mAdapter.clear();
        mAdapter.addAll(tracks);
        mSwipeContainer.setRefreshing(false);
    }

    // Append more data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    private void loadMoreTracks(int page) {
        // Send an API request to retrieve appropriate data using the offset value as a parameter.
        // Deserialize API response and then construct new objects to append to the adapter
        // Add the new objects to the data source for the adapter
//        items.addAll(moreItems);
        // For efficiency purposes, notify the adapter of only the elements that got changed
        // curSize will equal to the index of the first element inserted because the list is 0-indexed
        int curSize = mAdapter.getItemCount();
        mAdapter.notifyItemRangeInserted(curSize, mTracks.size() - 1);
    }
}
