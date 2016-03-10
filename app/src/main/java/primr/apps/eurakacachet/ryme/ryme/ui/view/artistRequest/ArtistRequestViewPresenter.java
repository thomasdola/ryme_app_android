package primr.apps.eurakacachet.ryme.ryme.ui.view.artistRequest;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.model.ActionResponse;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import primr.apps.eurakacachet.ryme.ryme.utils.Config;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx_gcm.internal.RxGcm;


public class ArtistRequestViewPresenter extends BasePresenter<ArtistRequestMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public ArtistRequestViewPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(ArtistRequestMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void yesTo(final String uuid) {
        checkViewAttached();
        getMvpView().showLoading();
        mSubscription = RxGcm.Notifications.currentToken()
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String token) {
                        mSubscription = mDataManager.yesTo(uuid, token)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<ActionResponse>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        e.printStackTrace();
                                        getMvpView().hideLoading();
                                        getMvpView().onVouchDone(false);
                                    }

                                    @Override
                                    public void onNext(ActionResponse actionResponse) {
                                        getMvpView().hideLoading();
                                        if(actionResponse.code == Config.STATUS_OK){
                                            getMvpView().onVouchDone(true);
                                        }else {
                                            getMvpView().onVouchDone(false);
                                        }
                                    }
                                });
                    }
                });
    }

    public void noTo(final String uuid) {
        checkViewAttached();
        getMvpView().showLoading();
        mSubscription = RxGcm.Notifications.currentToken()
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String token) {
                        mSubscription = mDataManager.noTo(uuid, token)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<ActionResponse>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        e.printStackTrace();
                                        getMvpView().hideLoading();
                                        getMvpView().onVouchDone(false);
                                    }

                                    @Override
                                    public void onNext(ActionResponse actionResponse) {
                                        getMvpView().hideLoading();
                                        if (actionResponse.code == Config.STATUS_OK) {
                                            getMvpView().onVouchDone(true);
                                        } else {
                                            getMvpView().onVouchDone(false);
                                        }
                                    }
                                });
                    }
                });
    }
}
