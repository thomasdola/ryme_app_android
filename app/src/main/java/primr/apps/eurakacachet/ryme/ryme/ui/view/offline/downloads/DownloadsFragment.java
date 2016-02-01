package primr.apps.eurakacachet.ryme.ryme.ui.view.offline.downloads;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.model.DownloadedTrack;


public class DownloadsFragment extends Fragment implements DownloadsMvpView{

    @Inject DownloadsPresenter mDownloadsPresenter;

    public List<DownloadedTrack> mTracks;

    public DownloadsFragment(){}

    public static DownloadsFragment newInstance() {
        Bundle args = new Bundle();
        DownloadsFragment fragment = new DownloadsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showFeedback(String trackTitle) {

    }
}
