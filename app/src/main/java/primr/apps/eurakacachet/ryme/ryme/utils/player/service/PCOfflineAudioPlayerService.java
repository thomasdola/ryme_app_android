package primr.apps.eurakacachet.ryme.ryme.utils.player.service;


import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.devbrackets.android.playlistcore.api.AudioPlayerApi;
import com.devbrackets.android.playlistcore.helper.AudioFocusHelper;
import com.devbrackets.android.playlistcore.service.BasePlaylistService;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.util.Random;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.RymeApplication;
import primr.apps.eurakacachet.ryme.ryme.ui.view.offline.downloads.OfflineTrackListActivity;
import primr.apps.eurakacachet.ryme.ryme.utils.player.dataInterface.PCOfflineAudioItemInterface;
import primr.apps.eurakacachet.ryme.ryme.utils.player.helpers.AudioApi;
import primr.apps.eurakacachet.ryme.ryme.utils.player.manager.PCOfflinePlaylistManager;

public class PCOfflineAudioPlayerService extends
        BasePlaylistService<PCOfflineAudioItemInterface, PCOfflinePlaylistManager>
        implements AudioFocusHelper.AudioFocusCallback{

    private static final int NOTIFICATION_ID = 11111;
    private static final int FOREGROUND_REQUEST_CODE = 22222;
    private static final float AUDIO_DUCK_VOLUME = 0.1f;

    private Bitmap defaultLargeNotificationImage;
    private Bitmap largeNotificationImage;
    private Bitmap lockScreenArtwork;

    private NotificationTarget notificationImageTarget = new NotificationTarget();
    private LockScreenTarget lockScreenImageTarget = new LockScreenTarget();

    private Picasso picasso;

    @Override
    public void onCreate() {
        super.onCreate();
        picasso = Picasso.with(getApplicationContext());
    }

    @Override
    protected void performShuffle() {
        int position = getRandomPosition();
        getPlaylistManager().setCurrentPosition(position);
        seekToPosition = 0;
        immediatelyPause = false;
        startItemPlayback();
    }

    private int getRandomPosition() {
        Random random = new Random();
        int min = 0;
        int playlistSize = getPlaylistManager().getItemCount();
        return random.nextInt(playlistSize - min + 1) + min;
    }

    @Override
    protected void performNext() {
        seekToPosition = 0;
        immediatelyPause = false;

        getPlaylistManager().next();
        startItemPlayback();
    }

    @Override
    protected void performRepeat() {
        getPlaylistManager().setCurrentPosition(0);
        seekToPosition = 0;
        startItemPlayback();
    }

    @Override
    protected void performOnMediaCompletion() {
        if(getPlaylistManager().isNextAvailable()){
            performNext();
        }
        performRepeat();
    }

    @Override
    protected int getNotificationId() {
        return NOTIFICATION_ID;
    }

    @NonNull
    @Override
    protected PendingIntent getNotificationClickPendingIntent() {
        Intent intent = OfflineTrackListActivity.newIntent(getApplicationContext());
        return PendingIntent.getActivity(getApplicationContext(),
                FOREGROUND_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Nullable
    @Override
    protected Bitmap getDefaultLargeNotificationImage() {
        if (defaultLargeNotificationImage == null) {
            defaultLargeNotificationImage  = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        }

        return defaultLargeNotificationImage;
    }

    @Override
    protected int getNotificationIconRes() {
        return R.mipmap.ic_launcher;
    }

    @Override
    protected int getRemoteViewIconRes() {
        return R.mipmap.ic_launcher;
    }

    @NonNull
    @Override
    protected AudioPlayerApi getNewAudioPlayer() {
        return new AudioApi(new MediaPlayer());
    }

    @Override
    protected float getAudioDuckVolume() {
        return AUDIO_DUCK_VOLUME;
    }

    @NonNull
    @Override
    protected PCOfflinePlaylistManager getPlaylistManager() {
        return RymeApplication.getPCOfflinePlaylistManager();
    }

    @Override
    protected void updateLargeNotificationImage(int size, PCOfflineAudioItemInterface playlistItem) {
        picasso.load(new File(playlistItem.getArtworkUrl()))
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .resize(100, 100)
                .centerCrop()
                .into(notificationImageTarget);
    }

    @Override
    protected void updateRemoteViewArtwork(PCOfflineAudioItemInterface playlistItem) {
        picasso.load(new File(playlistItem.getArtworkUrl()))
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .resize(100, 100)
                .centerCrop()
                .into(lockScreenImageTarget);
    }

    @Nullable
    @Override
    protected Bitmap getRemoteViewArtwork() {
        return lockScreenArtwork;
    }

    @Nullable
    @Override
    public Bitmap getLargeNotificationImage() {
        return largeNotificationImage;
    }

    private class NotificationTarget implements Target {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            largeNotificationImage = bitmap;
            onLargeNotificationImageUpdated();
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            largeNotificationImage = null;
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            //Purposefully left blank
        }
    }

    private class LockScreenTarget implements Target {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            lockScreenArtwork = bitmap;
            onLockScreenArtworkUpdated();
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            lockScreenArtwork = null;
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            //Purposefully left blank
        }
    }
}
