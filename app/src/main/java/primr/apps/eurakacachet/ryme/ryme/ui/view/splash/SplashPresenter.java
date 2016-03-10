package primr.apps.eurakacachet.ryme.ryme.ui.view.splash;

import android.util.Log;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
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
        mDataManager.isLoggedIn()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean isLoggedIn) {
                        if (!isLoggedIn) {
                            onNotLoggedIn();
                        } else {
                            mDataManager.isReady()
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

    public void getTrack(String track_id){
        checkViewAttached();
        Log.d("share", "getTrack is called with track id -> " + track_id);
        mDataManager.getTrack(track_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Track>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().closeActivity();
                        getMvpView().launchMainActivity();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Track track) {
                        if(track == null){
                            getMvpView().launchMainActivity();
                        }else {
                            getMvpView().launchPublicTrackDisplayActivity(track);
                        }
                        getMvpView().closeActivity();
                    }
                });
    }

    public void onLoggedInAndReady() {
        getMvpView().closeActivity();
        getMvpView().launchMainActivity();
    }

    public void onNotReady() {
        getMvpView().closeActivity();
        getMvpView().launchFollowCategoryActivity();
    }

    public void onNotLoggedIn() {
        getMvpView().closeActivity();
        getMvpView().launchSignUpActivity();
    }

}
