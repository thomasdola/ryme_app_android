package primr.apps.eurakacachet.ryme.ryme.ui.view.verify_code;


import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Subscription;

public class VerifyCodePresenter extends BasePresenter<VerifyCodeMvpView>{

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public VerifyCodePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(VerifyCodeMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void verify(int code){
        checkViewAttached();
        getMvpView().showLoading();
    }

}
