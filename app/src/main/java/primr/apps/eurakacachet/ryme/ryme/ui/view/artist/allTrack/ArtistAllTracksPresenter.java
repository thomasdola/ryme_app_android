package primr.apps.eurakacachet.ryme.ryme.ui.view.artist.allTrack;

import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import primr.apps.eurakacachet.ryme.ryme.ui.view.artist.ArtistTracksMvpView;
import rx.Subscription;


public class ArtistAllTracksPresenter extends BasePresenter<ArtistTracksMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public ArtistAllTracksPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(ArtistTracksMvpView mvpView) {
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
}
