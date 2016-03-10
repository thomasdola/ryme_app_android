package primr.apps.eurakacachet.ryme.ryme.ui.view.splash;

import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.ui.base.MvpView;


public interface SplashMvpView extends MvpView {

    void launchMainActivity();

    void launchSignUpActivity();

    void launchFollowCategoryActivity();

    void launchPublicTrackDisplayActivity(Track track);

    void closeActivity();
}
