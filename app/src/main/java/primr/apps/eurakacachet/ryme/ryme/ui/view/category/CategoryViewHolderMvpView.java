package primr.apps.eurakacachet.ryme.ryme.ui.view.category;

import primr.apps.eurakacachet.ryme.ryme.ui.base.MvpView;


public interface CategoryViewHolderMvpView extends MvpView {

    void enableFollowButton();

    void disableFollowButton();

    void toggleFollowButton();

    void beingFollowed(boolean isFollowing);

    void showStart();

    void hideStart();

    void showError(String message);
}
