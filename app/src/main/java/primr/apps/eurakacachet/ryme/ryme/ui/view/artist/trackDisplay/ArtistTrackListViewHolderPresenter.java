package primr.apps.eurakacachet.ryme.ryme.ui.view.artist.trackDisplay;

import android.util.Log;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.model.ActionResponse;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import primr.apps.eurakacachet.ryme.ryme.utils.Config;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ArtistTrackListViewHolderPresenter extends BasePresenter<ArtistTrackListViewHolderMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public ArtistTrackListViewHolderPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(ArtistTrackListViewHolderMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void isTrackDownloadable(String trackId, final boolean isDownloadable) {
        checkViewAttached();
        Log.d("artist", "payload -> " + trackId + "and -> " + isDownloadable);
        getMvpView().disableSwitch();
        mDataManager.updateTrackInfo(trackId, isDownloadable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ActionResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().checkSwitch(!isDownloadable);
                        e.printStackTrace();
                        getMvpView().enableSwitch();
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(ActionResponse actionResponse) {
                        getMvpView().enableSwitch();
                        if(actionResponse.code == Config.STATUS_OK){
                            getMvpView().checkSwitch(isDownloadable);
                        }else {
                            getMvpView().checkSwitch(!isDownloadable);
                            getMvpView().showError();
                        }
                    }
                });
    }
}
