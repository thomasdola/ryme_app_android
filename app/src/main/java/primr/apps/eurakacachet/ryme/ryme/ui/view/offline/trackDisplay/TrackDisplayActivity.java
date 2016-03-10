package primr.apps.eurakacachet.ryme.ryme.ui.view.offline.trackDisplay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.SavedTrack;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;


public class TrackDisplayActivity extends BaseActivity implements TrackDisplayMvpView {

    @Inject TrackDisplayPresenter mTrackDisplayPresenter;

    public static final String EXTRA_TRACK = "track";
    public static final String EXTRA_TRACK_POSITION = "track_position";
    SavedTrack mTrack;
    int mTrackPosition;
    ArrayList<SavedTrack> mSavedTracks;

    public TrackDisplayActivity(){}

    public static Intent newIntent(Context context, SavedTrack track, int trackPosition){
        Intent intent = new Intent(context, TrackDisplayActivity.class);
        intent.putExtra(EXTRA_TRACK, track);
        intent.putExtra(EXTRA_TRACK_POSITION, trackPosition);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        if( getIntent() != null ){
            mTrack = getIntent().getParcelableExtra(EXTRA_TRACK);
            mTrackPosition = getIntent().getIntExtra(EXTRA_TRACK_POSITION, 0);
        }
        setContentView(R.layout.downloaded_track_display);
        mTrackDisplayPresenter.attachView(this);
        mSavedTracks = new ArrayList<>(mTrackDisplayPresenter.loadSavedTracks());
        initDownloadTrackDisplayFragment();
    }




    private void initDownloadTrackDisplayFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.download_track_display_container);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if( fragment == null ){
            fragment = DownloadTrackDisplayFragment.newInstance(mTrack, mTrackPosition, mSavedTracks);
            fragmentTransaction.add(R.id.download_track_display_container, fragment)
                    .commit();
        }
    }
}
