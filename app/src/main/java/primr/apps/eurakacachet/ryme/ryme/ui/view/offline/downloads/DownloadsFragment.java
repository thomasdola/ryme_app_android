package primr.apps.eurakacachet.ryme.ryme.ui.view.offline.downloads;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.DownloadedTrack;


public class DownloadsFragment extends Fragment implements DownloadsMvpView{

    @Inject DownloadsPresenter mDownloadsPresenter;

    public List<DownloadedTrack> mTracks;
    public List<DownloadedTrack> tracksToDelete;

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

    private List<DownloadedTrack> loadTracks(){
        List<DownloadedTrack> tracks = new ArrayList<>();
        return tracks;
    }

    @Override
    public void showLoading() {

    }


    @Override
    public void hideLoading() {

    }

    private void initRecyclerView(View rootView) {
        RecyclerView recyclerView = (RecyclerView) rootView
                .findViewById(R.id.download_track_list_display_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTracks = loadTracks();
        DownloadsAdapter adapter = new DownloadsAdapter(this, mTracks);
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new TrackTouchHelper(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void showFeedback(String trackTitle) {

    }
}
