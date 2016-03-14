package primr.apps.eurakacachet.ryme.ryme.ui.view.trackDisplay;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ProgressBar;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;

public class PublicTrackDisplayActivity extends BaseActivity implements PublicTrackDisplayActivityMvpView{

    @Inject PublicTrackDisplayActivityPresenter mPresenter;
    public static final String EXTRA_TRACK = "track";
    private String mTrackId;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        mPresenter.attachView(this);
        Intent intent = getIntent();
        if(intent != null){
            mTrackId = intent.getStringExtra(EXTRA_TRACK);
        }
        setContentView(R.layout.activity_public_track_display);
        mProgressBar = (ProgressBar) findViewById(R.id.track_layout_loading);
        mPresenter.getTrack(mTrackId);
    }



    private void init(Track track) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment trackFragment = fragmentManager.findFragmentById(R.id.public_track_display_container);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(trackFragment == null){
            trackFragment =  TrackDisplayFragment.newInstance(track);
            fragmentTransaction.add(R.id.public_track_display_container, trackFragment).commit();
        }
    }

    public static Intent newIntent(Context packageContext, String track_id){
        Intent i = new Intent(packageContext, PublicTrackDisplayActivity.class);
        i.putExtra(EXTRA_TRACK, track_id);
        return i;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }

    @Override
    public void setTrack(Track track) {
        init(track);
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }
}
