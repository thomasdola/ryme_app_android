package primr.apps.eurakacachet.ryme.ryme.ui.view.verify_code;


import android.os.CountDownTimer;

import java.util.HashMap;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.model.AuthResponse;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class VerifyCodePresenter extends BasePresenter<VerifyCodeMvpView>{

    private final DataManager mDataManager;
    private Subscription mSubscription;
    public static final int PENDING = 0;
    public static final int DONE = 1;
    private AutoVerifyTimer countDownTimer;

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

    public void verify(String code){
        checkViewAttached();
        getMvpView().showLoading();
        getMvpView().updateVerificationState(PENDING);
        mDataManager.verifyCode(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AuthResponse>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().updateVerificationState(DONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        onFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(AuthResponse response) {
                        cancelTimer();
                        int code = response.code;
                        if (code == 200) {
                            onSuccess(response);
                        } else {
                            onFailure(response.message);
                        }
                    }
                });
    }

    public void cancelTimer() {
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
    }

    public HashMap<String, String> buildPayload(String code, HashMap<String, String> payload) {
        payload.put("code", code);
        payload.put("username", getUsername());
        payload.put("phone", getPhone());
        return payload;
    }

    private String getUsername() {
        final String[] username = {null};
        mDataManager.getUsername()
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        username[0] = s;
                    }
                });
        return username[0];
    }

    private String getPhone(){
        final String[] phone = {null};
        mDataManager.getPhone().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                phone[0] = s;
            }
        });
        return phone[0];
    }

    public void onFailure(String message) {
        getMvpView().updateVerificationState(DONE);
        getMvpView().stopLoading();
        getMvpView().showError(message);
    }

    public void onSuccess(AuthResponse response) {
        getMvpView().updateVerificationState(DONE);
        mDataManager.setIsVerified(true);
        mDataManager.setToken(response.token());
        mDataManager.setLogIn(true);
        getMvpView().stopLoading();
        getMvpView().launchFollowCategoryActivity();
    }


    public void autoVerify() {
        checkViewAttached();
        countDownTimer = new AutoVerifyTimer(5000, 1000);
        countDownTimer.start();
        getMvpView().disableField();
        getMvpView().disableVerifyButton();
        getMvpView().initSmsService();
    }

    private class AutoVerifyTimer extends CountDownTimer {
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public AutoVerifyTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long remaining = millisUntilFinished / 1000;
            getMvpView().updateTimerView(remaining);
        }

        @Override
        public void onFinish() {
            onCountDownFinish();
        }

        public void onCountDownFinish() {
            getMvpView().stopSmsService();
            getMvpView().hideTimer();
            getMvpView().stopLoading();
            getMvpView().enableField();
            getMvpView().showInstruction();
        }
    }
}
