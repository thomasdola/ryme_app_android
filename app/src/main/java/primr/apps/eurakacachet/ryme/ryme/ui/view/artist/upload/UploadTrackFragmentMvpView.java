package primr.apps.eurakacachet.ryme.ryme.ui.view.artist.upload;

import primr.apps.eurakacachet.ryme.ryme.ui.base.MvpView;


public interface UploadTrackFragmentMvpView extends MvpView {

    void showLoading();

    void hideLoading();

    void showError(String message);

    void showSuccess();
}
