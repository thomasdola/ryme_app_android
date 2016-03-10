package primr.apps.eurakacachet.ryme.ryme.ui.view.offline.trackDisplay;

import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.SavedTrack;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Subscription;

/**
 * Created by GURU on 2/1/2016.
 */
public class TrackDisplayPresenter extends BasePresenter<TrackDisplayMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public TrackDisplayPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(TrackDisplayMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public List<SavedTrack> loadSavedTracks(){
        checkViewAttached();
        return mDataManager.syncLoadDownloadedTracks();
    }

}
