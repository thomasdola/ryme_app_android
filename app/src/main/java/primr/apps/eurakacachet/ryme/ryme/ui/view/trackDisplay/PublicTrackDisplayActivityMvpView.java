package primr.apps.eurakacachet.ryme.ryme.ui.view.trackDisplay;


import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.ui.base.MvpView;

public interface PublicTrackDisplayActivityMvpView extends MvpView{

    void setTrack(Track track);

    void showLoading();

    void hideLoading();
}
