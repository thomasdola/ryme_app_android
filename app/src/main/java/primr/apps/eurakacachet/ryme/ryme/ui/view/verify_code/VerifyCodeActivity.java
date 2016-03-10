package primr.apps.eurakacachet.ryme.ryme.ui.view.verify_code;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;

import org.vaslabs.smsradar.Sms;
import org.vaslabs.smsradar.SmsListener;
import org.vaslabs.smsradar.SmsRadar;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.followCategory.FollowCategoryActivity;
import primr.apps.eurakacachet.ryme.ryme.utils.Config;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

public class VerifyCodeActivity extends BaseActivity implements VerifyCodeMvpView{

    @Inject VerifyCodePresenter mPresenter;

    int mVerificationState;
    CoordinatorLayout mCoordinatorLayout;
    EditText mCodeEdit;
    Button mVerifyButton;
    ProgressBar mLoadingView;
    TextView mCountDownView;

    public static Intent newIntent(Context context){
        return new Intent(context, VerifyCodeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_verify_code);

        initViews();
        mPresenter.attachView(this);
        initListeners();
        mPresenter.autoVerify();
    }

    private void initListeners() {
        mVerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onVerifyCode();
            }
        });

        Observable<Boolean> codeObservable = RxTextView.textChanges(mCodeEdit)
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        return charSequence.length() == 4;
                    }
                }).distinctUntilChanged();

        codeObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean isValid) {
                        mVerifyButton.setEnabled(isValid);
                    }
                });
    }

    public void onVerifyCode() {
        Log.d("auth", "onVerifyCode called");
        String code = mCodeEdit.getText().toString();
        mPresenter.verify(code);
//        launchFollowCategoryActivity();
    }

    public void launchFollowCategoryActivity() {
        Intent intent = FollowCategoryActivity.newIntent(this);
        startActivity(intent);
    }

    @Override
    public void showError(String message) {
        Snackbar snackbar = Snackbar.make(mCoordinatorLayout, message.toUpperCase(), Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void initSmsService() {
        SmsRadar.initializeSmsRadarService(this, new SmsListener() {
            @Override
            public void onSmsSent(Sms sms) {

            }

            @Override
            public void onSmsReceived(Sms sms) {
                if (sms.getAddress().equals(Config.SMS_SENDER) ||
                        sms.getAddress().toLowerCase().contains(Config.SMS_SENDER.toLowerCase())) {
                    String code = getCode(sms.getMsg());
                    mPresenter.verify(code);
                }
            }
        });
    }

    @Override
    public void updateVerificationState(int state) {
        mVerificationState = state;
    }

    @Override
    public void showInstruction() {
        showError(getString(R.string.enter_code_instruction));
    }

    @Override
    public void hideTimer() {
        mCountDownView.setVisibility(View.GONE);
    }

    private String getCode(String message) {
        int index = message.indexOf(Config.CODE_DELIMITER);
        if( index != -1 ){
            return extractCode(message, index);
        }
        return null;
    }

    @NonNull
    private String extractCode(String message, int index) {
        String code;
        int start = index + 2;
        int length = 4;
        code = message.substring(start, start + length);
        return code;
    }

    private void initViews() {
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.verify_code_coordinator_layout);
        mVerifyButton = (Button) findViewById(R.id.verifyOpt);
        mCodeEdit = (EditText) findViewById(R.id.opt_code_view);
        mLoadingView = (ProgressBar) findViewById(R.id.loading_view);
        mCountDownView = (TextView) findViewById(R.id.auto_verify_countdown);
    }

    @Override
    public void showLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void enableVerifyButton() {
        mVerifyButton.setEnabled(true);
    }

    @Override
    public void disableVerifyButton() {
        mVerifyButton.setEnabled(false);
    }

    @Override
    public void enableField() {
        mCodeEdit.setEnabled(true);
    }

    @Override
    public void disableField() {
        mCodeEdit.setEnabled(false);
    }

    @Override
    public void updateTimerView(long remaining) {
        mCountDownView.setText("seconds remaining: " + Long.toString(remaining));
    }

    @Override
    public void stopLoading() {
        mLoadingView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        if(mVerificationState == VerifyCodePresenter.PENDING){
            Log.d("verification", "verification not done yet");
        }
//        new AlertDialog.Builder(this)
//                .setTitle("Are You Sure?")
//                .setMessage("Automatic Verification Will Be Canceled!")
//                .setNegativeButton(android.R.string.no, null)
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface arg0, int arg1) {
//                        VerifyCodeActivity.super.onBackPressed();
//                    }
//                }).create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopSmsService();
    }

    @Override
    public void stopSmsService() {
        SmsRadar.stopSmsRadarService(this);
    }
}
