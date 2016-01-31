package primr.apps.eurakacachet.ryme.ryme.ui.view.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.signin.LoginActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.verify_code.VerifyCodeActivity;

public class SignUpActivity extends BaseActivity implements SignUpMvpView{

    @Inject SignUpPresenter mSignUpPresenter;

    @Bind(R.id.sign_up_button)
    Button mRegisterButton;

    @Bind(R.id.sign_in_option_text_view)
    TextView mLoginOption;

    @Bind(R.id.dial_code)
    AutoCompleteTextView mDialCode;

    private static final String[] CODES = new String[] {
            "+233", "+228", "+1"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);


        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, VerifyCodeActivity.class);
                startActivity(intent);
            }
        });


        mLoginOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        ArrayAdapter<String> codesAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_dropdown_item_1line, CODES);
        mDialCode.setAdapter(codesAdapter);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void disableSignUpButton() {

    }

    @Override
    public void enableSignUpButton() {

    }

    @Override
    public void showSignUpFail() {

    }

    @Override
    public void showSignUpSuccessful() {

    }
}
