package primr.apps.eurakacachet.ryme.ryme.ui.view.eventAd;

import java.util.List;

import primr.apps.eurakacachet.ryme.ryme.data.model.EventAd;
import primr.apps.eurakacachet.ryme.ryme.ui.base.MvpView;


public interface EventAdsMvpView extends MvpView {

    void showLoading();

    void hideLoading();

    void setEventAds(List<EventAd> ads);

    void showEmpty();
}
