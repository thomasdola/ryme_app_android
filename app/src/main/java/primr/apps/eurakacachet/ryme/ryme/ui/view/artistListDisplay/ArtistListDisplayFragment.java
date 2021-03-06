package primr.apps.eurakacachet.ryme.ryme.ui.view.artistListDisplay;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Artist;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;
import primr.apps.eurakacachet.ryme.ryme.utils.helpers.layout.MarginDecoration;


public class ArtistListDisplayFragment extends Fragment implements ArtistListDisplayMvpView{

    @Inject ArtistListDisplayAdapter mAdapter;
    @Inject ArtistListDisplayPresenter mPresenter;
    private RecyclerView mArtistListDisplayRecyclerView;
    private RelativeLayout mFavoriteArtistEmptyState;
    private ProgressBar mProgressBar;


    public static ArtistListDisplayFragment newInstance() {
        ArtistListDisplayFragment fragment = new ArtistListDisplayFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ArtistListDisplayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_artist_list_display, container, false);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        mArtistListDisplayRecyclerView = (RecyclerView) rootView.findViewById(R.id.artist_list_display_recycler_view);
        mFavoriteArtistEmptyState = (RelativeLayout) rootView.findViewById(R.id.favorite_artist_list_empty_state);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.loading_progress_bar);
        mArtistListDisplayRecyclerView.addItemDecoration(new MarginDecoration(getActivity()));
        mArtistListDisplayRecyclerView.setHasFixedSize(true);
        mArtistListDisplayRecyclerView.setLayoutManager(layoutManager);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((BaseActivity)getActivity()).getActivityComponent().inject(this);
        mPresenter.attachView(this);
        mPresenter.loadArtists();
        mArtistListDisplayRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setArtists(List<Artist> artists) {
        mArtistListDisplayRecyclerView.setVisibility(View.VISIBLE);
        mAdapter.setArtistList(this, artists);
    }

    @Override
    public void showEmptyState() {
        mFavoriteArtistEmptyState.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setIndeterminate(true);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }
}
