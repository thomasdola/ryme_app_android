package primr.apps.eurakacachet.ryme.ryme.ui.view.trackDisplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.devbrackets.android.exomedia.util.TimeFormatUtil;
import com.devbrackets.android.playlistcore.event.MediaProgress;
import com.devbrackets.android.playlistcore.event.PlaylistItemChange;
import com.devbrackets.android.playlistcore.listener.PlaylistListener;
import com.devbrackets.android.playlistcore.listener.ProgressListener;
import com.devbrackets.android.playlistcore.manager.IPlaylistItem;
import com.devbrackets.android.playlistcore.service.PlaylistServiceCore;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.github.jorgecastilloprz.FABProgressCircle;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadQueueSet;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.notification.BaseNotificationItem;
import com.liulishuo.filedownloader.notification.FileDownloadNotificationHelper;
import com.liulishuo.filedownloader.notification.FileDownloadNotificationListener;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import co.mobiwise.library.ProgressLayout;
import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.SharingHelper;
import io.branch.referral.util.LinkProperties;
import io.branch.referral.util.ShareSheetStyle;
import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.RymeApplication;
import primr.apps.eurakacachet.ryme.ryme.data.model.Comment;
import primr.apps.eurakacachet.ryme.ryme.data.model.PlayBackEvent;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.artist.profile.ArtistProfileActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.comment.AddCommentDialogFragment;
import primr.apps.eurakacachet.ryme.ryme.ui.view.comment.CommentListFragment;
import primr.apps.eurakacachet.ryme.ryme.utils.helpers.layout.DownloadNotificationItem;
import primr.apps.eurakacachet.ryme.ryme.utils.helpers.layout.NotificationItem;
import primr.apps.eurakacachet.ryme.ryme.utils.helpers.number.NumberFormatter;
import primr.apps.eurakacachet.ryme.ryme.utils.player.dataInterface.PCOnlineAudioItemInterface;
import primr.apps.eurakacachet.ryme.ryme.utils.player.manager.PCOnlinePlaylistManager;


