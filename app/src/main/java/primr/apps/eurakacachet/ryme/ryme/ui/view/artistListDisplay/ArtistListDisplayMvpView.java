package primr.apps.eurakacachet.ryme.ryme.ui.view.artistListDisplay;

import java.util.List;

import primr.apps.eurakacachet.ryme.ryme.data.model.Artist;
import primr.apps.eurakacachet.ryme.ryme.ui.base.MvpView;


public interface ArtistListDisplayMvpView extends MvpView {

    void setArtists(List<Artist> artists);

    void showEmptyState();

    void showLoading();

    void hideLoading();
}
