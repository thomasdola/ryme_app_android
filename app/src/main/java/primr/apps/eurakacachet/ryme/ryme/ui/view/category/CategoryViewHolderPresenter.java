package primr.apps.eurakacachet.ryme.ryme.ui.view.category;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.model.Category;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Subscription;


public class CategoryViewHolderPresenter extends BasePresenter<CategoryViewHolderMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public CategoryViewHolderPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(CategoryViewHolderMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void follow(Category category){
        checkViewAttached();
        getMvpView().disableFollowButton();
        getMvpView().toggleFollowButton();
        getMvpView().enableFollowButton();
    }

    public void unFollow(Category category){
        checkViewAttached();
        getMvpView().disableFollowButton();
        getMvpView().toggleFollowButton();
        getMvpView().disableFollowButton();
    }

    public boolean isFollowing(Category category){
        return false;
    }

}
