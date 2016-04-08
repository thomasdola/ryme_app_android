package primr.apps.eurakacachet.ryme.ryme.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.injection.context.ApplicationContext;
import rx.Observable;
import rx.Subscriber;


public class PreferencesHelper {

    public static final String PREF_FILE_NAME = "ryme_pref_file";
    public static final String KEY_IS_VERIFIED = "is_verified";
    public static final String KEY_IS_LOGIN = "is_loggedIn";
    public static final String KEY_IS_READY = "is_ready";
    public static final String KEY_IS_ARTIST = "is_artist";
    public static final String KEY_ACCESS_TOKEN = "token";
    public static final String KEY_GCM_TOKEN = "gcm_token";
    public static final String KEY_USER_UUID = "user_uuid";
    public static final String KEY_USER_AVATAR = "avatar";
    public static final String KEY_ARTIST_BACK_IMAGE = "back_image";
    public static final String KEY_USER_STAGE_NAME= "stage_name";
    public static final String KEY_USER_USERNAME= "username";
    public static final String KEY_USER_PASSWORD= "password";
    public static final String KEY_IS_ALLOWED_TO_MAKE_ARTIST_REQUEST = "is_allowed";
    private static final String KEY_PHONE_NUMBER = "phone_number";
    private static final String KEY_IS_REQUEST_ACTIVE = "is_request_active";

    private final SharedPreferences mPref;

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void setIsRequestActive(boolean isActive){
        mPref.edit().putBoolean(KEY_IS_REQUEST_ACTIVE, isActive)
                .apply();
    }

    public void setGcmToken(String token){
        mPref.edit().putString(KEY_GCM_TOKEN, token)
                .apply();
    }

    public String getRegistrationToken(){
        return mPref.getString(KEY_GCM_TOKEN, null);
    }

    public boolean isRequestActive(){
        return mPref.getBoolean(KEY_IS_REQUEST_ACTIVE, false);
    }

    public Observable<Boolean> isVerified(){
        return Observable.just(
                mPref.getBoolean(KEY_IS_VERIFIED, false)
        );
    }

    public void setKeyIsVerified(boolean isVerified){
        mPref.edit().putBoolean(KEY_IS_VERIFIED, isVerified)
                .apply();
    }

    public String apiToken(){
        return mPref.getString(KEY_ACCESS_TOKEN, "");
    }

    public String getUserPassword(){
        return mPref.getString(KEY_USER_PASSWORD, "");
    }

    public void setUserPassword(String password){
        mPref.edit().putString(KEY_USER_PASSWORD, password)
                .apply();
    }

    public void setAvatar(String avatar){
        mPref.edit().putString(KEY_USER_AVATAR, avatar)
                .apply();
    }

    public Observable<String> getAvatar(){
        return Observable.just(
                mPref.getString(KEY_USER_AVATAR, "")
        );
    }

    public void setBackImage(String path){
        mPref.edit().putString(KEY_ARTIST_BACK_IMAGE, path)
                .apply();
    }

    public Observable<String> getBackImage(){
        return Observable.just(
                mPref.getString(KEY_ARTIST_BACK_IMAGE, "")
        );
    }

    public void setUsername(String username){
        mPref.edit().putString(KEY_USER_USERNAME, username)
                .apply();
    }

    public String getUsername(){
        return mPref.getString(KEY_USER_USERNAME, "Ryme");
    }

    public void setStageName(String stageName){
        mPref.edit().putString(KEY_USER_STAGE_NAME, stageName)
                .apply();
    }

    public Observable<String> getStageName(){
        return Observable.just(
                mPref.getString(KEY_USER_STAGE_NAME, "Ryme")
        );
    }

    public void setLogin(boolean isLoggedIn){
        mPref.edit().putBoolean(KEY_IS_LOGIN, isLoggedIn)
                .apply();
    }

    public Observable<Boolean> isLoggedIn(){
        return Observable.just(
                mPref.getBoolean(KEY_IS_LOGIN, false)
        );
    }

    public void setArtist(boolean isHe){
        mPref.edit().putBoolean(KEY_IS_ARTIST, isHe)
                .apply();
    }

    public Observable<Boolean> isArtist(){
        return Observable.just(
                mPref.getBoolean(KEY_IS_ARTIST, false)
        );
    }

    public void setUserId(String userId){
        mPref.edit().putString(KEY_USER_UUID, userId)
                .apply();
    }

    public Observable<String> getUserId(){
        return Observable.just(
                mPref.getString(KEY_USER_UUID, "0121deda-7f61-4c6f-ada3-6bb31ac7322e")
        );
    }

    public void setAccessToken(String accessToken){
        mPref.edit().putString(KEY_ACCESS_TOKEN, accessToken)
                .apply();
    }

    public Observable<String> getAccessToken(){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                mPref.getString(KEY_ACCESS_TOKEN, "");
            }
        });
    }

    public void setIsAllowedToMakeArtistRequest(boolean isHeAllowed){
        mPref.edit().putBoolean(KEY_IS_ALLOWED_TO_MAKE_ARTIST_REQUEST, isHeAllowed)
                .apply();
    }

    public boolean isAllowedToMakeRequest(){
        return mPref.getBoolean(KEY_IS_ALLOWED_TO_MAKE_ARTIST_REQUEST, false);
    }

    public Observable<Boolean> isReady(){
        return Observable.just(
                mPref.getBoolean(KEY_IS_READY, false)
        );
    }

    public void setIsReady(boolean isReady){
        mPref.edit().putBoolean(KEY_IS_READY, isReady)
                .apply();
    }

    public void setPhone(String phone){
        mPref.edit().putString(KEY_PHONE_NUMBER, phone)
                .apply();
    }

    public Observable<String> getPhone(){
        return Observable.just(
                mPref.getString(KEY_PHONE_NUMBER, "")
        );
    }

    public void clear() {
        mPref.edit().clear().apply();
    }

}
