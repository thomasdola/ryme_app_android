package primr.apps.eurakacachet.ryme.ryme.ui.view.artist.profile;

import primr.apps.eurakacachet.ryme.ryme.ui.base.MvpView;


public interface ArtistProfileMvpView extends MvpView {

    void toggleFollowButton();

    void updateFollowers(long followers);

}
