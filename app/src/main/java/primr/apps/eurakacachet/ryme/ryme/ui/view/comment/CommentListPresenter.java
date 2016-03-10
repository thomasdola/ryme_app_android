package primr.apps.eurakacachet.ryme.ryme.ui.view.comment;

import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.model.Comment;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class CommentListPresenter extends BasePresenter<CommentListMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public CommentListPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(CommentListMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadComments(String trackId){
        checkViewAttached();
        getMvpView().showLoading();
        mDataManager.getTrackComments(trackId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Comment>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getMvpView().hideLoading();
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(List<Comment> comments) {
                        getMvpView().hideLoading();
                        if(comments.isEmpty()){
                            getMvpView().showNoCommentsYet();
                        }else {
                            getMvpView().setCommentList(comments);
                        }
                    }
                });
    }

}
