package primr.apps.eurakacachet.ryme.ryme.ui.view.artistRequest;

import primr.apps.eurakacachet.ryme.ryme.ui.base.MvpView;


public interface ArtistRequestMvpView extends MvpView {
    void showLoading();
    void hideLoading();
    void onVouchDone(boolean success);
}
