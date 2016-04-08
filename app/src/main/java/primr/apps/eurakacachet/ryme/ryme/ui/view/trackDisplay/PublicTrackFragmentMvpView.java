package primr.apps.eurakacachet.ryme.ryme.ui.view.trackDisplay;

import primr.apps.eurakacachet.ryme.ryme.ui.base.MvpView;


public interface PublicTrackFragmentMvpView extends MvpView{

    void showLoading();

    void hideLoading();

    void toggleLikeButton();

    void disableDownloadButton();

    void enableDownloadButton();

    void showErrorSnack(String message);

    void showSuccessSnack(int messageType);

    void isLiked(Boolean isLiked);

    void updateStreamsCount();

    void trackIsAlreadySaved(Boolean isSaved);

    void showDownloadSuccess();

    void showDownloadFailure();
}
