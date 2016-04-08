package primr.apps.eurakacachet.ryme.ryme.ui.view.main;

import android.util.Log;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.model.ActionResponse;
import primr.apps.eurakacachet.ryme.ryme.data.model.ApiData;
import primr.apps.eurakacachet.ryme.ryme.data.model.AuthResponse;
import primr.apps.eurakacachet.ryme.ryme.data.model.UserProfile;
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
        mSubscription = mDataManager.getUsername()
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
        mSubscription = mDataManager.getBackImage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String path) {
                        Log.d("main", "back image path -> " + path);
                        getMvpView().setBackImage(path);
                    }
                });
    }

    public void setAvatar(){
        checkViewAttached();
        mSubscription = mDataManager.getAvatar()
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
                            mSubscription = mDataManager.getUsername()
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
        mSubscription = mDataManager.isArtist()
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean isArtist) {
                        Log.d("main", "is artist -> " + isArtist);
                        getMvpView().isArtist(isArtist);
                        mDataManager.getUserId()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Action1<String>() {
                                    @Override
                                    public void call(String uuid) {
                                        Log.d("main", "user id -> " + uuid);
                                        getMvpView().setUserId(uuid);
                                    }
                                });
                    }
                });
    }

    private void startAutoLogin(){
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                autoLogin(false);
            }
        }, 0, 1800000);
    }

    public void autoLogin(final boolean fromMain) {
        Log.d("main", "autologin called");
        checkViewAttached();
        String username = mDataManager.getBlockingUsername();
        String password = mDataManager.getPassword();
        HashMap<String , String> payload = new HashMap<>();
        payload.put("username", username);
        payload.put("password", password);
        Log.d("mainAuth", "payload for main auth => " + payload.toString());
        mSubscription = mDataManager.login(payload)
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
                            updateAccount(authResponse);
                            if(fromMain){
                                getMvpView().initMainView();
                                startAutoLogin();
                            }
                        }
                    }
                });
    }

    private void updateAccount(AuthResponse authResponse) {
        mDataManager.setToken(authResponse.token());
        ApiData data = authResponse.data();
        if(data != null){
            UserProfile user = data.data();
            mDataManager.setUsername(user.username());
            mDataManager.setUserId(user.uuid());
            mDataManager.setAvatarPath(user.avatar());
            mDataManager.setBackImagePath(user.background_picture());
            mDataManager.setIsArtist(user.is_artist());
            mDataManager.setRequestIsActive(user.is_request_on);
        }
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