public class TrackDisplayFragment extends Fragment implements PublicTrackFragmentMvpView,
        PlaylistListener, ProgressListener{

    @Inject PublicTrackDisplayFragmentPresenter mPresenter;
    @Inject Bus mBus;
    private static final String COMMENT_ON_TRACK = "comment_track";
    public static final int TRACK_DOWNLOAD_ID = 0;
    public static final int COVER_DOWNLOAD_ID = 1;

    private FileDownloadNotificationHelper<DownloadNotificationItem> notificationHelper;
    private static final String ARG_TRACK = "track";
    private static final String EXTRA_INDEX = "index";
    public static final int PLAYLIST_ID = 20;
    public FloatingActionButton mDownloadButton;
    public FABProgressCircle mPlayLoadingFab;
    public FABProgressCircle mDownloadLoadingFab;

    private List<Comment> mCommentList;

    private PCOnlinePlaylistManager playlistManager;
    private int selectedIndex = 0;
    private int playingCount = 0;
    private boolean shouldSetDuration;
    private boolean userInteracting;
    private int downloadId = 0;

    CollapsingToolbarLayout mCollapsingToolbarLayout;
    FloatingActionButton playTrackFab;
    FloatingActionsMenu mTrackActionsMenu;
    com.getbase.floatingactionbutton.FloatingActionButton mShareTrackFab;
    com.getbase.floatingactionbutton.FloatingActionButton mViewArtistProfileFab;
    com.getbase.floatingactionbutton.FloatingActionButton mCommentTrackFab;
    TextView streamsView;
    TextView likesView;
    TextView downloadsView;
    TextView commentsView;
    Toolbar toolbar;
    Picasso mPicasso;
    ImageView mHeader;
    ImageButton mDownloadTrackButton;
    public Track mTrack;
    private ProgressLayout mTrackProgressBar;
    ProgressBar mDownloadProgressBar;
    private boolean mPlaylistOver;
    CoordinatorLayout mCoordinatorLayout;
    private boolean trackAlreadyLiked;
    private Menu mMenu;
    private boolean mIsTrack = false;
    private boolean mTrackLoadingError;
    private boolean trackAlreadyDownloaded;
    private boolean mAudioAdIsPlayed;
    private boolean mTrackIsPlayed;


    public static TrackDisplayFragment newInstance(Track track) {
        TrackDisplayFragment fragment = new TrackDisplayFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_TRACK, track);
        fragment.setArguments(args);
        return fragment;
    }

    public TrackDisplayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();
        if (getArguments() != null) {
            mTrack = getArguments().getParcelable(ARG_TRACK);
            if(mTrack != null) Log.d("track", "track -> " + mTrack.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_track, container, false);
        mPicasso = Picasso.with(getContext());
        initViews(rootView);
        initToolbar();
        initCommentView();
        return rootView;
    }

    private void initCommentView() {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment = fragmentManager.findFragmentById(R.id.comment_list_container);
        if(fragment == null){
            fragment = CommentListFragment.newInstance(mTrack);
            fragmentTransaction.add(R.id.comment_list_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((BaseActivity)getActivity()).getActivityComponent().inject(this);
        mPresenter.attachView(this);
        notificationHelper = new FileDownloadNotificationHelper<>();
        initImageHeader();
        initCollapsingToolbarLayout();
        initListeners();
        initPlayer();
        initTrackInfoViews();
    }

    private void initTrackInfoViews() {
        if(!mTrack.downloadable() || trackAlreadyDownloaded){
            mDownloadTrackButton.setVisibility(View.GONE);
        }
        streamsView.setText(NumberFormatter.format(mTrack.streams()));
        likesView.setText(NumberFormatter.format(mTrack.likes()));
        downloadsView.setText(NumberFormatter.format(mTrack.downloads()));
        commentsView.setText(NumberFormatter.format(mTrack.comments()));
    }

    public void initToolbar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void initViews(View rootView) {
        mTrackActionsMenu = (FloatingActionsMenu) rootView.findViewById(R.id.track_menu_fab);
        mShareTrackFab = (com.getbase.floatingactionbutton.FloatingActionButton)
                rootView.findViewById(R.id.share_track_fab);
        mViewArtistProfileFab = (com.getbase.floatingactionbutton.FloatingActionButton)
                rootView.findViewById(R.id.view_artist_profile_fab);
        mCommentTrackFab = (com.getbase.floatingactionbutton.FloatingActionButton)
                rootView.findViewById(R.id.comment_track_fab);
        streamsView = (TextView) rootView.findViewById(R.id.info_track_stream_text);
        likesView = (TextView) rootView.findViewById(R.id.info_track_favorite_text);
        downloadsView = (TextView) rootView.findViewById(R.id.info_track_download_text);
        commentsView = (TextView) rootView.findViewById(R.id.info_track_comment_list_text);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
        mHeader = (ImageView) rootView.findViewById(R.id.header);
        mDownloadTrackButton = (ImageButton) rootView.findViewById(R.id.ic_action_track_download);
        mDownloadTrackButton.setEnabled(false);
        mTrackProgressBar = (ProgressLayout) rootView.findViewById(R.id.progressLayout);
        mDownloadProgressBar = (ProgressBar) rootView.findViewById(R.id.downloading_progressbar);
        playTrackFab = (FloatingActionButton) rootView.findViewById(R.id.play_song_fab);
        mCoordinatorLayout = (CoordinatorLayout) rootView.findViewById(R.id.public_track_display_coordinator_layout);
        mPlayLoadingFab = (FABProgressCircle) rootView.findViewById(R.id.track_loading_fab);
        mPlayLoadingFab.measure(15, 15);
        toolbar = (Toolbar) rootView.findViewById(R.id.track_view_toolbar);
    }

    public void initListeners() {

        mCommentTrackFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTrackActionsMenu.collapse();
                showCommentDialog(mTrack.uuid());
            }
        });

        mViewArtistProfileFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTrackActionsMenu.collapse();
                launchArtistProfileActivity();
            }
        });

        mShareTrackFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShareTrack();
            }
        });

        playTrackFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAudioAdIsPlayed && mPlaylistOver && mTrack.firstAd() != null){
                    Log.d("playlist", "invoke repeat with ad");
                    playlistManager.invokeRepeat();
                }else {
                    playlistManager.invokePausePlay();
                }
            }
        });

        mDownloadTrackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStartTrackDownload();
            }
        });

