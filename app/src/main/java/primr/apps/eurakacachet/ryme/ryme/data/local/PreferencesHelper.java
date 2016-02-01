package primr.apps.eurakacachet.ryme.ryme.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.UUID;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.injection.ApplicationContext;
import rx.Observable;
import rx.Subscriber;
import timber.log.Timber;


public class PreferencesHelper {

    public static final String PREF_FILE_NAME = "ryme_pref_file";
    public static final String KEY_IS_LOGIN = "isLoggedIn";
    public static final String KEY_IS_ARTIST = "isArtist";
    public static final String KEY_ACCESS_TOKEN = "password";
    public static final String KEY_USER_ID = "userId";

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

    public void setIsArtist(boolean isHe){
        mPref.edit().putBoolean(KEY_IS_ARTIST, isHe)
                .apply();
    }

    public Observable<Boolean> isArtist(){
        return Observable.just(
                mPref.getBoolean(KEY_IS_ARTIST, false)
        );
    }

    public void setUserId(UUID userId){
        mPref.edit().putString(KEY_USER_ID, String.valueOf(userId))
                .apply();
    }

    public Observable<UUID> getUserId(){
        return Observable.just(
                UUID.fromString(mPref.getString(KEY_USER_ID, ""))
        );
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
