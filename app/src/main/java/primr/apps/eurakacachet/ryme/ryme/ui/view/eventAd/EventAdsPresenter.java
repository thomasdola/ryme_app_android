package primr.apps.eurakacachet.ryme.ryme.ui.view.eventAd;

import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.model.EventAd;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Subscription;


public class EventAdsPresenter extends BasePresenter<EventAdsMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public EventAdsPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(EventAdsMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public List<EventAd> loadEventAds(){
        checkViewAttached();
        getMvpView().showLoading();
        return null;
    }

    public void viewEventAd(EventAd event){
        checkViewAttached();
    }

}
