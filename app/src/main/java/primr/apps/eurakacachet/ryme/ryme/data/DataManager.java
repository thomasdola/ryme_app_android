package primr.apps.eurakacachet.ryme.ryme.data;

import android.util.Log;

import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResults;
import com.squareup.okhttp.RequestBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.local.PreferencesHelper;
import primr.apps.eurakacachet.ryme.ryme.data.local.SqlBrite.SqlBriteDatabaseHelper;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.StorioDatabaseHelper;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.Category;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.FollowedCategory;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.SavedTrack;
import primr.apps.eurakacachet.ryme.ryme.data.model.ActionResponse;
import primr.apps.eurakacachet.ryme.ryme.data.model.AddCommentResponse;
import primr.apps.eurakacachet.ryme.ryme.data.model.Artist;
import primr.apps.eurakacachet.ryme.ryme.data.model.ArtistData;
import primr.apps.eurakacachet.ryme.ryme.data.model.AuthResponse;
import primr.apps.eurakacachet.ryme.ryme.data.model.CategoryToFU;
import primr.apps.eurakacachet.ryme.ryme.data.model.Comment;
import primr.apps.eurakacachet.ryme.ryme.data.model.CommentsData;
import primr.apps.eurakacachet.ryme.ryme.data.model.EventAd;
import primr.apps.eurakacachet.ryme.ryme.data.model.EventAdData;
import primr.apps.eurakacachet.ryme.ryme.data.model.EventImage;
import primr.apps.eurakacachet.ryme.ryme.data.model.FavoriteArtistsData;
import primr.apps.eurakacachet.ryme.ryme.data.model.FavoriteTracksData;
import primr.apps.eurakacachet.ryme.ryme.data.model.FollowedArtist;
import primr.apps.eurakacachet.ryme.ryme.data.model.LikedTrack;
import primr.apps.eurakacachet.ryme.ryme.data.model.PlayBackEvent;
import primr.apps.eurakacachet.ryme.ryme.data.model.SearchResult;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.data.model.TrackData;
import primr.apps.eurakacachet.ryme.ryme.data.model.TracksData;
import primr.apps.eurakacachet.ryme.ryme.data.remote.RymeService;
import primr.apps.eurakacachet.ryme.ryme.utils.helpers.event.EventPostHelper;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class DataManager {

    private final RymeService mRymeService;
    private final SqlBriteDatabaseHelper mDatabaseHelper;
    private final StorioDatabaseHelper mStDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final EventPostHelper mEventPoster;

    @Inject
    public DataManager(RymeService rymeService, PreferencesHelper preferencesHelper,
                       SqlBriteDatabaseHelper databaseHelper, EventPostHelper eventPosterHelper,
                       StorioDatabaseHelper storioDatabaseHelper) {
        mRymeService = rymeService;
        mPreferencesHelper = preferencesHelper;
        mDatabaseHelper = databaseHelper;
        mEventPoster = eventPosterHelper;
        mStDatabaseHelper = storioDatabaseHelper;
    }

    //Preference Manager
    public PreferencesHelper getPreferencesHelper(){
        return mPreferencesHelper;
    }

    public Observable<String> getUserId(){
        return Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                return getPreferencesHelper().getUserId();
            }
        });
    }

    public void setPassword(String password){
        mPreferencesHelper.setUserPassword(password);
    }

    public String getPassword(){
        return mPreferencesHelper.getUserPassword();
    }

    public Observable<String> getAvatar(){
        return getPreferencesHelper().getAvatar();
    }

    public Observable<String> getBackImage(){
        return getPreferencesHelper().getBackImage();
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

    public Observable<Void> setIsArtist(final boolean isHe){
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                getPreferencesHelper().setArtist(isHe);
            }
        });
    }

    public Observable<String> getToken(){
        return getPreferencesHelper().getAccessToken();
    }

    public String getTokenForService(){
        return getPreferencesHelper().apiToken();
    }

    public boolean isAllowedToMakeRequest(){
        return getPreferencesHelper().isAllowedToMakeRequest();
    }

    public Observable<String> getUsername() {
        return Observable.just(mPreferencesHelper.getUsername());
    }

    public String getBlockingUsername(){
        return mPreferencesHelper.getUsername();
    }

    public void setUsername(String username){
        mPreferencesHelper.setUsername(username);
    }

    public void setUserId(String userId){
         getPreferencesHelper().setUserId(userId);
    }

    public void setLogIn(boolean isLoggedIn){
        getPreferencesHelper().setLogin(isLoggedIn);
    }

    public void setAvatarPath(String path){
        getPreferencesHelper().setAvatar(path);
    }

    public void setBackImagePath(String path){
        getPreferencesHelper().setBackImage(path);
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

    public Observable<String> getPhone() {
        return getPreferencesHelper().getPhone();
    }

    public Observable<Boolean> isReady(){
        return getPreferencesHelper().isReady();
    }

    public void setReady(boolean isReady){
        getPreferencesHelper().setIsReady(isReady);
    }

    public void setPhone(String phone){
        getPreferencesHelper().setPhone(phone);
    }


    // API Manager
    public Observable<AuthResponse> registerAttempt(HashMap<String , String> payload){
        return mRymeService.register(payload);
    }

    public Observable<Track> getTrack(final String uuid) {
        return Observable.defer(new Func0<Observable<Track>>() {
            @Override
            public Observable<Track> call() {
                return mRymeService.getTrack(uuid)
                        .map(new Func1<TrackData, Track>() {
                            @Override
                            public Track call(TrackData trackData) {
                                if(trackData.getData() == null){
                                    return null;
                                }else {
                                    return trackData.getData();
                                }
                            }
                        });
            }
        });
    }

    public Observable<Artist> getArtist(final String artistId) {
        return Observable.defer(new Func0<Observable<Artist>>() {
            @Override
            public Observable<Artist> call() {
                return mRymeService.getArtist(artistId)
                        .map(new Func1<ArtistData, Artist>() {
                            @Override
                            public Artist call(ArtistData artistData) {
                                if (artistData.data() != null) {
                                    return artistData.data();
                                }
                                return null;
                            }
                        });
            }
        });
    }


    public Observable<PutResults> downloadAndSaveCategories(){
        return Observable.defer(new Func0<Observable<PutResults>>() {
            @Override
            public Observable<PutResults> call() {
                return mRymeService.getCategories()
                        .concatMap(new Func1<List<Category>, Observable<? extends PutResults>>() {
                            @Override
                            public Observable<? extends PutResults> call(List<Category> categories) {
                                return mStDatabaseHelper.syncCategories(categories);
                            }
                        });
            }
        });
    }

    public Observable<PutResults<FollowedCategory>> downloadAndSaveFollowedCategories() {
        return Observable.defer(new Func0<Observable<PutResults<FollowedCategory>>>() {
            @Override
            public Observable<PutResults<FollowedCategory>> call() {
                return mRymeService.getFollowedCategories()
                        .concatMap(new Func1<List<FollowedCategory>, Observable<? extends PutResults<FollowedCategory>>>() {
                            @Override
                            public Observable<? extends PutResults<FollowedCategory>> call(List<FollowedCategory> followedCategories) {
                                return mStDatabaseHelper.putFollowedCategories(followedCategories);
                            }
                        });
            }
        });
    }

    public Observable<ActionResponse> followCategory(final String uuid, final String token){
        return Observable.defer(new Func0<Observable<ActionResponse>>() {
            @Override
            public Observable<ActionResponse> call() {
                return mRymeService.followCategory(uuid, token);
            }
        });
    }

    public Observable<ActionResponse> unFollowCategory(final String uuid, final String token){
        return Observable.defer(new Func0<Observable<ActionResponse>>() {
            @Override
            public Observable<ActionResponse> call() {
                return mRymeService.unFollowCategory(uuid, token);
            }
        });
    }

    public Observable<List<Track>> getTracks(final HashMap<String, String> specs){
        return Observable.defer(new Func0<Observable<List<Track>>>() {
            @Override
            public Observable<List<Track>> call() {
                return mRymeService.getPublicTracks(specs)
                        .concatMap(new Func1<TracksData, Observable<? extends List<Track>>>() {
                            @Override
                            public Observable<? extends List<Track>> call(TracksData tracksData) {
                                List<Track> tracks = new ArrayList<>();
                                if (tracksData.getData() != null) {
                                    tracks.addAll(Arrays.asList(tracksData.getData()));
                                }
                                return Observable.just(tracks);
                            }
                        });
            }
        });
    }

    public Observable<ActionResponse> isUserAllowed(){
        return Observable.defer(new Func0<Observable<ActionResponse>>() {
            @Override
            public Observable<ActionResponse> call() {
                return mRymeService.isUserAllowed();
            }
        });
    }


    //Database Manager

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

    protected List<String> getFollowedArtistsIds(){
        final List<String> ids = new ArrayList<>();
        mDatabaseHelper.getFollowedArtists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .distinct()
                .subscribe(new Action1<List<FollowedArtist>>() {
                    @Override
                    public void call(List<FollowedArtist> followedArtists) {
                        for (FollowedArtist artist : followedArtists) {
                            ids.add(artist.uuid);
                        }
                    }
                });
        return ids;
    }

    public Observable<List<FollowedCategory>> loadFollowedCategories(){
        return Observable.defer(new Func0<Observable<List<FollowedCategory>>>() {
            @Override
            public Observable<List<FollowedCategory>> call() {
                return mStDatabaseHelper.getFollowedCategories().distinct();
            }
        });
    }

    public Observable<Boolean> canUnfollow(){
        return mStDatabaseHelper.getFollowedCategories()
                .concatMap(new Func1<List<FollowedCategory>, Observable<? extends Boolean>>() {
                    @Override
                    public Observable<? extends Boolean> call(List<FollowedCategory> followedCategories) {
                        return Observable.just(followedCategories.size() > 1);
                    }
                }).distinctUntilChanged().take(1);
    }

    public Observable<PutResult> saveFollowedCategory(final FollowedCategory category){
        return Observable.defer(new Func0<Observable<PutResult>>() {
            @Override
            public Observable<PutResult> call() {
                return mStDatabaseHelper.putFollowedCategory(category);
            }
        });
    }

    public Observable<List<FollowedCategory>> loadBFCategories(){
        return Observable.just(
                mStDatabaseHelper.followedCategoriesBloackQuery()
        );
    }

    public Observable<List<String>> getFollowedCategoryNames(){
        return mStDatabaseHelper.getFollowedCategories()
                .concatMap(new Func1<List<FollowedCategory>, Observable<? extends String>>() {
                    @Override
                    public Observable<? extends String> call(List<FollowedCategory> followedCategories) {
                        return Observable.from(followedCategories)
                                .concatMap(new Func1<FollowedCategory, Observable<? extends String>>() {
                                    @Override
                                    public Observable<? extends String> call(FollowedCategory category) {
                                        return Observable.just(category.name());
                                    }
                                });
                    }
                }).toList();
    }

    public Observable<DeleteResult> deleteFollowedCategory(final FollowedCategory category){
        return Observable.defer(new Func0<Observable<DeleteResult>>() {
            @Override
            public Observable<DeleteResult> call() {
                return mStDatabaseHelper.deleteFollowedCategory(category);
            }
        });
    }

    public Observable<PutResults<Category>>
    saveCategories(final List<Category> categories){
        return Observable.defer(new Func0<Observable<PutResults<Category>>>() {
            @Override
            public Observable<PutResults<Category>> call() {
                return mStDatabaseHelper.putCategories(categories);
            }
        });
    }

    public Observable<PutResults<Category>>
    syncNewCategories(final List<Category> categories){
        return Observable.defer(new Func0<Observable<PutResults<Category>>>() {
            @Override
            public Observable<PutResults<Category>> call() {
                return mStDatabaseHelper.syncCategories(categories);
            }
        });
    }

    public Observable<AuthResponse> verifyCode(final String code) {
        return Observable.defer(new Func0<Observable<AuthResponse>>() {
            @Override
            public Observable<AuthResponse> call() {
                return mRymeService.verify(code);
            }
        });
    }

    public Observable<SavedTrack> getSavedTrack(final String uuid) {
        return Observable.defer(new Func0<Observable<SavedTrack>>() {
            @Override
            public Observable<SavedTrack> call() {
                return mStDatabaseHelper.getSavedTrack(uuid);
            }
        });
    }

    public Observable<PutResult> updateTrackWithCover(final SavedTrack savedTrack) {
        return Observable.defer(new Func0<Observable<PutResult>>() {
            @Override
            public Observable<PutResult> call() {
                return mStDatabaseHelper.updateTrackWithCover(savedTrack);
            }
        });
    }

    public Observable<PutResult> saveDownloadedTrack(final SavedTrack savedTrack) {
        return Observable.defer(new Func0<Observable<PutResult>>() {
            @Override
            public Observable<PutResult> call() {
                return mStDatabaseHelper.saveDownloadedTrack(savedTrack);
            }
        });
    }

    public Observable<PutResult> saveLikedTrack(final primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.LikedTrack likedTrack) {
        return Observable.defer(new Func0<Observable<PutResult>>() {
            @Override
            public Observable<PutResult> call() {
                return mStDatabaseHelper.saveLikedTrack(likedTrack);
            }
        });
    }

    public Observable<DeleteResult> deleteLikedTrack(final primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.LikedTrack likedTrack) {
        return Observable.defer(new Func0<Observable<DeleteResult>>() {
            @Override
            public Observable<DeleteResult> call() {
                return mStDatabaseHelper.deleteLikedTrack(likedTrack);
            }
        });
    }


    public Observable<Boolean> isLiked(final primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.LikedTrack likedTrack) {
        return Observable.defer(new Func0<Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call() {
                return mStDatabaseHelper.getLikedTracks()
                        .concatMap(new Func1<List<primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.LikedTrack>, Observable<? extends Boolean>>() {
                            @Override
                            public Observable<? extends Boolean> call(List<primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.LikedTrack> likedTracks) {
                                return Observable.just(likedTracks.contains(likedTrack));
                            }
                        });
            }
        });
    }

    public Observable<Boolean> getFollowedArtist(final primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.FollowedArtist artist) {
        return Observable.defer(new Func0<Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call() {
                return mStDatabaseHelper.getFollowedArtists()
                        .take(1)
                        .concatMap(new Func1<List<primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.FollowedArtist>, Observable<? extends Boolean>>() {
                            @Override
                            public Observable<? extends Boolean> call(List<primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.FollowedArtist> followedArtists) {
                                return Observable.just(
                                        followedArtists.contains(artist)
                                );
                            }
                        });
            }
        });
    }

    public Observable<PutResult> saveFollowedArtist(final primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.FollowedArtist followedArtist) {
        return Observable.defer(new Func0<Observable<PutResult>>() {
            @Override
            public Observable<PutResult> call() {
                return mStDatabaseHelper.saveFollowedArtist(followedArtist);
            }
        });
    }

    public Observable<DeleteResult> deleteFollowedArtist(final primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.FollowedArtist followedArtist) {
        return Observable.defer(new Func0<Observable<DeleteResult>>() {
            @Override
            public Observable<DeleteResult> call() {
                return mStDatabaseHelper.deleteFollowedArtist(followedArtist);
            }
        });
    }

    public Observable<List<Category>> loadCategories() {
        return Observable.defer(new Func0<Observable<List<Category>>>() {
            @Override
            public Observable<List<Category>> call() {
                return mStDatabaseHelper.getCategories();
            }
        });
    }

    public Observable<Category> loadCategoriesArray(){
        return mStDatabaseHelper.getCategories().take(1)
                .concatMap(new Func1<List<Category>, Observable<? extends Category>>() {
                    @Override
                    public Observable<? extends Category> call(List<Category> categories) {
                        return Observable.from(categories);
                    }
                });
    }

    public Observable<ActionResponse> logPlayTrack(final Track track) {
        return Observable.defer(new Func0<Observable<ActionResponse>>() {
            @Override
            public Observable<ActionResponse> call() {
                return mRymeService.streamTrack(track.uuid());
            }
        });
    }

    public Observable<primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.FollowedArtist>
    getFollowedArtist(){
        return mStDatabaseHelper.getFollowedArtists()
                .concatMap(new Func1<List<primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.FollowedArtist>, Observable<? extends primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.FollowedArtist>>() {
                    @Override
                    public Observable<? extends primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.FollowedArtist> call(List<primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.FollowedArtist> followedArtists) {
                        return Observable.from(followedArtists);
                    }
                }).concatMap(new Func1<primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.FollowedArtist, Observable<? extends primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.FollowedArtist>>() {
                    @Override
                    public Observable<? extends primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.FollowedArtist> call(primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.FollowedArtist followedArtist) {
                        return Observable.just(followedArtist);
                    }
                });
    }

    public Observable<List<Track>> loadTracks(){
        return Observable.defer(new Func0<Observable<List<Track>>>() {
            @Override
            public Observable<List<Track>> call() {
                return mRymeService.loadFavoriteTracks("tracks")
                        .map(new Func1<FavoriteTracksData, List<Track>>() {
                            @Override
                            public List<Track> call(FavoriteTracksData data) {
                                List<Track> tracks = new ArrayList<>();
                                if (data.getData() != null) {
                                    tracks.addAll(Arrays.asList(data.getData()));
                                }
                                return tracks;
                            }
                        });
            }
        });
    }

    public Observable<List<Artist>> loadArtists() {
        return Observable.defer(new Func0<Observable<List<Artist>>>() {
            @Override
            public Observable<List<Artist>> call() {
                return mRymeService.loadFavoriteArtists("artists")
                        .map(new Func1<FavoriteArtistsData, List<Artist>>() {
                            @Override
                            public List<Artist> call(FavoriteArtistsData data) {
                                List<Artist> artists = new ArrayList<>();
                                if (data.getData() != null) {
                                    artists.addAll(Arrays.asList(data.getData()));
                                }
                                return artists;
                            }
                        });
            }
        });
    }

    public Observable<List<EventAd>> getEventAds() {
        return Observable.defer(new Func0<Observable<List<EventAd>>>() {
            @Override
            public Observable<List<EventAd>> call() {
                return mRymeService.getEventAds()
                        .map(new Func1<EventAdData, List<EventAd>>() {
                            @Override
                            public List<EventAd> call(EventAdData eventAdData) {
                                List<EventAd> ads = new ArrayList<>();
                                if (eventAdData.getData() != null) {
                                    ads.addAll(Arrays.asList(eventAdData.getData()));
                                }
                                return ads;
                            }
                        });
            }
        });
    }

    public Observable<ActionResponse> viewEventAd(final String uuid){
        return Observable.defer(new Func0<Observable<ActionResponse>>() {
            @Override
            public Observable<ActionResponse> call() {
                return mRymeService.viewEventAd(uuid);
            }
        });
    }

    public Observable<List<SavedTrack>> loadDownloadedTracks() {
        return Observable.defer(new Func0<Observable<List<SavedTrack>>>() {
            @Override
            public Observable<List<SavedTrack>> call() {
                return mStDatabaseHelper.getSavedTracks();
            }
        });
    }

    public List<SavedTrack> syncLoadDownloadedTracks(){
        return mStDatabaseHelper.syncLoadSavedTracks();
    }

    public Observable<List<Comment>> getTrackComments(final String trackId) {
        return Observable.defer(new Func0<Observable<List<Comment>>>() {
            @Override
            public Observable<List<Comment>> call() {
                return mRymeService.getTrackComments(trackId)
                        .map(new Func1<CommentsData, List<Comment>>() {
                            @Override
                            public List<Comment> call(CommentsData commentsData) {
                                return new ArrayList<>(Arrays.asList(commentsData.getData()));
                            }
                        });
            }
        });
    }

    public Observable<AddCommentResponse> commentTrack(final String uuid, final HashMap<String, String> commentDetails) {
        return Observable.defer(new Func0<Observable<AddCommentResponse>>() {
            @Override
            public Observable<AddCommentResponse> call() {
                return mRymeService.commentTrack(uuid, commentDetails);
            }
        });
    }

    public Observable<ActionResponse> sendRequest(final HashMap<String, String> payload, final String token) {
        return Observable.defer(new Func0<Observable<ActionResponse>>() {
            @Override
            public Observable<ActionResponse> call() {
                return mRymeService.makeRequest(payload, token);
            }
        });
    }

    public Observable<Boolean> isThisTrackAlreadySaved(final String uuid) {
        return Observable.defer(new Func0<Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call() {
                return mStDatabaseHelper.getSavedTrack(uuid)
                        .map(new Func1<SavedTrack, Boolean>() {
                            @Override
                            public Boolean call(SavedTrack track) {
                                return track != null;
                            }
                        });
            }
        });
    }

    public Observable<AuthResponse> login(final HashMap<String, String> credentials) {
        return Observable.defer(new Func0<Observable<AuthResponse>>() {
            @Override
            public Observable<AuthResponse> call() {
                return mRymeService.login(credentials);
            }
        });
    }

    public Observable<ActionResponse> uploadAvatarImage(final RequestBody fileBody, final RequestBody imageTypeBody) {
        return Observable.defer(new Func0<Observable<ActionResponse>>() {
            @Override
            public Observable<ActionResponse> call() {
                return mRymeService.updateUserPhoto(fileBody, imageTypeBody);
            }
        });
    }

    public void doneSavingImage(EventImage image){
        mEventPoster.postEventSafely(image);
    }

    public Observable<ActionResponse> updateUserInfo(final HashMap<String, String> payload) {
        return Observable.defer(new Func0<Observable<ActionResponse>>() {
            @Override
            public Observable<ActionResponse> call() {
                return mRymeService.updateUserInfo(payload);
            }
        });
    }

    public void setRequestIsActive(boolean request_on) {
        mPreferencesHelper.setIsRequestActive(request_on);
    }

    public Observable<List<Object>> search(final String query) {
        return Observable.defer(new Func0<Observable<List<Object>>>() {
            @Override
            public Observable<List<Object>> call() {
                return mRymeService.searchArtist(query)
                        .map(new Func1<SearchResult, List<Object>>() {
                            @Override
                            public List<Object> call(SearchResult searchResult) {
                                List<Object> resultData = new ArrayList<>();
                                if (searchResult.getArtists() != null && searchResult.getRequests() != null) {
                                    resultData.addAll(Arrays.asList(searchResult.getRequests().getData()));
                                    resultData.addAll(Arrays.asList(searchResult.getArtists().getData()));
                                }
                                return resultData;
                            }
                        });
            }
        });
    }

    public Observable<ActionResponse> yesTo(final String uuid, final String token) {
        return Observable.defer(new Func0<Observable<ActionResponse>>() {
            @Override
            public Observable<ActionResponse> call() {
                return mRymeService.respondToVouch(uuid, true, token);
            }
        });
    }

    public Observable<ActionResponse> noTo(final String uuid, final String token) {
        return Observable.defer(new Func0<Observable<ActionResponse>>() {
            @Override
            public Observable<ActionResponse> call() {
                return mRymeService.respondToVouch(uuid, false, token);
            }
        });
    }

    public void notifyCommentAdapter(Comment comment) {
        mEventPoster.postEventSafely(comment);
    }

    public Observable<ActionResponse> likeTrack(final String uuid) {
        return Observable.defer(new Func0<Observable<ActionResponse>>() {
            @Override
            public Observable<ActionResponse> call() {
                return mRymeService.likeTrack(uuid);
            }
        });
    }

    public Observable<ActionResponse> dislikeTrack(final String uuid){
        return Observable.defer(new Func0<Observable<ActionResponse>>() {
            @Override
            public Observable<ActionResponse> call() {
                return mRymeService.dislikeTrack(uuid);
            }
        });
    }

    public Observable<ActionResponse> followArtist(final String uuid, final String token) {
        return Observable.defer(new Func0<Observable<ActionResponse>>() {
            @Override
            public Observable<ActionResponse> call() {
                return mRymeService.followArtist(uuid, token);
            }
        });
    }

    public Observable<ActionResponse> unfollowArtist(final String uuid, final String token){
        return Observable.defer(new Func0<Observable<ActionResponse>>() {
            @Override
            public Observable<ActionResponse> call() {
                return mRymeService.unFollowArtist(uuid, token);
            }
        });
    }

    public void subToCategoryChannel(Category category) {
        String name = category.name()+category.uuid();
        String action = "follow";
        CategoryToFU cat = CategoryToFU.newCat(name, action);
        mEventPoster.postEventSafely(cat);
    }

    public void unsubFromCategoryChannel(Category category){
        String name = category.name()+category.uuid();
        String action = "unfollow";
        CategoryToFU cat = CategoryToFU.newCat(name, action);
        mEventPoster.postEventSafely(cat);
    }

    public void subToArtistChannel(){}

    public void unsubFromArtistChannel(){}

    public void subToArtistRequestChannel(){}

    public void setRegistrationToken() {
        Log.d("application", "setRegistrationToken has called from Datamanager");
    }

    public Observable<Boolean> isUserAccount(final String artistId) {
        return Observable.defer(new Func0<Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call() {
                return mPreferencesHelper.getUserId()
                        .map(new Func1<String, Boolean>() {
                            @Override
                            public Boolean call(String userId) {
                                return userId.equals(artistId);
                            }
                        });
            }
        });
    }

    public Observable<ActionResponse> updateTrackInfo(final String trackId, final boolean isDownloadable) {
        return Observable.defer(new Func0<Observable<ActionResponse>>() {
            @Override
            public Observable<ActionResponse> call() {
                int downloadable = 0;
                if(isDownloadable){
                    downloadable = 1;
                }
                return mRymeService.updateTrackInfo(trackId, downloadable);
            }
        });
    }

    public Observable<List<Track>> getArtistTracks(final String artistId, final String type) {
        return Observable.defer(new Func0<Observable<List<Track>>>() {
            @Override
            public Observable<List<Track>> call() {
                HashMap<String, String > specs = new HashMap<>();
                specs.put("type", type);
                return mRymeService.getArtistTracks(artistId, specs)
                        .map(new Func1<TracksData, List<Track>>() {
                            @Override
                            public List<Track> call(TracksData tracksData) {
                                List<Track> tracks = new ArrayList<>();
                                if (tracksData.getData() != null) {
                                    tracks.addAll(Arrays.asList(tracksData.getData()));
                                }
                                return tracks;
                            }
                        });
            }
        });
    }

    public void trackFinishPlaying() {
        Log.d("playlist", "trackFinishPlaying called");
        PlayBackEvent event = PlayBackEvent.newEvent(PlayBackEvent.FINISH);
        Log.d("playlist", "event to post -> " + event.toString());
        mEventPoster.postEventSafely(event);
    }

    public Observable<ActionResponse> logEventStreamed(final String uuid) {
        return Observable.defer(new Func0<Observable<ActionResponse>>() {
            @Override
            public Observable<ActionResponse> call() {
                return mRymeService.streamAudioAd(uuid);
            }
        });
    }
}
