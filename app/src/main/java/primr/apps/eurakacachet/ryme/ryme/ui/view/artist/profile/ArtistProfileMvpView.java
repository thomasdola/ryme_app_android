package primr.apps.eurakacachet.ryme.ryme.ui.view.artist.profile;

import primr.apps.eurakacachet.ryme.ryme.data.model.Artist;
import primr.apps.eurakacachet.ryme.ryme.ui.base.MvpView;


public interface ArtistProfileMvpView extends MvpView {

    void updateFollowers(int followers);

    void enableFollowButton();

    void disableFollowButton();

    void hideFollowButton();

    void showFollowButton();

    void setUpArtist(Artist artist);

    void setArtistBeingFollowed(Boolean isFollowed);

    void showUploadButton();

    void hideUploadButton();

    void setCategories(String[] categories);

    void launchMainActivity();
}
