package primr.apps.eurakacachet.ryme.ryme.ui.view.offline.trackDisplay;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.DownloadedTrack;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadTrackDisplayFragment extends Fragment implements DownloadTrackDisplayMvpView {


    @Inject DownloadTrackDisplayPresenter mDownloadTrackDisplayPresenter;

    public static final String ARG_TRACK = "track";

    public static DownloadTrackDisplayFragment newInstance(DownloadedTrack track) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_TRACK, track);
        DownloadTrackDisplayFragment fragment = new DownloadTrackDisplayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public DownloadTrackDisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_download_track_display, container, false);
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void togglePlayPauseButton() {

    }
}
