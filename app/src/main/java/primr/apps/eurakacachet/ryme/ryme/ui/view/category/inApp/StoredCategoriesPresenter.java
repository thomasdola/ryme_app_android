package primr.apps.eurakacachet.ryme.ryme.ui.view.category.inApp;

import android.util.Log;

import com.pushtorefresh.storio.sqlite.operations.put.PutResults;

import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.Category;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.FollowedCategory;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class StoredCategoriesPresenter extends BasePresenter<StoredCategoriesMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;
    private CompositeSubscription mCompositeSubscription;

    @Inject
    public StoredCategoriesPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(StoredCategoriesMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
        if (mCompositeSubscription != null) mCompositeSubscription.unsubscribe();
    }

    public void syncCategories(){
        checkViewAttached();
        Log.d("main", "syncCategories called");
        mDataManager.downloadAndSaveCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PutResults>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.getMessage();
                    }

                    @Override
                    public void onNext(PutResults putResults) {
                        Log.d("main", "done syncing categories => "
                                + putResults.numberOfInserts());
                    }
                });
    }

    public void syncFollowedCategories(){
        checkViewAttached();
        mDataManager.downloadAndSaveFollowedCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PutResults<FollowedCategory>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(PutResults<FollowedCategory> followedCategoryPutResults) {
                        Log.d("main", "done syncing followed categories => "
                                + followedCategoryPutResults.numberOfInserts());
                    }
                });
    }


    public void loadCategories() {
        mDataManager.loadCategories()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Category>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<Category> categories) {
                        getMvpView().setAdapter(categories);
                    }
                });
    }
}
