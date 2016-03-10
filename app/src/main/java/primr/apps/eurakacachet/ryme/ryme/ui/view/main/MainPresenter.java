package primr.apps.eurakacachet.ryme.ryme.ui.view.main;

import android.util.Log;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.model.ActionResponse;
import primr.apps.eurakacachet.ryme.ryme.data.model.AuthResponse;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import primr.apps.eurakacachet.ryme.ryme.utils.Config;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;
    private Timer mTimer;

    @Inject
    public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
        if(mTimer != null) mTimer.cancel();
    }

    public void setUsername(){
        checkViewAttached();
        mDataManager.getUsername()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String username) {
                        getMvpView().setUsername(username);
                    }
                });
    }

    public void setBackImage(){
        checkViewAttached();
        mDataManager.getBackImage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String path) {
                        getMvpView().setBackImage(path);
                    }
                });
    }

    public void setAvatar(){
        checkViewAttached();
        mDataManager.getAvatar()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String path) {
                        if (path.isEmpty()) {
                            mDataManager.getUsername()
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Action1<String>() {
                                        @Override
                                        public void call(String username) {
                                            getMvpView().hidePhotoAvatarView();
                                            getMvpView().setLetterAvatar(username);
                                        }
                                    });
                        } else {
                            getMvpView().hideLetterAvatarView();
                            getMvpView().setPhotoAvatar(path);
                        }
                    }
                });
    }

    public void setUserType(){
        checkViewAttached();
        mDataManager.isArtist()
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean isArtist) {
                        getMvpView().isArtist(isArtist);
                        mDataManager.getUserId()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Action1<String>() {
                                    @Override
                                    public void call(String uuid) {
                                        getMvpView().setUserId(uuid);
                                    }
                                });
                    }
                });
    }

    public void startAutoLogin(){
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                autoLogin();
            }
        }, 0, 1800000);
    }

    private void autoLogin() {
        Log.d("main", "autologin called");
        checkViewAttached();
        String username = mDataManager.getBlockingUsername();
        String password = mDataManager.getPassword();
        HashMap<String , String> payload = new HashMap<>();
        payload.put("username", username);
        payload.put("password", password);
        Log.d("mainAuth", "payload for main auth => " + payload.toString());
        mDataManager.login(payload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AuthResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(AuthResponse authResponse) {
                        if (authResponse.code == Config.STATUS_OK) {
                            Log.d("mainAuth", authResponse.toString());
                            Log.d("mainAuth", "auto login done successfully");
                            mDataManager.setToken(authResponse.token());
                        }
                    }
                });
    }

    public void isUserAllowedToMakeArtistRequest(){
        checkViewAttached();
        mDataManager.isUserAllowed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ActionResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ActionResponse actionResponse) {
                        if(actionResponse.code == Config.STATUS_OK){
                            mDataManager.setIsAllowedToMakeRequest(true);
                        }else {
                            mDataManager.setIsAllowedToMakeRequest(false);
                        }
                    }
                });
    }
}
