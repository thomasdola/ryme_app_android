package primr.apps.eurakacachet.ryme.ryme;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.liulishuo.filedownloader.FileDownloader;

import javax.inject.Inject;

import io.branch.referral.Branch;
import primr.apps.eurakacachet.ryme.ryme.data.remote.RymeService;
import primr.apps.eurakacachet.ryme.ryme.injection.component.ApplicationComponent;
import primr.apps.eurakacachet.ryme.ryme.injection.component.DaggerApplicationComponent;
import primr.apps.eurakacachet.ryme.ryme.injection.module.ApplicationModule;
import primr.apps.eurakacachet.ryme.ryme.utils.player.manager.OfflinePlaylistManager;
import primr.apps.eurakacachet.ryme.ryme.utils.player.manager.OnlineAudioPlaylistManager;
import primr.apps.eurakacachet.ryme.ryme.utils.player.manager.PCOfflinePlaylistManager;
import primr.apps.eurakacachet.ryme.ryme.utils.player.manager.PCOnlinePlaylistManager;
import primr.apps.eurakacachet.ryme.ryme.utils.services.BackgroundGcmMessageReceiver;
import primr.apps.eurakacachet.ryme.ryme.utils.services.RefreshTokenReceiver;
import rx.Subscriber;
import rx_gcm.ForegroundMessage;
import rx_gcm.internal.RxGcm;


public class RymeApplication extends Application{

    @Inject
    RymeService mRymeService;
    ApplicationComponent mApplicationComponent;
    public static RymeApplication sApplication;
    public static OnlineAudioPlaylistManager sOnlineAudioPlaylistManager;
    public static OfflinePlaylistManager sOfflinePlaylistManager;
    public static PCOfflinePlaylistManager sPCOfflinePlaylistManager;
    public static PCOnlinePlaylistManager sPCOnlinePlaylistManager;

    @Override
    public void onCreate(){
        super.onCreate();

        Log.d("application", "onCreate called from application");

        sApplication = this;

        sOnlineAudioPlaylistManager = new OnlineAudioPlaylistManager();

        sOfflinePlaylistManager = new OfflinePlaylistManager();

        sPCOnlinePlaylistManager = new PCOnlinePlaylistManager();

        sPCOfflinePlaylistManager = new PCOfflinePlaylistManager();

        FileDownloader.init(this);

        Branch.getAutoInstance(this);

        RxGcm.Notifications.register(this)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("application", "error from application -> " + e.getMessage());
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String token) {
                        Log.d("application", "GCM token -> " + token);
                    }
                });

        RxGcm.Notifications.onBackgroundNotification(BackgroundGcmMessageReceiver.class);

        RxGcm.Notifications.onForegroundNotification()
                .subscribe(new Subscriber<ForegroundMessage>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ForegroundMessage message) {
                        Activity activity = message.getCurrentActivity();
                        Bundle payload = message.getPayload();
                    }
                });

        RxGcm.Notifications.onRefreshToken(RefreshTokenReceiver.class);
    }

    public static RymeApplication get(Context context){
        return (RymeApplication) context.getApplicationContext();
    }

    public static RymeApplication getApplication(){
        return sApplication;
    }

    public static OnlineAudioPlaylistManager getOnlineAudioPlaylistManager(){
        return sOnlineAudioPlaylistManager;
    }

    public static OfflinePlaylistManager getOfflinePlaylistManager(){
        return sOfflinePlaylistManager;
    }

    public static PCOfflinePlaylistManager getPCOfflinePlaylistManager(){
        return sPCOfflinePlaylistManager;
    }

    public static PCOnlinePlaylistManager getPCOnlinePlaylistManager(){
        return sPCOnlinePlaylistManager;
    }

    public ApplicationComponent getComponent(){
        if( mApplicationComponent == null ){
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    public void setComponent(ApplicationComponent applicationComponent){
        mApplicationComponent = applicationComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d("application", "onTerminate called from application");
        sApplication = null;
        sOnlineAudioPlaylistManager = null;
        sOfflinePlaylistManager = null;
    }
}
