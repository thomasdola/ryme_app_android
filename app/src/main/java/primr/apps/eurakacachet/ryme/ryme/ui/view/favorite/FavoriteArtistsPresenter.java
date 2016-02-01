package primr.apps.eurakacachet.ryme.ryme.ui.view.favorite;

import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.model.Artist;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Subscription;

/**
 * Created by GURU on 2/1/2016.
 */
public class FavoriteArtistsPresenter extends BasePresenter<FavoriteMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public FavoriteArtistsPresenter(DataManager dataManager) {
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

    public List<Artist> syncFavoriteArtists(){
        checkViewAttached();
        getMvpView().showLoading();
        return null;
    }

}
