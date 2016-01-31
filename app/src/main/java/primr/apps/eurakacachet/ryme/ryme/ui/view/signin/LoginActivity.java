package primr.apps.eurakacachet.ryme.ryme.ui.view.signin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;

public class LoginActivity extends BaseActivity implements LoginMvpView{

    @Inject LoginPresenter mLoginPresenter;

    @Bind(R.id.login_button)
    Button mLoginButton;

    @Bind(R.id.username_edit)
    EditText mUsernameEdit;

    @Bind(R.id.password_edit)
    EditText mPasswordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

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
