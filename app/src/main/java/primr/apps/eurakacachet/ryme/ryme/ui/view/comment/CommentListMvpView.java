package primr.apps.eurakacachet.ryme.ryme.ui.view.comment;

import primr.apps.eurakacachet.ryme.ryme.ui.base.MvpView;


public interface CommentListMvpView extends MvpView {

    void showLoading();

    void hideLoading();

    void disableSendCommentButton();

    void enableSendCommentButton();

}
