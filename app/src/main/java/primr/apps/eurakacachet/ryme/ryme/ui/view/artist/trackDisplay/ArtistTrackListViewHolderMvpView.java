package primr.apps.eurakacachet.ryme.ryme.ui.view.artist.trackDisplay;

import primr.apps.eurakacachet.ryme.ryme.ui.base.MvpView;


public interface ArtistTrackListViewHolderMvpView extends MvpView {

    void disableSwitch();

    void enableSwitch();

    void showError();

    void checkSwitch(boolean isDownloadable);

}
