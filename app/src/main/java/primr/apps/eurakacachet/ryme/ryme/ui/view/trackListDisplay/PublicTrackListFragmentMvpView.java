package primr.apps.eurakacachet.ryme.ryme.ui.view.trackListDisplay;

import java.util.List;

import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.ui.base.MvpView;


public interface PublicTrackListFragmentMvpView extends MvpView {

    void showLoading();

    void setTrackListAdapter(List<Track> tracks);

    void showNewTracksEmptyState();

    void showTrendingTracksEmptyState();

    void hideLoading();

    void setMoreTracks(List<Track> moreTracks);

    void showFavoriteTracksEmptyState();

    void hideRefreshLoading();
}
