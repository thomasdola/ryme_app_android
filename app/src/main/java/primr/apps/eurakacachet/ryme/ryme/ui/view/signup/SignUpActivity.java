package primr.apps.eurakacachet.ryme.ryme.ui.view.signup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func4;

public class SignUpActivity extends BaseActivity implements SignUpMvpView {

    @Inject SignUpPresenter mSignUpPresenter;

    Subscription mSubscription;
    CoordinatorLayout mCoordinatorLayout;
    ProgressBar mProgressView;
    Button mRegisterButton;
    TextView mLoginOption;
    CustomAutoCompleteTextView mDialCode;
    EditText mPhoneNumberEditView;
    EditText mUsernameEditView;
    EditText mPasswordEditView;
    AppCompatRadioButton mFemaleRadioButton;
    AppCompatRadioButton mMaleRadioButton;
    RadioGroup mUserGenderRadioGroup;

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
//        final Pattern no_space = Pattern.compile("[^\\s-]");

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsernameEditView.getText().toString();
                String password = mPasswordEditView.getText().toString();
                String dialCode = mDialCode.getText().toString();
                String phoneNumber = mPhoneNumberEditView.getText().toString();
                int userGender = getUserGender();
                mSignUpPresenter.signUp(username, password, dialCode, phoneNumber, userGender);
            }
        });

        mLoginOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

//        Observable<Boolean> userGenderObservable = RxRadioGroup.checkedChanges(mUserGenderRadioGroup)
//                .map(new Func1<Integer, Boolean>() {
//                    @Override
//                    public Boolean call(Integer checkedId) {
//                        return checkedId != null;
//                    }
//                });

        Observable<Boolean> usernameObservable = RxTextView.textChanges(mUsernameEditView)
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence username) {
                        return username.toString().trim().length() >= 8;
//                                && no_space.matcher(username).matches();
                    }
                }).distinctUntilChanged()
                .map(new Func1<Boolean, Boolean>() {
                    @Override
                    public Boolean call(Boolean isValid) {
                        Log.d("register", "username is valid -> " + isValid);
                        updateTextView(isValid, mUsernameEditView);
                        return isValid;
                    }
                });

        Observable<Boolean> passwordObservable = RxTextView.textChanges(mPasswordEditView)
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence password) {
                        return password.toString().trim().length() >= 10;
                    }
                }).distinctUntilChanged()
                .map(new Func1<Boolean, Boolean>() {
                    @Override
                    public Boolean call(Boolean isValid) {
                        updateTextView(isValid, mPasswordEditView);
                        return isValid;
                    }
                });

        Observable<Boolean> phoneNumberObservable = RxTextView.textChanges(mPhoneNumberEditView)
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence phoneNumber) {
                        return phone.matcher(phoneNumber).matches();
                    }
                }).distinctUntilChanged()
                .map(new Func1<Boolean, Boolean>() {
                    @Override
                    public Boolean call(Boolean isValid) {
                        updateTextView(isValid, mPhoneNumberEditView);
                        return isValid;
                    }
                });

        Observable<Boolean> dialCodeObservable = RxAutoCompleteTextView.itemClickEvents(mDialCode)
                .map(new Func1<AdapterViewItemClickEvent, Boolean>() {
                    @Override
                    public Boolean call(AdapterViewItemClickEvent adapterViewItemClickEvent) {
                        return mDialCode.isSelectionFromPopUp();
                    }
                }).distinctUntilChanged();

        mSubscription = Observable.combineLatest(usernameObservable,
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
//                        if(!usernameValid){
//                            mUsernameEditView.setTextColor(ContextCompat
//                                    .getColor(SignUpActivity.this, R.color.redPink));
//                        }
//
//                        if(!passwordValid){
//                            mPasswordEditView.setTextColor(ContextCompat
//                                    .getColor(SignUpActivity.this, R.color.redPink));
//                        }
//
//                        if(!phoneValid){
//                            mPhoneNumberEditView.setTextColor(ContextCompat
//                                    .getColor(SignUpActivity.this, R.color.redPink));
//                        }
                        Log.d("register", String.format("username -> %s , password -> %s , phone -> %s",
                                usernameValid, passwordValid, phoneValid));
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

    public void updateTextView(Boolean isValid, TextView textView) {
        Log.d("register", "updateTextView called with -> " + isValid);
        if (!isValid) {
            textView.setTextColor(ContextCompat
                    .getColor(SignUpActivity.this, R.color.redPink));
        }else {
            textView.setTextColor(ContextCompat
                    .getColor(SignUpActivity.this, R.color.white_ash));
        }
    }

    private int getUserGender() {
        int gender = 1;
        int checkedRadioId = mUserGenderRadioGroup.getCheckedRadioButtonId();
        switch (checkedRadioId){
            case R.id.male_radio_button:
                gender = 1;
                break;
            case R.id.female_radio_button:
                gender = 0;
                break;
        }
        return gender;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscription != null) mSubscription.unsubscribe();
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
        mMaleRadioButton = (AppCompatRadioButton) findViewById(R.id.male_radio_button);
        mMaleRadioButton.setChecked(true);
        mFemaleRadioButton = (AppCompatRadioButton) findViewById(R.id.female_radio_button);
        mUserGenderRadioGroup = (RadioGroup) findViewById(R.id.user_gender_radio_group);
    }

    @Override
    public void showLoading() {
        mProgressView.setVisibility(View.VISIBLE);
        mProgressView.setIndeterminate(true);
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
        Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSignUpSuccessful() {

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
        mUserGenderRadioGroup.setEnabled(false);
    }

    @Override
    public void enableFields() {
        mUsernameEditView.setEnabled(true);
        mPasswordEditView.setEnabled(true);
        mDialCode.setEnabled(true);
        mPhoneNumberEditView.setEnabled(true);
        mUserGenderRadioGroup.setEnabled(true);
    }
}
