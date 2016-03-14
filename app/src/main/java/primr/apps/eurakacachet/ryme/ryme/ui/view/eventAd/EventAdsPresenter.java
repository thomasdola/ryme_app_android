package primr.apps.eurakacachet.ryme.ryme.ui.view.eventAd;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.model.ActionResponse;
import primr.apps.eurakacachet.ryme.ryme.data.model.EventAd;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class EventAdsPresenter extends BasePresenter<EventAdsMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public EventAdsPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(EventAdsMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadEventAds(){
        Log.d("events", "loadEventAds called");
        checkViewAttached();
        getMvpView().showLoading();
        mSubscription = mDataManager.getEventAds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<EventAd>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<EventAd> eventAds) {
                        Log.d("events", "events -> " + eventAds.toString());
                        if(eventAds.isEmpty()){
                            getMvpView().showEmpty();
                        }else {
                            getMvpView().setEventAds(eventAds);
                        }
                    }
                });
    }

    public void logView(EventAd ad) {
        checkViewAttached();
        mSubscription = mDataManager.viewEventAd(ad.getUuid())
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
                        if(actionResponse.code == 200){
                            Log.d("events", "event viewed successfully");
                        }else {
                            Log.d("events", "error -> " + actionResponse.message);
                        }
                    }
                });
    }
}
