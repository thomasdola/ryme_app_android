package primr.apps.eurakacachet.ryme.ryme.ui.view.artist.trackDisplay;

import java.util.List;

import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.ui.base.MvpView;


public interface ArtistTrackListDisplayFragmentMvpView extends MvpView{

    void showLoading();

    void hideLoading();

    void setTrackList(List<Track> trackList);

    void showError();

    void showEmptyState();
}
