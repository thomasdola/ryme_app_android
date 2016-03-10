package primr.apps.eurakacachet.ryme.ryme.ui.view.offline.downloads;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.SavedTrack;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;


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

    public void loadTracks(){
        checkViewAttached();
        getMvpView().showLoading();
        mDataManager.loadDownloadedTracks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<SavedTrack>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<SavedTrack> savedTracks) {
                        Log.d("offline", savedTracks.toString());
                        getMvpView().setTracks(savedTracks);
                    }
                });
    }

    public void deleteTrack(){
        checkViewAttached();
//        mDataManager.deleteDownloadedTrack(trackId);
        String trackTitle = "";
        getMvpView().showFeedback(trackTitle);
    }
}
