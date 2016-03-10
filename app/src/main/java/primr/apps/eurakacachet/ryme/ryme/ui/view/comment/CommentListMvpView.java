package primr.apps.eurakacachet.ryme.ryme.ui.view.comment;

import java.util.List;

import primr.apps.eurakacachet.ryme.ryme.data.model.Comment;
import primr.apps.eurakacachet.ryme.ryme.ui.base.MvpView;


public interface CommentListMvpView extends MvpView {

    void showLoading();

    void hideLoading();

    void setCommentList(List<Comment> commentList);

    void showNoCommentsYet();

    void showError();
}
