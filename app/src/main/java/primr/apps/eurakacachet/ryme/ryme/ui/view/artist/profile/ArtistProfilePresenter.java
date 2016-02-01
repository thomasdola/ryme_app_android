package primr.apps.eurakacachet.ryme.ryme.ui.view.artist.profile;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.model.Artist;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Subscription;

/**
 * Created by GURU on 2/1/2016.
 */
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

    public Artist getArtist(UUID artistId){
        checkViewAttached();
        return null;
    }

    public long followArtist(UUID artistId){
        checkViewAttached();
        getMvpView().toggleFollowButton();
        long followers = 20;
        getMvpView().updateFollowers(followers);
        return followers;
    }

    public long unFollowArtist(UUID artistId){
        checkViewAttached();
        getMvpView().toggleFollowButton();
        long followers = 19;
        getMvpView().updateFollowers(followers);
        return followers;
    }

    public boolean isFollowing(UUID artistId){
        checkViewAttached();
        return false;
    }

    private List<UUID> getFollowedArtistIds(){
        return null;
    }
}
