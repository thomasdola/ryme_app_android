package primr.apps.eurakacachet.ryme.ryme.ui.view.search;

import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


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

    public void performSearch(String query){
        checkViewAttached();
        getMvpView().showLoading();
        mSubscription = mDataManager.search(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Object>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getMvpView().hideLoading();
                        getMvpView().setOops(true);
                    }

                    @Override
                    public void onNext(List<Object> objects) {
                        if(objects.isEmpty()){
                            getMvpView().setOops(false);
                        }else {
                            getMvpView().setResults(objects);
                        }
                    }
                });
    }


    public void getUserId() {
        checkViewAttached();
        mDataManager.getUserId()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String user_id) {
                        getMvpView().setUserId(user_id);
                    }
                });
    }
}
