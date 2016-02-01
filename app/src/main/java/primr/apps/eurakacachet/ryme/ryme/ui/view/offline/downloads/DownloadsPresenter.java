package primr.apps.eurakacachet.ryme.ryme.ui.view.offline.downloads;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.model.DownloadedTrack;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Subscription;


public class DownloadsPresenter extends BasePresenter<DownloadsMvpView>{

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public DownloadsPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(DownloadsMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public List<DownloadedTrack> loadTracks(){
        checkViewAttached();
        getMvpView().showLoading();
        return null;
    }

    public void deleteTrack(UUID trackId){
        checkViewAttached();
//        mDataManager.deleteDownloadedTrack(trackId);
        String trackTitle = "";
        getMvpView().showFeedback(trackTitle);
    }
}
