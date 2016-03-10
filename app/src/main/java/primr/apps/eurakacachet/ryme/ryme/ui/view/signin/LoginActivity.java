package primr.apps.eurakacachet.ryme.ryme.ui.view.signin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.main.MainActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.signup.SignUpActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

public class LoginActivity extends BaseActivity implements LoginMvpView{

    @Inject LoginPresenter mLoginPresenter;

    Button mLoginButton;
    EditText mUsernameEdit;
    EditText mPasswordEdit;
    ProgressBar mLoadingView;
    TextView mSignUpOption;
    RelativeLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_login);
        initViews();
        mLoginPresenter.attachView(this);
        initListeners();
    }

    private void initListeners() {
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogin();
            }
        });

        mSignUpOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchRegisterActivity();
            }
        });

        Observable<Boolean> usernameObservable = RxTextView.textChanges(mUsernameEdit)
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        return charSequence.length() >= 8;
                    }
                }).distinctUntilChanged();
        Observable<Boolean> passwordObservable = RxTextView.textChanges(mPasswordEdit)
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        return charSequence.length() >= 10;
                    }
                }).distinctUntilChanged();

        Observable.combineLatest(usernameObservable,
                passwordObservable, new Func2<Boolean, Boolean, Boolean>() {
                    @Override
                    public Boolean call(Boolean usernameIsValid, Boolean passwordIsValid) {
                        return usernameIsValid && passwordIsValid;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean isValid) {
                        mLoginButton.setEnabled(isValid);
                    }
                });
    }

    private void launchRegisterActivity() {
        Intent intent = SignUpActivity.newIntent(this);
        startActivity(intent);
    }

    private void onLogin() {
        String username = mUsernameEdit.getText().toString();
        String password = mPasswordEdit.getText().toString();
        mLoginPresenter.login(username, password);
//        launchMainActivity();
    }

    private void initViews() {
        mPasswordEdit = (EditText) findViewById(R.id.password_edit);
        mUsernameEdit = (EditText) findViewById(R.id.username_edit);
        mSignUpOption = (TextView) findViewById(R.id.sign_up_option_view);
        mLoginButton = (Button) findViewById(R.id.login_button);
        mLoadingView = (ProgressBar) findViewById(R.id.loading_view);
        mLayout = (RelativeLayout) findViewById(R.id.login_layout);
    }

    @Override
    public void showLoginSuccessful() {
        mLoadingView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showLoginFail(String message) {
        mLoadingView.setVisibility(View.INVISIBLE);
        Log.d("auth", message);
        Snackbar snackbar = Snackbar.make(mLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void showLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void disableLoginButton() {
        mLoginButton.setEnabled(false);
        mSignUpOption.setEnabled(false);
    }

    @Override
    public void enableLoginButton() {
        mLoginButton.setEnabled(true);
        mSignUpOption.setEnabled(true);
    }

    @Override
    public void disableFields() {
        mUsernameEdit.setEnabled(false);
        mPasswordEdit.setEnabled(false);
    }

    @Override
    public void enableFields() {
        mUsernameEdit.setEnabled(true);
        mPasswordEdit.setEnabled(true);
    }

    @Override
    public void launchMainActivity() {
        Intent intent = MainActivity.newIntent(this);
        startActivity(intent);
    }
}
