package primr.apps.eurakacachet.ryme.ryme.ui.view.signup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.AdapterViewItemClickEvent;
import com.jakewharton.rxbinding.widget.RxAutoCompleteTextView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.regex.Pattern;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.signin.LoginActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.verify_code.VerifyCodeActivity;
import primr.apps.eurakacachet.ryme.ryme.utils.helpers.layout.CustomAutoCompleteTextView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func4;

public class SignUpActivity extends BaseActivity implements SignUpMvpView {

    @Inject SignUpPresenter mSignUpPresenter;

    CoordinatorLayout mCoordinatorLayout;
    ProgressBar mProgressView;
    Button mRegisterButton;
    TextView mLoginOption;
    CustomAutoCompleteTextView mDialCode;
    EditText mPhoneNumberEditView;
    EditText mUsernameEditView;
    EditText mPasswordEditView;

    public static Intent newIntent(Context context){
        return new Intent(context, SignUpActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_register);

        initViews();

        mSignUpPresenter.attachView(this);

        initListeners();


        String[] codes = getResources().getStringArray(R.array.CountryCodes);
        ArrayAdapter<String> codesAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_dropdown_item_1line, codes);
        mDialCode.setAdapter(codesAdapter);
    }

    private void initListeners() {
        final Pattern phone = Pattern.compile("^((?!(0))[0-9]{4,20})$");

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsernameEditView.getText().toString();
                String password = mPasswordEditView.getText().toString();
                String dialCode = mDialCode.getText().toString();
                String phoneNumber = mPhoneNumberEditView.getText().toString();
                mSignUpPresenter.signUp(username, password, dialCode, phoneNumber);
            }
        });

        mLoginOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        Observable<Boolean> usernameObservable = RxTextView.textChanges(mUsernameEditView)
                .map(new Func1<CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence username) {
                return username.length() >= 8;
            }
        }).distinctUntilChanged();

        Observable<Boolean> passwordObservable = RxTextView.textChanges(mPasswordEditView)
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence password) {
                        return password.length() >= 10;
                    }
                }).distinctUntilChanged();

        Observable<Boolean> phoneNumberObservable = RxTextView.textChanges(mPhoneNumberEditView)
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence phoneNumber) {
                        return phone.matcher(phoneNumber).matches();
                    }
                }).distinctUntilChanged();

        Observable<Boolean> dialCodeObservable = RxAutoCompleteTextView.itemClickEvents(mDialCode)
                .map(new Func1<AdapterViewItemClickEvent, Boolean>() {
                    @Override
                    public Boolean call(AdapterViewItemClickEvent adapterViewItemClickEvent) {
                        return mDialCode.isSelectionFromPopUp();
                    }
                }).distinctUntilChanged();

        Observable.combineLatest(usernameObservable,
                passwordObservable,
                dialCodeObservable,
                phoneNumberObservable, new Func4<Boolean,
                                        Boolean,
                                        Boolean,
                                        Boolean,
                                        Boolean>() {
                    @Override
                    public Boolean call(Boolean usernameValid,
                                        Boolean passwordValid,
                                        Boolean dialCodeValid,
                                        Boolean phoneValid) {
                        return usernameValid &&
                                passwordValid &&
                                dialCodeValid &&
                                phoneValid;
                    }
                }).distinctUntilChanged()
//                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean formValid) {
                        if (!formValid) {
                            disableSignUpButton();
                        } else {
                            enableSignUpButton();
                        }
                    }
                });

    }

    private void initViews() {
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.register_layout);
        mRegisterButton = (Button) findViewById(R.id.sign_up_button);
        mRegisterButton.setEnabled(false);
        mProgressView = (ProgressBar) findViewById(R.id.loading_view);
        mLoginOption = (TextView) findViewById(R.id.sign_in_option_text_view);
        mDialCode = (CustomAutoCompleteTextView) findViewById(R.id.dial_code);
        mUsernameEditView = (EditText) findViewById(R.id.username_edit_view);
        mPasswordEditView = (EditText) findViewById(R.id.password_edit_view);
        mPhoneNumberEditView = (EditText) findViewById(R.id.phone_number_edit_text);
    }

    @Override
    public void showLoading() {
        mProgressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void disableSignUpButton() {
        mRegisterButton.setEnabled(false);
    }

    @Override
    public void enableSignUpButton() {
        mRegisterButton.setEnabled(true);
    }

    @Override
    public void showSignUpFail(String message) {
        Snackbar errorSnackbar = Snackbar.make(mCoordinatorLayout,
                message.toUpperCase(), Snackbar.LENGTH_LONG);
        errorSnackbar.show();
    }

    @Override
    public void showSignUpSuccessful() {
        Snackbar successSnackbar = Snackbar.make(mCoordinatorLayout,
                "Registration Successful", Snackbar.LENGTH_SHORT);
        successSnackbar.show();
    }

    @Override
    public void launchVerifyCodeActivity() {
        Intent i = VerifyCodeActivity.newIntent(this);
        startActivity(i);
    }

    @Override
    public void stopLoading() {
        mProgressView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void disableFields() {
        mUsernameEditView.setEnabled(false);
        mPasswordEditView.setEnabled(false);
        mDialCode.setEnabled(false);
        mPhoneNumberEditView.setEnabled(false);
    }

    @Override
    public void enableFields() {
        mUsernameEditView.setEnabled(true);
        mPasswordEditView.setEnabled(true);
        mDialCode.setEnabled(true);
        mPhoneNumberEditView.setEnabled(true);
    }
}
