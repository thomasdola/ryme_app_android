package primr.apps.eurakacachet.ryme.ryme.ui.view.offline.trackDisplay;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.devbrackets.android.exomedia.event.EMMediaProgressEvent;
import com.devbrackets.android.exomedia.event.EMPlaylistItemChangedEvent;
import com.devbrackets.android.exomedia.listener.EMPlaylistServiceCallback;
import com.devbrackets.android.exomedia.listener.EMProgressCallback;
import com.devbrackets.android.exomedia.manager.EMPlaylistManager;
import com.devbrackets.android.exomedia.service.EMPlaylistService;
import com.devbrackets.android.exomedia.util.TimeFormatUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.RymeApplication;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.SavedTrack;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;
import primr.apps.eurakacachet.ryme.ryme.utils.player.dataInterface.OfflineAudioItemInterface;
import primr.apps.eurakacachet.ryme.ryme.utils.player.manager.OfflinePlaylistManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadTrackDisplayFragment extends Fragment implements DownloadTrackDisplayMvpView,EMPlaylistServiceCallback, EMProgressCallback {


    private static final String ARG_SELECTED_INDEX = "selected_index";
    private static final String ARG_TRACKS = "track_list";
    @Inject DownloadTrackDisplayPresenter mPresenter;

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

    private LinearLayout mPlayerControlsView;
    private LinearLayout mTrackInfoView;

    private OfflinePlaylistManager playlistManager;
    private SavedTrack mTrack;
    private List<SavedTrack> mTrackList;
    private int mSelectedIndex = 0;

    private Picasso picasso;

    public static final int PLAYLIST_ID = 2016;
    public static final String ARG_TRACK = "track";

    public static DownloadTrackDisplayFragment newInstance(SavedTrack track, int selectedIndex,
                                                           @NonNull ArrayList<SavedTrack> trackList) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_TRACK, track);
        args.putInt(ARG_SELECTED_INDEX, selectedIndex);
        args.putParcelableArrayList(ARG_TRACKS, trackList);
        DownloadTrackDisplayFragment fragment = new DownloadTrackDisplayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mTrack = getArguments().getParcelable(ARG_TRACK);
            mTrackList = getArguments().getParcelableArrayList(ARG_TRACKS);
            mSelectedIndex = getArguments().getInt(ARG_SELECTED_INDEX);
        }
    }

    public DownloadTrackDisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_download_track_display, container, false);
        initViews(rootView);
        initListeners();
        initBlur();
        return rootView;
    }

    private void initBlur() {
//        Blurry.with(getContext())
//                .radius(25)
//                .sampling(2)
//                .async()
//                .animate(500)
//                .onto(mPlayerControlsView);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((BaseActivity)getActivity()).getActivityComponent().inject(this);
        mPresenter.attachView(this);
        initPlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        playlistManager.unRegisterServiceCallbacks(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        playlistManager = RymeApplication.getOfflinePlaylistManager();
        playlistManager.registerServiceCallbacks(this);

        //Makes sure to retrieve the current playback information
        updateCurrentPlaybackInformation();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public boolean onPlaylistItemChanged(EMPlaylistManager.PlaylistItem currentItem,
                                         boolean hasNext, boolean hasPrevious) {
        shouldSetDuration = true;

        //Updates the button states
        nextButton.setEnabled(hasNext);
        previousButton.setEnabled(hasPrevious);

        //Loads the new image
        picasso.load(new File(currentItem.getArtworkUrl())).into(artworkView);
        currentTrackTitle.setText(currentItem.getTitle());
        artistName.setText(currentItem.getArtist());

        return true;
    }

    @Override
    public boolean onMediaStateChanged(EMPlaylistService.MediaState mediaState) {
        switch (mediaState) {
            case STOPPED:
                getActivity().finish();
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
    public boolean onProgressUpdated(EMMediaProgressEvent event) {
        if (shouldSetDuration && event.getDuration() > 0) {
            shouldSetDuration = false;
            setDuration(event.getDuration());
        }
        if (!userInteracting) {
            seekBar.setSecondaryProgress((int) (event.getDuration() * event.getBufferPercentFloat()));
            seekBar.setProgress((int)event.getPosition());
            currentPositionView.setText(TimeFormatUtil.formatMs(event.getPosition()));
        }
        return true;
    }

    private void initViews(View rootView) {
        artworkView = (ImageView) rootView.findViewById(R.id.track_cover_view);

        currentTrackTitle = (TextView) rootView.findViewById(R.id.track_title_view);
        artistName = (TextView) rootView.findViewById(R.id.artist_name_view);

        currentPositionView = (TextView)rootView.findViewById(R.id.audio_player_position);
        durationView = (TextView)rootView.findViewById(R.id.audio_player_duration);

        seekBar = (SeekBar)rootView.findViewById(R.id.audio_player_seek);

        previousButton = (ImageButton)rootView.findViewById(R.id.audio_player_previous);
        playPauseButton = (ImageButton)rootView.findViewById(R.id.audio_player_play_pause);
        nextButton = (ImageButton)rootView.findViewById(R.id.audio_player_next);

        mTrackInfoView = (LinearLayout) rootView.findViewById(R.id.track_info_view);
        mPlayerControlsView = (LinearLayout) rootView.findViewById(R.id.player_controls_view);
    }

    private void initListeners() {

        seekBar.setOnSeekBarChangeListener(new SeekBarChanged());

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playlistManager.invokePrevious();
            }
        });

        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playlistManager.invokePausePlay();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playlistManager.invokeNext();
            }
        });
    }

    private boolean setupPlaylistManager(){
        Log.d("offline", "setupPlaylistManager called");
        playlistManager = RymeApplication.getOfflinePlaylistManager();
        if (playlistManager.getPlayListId() == PLAYLIST_ID) {
            return false;
        }
        List<OfflineAudioItemInterface> items = new ArrayList<>();
        for (SavedTrack track : mTrackList) {
            OfflineAudioItemInterface item = new OfflineAudioItemInterface(track);
            items.add(item);
        }
        playlistManager.setParameters(items, mSelectedIndex);
        return true;
    }

    private void updateCurrentPlaybackInformation() {
        EMPlaylistItemChangedEvent itemChangedEvent = playlistManager.getCurrentItemChangedEvent();
        if (itemChangedEvent != null) {
            onPlaylistItemChanged(itemChangedEvent.getCurrentItem(),
                    itemChangedEvent.hasNext(), itemChangedEvent.hasPrevious());
        }

        EMPlaylistService.MediaState currentMediaState = playlistManager.getCurrentMediaState();
        if (currentMediaState != EMPlaylistService.MediaState.STOPPED) {
            onMediaStateChanged(currentMediaState);
        }

        EMMediaProgressEvent progressEvent = playlistManager.getCurrentProgress();
        if (progressEvent != null) {
            onProgressUpdated(progressEvent);
        }
    }

    private void initPlayer(){
        picasso = Picasso.with(getContext());

        boolean generatedPlaylist = setupPlaylistManager();
        startPlayback(generatedPlaylist);
    }

    private void doneLoading(boolean isPlaying) {
        loadCompleted();
        updatePlayPauseImage(isPlaying);
    }

    private void updatePlayPauseImage(boolean isPlaying) {
        int resId = isPlaying ? R.drawable.exomedia_ic_pause_black : R.drawable.exomedia_ic_play_arrow_black;
        playPauseButton.setImageResource(resId);
    }

    public void loadCompleted() {
        playPauseButton.setVisibility(View.VISIBLE);
        previousButton.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.VISIBLE );
    }

    public void restartLoading() {
        playPauseButton.setVisibility(View.GONE);
        previousButton.setVisibility(View.GONE);
        nextButton.setVisibility(View.GONE );
    }

    private void setDuration(long duration) {
        seekBar.setMax((int)duration);
        durationView.setText(TimeFormatUtil.formatMs(duration));
    }

    private void startPlayback(boolean forceStart) {
        //If we are changing audio files, or we haven't played before then start the playback
        if (forceStart || playlistManager.getCurrentIndex() != mSelectedIndex) {
            playlistManager.play(0, false);
        }
    }

    /**
     * Listens to the seek bar change events and correctly handles the changes
     */
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
            playlistManager.invokeSeekStarted();
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            userInteracting = false;

            playlistManager.invokeSeekEnded(seekPosition);
            seekPosition = -1;
        }
    }
}
