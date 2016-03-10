package primr.apps.eurakacachet.ryme.ryme.utils.services;

import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx_gcm.GcmRefreshTokenReceiver;
import rx_gcm.TokenUpdate;


public class RefreshTokenReceiver implements GcmRefreshTokenReceiver {

    @Override
    public void onTokenReceive(Observable<TokenUpdate> onTokenUpdate) {
        onTokenUpdate.subscribe(new Subscriber<TokenUpdate>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(TokenUpdate tokenUpdate) {
                Log.d("token", tokenUpdate.toString());
            }
        });
    }
}
