package primr.apps.eurakacachet.ryme.ryme.ui.view.eventAd;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.model.EventAd;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Subscription;


public class EventAdViewHolderPresenter extends BasePresenter<EventAdViewHolderMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public EventAdViewHolderPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(EventAdViewHolderMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadCover(EventAd ad){
        checkViewAttached();
        getMvpView().showLoading();
    }

    public long view(EventAd ad){
        checkViewAttached();
        long views = 20;
        getMvpView().updateViews(views);
        return views;
    }

}
