package primr.apps.eurakacachet.ryme.ryme.ui.view.trackListDisplay;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Subscription;


public class PublicTrackDisplayViewHolderPresenter extends BasePresenter<PublicTrackDisplayViewHolderMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public PublicTrackDisplayViewHolderPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(PublicTrackDisplayViewHolderMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void view(Track track){
        checkViewAttached();
    }

}
