package primr.apps.eurakacachet.ryme.ryme.ui.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.UUID;

import primr.apps.eurakacachet.ryme.ryme.R;

public class ArtistTrackDisplayActivity extends AppCompatActivity {

    private static final String EXTRA_TRACK_ID = "uuid";
    private static final String EXTRA_TRACK_TITLE = "trackTitle";
    private UUID mTrackId;
    private String mTrackTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if(intent != null){
//            mTrackTitle = intent.getStringExtra(EXTRA_TRACK_TITLE);
        }

        setContentView(R.layout.activity_artist_track_display);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment trackFragment = fragmentManager.findFragmentById(R.id.artist_track_display_container);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(trackFragment == null){
            trackFragment =  TrackDisplayFragment.newInstance();
            fragmentTransaction.add(R.id.artist_track_display_container, trackFragment).commit();
        }
    }

    public static Intent newIntent(Context packageContext){
        Intent i = new Intent(packageContext, PublicTrackDisplayActivity.class);
//        i.putExtra(EXTRA_TRACK_TITLE, trackTitle);
        return i;
    }

}
