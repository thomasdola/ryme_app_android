package primr.apps.eurakacachet.ryme.ryme.ui.view.comment;

import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.model.Comment;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Subscription;


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

    public void comment(Track track, Comment comment){
        checkViewAttached();
    }

    public List<Comment> loadComments(Track track){
        checkViewAttached();
        return null;
    }

}
