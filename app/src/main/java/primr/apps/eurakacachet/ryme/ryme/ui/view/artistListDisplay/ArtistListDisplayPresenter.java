package primr.apps.eurakacachet.ryme.ryme.ui.view.artistListDisplay;

import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.model.Artist;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ArtistListDisplayPresenter extends BasePresenter<ArtistListDisplayMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public ArtistListDisplayPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(ArtistListDisplayMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadArtists(){
        checkViewAttached();
        getMvpView().showLoading();
        mSubscription = mDataManager.loadArtists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Artist>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<Artist> artistList) {
                        getMvpView().hideLoading();
                        if(artistList.isEmpty()){
                            getMvpView().showEmptyState();
                        }else {
                            getMvpView().setArtists(artistList);
                        }
                    }
                });
    }

}
