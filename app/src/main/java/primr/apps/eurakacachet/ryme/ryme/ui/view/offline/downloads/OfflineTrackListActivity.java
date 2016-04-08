package primr.apps.eurakacachet.ryme.ryme.ui.view.offline.downloads;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.devbrackets.android.exomedia.util.TimeFormatUtil;
import com.devbrackets.android.playlistcore.event.MediaProgress;
import com.devbrackets.android.playlistcore.event.PlaylistItemChange;
import com.devbrackets.android.playlistcore.listener.PlaylistListener;
import com.devbrackets.android.playlistcore.listener.ProgressListener;
import com.devbrackets.android.playlistcore.manager.IPlaylistItem;
import com.devbrackets.android.playlistcore.service.PlaylistServiceCore;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.RymeApplication;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.SavedTrack;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;
import primr.apps.eurakacachet.ryme.ryme.utils.helpers.layout.DividerItemDecorator;
import primr.apps.eurakacachet.ryme.ryme.utils.helpers.layout.ItemClickSupport;
import primr.apps.eurakacachet.ryme.ryme.utils.player.dataInterface.PCOfflineAudioItemInterface;
import primr.apps.eurakacachet.ryme.ryme.utils.player.manager.PCOfflinePlaylistManager;

public class OfflineTrackListActivity extends BaseActivity implements DownloadsMvpView
        ,PlaylistListener, ProgressListener {


    private static final String EXTRA_CURRENT_POSITION = "current_position";
    @Inject DownloadsPresenter mDownloadsPresenter;
    @Inject DownloadsAdapter mDownloadsAdapter;
    RecyclerView mRecyclerView;
    private ImageView artworkView;
    private TextView currentTrackTitle;
    private TextView artistName;

    private TextView currentPositionView;
    private TextView durationView;

    private SeekBar seekBar;
    private boolean shouldSetDuration;
    private boolean userInteracting;

    private ImageButton previousButton;
    private ImageButton playPauseButton;
    private ImageButton nextButton;

    private PCOfflinePlaylistManager mPlaylistManager;
    private SavedTrack mTrack;
    private List<SavedTrack> mTrackList;
    private int mSelectedIndex = 0;

    private Picasso mPicasso;

    public static final int PLAYLIST_ID = 2016;
    public static final String ARG_TRACK = "track";
    private RelativeLayout mTrackCv;
    private RelativeLayout mDownloadsEmptyState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("offline", "onCreate called");
        getActivityComponent().inject(this);
        mPicasso = Picasso.with(this);
        mDownloadsPresenter.attachView(this);
        mDownloadsPresenter.loadTracks();
        setContentView(R.layout.activity_offline_track_list);
        initViews();
        initListeners();
        initRecyclerView();
        initToolbar();
    }

    private void initViews() {
        Log.d("offline", "iniViews Called");
        artworkView = (ImageView) findViewById(R.id.track_cover_avatar);
        mTrackCv = (RelativeLayout) findViewById(R.id.track_cv);
        currentTrackTitle = (TextView) findViewById(R.id.current_track_title_text_view);
        artistName = (TextView) findViewById(R.id.artist_name_view);
        mDownloadsEmptyState = (RelativeLayout) findViewById(R.id.downloads_list_empty_state);
        currentPositionView = (TextView)findViewById(R.id.audio_player_position);
        durationView = (TextView)findViewById(R.id.audio_player_duration);

        seekBar = (SeekBar)findViewById(R.id.audio_player_seek);

        previousButton = (ImageButton)findViewById(R.id.audio_player_previous);
        playPauseButton = (ImageButton)findViewById(R.id.audio_player_play_pause);
        nextButton = (ImageButton)findViewById(R.id.audio_player_next);
    }

    private void initToolbar() {
        Log.d("offline", "initToolbar Called");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle(R.string.downloads_text);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initRecyclerView() {
        Log.d("offline", "initRecyclerView Called");
        mRecyclerView = (RecyclerView)findViewById(R.id.download_track_list_display_recycler_view);
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                updatePlayer(position);
            }
        });
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecorator(this,
                R.drawable.track_list_divider);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mDownloadsAdapter);
    }

    private void updatePlayer(int position) {
        Log.d("offline", "updatePlayer Called  with -> " + position);
        int currentPlayingPosition = mPlaylistManager.getCurrentPosition();

        if(currentPlayingPosition == position) return;

        mPlaylistManager.setCurrentPosition(position);
        mPlaylistManager.play(0, false);
    }

    public static Intent newIntent(Context context) {
        Log.d("offline", "newIntent Called");
        return new Intent(context, OfflineTrackListActivity.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("offline", "onResume Called");
        mPlaylistManager = RymeApplication.getPCOfflinePlaylistManager();
        mPlaylistManager.registerPlaylistListener(this);
        mPlaylistManager.registerProgressListener(this);
        updateCurrentPlaybackInformation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("offline", "onPause Called");
        mPlaylistManager.unRegisterPlaylistListener(this);
        mPlaylistManager.unRegisterProgressListener(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showFeedback(String trackTitle) {

    }

    @Override
    public void setTracks(List<SavedTrack> savedTracks) {
        Log.d("offline", "setTracks Called with -> " + savedTracks.toString());
        mTrackCv.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
        initPlayer(savedTracks);
        mDownloadsAdapter.setTracks(this, savedTracks);
        mDownloadsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyState() {
        mDownloadsEmptyState.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onPlaylistItemChanged(@Nullable IPlaylistItem currentItem,
                                         boolean hasNext, boolean hasPrevious) {
        Log.d("offline", "onPlaylistItemChanged Called");
        shouldSetDuration = true;
        //Updates the button states
        showNextButton(hasNext);
        showPreviousButton(hasPrevious);

        //Loads the new image
        if(currentItem.getArtworkUrl() != null){
            Log.d("offline", "cover exists ? -> " + (new File(currentItem.getArtworkUrl()))
                    .exists());
            mPicasso.load(new File(currentItem.getArtworkUrl()))
                    .into(artworkView);
        }

        currentTrackTitle.setText(currentItem.getTitle());
        artistName.setText(currentItem.getArtist());

        return true;
    }

    private void showPreviousButton(boolean hasPrevious) {
        Log.d("offline", "showPreviousButton called with -> " + hasPrevious);
        if(hasPrevious){
            previousButton.setVisibility(View.VISIBLE);
        }else {
            previousButton.setVisibility(View.INVISIBLE);
        }
    }

    private void showNextButton(boolean hasNext) {
        Log.d("offline", "showNextButton called with -> " + hasNext);
        if(hasNext){
            nextButton.setVisibility(View.VISIBLE);
        }else {
            nextButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onPlaybackStateChanged(@NonNull PlaylistServiceCore.PlaybackState mediaState) {
        Log.d("offline", "onPlaybackStateChanged Called with -> " + mediaState);
        switch (mediaState) {
            case STOPPED:
                finish();
                break;

            case RETRIEVING:
            case PREPARING:
                restartLoading();
                break;

            case PLAYING:
                doneLoading(true);
                break;

            case PAUSED:
                doneLoading(false);
                break;

            default:
                break;
        }

        return true;
    }

    @Override
    public boolean onProgressUpdated(@NonNull MediaProgress progress) {
        if (shouldSetDuration && progress.getDuration() > 0) {
            shouldSetDuration = false;
            setDuration(progress.getDuration());
        }
        if (!userInteracting) {
            seekBar.setSecondaryProgress((int) (progress.getDuration() * progress.getBufferPercentFloat()));
            seekBar.setProgress((int) progress.getPosition());
            currentPositionView.setText(TimeFormatUtil.formatMs(progress.getPosition()));
        }
        return true;
    }

    private void updateCurrentPlaybackInformation() {
        Log.d("offline", "updateCurrentPlaybackInformation Called");
        PlaylistItemChange<PCOfflineAudioItemInterface> itemChanged =
                mPlaylistManager.getCurrentItemChange();
        if (itemChanged != null) {
            Log.d("offline", "from updateCurrentPlaybackInformation " + itemChanged.getCurrentItem());
                    onPlaylistItemChanged(itemChanged.getCurrentItem(),
                            itemChanged.hasNext(), itemChanged.hasPrevious());
        }

        PlaylistServiceCore.PlaybackState currentPlaybackState =
                mPlaylistManager.getCurrentPlaybackState();
        if (currentPlaybackState != PlaylistServiceCore.PlaybackState.STOPPED) {
            onPlaybackStateChanged(currentPlaybackState);
        }

        MediaProgress mediaProgress = mPlaylistManager.getCurrentProgress();
        if (mediaProgress != null) {
            onProgressUpdated(mediaProgress);
        }
    }

    private boolean setupPlaylistManager(List<SavedTrack> tracks){
        mPlaylistManager = RymeApplication.getPCOfflinePlaylistManager();
        Log.d("offline", "setupPlaylistManager called");
        Log.d("offline", "local playlist id -> " + PLAYLIST_ID);
        Log.d("offline", "foreign playlist id -> " + mPlaylistManager.getId());
        if (mPlaylistManager.getId() == PLAYLIST_ID) {
            Log.d("offline", "setupPlaylistManager already exists");
            return false;
        }
        Log.d("offline", "setupPlaylistManager does not exist");
        List<PCOfflineAudioItemInterface> items = prepareAudioData(tracks);
        mPlaylistManager.setParameters(items, mSelectedIndex);
        mPlaylistManager.setId(PLAYLIST_ID);
        return true;
    }

    @NonNull
    private List<PCOfflineAudioItemInterface> prepareAudioData(List<SavedTrack> tracks) {
        List<PCOfflineAudioItemInterface> items = new ArrayList<>();
        for (SavedTrack track : tracks) {
            PCOfflineAudioItemInterface item = new PCOfflineAudioItemInterface(track);
            items.add(item);
        }
        return items;
    }

    private void initListeners() {
        seekBar.setOnSeekBarChangeListener(new SeekBarChanged());

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlaylistManager.invokePrevious();
            }
        });

        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlaylistManager.invokePausePlay();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlaylistManager.invokeNext();
            }
        });
    }

    private void initPlayer(List<SavedTrack> tracks){
        Log.d("offline", "initPlayer called");

        boolean generatedPlaylist = setupPlaylistManager(tracks);
        startPlayback(generatedPlaylist);
    }

    private void doneLoading(boolean isPlaying) {
        loadCompleted();
        updatePlayPauseImage(isPlaying);
    }

    private void updatePlayPauseImage(boolean isPlaying) {
        int resId = isPlaying ? R.drawable.ic_action_pause_whiteash : R.drawable.ic_action_play_whiteash;
        playPauseButton.setImageResource(resId);
    }

    public void loadCompleted() {
        playPauseButton.setEnabled(true);
        previousButton.setEnabled(true);
        nextButton.setEnabled(true);
    }

    public void restartLoading() {
        playPauseButton.setEnabled(false);
        previousButton.setEnabled(false);
        nextButton.setEnabled(false);
    }

    private void setDuration(long duration) {
        seekBar.setMax((int)duration);
        durationView.setText(TimeFormatUtil.formatMs(duration));
    }

    private void startPlayback(boolean forceStart) {
        Log.d("offline", "startPlayback called with -> " + forceStart);
        Log.d("offline", "local playing position -> " + mSelectedIndex);
        Log.d("offline", "foreign playing position -> " + mPlaylistManager.getCurrentPosition());
        //If we are changing audio files, or we haven't played before then start the playback
        if (forceStart || mPlaylistManager.getCurrentPosition() != mSelectedIndex) {
            mPlaylistManager.play(0, true);
        }
    }

    private class SeekBarChanged implements SeekBar.OnSeekBarChangeListener {
        private int seekPosition = -1;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (!fromUser) {
                return;
            }

            seekPosition = progress;
            currentPositionView.setText(TimeFormatUtil.formatMs(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            userInteracting = true;

            seekPosition = seekBar.getProgress();
            mPlaylistManager.invokeSeekStarted();
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            userInteracting = false;

            //noinspection Range - seekPosition won't be less than 0
            mPlaylistManager.invokeSeekEnded(seekPosition);
            seekPosition = -1;
        }
    }
}
