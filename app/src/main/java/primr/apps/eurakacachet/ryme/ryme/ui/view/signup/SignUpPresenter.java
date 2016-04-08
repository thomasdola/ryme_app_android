package primr.apps.eurakacachet.ryme.ryme.ui.view.signup;

import android.support.annotation.NonNull;

import java.util.HashMap;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.model.AuthResponse;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class SignUpPresenter extends BasePresenter<SignUpMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public SignUpPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(SignUpMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void signUp(String username, String password, String dial_code,
                       String phone_number, int userGender){
        checkViewAttached();
        getMvpView().disableSignUpButton();
        getMvpView().disableFields();
        getMvpView().showLoading();
        updateUserPreferences(username, dial_code, phone_number);
        HashMap<String, String> payload =
                getPayload(username, password, dial_code, phone_number, userGender);
        mDataManager.setUsername(username);
        mDataManager.setPassword(password);
        mDataManager.setPhone(dial_code+phone_number);

        mSubscription = mDataManager.registerAttempt(payload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AuthResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        onFailure(e.getMessage());
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(AuthResponse response) {
                        if(response.code != 200 ){
                            onFailure(response.message);
                        }else {
                            onSuccess();
                        }
                    }
                });
    }

    private void updateUserPreferences(String username, String dial_code, String phone_number) {
        String phone = dial_code+phone_number;
        mDataManager.setUsername(username);
        mDataManager.setPhone(phone);
    }

    @NonNull
    public HashMap<String, String> getPayload(String username, String password, String dial_code,
                                              String phone_number, int user_gender) {
        HashMap<String , String> payload = new HashMap<>();
        payload.put("username", username);
        payload.put("password", password);
        payload.put("dial_code", dial_code);
        payload.put("phone_number", phone_number);
        payload.put("gender", String.valueOf(user_gender));
        return payload;
    }

    public void onSuccess() {
        getMvpView().stopLoading();
        getMvpView().showSignUpSuccessful();
        getMvpView().launchVerifyCodeActivity();
    }

    public void onFailure(String message) {
        getMvpView().stopLoading();
        getMvpView().showSignUpFail(message);
        getMvpView().enableFields();
        getMvpView().enableSignUpButton();
    }

    public void setUsername(String username){
        checkViewAttached();
        mDataManager.setUsername(username);
    }
}
