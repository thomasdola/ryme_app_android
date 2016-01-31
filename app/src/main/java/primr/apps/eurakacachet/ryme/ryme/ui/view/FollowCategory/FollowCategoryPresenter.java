package primr.apps.eurakacachet.ryme.ryme.ui.view.followCategory;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.model.Category;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Subscription;


public class FollowCategoryPresenter extends BasePresenter<FollowCategoryMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

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
    }

    public void follow(Category category){
        checkViewAttached();
        getMvpView().toggleFollowButton();
        getMvpView().showStartButton();
    }

    public void unFollow(Category category){
        checkViewAttached();
        getMvpView().toggleFollowButton();
        getMvpView().hideStartButton();
    }
}
