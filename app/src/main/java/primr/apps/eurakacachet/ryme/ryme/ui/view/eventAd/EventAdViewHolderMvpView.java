package primr.apps.eurakacachet.ryme.ryme.ui.view.eventAd;

import primr.apps.eurakacachet.ryme.ryme.ui.base.MvpView;


public interface EventAdViewHolderMvpView extends MvpView {

    void showLoading();

    void hideLoading();

    void updateViews(long views);
}
