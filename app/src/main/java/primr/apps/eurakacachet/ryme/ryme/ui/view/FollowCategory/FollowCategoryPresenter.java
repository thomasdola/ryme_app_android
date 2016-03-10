package primr.apps.eurakacachet.ryme.ryme.ui.view.followCategory;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public class FollowCategoryPresenter extends BasePresenter<FollowCategoryMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;
    private CompositeSubscription mSubscriptions;

    @Inject
    public FollowCategoryPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(FollowCategoryMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
        if (mSubscriptions != null) mSubscriptions.unsubscribe();
    }

}
