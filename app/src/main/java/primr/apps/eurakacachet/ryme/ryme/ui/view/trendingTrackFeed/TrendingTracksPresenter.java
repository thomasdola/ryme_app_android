package primr.apps.eurakacachet.ryme.ryme.ui.view.trendingTrackFeed;

import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Subscription;


public class TrendingTracksPresenter extends BasePresenter<TrendingTracksMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public TrendingTracksPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(TrendingTracksMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public List<Track> loadTracks(){
        checkViewAttached();
        return null;
    }

}
