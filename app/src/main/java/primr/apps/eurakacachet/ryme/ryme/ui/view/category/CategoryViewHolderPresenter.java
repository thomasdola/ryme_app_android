package primr.apps.eurakacachet.ryme.ryme.ui.view.category;

import android.util.Log;

import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.Category;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.FollowedCategory;
import primr.apps.eurakacachet.ryme.ryme.data.model.ActionResponse;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import primr.apps.eurakacachet.ryme.ryme.utils.Config;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx_gcm.internal.RxGcm;


public class CategoryViewHolderPresenter extends BasePresenter<CategoryViewHolderMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public CategoryViewHolderPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(CategoryViewHolderMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void follow(final Category category){
        checkViewAttached();
        Log.d("adapter", "follow Called");
        getMvpView().disableFollowButton();
        RxGcm.Notifications.currentToken()
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("token", "could not get the gcm token");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String token) {
                        Log.d("token", token);
                        mSubscription = mDataManager.followCategory(category.uuid(), token)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<ActionResponse>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        e.printStackTrace();
                                        onOperationFailed();
                                    }

                                    @Override
                                    public void onNext(ActionResponse actionResponse) {
                                        Log.d("adapter", "follow done with => " + actionResponse.code);
                                        if (actionResponse.code == Config.STATUS_OK) {
                                            onFollowSuccessful(category);
                                        }else {
                                            onOperationFailed();
                                        }
                                    }
                                });
                    }
                });
    }

    public void onOperationFailed() {
        getMvpView().enableFollowButton();
    }

    public void onFollowSuccessful(final Category category) {
        Log.d("adapter", "onFollowSuccessful Called");
        FollowedCategory followedCategory = FollowedCategory
                .newCategory(null, category.name(), String.valueOf(category.uuid()));
        mSubscription = mDataManager.saveFollowedCategory(followedCategory)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PutResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        onOperationFailed();
                    }

                    @Override
                    public void onNext(PutResult putResult) {
                        getMvpView().enableFollowButton();
                        if(putResult.wasInserted()){
                            getMvpView().beingFollowed(true);
                            getMvpView().toggleFollowButton();
                        }
                    }
                });
    }

    public void unFollow(final Category category){
        Log.d("adapter", "UnFollow Called");
        checkViewAttached();
        getMvpView().disableFollowButton();
        RxGcm.Notifications.currentToken()
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d("token", "could not get the gcm token for unfollowing");
                        onOperationFailed();
                    }

                    @Override
                    public void onNext(String token) {

                        mSubscription = mDataManager.unFollowCategory(category.uuid(), token)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<ActionResponse>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        e.printStackTrace();
                                        onOperationFailed();
                                    }

                                    @Override
                                    public void onNext(ActionResponse actionResponse) {
                                        if(actionResponse.code == Config.STATUS_OK){
                                            onUnFollowSuccessful(category);
                                        }else {
                                            onOperationFailed();
                                        }
                                    }
                                });
                    }
                });
    }

    public void attemptUnfollow(Category category){
        checkViewAttached();
        getMvpView().disableFollowButton();
        Log.d("adapter", "attemptUnfollow Called from StoredCategories");
        final FollowedCategory followedCategory = FollowedCategory
                .newCategory(null, category.name(), String.valueOf(category.uuid()));
        mSubscription = mDataManager.canUnfollow()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        onOperationFailed();
                    }
                })
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean canUnFollow) {
                        if (canUnFollow) {
                            Log.d("adapter", "attemptUnfollow Called from StoredCategories" );
                            doUnFollow(followedCategory);
                        } else {
                            Log.d("adapter", "attemptUnfollow Called from StoredCategories with No");
                            getMvpView().showError("Sorry! Should have on Category followed.");
                            onOperationFailed();
                        }
                    }
                });
    }

    public void onUnFollowSuccessful(final Category category) {
        Log.d("adapter", "attemptUnfollow Called");
        final FollowedCategory followedCategory = FollowedCategory
                .newCategory(null, category.name(), String.valueOf(category.uuid()));
        mSubscription = mDataManager.deleteFollowedCategory(followedCategory)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DeleteResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        onOperationFailed();
                    }

                    @Override
                    public void onNext(DeleteResult deleteResult) {
                        getMvpView().enableFollowButton();
                        if(deleteResult.numberOfRowsDeleted() == 1){
                            getMvpView().beingFollowed(false);
                            getMvpView().toggleFollowButton();
                        }
                    }
                });
    }

    public void doUnFollow(FollowedCategory followedCategory) {
        Category category = Category.newCategory(followedCategory.id(), followedCategory.name(),
                followedCategory.uuid());
        unFollow(category);
    }

    public void watching(){
        checkViewAttached();
        mSubscription = mDataManager.loadFollowedCategories()
                .subscribe(new Action1<List<FollowedCategory>>() {
                    @Override
                    public void call(List<FollowedCategory> followedCategories) {
                        if( followedCategories.isEmpty()){
                            getMvpView().hideStart();
                            setIsReady(false);
                        }else {
                            getMvpView().showStart();
                            setIsReady(true);
                        }
                    }
                });
    }

    protected void setIsReady(boolean isReady){
        mDataManager.setReady(isReady);
    }

    public void isFollowing(final Category category){
        Log.d("adapter", "isFollowing Called");
        mSubscription = mDataManager.loadBFCategories()
                .subscribe(new Subscriber<List<FollowedCategory>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("adapter", "Oops...Error from loading list of followed category");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<FollowedCategory> followedCategories) {
                        FollowedCategory followedCategory = FollowedCategory
                                .newCategory(null, category.name(), category.uuid());
                        Log.d("adapter", " from is following " +
                                Arrays.toString(followedCategories.toArray()));
                        Log.d("adapter", String.valueOf(followedCategory));
                        Log.d("adapter", "List contains " + followedCategory.name() + " ? => "
                                + followedCategories
                                .contains(followedCategory));
                        if (followedCategories.contains(followedCategory)) {
                            getMvpView().beingFollowed(true);
                        } else {
                            getMvpView().beingFollowed(false);
                        }
                    }
                });
    }

}
