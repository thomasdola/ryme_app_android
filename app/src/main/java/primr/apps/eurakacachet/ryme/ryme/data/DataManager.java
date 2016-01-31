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
import rx.functions.Func1;


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

    public PreferencesHelper getPreferencesHelper(){
        return mPreferencesHelper;
    }


    public Observable<DownloadedTrack> loadDownloadedTracks(){
        return mDatabaseHelper.getDownloadedTracks().concatMap(new Func1<List<DownloadedTrack>, Observable<? extends DownloadedTrack>>() {
            @Override
            public Observable<? extends DownloadedTrack> call(List<DownloadedTrack> downloadedTracks) {
                return Observable.from(downloadedTracks);
            }
        }).concatMap(new Func1<DownloadedTrack, Observable<? extends DownloadedTrack>>() {
            @Override
            public Observable<? extends DownloadedTrack> call(DownloadedTrack track) {
                return Observable.just(track);
            }
        }).distinct();
    }

    public Observable<LikedTrack> loadLikedTracks(){
        return mDatabaseHelper.getLikedTracks()
                .concatMap(new Func1<List<LikedTrack>, Observable<? extends LikedTrack>>() {
                    @Override
                    public Observable<? extends LikedTrack> call(List<LikedTrack> likedTracks) {
                        return Observable.from(likedTracks);
                    }
                }).concatMap(new Func1<LikedTrack, Observable<? extends LikedTrack>>() {
                    @Override
                    public Observable<? extends LikedTrack> call(LikedTrack track) {
                        return Observable.just(track);
                    }
                });
    }

    public Observable<FollowedArtist> loadFollowedArtist(){
        return mDatabaseHelper.getFollowedArtists()
                .concatMap(new Func1<List<FollowedArtist>, Observable<? extends FollowedArtist>>() {
                    @Override
                    public Observable<? extends FollowedArtist> call(List<FollowedArtist> followedArtists) {
                        return Observable.from(followedArtists);
                    }
                })
                .concatMap(new Func1<FollowedArtist, Observable<? extends FollowedArtist>>() {
                    @Override
                    public Observable<? extends FollowedArtist> call(FollowedArtist followedArtist) {
                        return Observable.just(followedArtist);
                    }
                });
    }

    public Observable<FollowedCategory> loadFollowedCategory(){
        return mDatabaseHelper.getFollowedCategories()
                .concatMap(new Func1<List<FollowedCategory>, Observable<? extends FollowedCategory>>() {
                    @Override
                    public Observable<? extends FollowedCategory> call(List<FollowedCategory> followedCategories) {
                        return Observable.from(followedCategories);
                    }
                })
                .concatMap(new Func1<FollowedCategory, Observable<? extends FollowedCategory>>() {
                    @Override
                    public Observable<? extends FollowedCategory> call(FollowedCategory followedCategory) {
                        return Observable.just(followedCategory);
                    }
                });
    }

    public Observable<Track> getTracksFromIds(UUID[] ids){
        List<UUID> trackIds = new ArrayList<>(ids.length);
        for (UUID id : ids) {
            trackIds.add(id);
        }
        return Observable.from(trackIds)
                .concatMap(new Func1<UUID, Observable<? extends Track>>() {
                    @Override
                    public Observable<? extends Track> call(UUID uuid) {
                        return mRymeService.getTrack(uuid);
                    }
                });
    }

    public Observable<Artist> getArtistsFromIds(UUID[] ids){
        List<UUID> artistIds = new ArrayList<>(ids.length);
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

    private Observable<? extends Artist> getArtist(UUID uuid) {
        return mRymeService.getArtist(uuid);
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

}
