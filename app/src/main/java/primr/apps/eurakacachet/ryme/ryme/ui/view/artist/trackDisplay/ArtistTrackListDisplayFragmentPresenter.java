package primr.apps.eurakacachet.ryme.ryme.ui.view.artist.trackDisplay;


import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ArtistTrackListDisplayFragmentPresenter extends BasePresenter<ArtistTrackListDisplayFragmentMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public ArtistTrackListDisplayFragmentPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(ArtistTrackListDisplayFragmentMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void downloadTracks(String artistId, int trackType){
        checkViewAttached();
        getMvpView().showLoading();
        Log.d("artist", "downloadTracks is called");
        if(trackType == ArtistTrackListDisplayFragment.ALL_TRACK){
            getAllTracks(artistId);
        }else if(trackType == ArtistTrackListDisplayFragment.NEW_RELEASE_TRACK){
            getNewTracks(artistId);
        }
    }

    private void getNewTracks(String artistId) {
        mSubscription = mDataManager.getArtistTracks(artistId, "new")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Track>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().hideLoading();
                        getMvpView().showError();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<Track> tracks) {
                        Log.d("artist", "download tracks done in all with -> " + tracks.toString());
                        getMvpView().hideLoading();
                        if(tracks.isEmpty()){
                            getMvpView().showEmptyState();
                        }else {
                            getMvpView().setTrackList(tracks);
                        }
                    }
                });
    }

    private void getAllTracks(String artistId) {
        mSubscription = mDataManager.getArtistTracks(artistId, "all")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Track>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().hideLoading();
                        e.printStackTrace();
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(List<Track> tracks) {
                        Log.d("artist", "download tracks done in all with -> " + tracks.toString());
                        getMvpView().hideLoading();
                        if(tracks.isEmpty()){
                            getMvpView().showEmptyState();
                        }else {
                            getMvpView().setTrackList(tracks);
                        }
                    }
                });
    }

}
