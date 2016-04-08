package primr.apps.eurakacachet.ryme.ryme.ui.view.splash;

import android.util.Log;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class SplashPresenter extends BasePresenter<SplashMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public SplashPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(SplashMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void launchApp(){
        checkViewAttached();
        mSubscription = mDataManager.isLoggedIn()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean isLoggedIn) {
                        if (!isLoggedIn) {
                            onNotLoggedIn();
                        } else {
                            mSubscription = mDataManager.isReady()
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Action1<Boolean>() {
                                        @Override
                                        public void call(Boolean isReady) {
                                            if (!isReady) {
                                                onNotReady();
                                            } else {
                                                onLoggedInAndReady();
                                            }
                                        }
                                    });
                        }
                    }
                });
    }


    public void onLoggedInAndReady() {
        getMvpView().launchMainActivity();
        getMvpView().closeActivity();
    }

    public void onNotReady() {
        Log.d("verify", "onNotReady called");
        mSubscription = mDataManager.isVerified()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Boolean isVerified) {
                        if(isVerified){
                            getMvpView().launchFollowCategoryActivity();
                        }else {
                            getMvpView().launchVerifyCodeActivity();
                        }
                        getMvpView().closeActivity();
                    }
                });

    }

    public void onNotLoggedIn() {
        getMvpView().launchSignUpActivity();
        getMvpView().closeActivity();
    }

    public void launchTrackDisplay(String track_id) {
        checkViewAttached();
        getMvpView().launchPublicTrackDisplayActivity(track_id);
        getMvpView().closeActivity();
    }
}
