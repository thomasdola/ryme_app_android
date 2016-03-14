package primr.apps.eurakacachet.ryme.ryme.ui.view.trackDisplay;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class PublicTrackDisplayActivityPresenter extends BasePresenter<PublicTrackDisplayActivityMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public PublicTrackDisplayActivityPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(PublicTrackDisplayActivityMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void getTrack(String track_id){
        checkViewAttached();
        getMvpView().showLoading();
        mSubscription = mDataManager.getTrack(track_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Track>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().hideLoading();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Track track) {
                        getMvpView().hideLoading();
                        getMvpView().setTrack(track);
                    }
                });
    };

}
