package primr.apps.eurakacachet.ryme.ryme.ui.view.newTrackFeed;

import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Subscription;


public class NewReleasedTracksPresenter extends BasePresenter<NewReleasedTracksMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public NewReleasedTracksPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(NewReleasedTracksMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public List<Track> loadTracks(){
        checkViewAttached();
        getMvpView().showLoading();
        return null;
    }

    public void viewTrack(Track track){
        checkViewAttached();
    }
}
