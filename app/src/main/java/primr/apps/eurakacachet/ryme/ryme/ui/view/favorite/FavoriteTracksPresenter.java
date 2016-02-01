package primr.apps.eurakacachet.ryme.ryme.ui.view.favorite;

import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Subscription;


public class FavoriteTracksPresenter extends BasePresenter<FavoriteMvpView> {


    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public FavoriteTracksPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(FavoriteMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public List<Track> syncFavoriteTracks(){
        checkViewAttached();
        getMvpView().showLoading();
        return null;
    }

}
