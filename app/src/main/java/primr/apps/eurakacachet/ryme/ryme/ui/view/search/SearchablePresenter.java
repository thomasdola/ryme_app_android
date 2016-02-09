package primr.apps.eurakacachet.ryme.ryme.ui.view.search;

import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.model.Searchable;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Subscription;


public class SearchablePresenter extends BasePresenter<SearchableMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public SearchablePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(SearchableMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public List<Searchable> performSearch(String query){
        checkViewAttached();
        getMvpView().showLoading();
//        mDataManager.search(query)
        return null;
    }

}