//        mRepeatTrackSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                buttonView.setChecked(isChecked);
//                playlistManager.invokeRepeat();
//            }
//        });

//        downloadTrackFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Snackbar snackbar = Snackbar.make(v, "Ready to download", Snackbar.LENGTH_LONG);
//                snackbar.show();
//                onStartTrackDownload();
//            }
//        });
    }

    @Subscribe
    public void updateInterface(PlayBackEvent playBackEvent){
        Log.d("playlist", "updateInterface is called with -> " + playBackEvent.toString());
        int event = playBackEvent.geteventType();
        if(event == PlayBackEvent.FINISH){
            mPlaylistOver = true;
            updatePlayPauseImage(false);
            onTrackCompleted();
        }
    }

    private void onShareTrack() {
        if(mTrack.cover() != null){
            BranchUniversalObject branchUniversalObject = new BranchUniversalObject();
            branchUniversalObject
                    .setCanonicalIdentifier("tracks/"+mTrack.uuid())
                    .setTitle(mTrack.title() + " by " + mTrack.artist_name())
                    .setContentImageUrl(mTrack.cover())
                    .addContentMetadata("track_id", mTrack.uuid())
                    .setContentIndexingMode(BranchUniversalObject.CONTENT_INDEX_MODE.PRIVATE);

            LinkProperties linkProperties = new LinkProperties();
            linkProperties.setFeature("sharing");

            ShareSheetStyle shareSheetStyle = new ShareSheetStyle(getContext(),
                    mTrack.title(), mTrack.title());
            shareSheetStyle.addPreferredSharingOption(SharingHelper.SHARE_WITH.FACEBOOK);
            branchUniversalObject.showShareSheet(getActivity(), linkProperties, shareSheetStyle,
                    new Branch.BranchLinkShareListener() {
                        @Override
                        public void onShareLinkDialogLaunched() {
                            Log.d("share", "onSharedLinkDialogLaunched called");
                        }

                        @Override
                        public void onShareLinkDialogDismissed() {
                            Log.d("share", "onShareLinkDialogDismissed called");
                        }

                        @Override
                        public void onLinkShareResponse(String sharedLink, String sharedChannel,
                                                        BranchError error) {
                            if(error == null){
                                Log.d("share", "sharedLink -> " + sharedLink);
                                Log.d("share", "sharedChannel -> " + sharedChannel);
                            }else {
                                Log.d("share", "error -> " + error.getMessage());
                            }
                        }

                        @Override
                        public void onChannelSelected(String channelName) {
                            Log.d("share", "Channel chosen for the sharing -> " + channelName);
                        }
                    });
        }
    }

    private void showCommentDialog(String trackId) {
        FragmentManager fragmentManager = getChildFragmentManager();
        AddCommentDialogFragment fragment = AddCommentDialogFragment.newInstance(trackId);
        fragment.show(fragmentManager, COMMENT_ON_TRACK);
    }

    private void retryPlayback() {
//        int index = playlistManager.getCurrentIndex();
//        startPlayback();
        playlistManager.invokeRepeat();
    }

    private void onStartTrackDownload() {
        disableDownloadButton();
        final Track item = mTrack;
        final FileDownloadNotificationListener queueTarget = new
                FileDownloadNotificationListener(notificationHelper) {
                    @Override
                    protected BaseNotificationItem create(
                            BaseDownloadTask task) {
                        return new NotificationItem(task.getDownloadId(), item.title(), item.title());
                    }

                    @Override
                    public void createNotification(BaseDownloadTask task) {
                        super.createNotification(task);
//                            showNotificationCb.setEnabled(false);
                    }

                    @Override
                    public void destroyNotification(BaseDownloadTask task) {
                        super.destroyNotification(task);
//                            showNotificationCb.setEnabled(true);
                    }

                    @Override
                    protected boolean interceptCancel(BaseDownloadTask task,
                                                      BaseNotificationItem n) {
                        // in this demo, I don't want to cancel the notification, just show for the test
                        // so return true
                        return true;
                    }

                    @Override
                    protected boolean disableNotification(BaseDownloadTask task) {
                        return true;
                    }

                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.pending(task, soFarBytes, totalBytes);
                        mDownloadProgressBar.setIndeterminate(true);
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.progress(task, soFarBytes, totalBytes);
                        mDownloadProgressBar.setIndeterminate(false);
                        mDownloadProgressBar.setMax(totalBytes);
                        mDownloadProgressBar.setProgress(soFarBytes);
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        super.completed(task);
                        Object taskTag = task.getTag();
                        if(taskTag.equals(TRACK_DOWNLOAD_ID)){
                            String path = task.getPath();
                            mPresenter.saveTrack(mTrack, path);
                        }else if(taskTag.equals(COVER_DOWNLOAD_ID)){
                            String path = task.getPath();
                            mPresenter.updateSavedTrackWithCover(path, mTrack.uuid());
                        }
                        mDownloadProgressBar.setIndeterminate(false);
                        mDownloadProgressBar.setProgress(task.getSmallFileTotalBytes());

                    }
                };

        final FileDownloadQueueSet queueSet = new FileDownloadQueueSet(queueTarget);
        String savePath = getActivity().getFilesDir().getPath();
        queueSet.downloadSequentially(
                FileDownloader.getImpl().create(mTrack.path())
                        .setPath(savePath + mTrack.uuid() + UUID.randomUUID().toString() + ".mp3")
                        .setTag(0),
                FileDownloader.getImpl().create(mTrack.cover())
                        .setPath(savePath + mTrack.uuid() + UUID.randomUUID().toString() + ".jpg")
                        .setTag(1)
        );
        queueSet.start();
    }

    private void initPlayer() {
        boolean generatedPlaylist = setupPlaylistManager();
        startPlayback(generatedPlaylist);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
        menuInflater.inflate(R.menu.menu_track, menu);
        this.mMenu = menu;
        super.onCreateOptionsMenu(menu, menuInflater);
        mPresenter.isLiked(mTrack);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu){
        MenuItem like = menu.findItem(R.id.favorite_track_action);
        if(trackAlreadyLiked){
            like.setIcon(R.drawable.ic_action_like_blueblack);
        }else {
            like.setIcon(R.drawable.ic_action_like_fill_blueblack);
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        int menuItemId = menuItem.getItemId();
        switch (menuItemId){
            case android.R.id.home:
                finish();
                getActivity().overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                return true;
            case R.id.favorite_track_action:
                toggleLikeTrack();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void toggleLikeTrack() {
        if( !trackAlreadyLiked){
            mPresenter.likeTrack(mTrack);
        }else {
            mPresenter.dislikeTrack(mTrack);
        }
    }

    private void initCollapsingToolbarLayout() {
        mCollapsingToolbarLayout.setTitle(mTrack.title());
        mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorPrimary));
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.transparent));
    }

    private void initImageHeader() {
        mPicasso.load(mTrack.cover())
                .placeholder(R.drawable.wallpaper)
                .error(R.drawable.wallpaper)
                .into(mHeader);
    }

    @Override
    public void showLoading() {
        mPlayLoadingFab.show();
    }

    @Override
    public void hideLoading() {
        mPlayLoadingFab.hide();
    }

    @Override
    public void toggleLikeButton() {

    }


    @Override
    public void disableDownloadButton() {
        mDownloadTrackButton.setEnabled(false);
    }

    @Override
    public void enableDownloadButton() {
        mDownloadTrackButton.setEnabled(true);
    }

    @Override
    public void showErrorSnack(String message) {
        Snackbar errorSnack = Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG);
        errorSnack.show();
    }

    @Override
    public void showSuccessSnack(int messageType) {

    }

    @Override
    public void isLiked(Boolean isLiked) {
        trackAlreadyLiked = isLiked;
        updateLikeButton(isLiked);
    }

    @Override
    public void updateStreamsCount() {

    }

    @Override
    public void trackIsAlreadySaved(Boolean isSaved) {
        Log.d("playlist", "trackIsAlreadySaved called with => " + isSaved);
        if(isSaved && mTrack.downloaded()){
            trackAlreadyDownloaded = true;
        }
    }

    @Override
    public void showDownloadSuccess() {
        Toast.makeText(getContext(),"Track Downloaded.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDownloadFailure() {
        Toast.makeText(getContext(),"Downloading Failed.", Toast.LENGTH_SHORT).show();
    }

    private void updateLikeButton(Boolean isLiked) {
        if(mMenu != null ){
            MenuItem likeButton = mMenu.findItem(R.id.favorite_track_action);
            if(isLiked){
                likeButton.setIcon(R.drawable.ic_action_like_fill_blueblack);
            }else {
                likeButton.setIcon(R.drawable.ic_action_like_blueblack);
            }
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        mBus.unregister(this);
        playlistManager.unRegisterPlaylistListener(this);
        playlistManager.unRegisterProgressListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mBus.register(this);
        playlistManager = RymeApplication.getPCOnlinePlaylistManager();
        playlistManager.registerPlaylistListener(this);
        playlistManager.registerProgressListener(this);
        updateCurrentPlaybackInformation();
    }

    //Player Implementations

    private boolean setupPlaylistManager() {
        playlistManager = RymeApplication.getPCOnlinePlaylistManager();
        Log.d("online", "setupPlaylistManager called");
        Log.d("online", "local playlist id -> " + PLAYLIST_ID);
        Log.d("online", "foreign playlist id -> " + playlistManager.getId());
        if (playlistManager.getId() == PLAYLIST_ID) {
            return false;
        }
        List<PCOnlineAudioItemInterface> audioList = new ArrayList<>();

        //First Ad
        if(mTrack.firstAd() != null){
            PCOnlineAudioItemInterface audioAd1 = new
                    PCOnlineAudioItemInterface(mTrack.firstAd(), mTrack.title(),
                    mTrack.artist_name(), mTrack.cover());
            audioList.add(audioAd1);
        }

        //The track itself
        PCOnlineAudioItemInterface audioItem = new PCOnlineAudioItemInterface(mTrack);
        audioList.add(audioItem);
//        audioList.add(audioItem);
//        audioList.add(audioItem);

        //Second Ad
        if(mTrack.secondAd() != null){
            PCOnlineAudioItemInterface audioAd2 = new PCOnlineAudioItemInterface(mTrack.secondAd(), mTrack.title(),
                    mTrack.artist_name(), mTrack.cover());
            audioList.add(audioAd2);
        }

        playlistManager.setParameters(audioList, selectedIndex);
        playlistManager.setId(PLAYLIST_ID);
        return true;
    }

    private void startPlayback(boolean forceStart) {
        if (forceStart || playlistManager.getCurrentPosition() != selectedIndex) {
            int size = playlistManager.getItemCount();
            if(size == 1 || size == 2){
                playlistManager.play(0, true);
            }
            playlistManager.play(0, false);
        }
    }

    private void updateCurrentPlaybackInformation() {
        PlaylistItemChange<PCOnlineAudioItemInterface> itemChanged =
                playlistManager.getCurrentItemChange();
        if (itemChanged != null) {
            onPlaylistItemChanged(itemChanged.getCurrentItem(),
                    itemChanged.hasNext(), itemChanged.hasPrevious());
        }

        PlaylistServiceCore.PlaybackState currentPlaybackState =
                playlistManager.getCurrentPlaybackState();
        if (currentPlaybackState != PlaylistServiceCore.PlaybackState.STOPPED) {
            onPlaybackStateChanged(currentPlaybackState);
        }

        MediaProgress mediaProgress = playlistManager.getCurrentProgress();
        if (mediaProgress != null) {
            onProgressUpdated(mediaProgress);
        }
    }

    @Override
    public boolean onPlaylistItemChanged(@Nullable IPlaylistItem currentItem,
                                         boolean hasNext, boolean hasPrevious) {
        Log.d("please", "onPlaylistItemChanged is Called");
        if(currentItem != null){
            PCOnlineAudioItemInterface item = playlistManager.getCurrentItem();
            if(item != null){
                if(item.getUuid().equals(mTrack.uuid())){
                    shouldSetDuration = true;
                    mIsTrack = true;
                }
                if(hasPrevious && item.getUuid().equals(mTrack.uuid())){
                    mAudioAdIsPlayed = true;
                    mPresenter.logAudioEventStream(mTrack.firstAd());
                }
            }
        }
//        if(currentPlaylistItem.getUuid().equals(mTrack.uuid())) {
//            mPresenter.isTrackAlreadyDownloaded(currentPlaylistItem.getUuid());
//            shouldSetDuration = true;
//            mIsTrack = true;
//            Log.d("please", "New Condition is called");
//        }

        return true;
    }

    @Override
    public boolean onPlaybackStateChanged(@NonNull PlaylistServiceCore.PlaybackState playbackState) {
        switch (playbackState) {
            case STOPPED:
                finish();
                onSomethingStopped();
                break;

            case RETRIEVING:
            case PREPARING:
                restartLoading();
                break;

            case PLAYING:
                doneLoading(true);
                break;

            case ERROR:
                onLoadingError();
                break;

            case PAUSED:
                doneLoading(false);
                break;

            default:
                break;
        }

        return true;
    }

    private void onSomethingStopped() {
        Log.d("playlist", "onSomethingStopped called");
    }

    public void onLoadingError() {
        mTrackLoadingError = true;
        showError(getString(R.string.errorWhileLoadingTheTrackMessage));
        reloadPlaylist();
    }

    private void reloadPlaylist() {
        initPlayer();
    }

    private void showError(String message) {
        Toast.makeText(getContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    private void finish() {
        getActivity().finish();
    }

    private void doneLoading(boolean isPlaying) {
        Log.d("playlist", "doneLoading called");
        loadCompleted();
        updatePlayPauseImage(isPlaying);
    }

    private void restartLoading() {
        Log.d("playlist", "restartLoading called");
        showLoading();
        playTrackFab.setEnabled(false);
    }

    private void loadCompleted(){
        Log.d("playlist", "loadCompleted called");
        hideLoading();
        if(mIsTrack){
            Log.d("playlist", "About to start and enable playTrackFab and mTrackProgressBar => 1");
            playTrackFab.setEnabled(true);
            mTrackProgressBar.start();
        }
    }

    private void updatePlayPauseImage(boolean isPlaying) {
        int resId;
        if (isPlaying) {
            resId = R.drawable.exomedia_ic_pause_white;
        }else {
            resId = R.drawable.exomedia_ic_play_arrow_white;
            Log.d("playlist", "About to stop mTrackProgressBar => 2");
            mTrackProgressBar.stop();
        }
        playTrackFab.setImageResource(resId);
    }

    @Override
    public boolean onProgressUpdated(@NonNull MediaProgress progress) {
        if (shouldSetDuration && progress.getDuration() > 0 && mIsTrack) {
            shouldSetDuration = false;
            mTrackIsPlayed = true;
            setDuration(progress.getDuration());
        }

//        if( progress.getDuration() > 0 && TimeFormatUtil.formatMs(progress.getPosition() )
//                .equals(TimeFormatUtil.formatMs(progress.getDuration())) && mIsTrack) {
////                onTrackCompleted();
//        }

        if( ! userInteracting ){
            if(mIsTrack){
                mTrackProgressBar.setCurrentProgress((int) progress.getPosition());
            }
        }

        return true;
    }

    public void onTrackCompleted() {
        Log.d("playlist", "About to cancel mTrackProgressBar => 4");
        mTrackProgressBar.cancel();
        if(mTrack.downloadable() && !trackAlreadyDownloaded){
            Log.d("adapter", "local onTrackCompleted called");
            Log.d("adapter", "And this is the time to invoke repeat");
            mDownloadTrackButton.setEnabled(true);
        }
    }

    private void setDuration(long duration) {
        Log.d("playlist", "About to set Max Duration mTrackProgressBar => 5");
        mTrackProgressBar.setMaxProgress((int) duration);
        Log.d("adapter", "Total Duration (" + duration + ") => " + TimeFormatUtil.formatMs(duration));
    }

    public void launchArtistProfileActivity() {
        Intent intent = ArtistProfileActivity.newIntent(getActivity(), mTrack.artistId());
        startActivity(intent);
    }
}
