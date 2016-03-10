package primr.apps.eurakacachet.ryme.ryme.ui.view.offline.downloads;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.SavedTrack;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;


public class DownloadsFragment extends Fragment implements DownloadsMvpView{

    @Inject DownloadsPresenter mPresenter;
    @Inject DownloadsAdapter mAdapter;

    public List<SavedTrack> tracksToDelete;
    private RecyclerView mRecyclerView;

    public DownloadsFragment(){}

    public static DownloadsFragment newInstance() {
        Bundle args = new Bundle();
        DownloadsFragment fragment = new DownloadsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_download_track_list_display,
                container, false);
        initRecyclerView(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((BaseActivity)getActivity()).getActivityComponent().inject(this);
        mPresenter.attachView(this);
        mPresenter.loadTracks();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showLoading() {

    }


    @Override
    public void hideLoading() {

    }

    private void initRecyclerView(View rootView) {
        mRecyclerView = (RecyclerView) rootView
                .findViewById(R.id.download_track_list_display_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ItemTouchHelper.Callback callback = new TrackTouchHelper(mAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void showFeedback(String trackTitle) {

    }

    @Override
    public void setTracks(List<SavedTrack> savedTracks) {
        if(savedTracks.isEmpty()){
            showEmptyState();
        }
        mAdapter.setTracks(this, savedTracks);
        mAdapter.notifyDataSetChanged();
    }

    private void showEmptyState() {
        Log.d("downloads", "showing empty state");
    }
}
