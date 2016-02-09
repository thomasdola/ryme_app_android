package primr.apps.eurakacachet.ryme.ryme.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.local.DatabaseHelper;
import primr.apps.eurakacachet.ryme.ryme.data.local.PreferencesHelper;
import primr.apps.eurakacachet.ryme.ryme.data.model.Artist;
import primr.apps.eurakacachet.ryme.ryme.data.model.Category;
import primr.apps.eurakacachet.ryme.ryme.data.model.DownloadedTrack;
import primr.apps.eurakacachet.ryme.ryme.data.model.FollowedArtist;
import primr.apps.eurakacachet.ryme.ryme.data.model.FollowedCategory;
import primr.apps.eurakacachet.ryme.ryme.data.model.LikedTrack;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.data.remote.RymeService;
import primr.apps.eurakacachet.ryme.ryme.utils.helpers.event.EventPostHelper;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class DataManager {

    private final RymeService mRymeService;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final EventPostHelper mEventPoster;

    @Inject
    public DataManager(RymeService rymeService, PreferencesHelper preferencesHelper,
                       DatabaseHelper databaseHelper, EventPostHelper eventPosterHelper) {
        mRymeService = rymeService;
        mPreferencesHelper = preferencesHelper;
        mDatabaseHelper = databaseHelper;
        mEventPoster = eventPosterHelper;
    }

    //Preference Manager
    public PreferencesHelper getPreferencesHelper(){
        return mPreferencesHelper;
    }

    public Observable<UUID> getUserId(){
        return getPreferencesHelper().getUserId();
    }

    public Observable<String> getAvatar(){
        return getPreferencesHelper().getAvatar();
    }

    public Observable<String> getArtistName(){
        return getPreferencesHelper().getStageName();
    }

    public Observable<Boolean> isLoggedIn(){
        return getPreferencesHelper().isLoggedIn();
    }

    public Observable<Boolean> isArtist(){
        return getPreferencesHelper().isArtist();
    }

    public Observable<String> getToken(){
        return getPreferencesHelper().getAccessToken();
    }

    public Observable<Boolean> isAllowedToMakeRequest(){
        return getPreferencesHelper().getIsAllowedToMakeRequest();
    }

    public void setUserId(UUID userId){
         getPreferencesHelper().setUserId(userId);
    }

    public void setLogIn(boolean isLoggedIn){
        getPreferencesHelper().setLogin(isLoggedIn);
    }

    public void setAvatarPath(String path){
        getPreferencesHelper().setAvatar(path);
    }

    public void setToken(String token){
        getPreferencesHelper().setAccessToken(token);
    }

    public void setIsAllowedToMakeRequest(boolean isHeAllowed){
        getPreferencesHelper().setIsAllowedToMakeArtistRequest(isHeAllowed);
    }

    public void setArtistName(String name){
        getPreferencesHelper().setStageName(name);
    }


    // API Manager
    public Observable<List<Track>> getLikedTracks(){
        List<UUID> ids = getLikedTrackIds();
        return getTracksFromIds(ids).toList();
    }

    public Observable<List<Artist>> loadFollowedArtists(){
        List<UUID> ids = getFollowedArtistsIds();
        return getArtistsFromIds(ids).toList();
    }

    public Observable<Track> getTracksFromIds(List<UUID> ids){
        List<UUID> trackIds = new ArrayList<>();
        for (UUID id : ids) {
            trackIds.add(id);
        }
        return Observable.from(trackIds)
                .concatMap(new Func1<UUID, Observable<? extends Track>>() {
                    @Override
                    public Observable<? extends Track> call(UUID uuid) {
                        return getTrack(uuid);
                    }
                });
    }

    public Observable<Artist> getArtistsFromIds(List<UUID> ids){
        List<UUID> artistIds = new ArrayList<>();
        for(UUID id : ids ) {
            artistIds.add(id);
        }
        return Observable.from(artistIds)
                .concatMap(new Func1<UUID, Observable<? extends Artist>>() {
                    @Override
                    public Observable<? extends Artist> call(UUID uuid) {
                        return getArtist(uuid);
                    }
                });
    }

    public Observable<? extends Track> getTrack(UUID uuid) {

        return mRymeService.getTrack(String.valueOf(uuid));
    }

    private Observable<? extends Artist> getArtist(UUID uuid) {
        return mRymeService.getArtist(String.valueOf(uuid));
    }

    public Observable<Category> syncCategories(){
        return mRymeService.getCategories()
                .concatMap(new Func1<List<Category>, Observable<? extends Category>>() {
                    @Override
                    public Observable<? extends Category> call(List<Category> categories) {
                        return mDatabaseHelper.setCategories(categories);
                    }
                });
    }

//    public Observable<Track> getNewTracks(){}
//
//    public Observable<Track> getTrendingTracks(){}
//
//    public Observable<Track> getNewTracksInUserCategories(){}
//
//    public Observable<Track> getTrendingTracksInUserCategories(){}
//
//    public Observable<Track> getTracksForArtist(){}
//
//    public Observable<EventAd> getEventAds(){}
//
//    public Observable<EventAd> viewEventAd(UUID eventAdId){}
//
//    public Observable<Track> likeTrack(UUID trackId){}
//
//    public Observable<Track> dislikeTrack(UUID trackId){}
//
//    public Observable<Artist> followArtist(UUID artistId){}
//
//    public Observable<Artist> unFollowArtist(UUID artistId){}
//
//    public Observable<Track> downloadTrack(UUID trackId){}
//
//    public Observable<Track> streamTrack(UUID trackId){}
//
//    public Observable<Track> viewTrack(UUID trackId){}



    //Database Manager
    public Observable<List<DownloadedTrack>> loadDownloadedTracks(){
        return mDatabaseHelper.getDownloadedTracks().distinct();
    }

    public Observable<DownloadedTrack> saveDownloadedTrack(DownloadedTrack track){
        return mDatabaseHelper.saveDownloadedTrack(track);
    }

    public Observable<DownloadedTrack> deleteDownloadedTrack(DownloadedTrack track){
        return mDatabaseHelper.deleteDownloadedTrack(track);
    }

    protected List<UUID> getLikedTrackIds(){
        final List<UUID> ids = new ArrayList<>();
        mDatabaseHelper.getLikedTracks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<LikedTrack>>() {
                    @Override
                    public void call(List<LikedTrack> likedTracks) {
                        for (LikedTrack track : likedTracks) {
                            ids.add(track.uuid);
                        }
                    }
                });
        return ids;
    }

    public Observable<LikedTrack> saveLikedTrack(LikedTrack track){
        return mDatabaseHelper.saveLikedTrack(track);
    }

    public Observable<LikedTrack> deleteLikedTrack(LikedTrack track){
        return mDatabaseHelper.deleteLikedTrack(track);
    }

    protected List<UUID> getFollowedArtistsIds(){
        final List<UUID> ids = new ArrayList<>();
        mDatabaseHelper.getFollowedArtists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<FollowedArtist>>() {
                    @Override
                    public void call(List<FollowedArtist> followedArtists) {
                        for( FollowedArtist artist : followedArtists ){
                            ids.add(artist.uuid);
                        }
                    }
                });
        return ids;
    }

    public Observable<FollowedArtist> saveFollowedArtist(FollowedArtist artist){
        return mDatabaseHelper.saveFollowedArtist(artist);
    }

    public Observable<FollowedArtist> deleteFollowedArtist(FollowedArtist artist){
        return mDatabaseHelper.deleteFollowedArtist(artist);
    }

    public Observable<List<FollowedCategory>> loadFollowedCategory(){
        return mDatabaseHelper.getFollowedCategories().distinct();
    }

    public Observable<FollowedCategory> deleteFollowedCategory(FollowedCategory category){
        return mDatabaseHelper.deleteFollowedCategory(category);
    }

    public Observable<FollowedCategory> saveFollowedCategory(FollowedCategory category){
        return mDatabaseHelper.saveFollowedCategory(category);
    }

}
