package primr.apps.eurakacachet.ryme.ryme.ui.view.verify_code;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.followCategory.FollowCategoryActivity;

public class VerifyCodeActivity extends BaseActivity implements VerifyCodeMvpView{

    @Inject VerifyCodePresenter mVerifyCodePresenter;

    EditText mCodeEdit;
    Button mJoinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        mJoinButton = (Button) findViewById(R.id.verifyOpt);
        mJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerifyCodeActivity.this, FollowCategoryActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void enableVerifyButton() {

    }

    @Override
    public void disableVerifyButton() {

    }
}