package primr.apps.eurakacachet.ryme.ryme.ui.view.comment;

import java.util.HashMap;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.model.AddCommentResponse;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import primr.apps.eurakacachet.ryme.ryme.utils.Config;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class AddCommentPresenter extends BasePresenter<AddCommentMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public AddCommentPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(AddCommentMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void comment(String trackId, String message){
        checkViewAttached();
        getMvpView().disableButtons();
        getMvpView().showLoading();
        HashMap<String, String> commentDetails = new HashMap<>();
        commentDetails.put("message", message);
        mDataManager.commentTrack(trackId, commentDetails)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AddCommentResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().hideLoading();
                        getMvpView().enableButtons();
                        getMvpView().showError();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(AddCommentResponse actionResponse) {
                        getMvpView().hideLoading();
                        if (actionResponse.code == Config.STATUS_OK) {
                            mDataManager.notifyCommentAdapter(actionResponse.getData());
                            getMvpView().dismissDialog();
                        } else {
                            getMvpView().enableButtons();
                            getMvpView().showError();
                        }
                    }
                });
    }

}
