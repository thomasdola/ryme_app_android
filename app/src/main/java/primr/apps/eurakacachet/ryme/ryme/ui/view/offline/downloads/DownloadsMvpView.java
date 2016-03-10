package primr.apps.eurakacachet.ryme.ryme.ui.view.offline.downloads;


import java.util.List;

import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.SavedTrack;
import primr.apps.eurakacachet.ryme.ryme.ui.base.MvpView;

public interface DownloadsMvpView extends MvpView{

    void showLoading();

    void hideLoading();

    void showFeedback(String trackTitle);

    void setTracks(List<SavedTrack> savedTracks);
}
