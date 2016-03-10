package primr.apps.eurakacachet.ryme.ryme.utils.player.service;


import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.Log;

import com.devbrackets.android.exomedia.listener.EMAudioFocusCallback;
import com.devbrackets.android.exomedia.service.EMPlaylistService;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.RymeApplication;
import primr.apps.eurakacachet.ryme.ryme.ui.view.main.MainActivity;
import primr.apps.eurakacachet.ryme.ryme.utils.player.dataInterface.OfflineAudioItemInterface;
import primr.apps.eurakacachet.ryme.ryme.utils.player.manager.OfflinePlaylistManager;

public class OfflineAudioPlayerService extends EMPlaylistService<OfflineAudioItemInterface, OfflinePlaylistManager>
        implements EMAudioFocusCallback {

    private static final int NOTIFICATION_ID = 2222; //Arbitrary
    private static final int FOREGROUND_REQUEST_CODE = 2626; //Arbitrary
    private static final float AUDIO_DUCK_VOLUME = 0.1f;

    private Bitmap defaultLargeNotificationImage;
    private Bitmap largeNotificationImage;
    private Bitmap lockScreenArtwork;

    private NotificationTarget notificationImageTarget = new NotificationTarget();
    private LockScreenTarget lockScreenImageTarget = new LockScreenTarget();

    private Picasso mPicasso;
    private boolean repeatPlaylist = false;

    @Override
    public void onCreate() {
        super.onCreate();
        mPicasso = Picasso.with(getApplicationContext());
    }

    @Override
    protected int getNotificationId() {
        return NOTIFICATION_ID;
    }

    @Override
    protected float getAudioDuckVolume() {
        return AUDIO_DUCK_VOLUME;
    }

    @Override
    protected OfflinePlaylistManager getMediaPlaylistManager() {
        return RymeApplication.getOfflinePlaylistManager();
    }

    @Override
    protected PendingIntent getNotificationClickPendingIntent() {
        Intent intent = MainActivity.newIntent(getApplicationContext());
        return PendingIntent.getActivity(getApplicationContext(), FOREGROUND_REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    protected void performRepeat() {
        getMediaPlaylistManager().setCurrentIndex(0);
        seekToPosition = 0;
        startItemPlayback();
    }

    @Override
    protected void performMediaCompletion() {
        Log.d("offline", "media done playing => performMediaCompletion");
        if(getMediaPlaylistManager().isNextAvailable()){
            performNext();
        }else {
            performRepeat();
        }
    }

    @Override
    protected void onAudioPlaybackStarted(OfflineAudioItemInterface playlistItem, long currentPosition, long duration) {
        Log.d("offline", "media starts playing");
    }

    @Override
    protected Bitmap getDefaultLargeNotificationImage() {
        if (defaultLargeNotificationImage == null){
            defaultLargeNotificationImage = BitmapFactory.decodeResource(getResources(),
                    R.mipmap.ic_launcher);
        }
        return defaultLargeNotificationImage;
    }

    @Override
    protected void updateLargeNotificationImage(int size, OfflineAudioItemInterface playlistItem) {
        mPicasso.load(new File(playlistItem.getThumbnailUrl())).into(notificationImageTarget);
    }

    @Override
    protected void updateLockScreenArtwork(OfflineAudioItemInterface playlistItem) {
        mPicasso.load(new File(playlistItem.getArtworkUrl())).into(lockScreenImageTarget);
    }

    @Nullable
    @Override
    protected Bitmap getDefaultLargeNotificationSecondaryImage() {
        return null;
    }

    @Override
    protected int getNotificationIconRes() {
        return R.mipmap.ic_launcher;
    }

    @Override
    protected int getLockScreenIconRes() {
        return R.mipmap.ic_launcher;
    }


    @Nullable
    @Override
    public Bitmap getLockScreenArtwork() {
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

        }
    }


    private class LockScreenTarget implements Target{

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

        }
    }
}
