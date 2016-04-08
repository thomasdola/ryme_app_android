package primr.apps.eurakacachet.ryme.ryme.ui.view.settings;


import primr.apps.eurakacachet.ryme.ryme.ui.base.MvpView;

public interface SettingsFragmentMvpView extends MvpView{

    void setIsArtist(Boolean isArtist);

    void setStageName(String stageName);

    void setUsername(String username);

    void isAllowed(Boolean isAllowed);

    void hideArtistRequest();

    void showDialogLoading();

    void hideDialogLoading();

    void setCategories(String[] categories);

    void showError(int errorCode);

    void showSuccessDialog();
}
