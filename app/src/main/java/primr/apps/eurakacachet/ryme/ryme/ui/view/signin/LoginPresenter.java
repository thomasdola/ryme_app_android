package primr.apps.eurakacachet.ryme.ryme.ui.view.signin;


import android.util.Log;

import com.pushtorefresh.storio.sqlite.operations.put.PutResults;

import java.util.HashMap;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.FollowedCategory;
import primr.apps.eurakacachet.ryme.ryme.data.model.ApiData;
import primr.apps.eurakacachet.ryme.ryme.data.model.AuthResponse;
import primr.apps.eurakacachet.ryme.ryme.data.model.UserProfile;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import primr.apps.eurakacachet.ryme.ryme.utils.Config;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

    public void login(final String username, String password){
        checkViewAttached();
        getMvpView().showLoading();
        getMvpView().disableFields();
        getMvpView().disableLoginButton();
        HashMap<String, String> credentials = new HashMap<>();
        credentials.put("username", username);
        credentials.put("password", password);
        saveCredentials(credentials);
        mDataManager.login(credentials)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AuthResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getMvpView().showLoginFail(e.getMessage());
                        onLoginFailed();
                    }

                    @Override
                    public void onNext(AuthResponse authResponse) {
                        Log.d("auth", "login done with " + authResponse.code);
                        if (authResponse.code == Config.STATUS_OK) {
                            setUpAccount(authResponse);
                            mDataManager.setLogIn(true);
                            mDataManager.setReady(true);
                            getMvpView().showLoginSuccessful();
                            getMvpView().launchMainActivity();
                        } else {
                            onLoginFailed();
                            getMvpView().showLoginFail(authResponse.message());
                        }
                    }
                });
    }

    private void saveCredentials(HashMap<String, String> credentials) {
        mDataManager.setUsername(credentials.get("username"));
        Log.d("auth", credentials.get("username"));
        mDataManager.setPassword(credentials.get("password"));
        Log.d("auth", credentials.get("password"));
    }

    public void onLoginFailed() {
        getMvpView().enableFields();
        getMvpView().enableLoginButton();
    }

    public void setUpAccount(AuthResponse authResponse) {
        ApiData data = authResponse.data();
        if(data != null){
            UserProfile user = data.data();
            mDataManager.setUserId(user.uuid());
            mDataManager.setUsername(user.username());
            mDataManager.setRequestIsActive(user.is_request_on());
            if(user.is_artist()){
                mDataManager.setIsArtist(user.is_artist());
                mDataManager.setArtistName(user.stage_name());
                if(user.background_picture() != null){
                    mDataManager.setBackImagePath(user.background_picture());
                }
            }
            if(user.avatar() != null) {
                mDataManager.setAvatarPath(user.avatar());
            }
        }
        if(authResponse.token() != null){
            mDataManager.setToken(authResponse.token());
        }
        mDataManager.downloadAndSaveCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PutResults>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(PutResults putResults) {

                    }
                });
        mDataManager.downloadAndSaveFollowedCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PutResults<FollowedCategory>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(PutResults<FollowedCategory> followedCategoryPutResults) {
                        Log.d("auth", "done downloading and saving followed categories");
                    }
                });
    }

}
