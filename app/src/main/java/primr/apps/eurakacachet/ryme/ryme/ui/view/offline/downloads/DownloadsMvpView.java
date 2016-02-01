package primr.apps.eurakacachet.ryme.ryme.ui.view.offline.downloads;


import primr.apps.eurakacachet.ryme.ryme.ui.base.MvpView;

public interface DownloadsMvpView extends MvpView{

    void showLoading();

    void hideLoading();

    void showFeedback(String trackTitle);
}
