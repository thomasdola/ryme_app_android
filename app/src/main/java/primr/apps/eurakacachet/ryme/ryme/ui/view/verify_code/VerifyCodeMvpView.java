package primr.apps.eurakacachet.ryme.ryme.ui.view.verify_code;


import primr.apps.eurakacachet.ryme.ryme.ui.base.MvpView;

public interface VerifyCodeMvpView extends MvpView{

    void showLoading();

    void enableVerifyButton();

    void disableVerifyButton();

    void enableField();

    void disableField();

    void updateTimerView(long remaining);

    void stopLoading();

    void launchFollowCategoryActivity();

    void showError(String message);

    void initSmsService();

    void stopSmsService();

    void updateVerificationState(int state);

    void showInstruction();

    void hideTimer();
}
