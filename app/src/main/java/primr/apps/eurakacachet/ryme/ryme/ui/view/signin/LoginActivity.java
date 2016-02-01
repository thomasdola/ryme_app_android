package primr.apps.eurakacachet.ryme.ryme.ui.view.signin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;

public class LoginActivity extends BaseActivity implements LoginMvpView{

    @Inject LoginPresenter mLoginPresenter;

    Button mLoginButton;
    EditText mUsernameEdit;
    EditText mPasswordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mPasswordEdit = (EditText) findViewById(R.id.password_edit);
        mUsernameEdit = (EditText) findViewById(R.id.username_edit);
        mLoginButton = (Button) findViewById(R.id.login_button);

    }

    @Override
    public void showLoginSuccessful() {

    }

    @Override
    public void showLoginFail() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void disableLoginButton() {

    }

    @Override
    public void enableLoginButton() {

    }
}
