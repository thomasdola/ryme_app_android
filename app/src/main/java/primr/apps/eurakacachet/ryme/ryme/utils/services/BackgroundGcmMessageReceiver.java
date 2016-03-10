package primr.apps.eurakacachet.ryme.ryme.utils.services;

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

            }
        });
    }
}
