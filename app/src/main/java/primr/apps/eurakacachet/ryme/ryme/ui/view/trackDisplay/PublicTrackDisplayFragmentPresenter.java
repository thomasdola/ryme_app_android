package primr.apps.eurakacachet.ryme.ryme.ui.view.trackDisplay;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Subscription;


public class PublicTrackDisplayFragmentPresenter extends BasePresenter<PublicTrackDisplayFragmentMvpView>{

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public PublicTrackDisplayFragmentPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(PublicTrackDisplayFragmentMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void playTrack(Track track){
        checkViewAttached();
    }

    public void pauseTrack(Track track){
        checkViewAttached();
    }

    public void downloadTrack(Track track){
        checkViewAttached();
    }

    public void toggleLike(Track track){
        if( ! isLiked(track) ){
            likeTrack(track);
        }else {
            dislikeTrack(track);
        }
    }

    private boolean isLiked(Track track) {
        return false;
    }

    protected void likeTrack(Track track){
        checkViewAttached();
    }

    protected void dislikeTrack(Track track){
        checkViewAttached();
    }

}
