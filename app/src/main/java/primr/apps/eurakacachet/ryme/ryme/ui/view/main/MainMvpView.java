package primr.apps.eurakacachet.ryme.ryme.ui.view.main;

import primr.apps.eurakacachet.ryme.ryme.ui.base.MvpView;


public interface MainMvpView extends MvpView {

    void setUsername(String username);

    void setPhotoAvatar(String path);

    void setLetterAvatar(String username);

    void setBackImage(String path);

    void hidePhotoAvatarView();

    void hideLetterAvatarView();

    void hideUploadMenu();

    void isArtist(Boolean isArtist);

    void setUserId(String uuid);

    void initMainView();
}
