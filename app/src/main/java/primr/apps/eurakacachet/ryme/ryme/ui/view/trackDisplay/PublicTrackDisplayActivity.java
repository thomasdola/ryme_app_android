package primr.apps.eurakacachet.ryme.ryme.ui.view.trackDisplay;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;

public class PublicTrackDisplayActivity extends BaseActivity implements PublicTrackDisplayFragmentMvpView {

//    private static final String EXTRA_TRACK_ID = "uuid";
    private static final String EXTRA_TRACK = "track";
//    private UUID mTrackId;
//    private String mTrackTitle;
    private Track mTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if(intent != null){
            mTrack = intent.getParcelableExtra(EXTRA_TRACK);
        }

        setContentView(R.layout.activity_public_track_display);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment trackFragment = fragmentManager.findFragmentById(R.id.public_track_display_container);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(trackFragment == null){
            trackFragment =  TrackDisplayFragment.newInstance(mTrack);
            fragmentTransaction.add(R.id.public_track_display_container, trackFragment).commit();
        }
    }

    public static Intent newIntent(Context packageContext){
        Intent i = new Intent(packageContext, PublicTrackDisplayActivity.class);
//        i.putExtra(EXTRA_TRACK_TITLE, trackTitle);
        return i;
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void togglePayButton() {

    }

    @Override
    public void toggleLikeButton() {

    }

    @Override
    public void disableDownloadButton() {

    }

    @Override
    public void enableDownloadButton() {

    }
}
