package primr.apps.eurakacachet.ryme.ryme.ui.view.artist.profile;

import android.util.Log;

import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.Category;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.FollowedArtist;
import primr.apps.eurakacachet.ryme.ryme.data.model.ActionResponse;
import primr.apps.eurakacachet.ryme.ryme.data.model.Artist;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import primr.apps.eurakacachet.ryme.ryme.utils.Config;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx_gcm.internal.RxGcm;


public class ArtistProfilePresenter extends BasePresenter<ArtistProfileMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public ArtistProfilePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(ArtistProfileMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void followArtist(final Artist artist){
        checkViewAttached();
        getMvpView().disableFollowButton();
        final FollowedArtist followedArtist = makeFollowedArtist(artist);
        mSubscription = RxGcm.Notifications.currentToken()
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().enableFollowButton();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String token) {
                        Log.d("adapter", "user gcm token -> " + token);
                        mSubscription = mDataManager.followArtist(artist.uuid(), token)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<ActionResponse>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        e.printStackTrace();
                                        getMvpView().enableFollowButton();
                                    }

                                    @Override
                                    public void onNext(ActionResponse actionResponse) {
                                        Log.d("adapter", "actionResponse -> " + actionResponse.toString());
                                        if (actionResponse.code == Config.STATUS_OK) {
                                            saveFollowing(followedArtist);
                                        }else {
                                            getMvpView().enableFollowButton();
                                        }
                                    }
                                });
                    }
                });
    }

    private void saveFollowing(FollowedArtist followedArtist) {
        mSubscription = mDataManager.saveFollowedArtist(followedArtist)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PutResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().enableFollowButton();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(PutResult putResult) {
                        getMvpView().enableFollowButton();
                        Log.d("adapter", "Artist was saved ? => " + putResult.wasInserted());
                        getMvpView().setArtistBeingFollowed(true);
                        getMvpView().updateFollowers(+1);
                    }
                });
    }

    public void unFollowArtist(final Artist artist){
        checkViewAttached();
        getMvpView().disableFollowButton();
        final FollowedArtist followedArtist = makeFollowedArtist(artist);
        mSubscription = RxGcm.Notifications.currentToken()
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String token) {
                        mSubscription = mDataManager.unfollowArtist(artist.uuid(), token)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<ActionResponse>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        e.printStackTrace();
                                        getMvpView().enableFollowButton();
                                    }

                                    @Override
                                    public void onNext(ActionResponse actionResponse) {
                                        if (actionResponse.code == Config.STATUS_OK) {
                                            deleteFollowing(followedArtist);
                                        } else {
                                            getMvpView().enableFollowButton();
                                        }
                                    }
                                });
                    }
                });
    }

    private void deleteFollowing(FollowedArtist followedArtist) {
        mSubscription = mDataManager.deleteFollowedArtist(followedArtist)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DeleteResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getMvpView().enableFollowButton();
                    }

                    @Override
                    public void onNext(DeleteResult deleteResult) {
                        Log.d("adapter", "Artist was deleted ? => " + deleteResult.numberOfRowsDeleted());
                        getMvpView().setArtistBeingFollowed(false);
                        getMvpView().updateFollowers(-1);
                        getMvpView().enableFollowButton();
                    }
                });
    }

    public FollowedArtist makeFollowedArtist(Artist artist) {
        return FollowedArtist
                .newArtist(null, artist.uuid(), artist.stage_name());
    }

    public void loadArtist(String artistId) {
        checkViewAttached();
        if(artistId == null){
            Log.d("adapter" , "artist id is null");
        }
        mSubscription = mDataManager.getArtist(artistId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Artist>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getMvpView().launchMainActivity();
                    }

                    @Override
                    public void onNext(Artist artist) {
                        if(artist == null){
                            getMvpView().launchMainActivity();
                        }else {
                            Log.d("artist", artist.toString());
                            getMvpView().setUpArtist(artist);
                            getMvpView().setArtistBeingFollowed(artist.followed());
                            if(artist.amTheOne()){
                                getMvpView().showUploadButton();
                                getMvpView().hideFollowButton();
                            }
                        }
                    }
                });
    }

    public void loadCategoriesArray(){
        Log.d("settings", "loadCategoriesArray called");
        checkViewAttached();
        final List<String> categories = new ArrayList<>();
        mDataManager.loadCategoriesArray()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Category>() {
                    @Override
                    public void onCompleted() {
                        String[] catArray = new String[categories.size()];
                        categories.toArray(catArray);
                        getMvpView().setCategories(catArray);
                        Log.d("settings", "loadCategoriesArray called Done with => " + Arrays.toString(catArray));
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Category category) {
                        categories.add(category.name());
                    }
                });
    }
}
