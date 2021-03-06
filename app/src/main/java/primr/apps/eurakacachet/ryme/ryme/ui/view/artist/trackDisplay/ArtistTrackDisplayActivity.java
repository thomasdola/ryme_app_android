package primr.apps.eurakacachet.ryme.ryme.ui.view.artist.trackDisplay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.UUID;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.ui.view.trackDisplay.PublicTrackDisplayActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.trackDisplay.TrackDisplayFragment;

public class ArtistTrackDisplayActivity extends AppCompatActivity {

    private static final String EXTRA_TRACK_ID = "uuid";
    private static final String EXTRA_TRACK = "track";
    private static final String EXTRA_TRACK_TITLE = "trackTitle";
    private UUID mTrackId;
    private Track mTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if(intent != null){
//            mTrackTitle = intent.getStringExtra(EXTRA_TRACK_TITLE);
            mTrack = intent.getParcelableExtra(EXTRA_TRACK);
        }

        setContentView(R.layout.activity_artist_track_display);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment trackFragment = fragmentManager.findFragmentById(R.id.artist_track_display_container);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(trackFragment == null){
            trackFragment =  TrackDisplayFragment.newInstance(mTrack);
            fragmentTransaction.add(R.id.artist_track_display_container, trackFragment).commit();
        }
    }

    public static Intent newIntent(Context packageContext, Track track){
        Intent i = new Intent(packageContext, PublicTrackDisplayActivity.class);
        i.putExtra(EXTRA_TRACK, track);
        return i;
    }

}
