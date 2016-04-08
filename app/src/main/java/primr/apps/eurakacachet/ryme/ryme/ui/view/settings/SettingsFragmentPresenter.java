package primr.apps.eurakacachet.ryme.ryme.ui.view.settings;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.Category;
import primr.apps.eurakacachet.ryme.ryme.data.model.ActionResponse;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import primr.apps.eurakacachet.ryme.ryme.utils.Config;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx_gcm.internal.RxGcm;


public class SettingsFragmentPresenter extends BasePresenter<SettingsFragmentMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public SettingsFragmentPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(SettingsFragmentMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void getArtistStageName() {
        Log.d("settings", "getArtistStageName called");
        checkViewAttached();
        mSubscription = mDataManager.getArtistName()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String stage_name) {
                        Log.d("settings", "getArtistStageName Done");
                        getMvpView().setStageName(stage_name);
                    }
                });
    }

    public void isArtist() {
        Log.d("settings", "isArtist called");
        mDataManager.isArtist()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Boolean isArtist) {
                        Log.d("settings", "isArtist Done");
                        getMvpView().setIsArtist(isArtist);
                    }
                });
    }

    public void setUpdateUsername(final String username) {
        Log.d("settings", "setUpdateUsername called");
        checkViewAttached();
        HashMap<String , String> payload = new HashMap<>();
        payload.put("username", username);
        mDataManager.updateUserInfo(payload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ActionResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ActionResponse actionResponse) {
                        if (actionResponse.code == Config.STATUS_OK) {
                            mDataManager.setUsername(username);
                        }
                    }
                });
    }

    public void updateIsArtist(boolean isArtist) {
        Log.d("settings", "updateIsArtist called");
        checkViewAttached();
        mDataManager.setIsArtist(isArtist);
    }

    public void updateStageName(final String stageName) {
        checkViewAttached();
        HashMap<String, String> payload = new HashMap<>();
        payload.put("stage_name", stageName);
        mSubscription = mDataManager.updateUserInfo(payload)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ActionResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ActionResponse actionResponse) {
                        if (actionResponse.code == Config.STATUS_OK){
                            mDataManager.setArtistName(stageName);
                        }
                    }
                });
    }

    public void getUsernameDefaultValue() {
        Log.d("settings", "getUsernameDefaultValue called");
        checkViewAttached();
        mSubscription = mDataManager.getUsername()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("settings", "getUsernameDefaultValue done");
                        getMvpView().setUsername(s);
                    }
                });
    }

    public void isAllowedToMakeRequest() {
        Log.d("settings", "isAllowedToMakeRequest called");
        checkViewAttached();
        getMvpView().isAllowed(mDataManager.isAllowedToMakeRequest());
    }

    public void sendRequest(String stage_name, String category) {
        checkViewAttached();
        getMvpView().showDialogLoading();
        final HashMap<String, String> payload = new HashMap<>();
        payload.put("stage_name", stage_name);
        payload.put("category", category);
        RxGcm.Notifications.currentToken()
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().hideDialogLoading();
                        getMvpView().setIsArtist(false);
                        e.printStackTrace();
                        getMvpView().showError(SettingsFragment.NO_TOKEN_ERROR);
                    }

                    @Override
                    public void onNext(String token) {
                        mSubscription = mDataManager.sendRequest(payload, token)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<ActionResponse>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        e.printStackTrace();
                                        getMvpView().setIsArtist(false);
                                        getMvpView().hideDialogLoading();
                                    }

                                    @Override
                                    public void onNext(ActionResponse actionResponse) {
                                        getMvpView().hideDialogLoading();
                                        if (actionResponse.code == Config.STATUS_OK){
                                            getMvpView().hideArtistRequest();
                                            getMvpView().showSuccessDialog();
                                        }else {
                                            Log.d("settings", actionResponse.toString());
                                            getMvpView().showError(SettingsFragment.ON_FAILURE);
                                            getMvpView().setIsArtist(false);
                                        }
                                    }
                                });
                    }
                });
    }
    
    public void loadCategoriesArray(){
        Log.d("settings", "loadCategoriesArray called");
        checkViewAttached();
        final List<String> categories = new ArrayList<>();
        mSubscription = mDataManager.loadCategoriesArray()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Category>() {
                    @Override
                    public void onCompleted() {
                        String[] catArray = new String[categories.size()];
                        categories.toArray(catArray);
                        getMvpView().setCategories(catArray);
                        Log.d("settings", "loadCategoriesArray called Done with => " + Arrays.toString(catArray));
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Category category) {
                        categories.add(category.name());
                    }
                });
    }
}
