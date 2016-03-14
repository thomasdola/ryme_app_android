package primr.apps.eurakacachet.ryme.ryme.utils.services;

import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.ui.view.artist.profile.ArtistProfileActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.trackDisplay.PublicTrackDisplayActivity;
import rx.Observable;
import rx.Subscriber;
import rx_gcm.BackgroundMessage;
import rx_gcm.GcmBackgroundReceiver;


public class BackgroundGcmMessageReceiver implements GcmBackgroundReceiver {

    @Override
    public void onMessage(Observable<BackgroundMessage> onMessage) {
        onMessage.subscribe(new Subscriber<BackgroundMessage>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(BackgroundMessage message) {
                Bundle payload = message.getPayload();
                Application application = message.getApplication();
                createNotification(application, payload);
            }
        });
    }

    private void createNotification(Application application, Bundle payload) {
        NotificationCompat.Builder builder = new NotificationCompat
                .Builder(application).setAutoCancel(true);
        String event = payload.getString("event");
        String title = payload.getString("title");
        String text = payload.getString("body");

        if(event != null && title != null && text != null){
            builder.setContentTitle(title).setContentText(text);
            switch (event){
                case "track_uploaded":
                    builder.setSmallIcon(R.drawable.play_button);
                    String track_id = payload.getString("track_id");
                    if(track_id != null){
                        Intent intent = PublicTrackDisplayActivity.newIntent(application, track_id);
                        PendingIntent pendingIntent = PendingIntent.getActivity(application,
                                (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        builder.setContentIntent(pendingIntent);
                    }
                    break;
                case "artist_joined":
                    builder.setSmallIcon(R.drawable.play_button);
                    String artist_id = payload.getString("artist_id");
                    if(artist_id != null){
                        Intent intent = ArtistProfileActivity.newIntent(application, artist_id);
                        PendingIntent pendingIntent = PendingIntent.getActivity(application,
                                (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        builder.setContentIntent(pendingIntent);
                    }
                    break;
            }
        }
        NotificationManager notificationManager = (NotificationManager) application
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(7777, builder.build());
    }
}
