package primr.apps.eurakacachet.ryme.ryme.ui.view.offline.trackDisplay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.DownloadedTrack;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;


public class TrackDisplayActivity extends BaseActivity implements TrackDisplayMvpView {

    @Inject TrackDisplayPresenter mTrackDisplayPresenter;

    public static final String EXTRA_TRACK = "track";
    DownloadedTrack mTrack;

    public TrackDisplayActivity(){}

    public static Intent newIntent(Context context, DownloadedTrack track){
        Intent intent = new Intent(context, TrackDisplayActivity.class);
        intent.putExtra(EXTRA_TRACK, track);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if( getIntent() != null ){
            mTrack = getIntent().getParcelableExtra(EXTRA_TRACK);
        }

        setContentView(R.layout.downloaded_track_display);

        init();
    }




    private void init() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.download_track_display_container);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if( fragment == null ){
            fragment = DownloadTrackDisplayFragment.newInstance(mTrack);
            fragmentTransaction.add(R.id.download_track_display_container, fragment)
                    .commit();
        }
    }
}
