package primr.apps.eurakacachet.ryme.ryme.ui.view.offline.trackDisplay;

import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.SavedTrack;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Subscription;


public class DownloadTrackDisplayPresenter extends BasePresenter<DownloadTrackDisplayMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public DownloadTrackDisplayPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(DownloadTrackDisplayMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public List<SavedTrack> loadTracks(){
        checkViewAttached();
        return mDataManager.syncLoadDownloadedTracks();
    }
}
