package primr.apps.eurakacachet.ryme.ryme.utils.player.service;


import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.devbrackets.android.playlistcore.api.AudioPlayerApi;
import com.devbrackets.android.playlistcore.helper.AudioFocusHelper;
import com.devbrackets.android.playlistcore.service.BasePlaylistService;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.RymeApplication;
import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.ui.view.main.MainActivity;
import primr.apps.eurakacachet.ryme.ryme.utils.player.dataInterface.PCOnlineAudioItemInterface;
import primr.apps.eurakacachet.ryme.ryme.utils.player.helpers.AudioApi;
import primr.apps.eurakacachet.ryme.ryme.utils.player.manager.PCOnlinePlaylistManager;

public class PCOnlineAudioPlayerService extends BasePlaylistService<PCOnlineAudioItemInterface,
        PCOnlinePlaylistManager> implements AudioFocusHelper.AudioFocusCallback{

    @Inject
    DataManager mDataManager;
    private static final int NOTIFICATION_ID = 33333; //Arbitrary
    private static final int FOREGROUND_REQUEST_CODE = 44444; //Arbitrary
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
        RymeApplication.get(this).getComponent().inject(this);
        picasso = Picasso.with(getApplicationContext());
    }

    @Override
    protected void performRepeat() {
        Log.d("playlist" , "performRepeat called");
        PCOnlinePlaylistManager playlistManager = getPlaylistManager();
        if(playlistManager.getItemCount() == 1){
            playlistManager.setCurrentPosition(0);
        }else {
            playlistManager.setCurrentPosition(1);
        }
        seekToPosition = 0;
        startItemPlayback();
    }

    @Override
    protected void performOnMediaError() {
        super.performOnMediaError();
        Log.d("playlist", "performOnMediaError called");
        getPlaylistManager().reset();
    }

    @Override
    protected void performOnMediaCompletion() {
        Log.d("playlist", "performOnMediaCompletion called");
        PCOnlinePlaylistManager playlistManager = getPlaylistManager();
        if(playlistManager.isNextAvailable()){
            Log.d("playlist", "there is a track available to play");
            performNext();
        }else {
            Log.d("playlist", "about to post event of track finish playing");
            mDataManager.trackFinishPlaying();
        }
    }

    @Override
    protected int getNotificationId() {
        return NOTIFICATION_ID;
    }

    @NonNull
    @Override
    protected PendingIntent getNotificationClickPendingIntent() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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

    @Override
    protected void updateLargeNotificationImage(int size, PCOnlineAudioItemInterface playlistItem) {
        picasso.load(new File(playlistItem.getArtworkUrl()))
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .resize(100, 100)
                .centerCrop()
                .into(notificationImageTarget);
    }

    @Override
    protected void updateRemoteViewArtwork(PCOnlineAudioItemInterface playlistItem) {
        picasso.load(new File(playlistItem.getArtworkUrl()))
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .resize(100, 100)
                .centerCrop()
                .into(lockScreenImageTarget);
    }

    @NonNull
    @Override
    protected PCOnlinePlaylistManager getPlaylistManager() {
        return RymeApplication.getPCOnlinePlaylistManager();
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
