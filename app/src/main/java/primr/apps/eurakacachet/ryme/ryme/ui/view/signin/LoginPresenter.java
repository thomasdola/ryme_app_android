package primr.apps.eurakacachet.ryme.ryme.ui.view.signin;


import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Subscription;

public class LoginPresenter extends BasePresenter<LoginMvpView> {


    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public LoginPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(LoginMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void login(String username, String password){
        checkViewAttached();
        getMvpView().showLoading();

        if(false){
            getMvpView().showLoginFail();
        }else {
            getMvpView().showLoginSuccessful();
        }
    }

}
