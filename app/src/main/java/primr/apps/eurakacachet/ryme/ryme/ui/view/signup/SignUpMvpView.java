package primr.apps.eurakacachet.ryme.ryme.ui.view.signup;


import primr.apps.eurakacachet.ryme.ryme.ui.base.MvpView;

public interface SignUpMvpView extends MvpView{

    void showLoading();

    void disableSignUpButton();

    void enableSignUpButton();

    void showSignUpFail(String message);

    void showSignUpSuccessful();

    void launchVerifyCodeActivity();

    void stopLoading();

    void disableFields();

    void enableFields();
}
