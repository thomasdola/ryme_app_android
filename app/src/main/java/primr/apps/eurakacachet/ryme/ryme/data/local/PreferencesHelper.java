package primr.apps.eurakacachet.ryme.ryme.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.injection.ApplicationContext;
import rx.Observable;
import rx.Subscriber;
import timber.log.Timber;


public class PreferencesHelper {

    public static final String PREF_FILE_NAME = "ryme_pref_file";
    public static final String KEY_IS_LOGIN = "isLoggedIn";
    public static final String KEY_ACCESS_TOKEN = "password";

    private final SharedPreferences mPref;

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void setLogin(boolean isLoggedIn){
        mPref.edit().putBoolean(KEY_IS_LOGIN, isLoggedIn)
                .apply();
        Timber.i("User login Session changed to " + isLoggedIn);
    }

    public Observable<Boolean> isLoggedIn(){
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                mPref.getBoolean(KEY_IS_LOGIN, false);
            }
        });
    }

    public void setAccessToken(String accessToken){
        mPref.edit().putString(KEY_ACCESS_TOKEN, accessToken)
                .apply();
    }

    public Observable<String> getAccessToken(){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                mPref.getString(KEY_ACCESS_TOKEN, null);
            }
        });
    }

    public void clear() {
        mPref.edit().clear().apply();
    }

}
