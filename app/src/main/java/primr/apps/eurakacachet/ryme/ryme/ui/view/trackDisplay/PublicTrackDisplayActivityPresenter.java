package primr.apps.eurakacachet.ryme.ryme.ui.view.trackDisplay;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Subscription;


public class PublicTrackDisplayActivityPresenter extends BasePresenter<PublicTrackDisplayActivityMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public PublicTrackDisplayActivityPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(PublicTrackDisplayActivityMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

}
