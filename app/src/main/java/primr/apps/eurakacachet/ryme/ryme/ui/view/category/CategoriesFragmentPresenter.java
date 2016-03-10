package primr.apps.eurakacachet.ryme.ryme.ui.view.category;

import android.util.Log;

import com.pushtorefresh.storio.sqlite.operations.put.PutResults;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.Category;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.FollowedCategory;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class CategoriesFragmentPresenter extends BasePresenter<CategoriesFragmentMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public CategoriesFragmentPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(CategoriesFragmentMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadCategories(){
        checkViewAttached();
        getMvpView().showLoading();
        mDataManager.downloadAndSaveCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PutResults>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(PutResults putResults) {
                        Log.d("adapter", " " + putResults.numberOfInserts() + " of category saved");
                        mDataManager.loadCategories()
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<List<Category>>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        e.printStackTrace();
                                    }

                                    @Override
                                    public void onNext(List<Category> categories) {
                                        getMvpView().setCategoriesAdapter(categories);
                                        getMvpView().hideLoading();
                                    }
                                });
                    }
                });
    }

    private List<Category> makeCategories() {
        List<Category> categories = new ArrayList<>();
        Category category1 = Category.newCategory(null, "DancelHall", "ef9d2ef1-8c25-4157-bf60-15812b8fac46");
        Category category2 = Category.newCategory(null, "Gospel", "ef9d2ef1-8c25-4157-bfh0-15812b8fac46");
        Category category3 = Category.newCategory(null, "HipLife", "ef9d2ef1-8c25-3057-bf60-15812b8fac46");
        categories.add(category1);
        categories.add(category2);
        categories.add(category3);
        return categories;
    }

    public void startApp(){
        checkViewAttached();
        mDataManager.setReady(true);
        getMvpView().launchMainActivity();
    }

    public void startWatch() {
        mDataManager.loadFollowedCategories()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<FollowedCategory>>() {
                    @Override
                    public void call(List<FollowedCategory> followedCategories) {
                        Log.d("watch", "called from CategoriesFragment => "+followedCategories);
                    }
                });
    }
}
