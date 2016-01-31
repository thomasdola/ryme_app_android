package primr.apps.eurakacachet.ryme.ryme.ui.view.trackDisplay;

import primr.apps.eurakacachet.ryme.ryme.ui.base.MvpView;


public interface PublicTrackDisplayFragmentMvpView extends MvpView{

    void showLoading();

    void hideLoading();

    void togglePayButton();

    void toggleLikeButton();

    void disableDownloadButton();

    void enableDownloadButton();

}
