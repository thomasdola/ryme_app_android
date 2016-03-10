package primr.apps.eurakacachet.ryme.ryme.ui.view.splash;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.followCategory.FollowCategoryActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.main.MainActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.signup.SignUpActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.trackDisplay.PublicTrackDisplayActivity;

public class SplashActivity extends BaseActivity implements SplashMvpView {

    private static final String EXTRA_TRACK_ID = "track_id";
    @Inject SplashPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        Log.d("share", "onCreate called");
        mPresenter.attachView(this);
        if(getIntent().getStringExtra(EXTRA_TRACK_ID) == null){
            mPresenter.launchApp();
        }else if(getIntent().getStringExtra(EXTRA_TRACK_ID) != null){
            String track_id = getIntent().getStringExtra(EXTRA_TRACK_ID);
            mPresenter.getTrack(track_id);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("share", "onStart called");
        Branch branch = Branch.getInstance(getApplicationContext());
        branch.initSession(new Branch.BranchReferralInitListener() {
            @Override
            public void onInitFinished(JSONObject referringParams, BranchError error) {
                if (error == null) {
                    try {
                        String track_id = referringParams.getString(EXTRA_TRACK_ID);
                    } catch (JSONException e) {
                        Log.d("share", "JSONException -> " + e.getMessage());
                    }
                } else {
                    Log.d("share", error.toString());
                }
            }
        }, this.getIntent().getData(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d("share", "onNewIntent called");
        this.setIntent(intent);
    }

    @Override
    public void launchMainActivity() {
        Log.d("share", "launchMainActivity called");
        Intent intent = MainActivity.newIntent(this);
        overridePendingTransition(R.anim.activity_push_up_in, R.anim.activity_push_up_out);
        startActivity(intent);
    }

    @Override
    public void launchSignUpActivity() {
        Log.d("share", "launchSignUpActivity called");
        Intent intent = SignUpActivity.newIntent(this);
        overridePendingTransition(R.anim.activity_push_up_in, R.anim.activity_push_up_out);
        startActivity(intent);
    }

    @Override
    public void launchFollowCategoryActivity() {
        Log.d("share", "launchFollowCategoryActivity called");
        Intent intent = FollowCategoryActivity.newIntent(this);
        overridePendingTransition(R.anim.activity_push_up_in, R.anim.activity_push_up_out);
        startActivity(intent);
    }

    @Override
    public void launchPublicTrackDisplayActivity(Track track) {
        Log.d("share", "launchPublicTrackDisplayActivity called");
        Intent intent = PublicTrackDisplayActivity.newIntent(this, track);
        overridePendingTransition(R.anim.activity_push_up_in, R.anim.activity_push_up_out);
        startActivity(intent);
    }

    @Override
    public void closeActivity() {
        finish();
    }
}
