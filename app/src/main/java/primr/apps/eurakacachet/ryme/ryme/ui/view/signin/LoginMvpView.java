package primr.apps.eurakacachet.ryme.ryme.ui.view.signin;

import primr.apps.eurakacachet.ryme.ryme.ui.base.MvpView;


public interface LoginMvpView extends MvpView {

    void showLoginSuccessful();

    void showLoginFail(String message);

    void showLoading();

    void disableLoginButton();

    void enableLoginButton();

    void disableFields();

    void enableFields();

    void launchMainActivity();
}
