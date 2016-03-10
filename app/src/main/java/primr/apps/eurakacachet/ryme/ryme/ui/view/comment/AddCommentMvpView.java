package primr.apps.eurakacachet.ryme.ryme.ui.view.comment;

import primr.apps.eurakacachet.ryme.ryme.ui.base.MvpView;


public interface AddCommentMvpView extends MvpView{

    void showLoading();

    void dismissDialog();

    void disableButtons();

    void enableButtons();

    void showError();

    void hideLoading();

}
