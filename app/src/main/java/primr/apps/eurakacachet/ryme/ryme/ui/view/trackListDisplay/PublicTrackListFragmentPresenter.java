package primr.apps.eurakacachet.ryme.ryme.ui.view.trackListDisplay;

import android.util.Log;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class PublicTrackListFragmentPresenter extends BasePresenter<PublicTrackListFragmentMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public PublicTrackListFragmentPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(PublicTrackListFragmentMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void refreshTrackStore(int type){
        checkViewAttached();
        downloadTracks(type);
    }

    public void downloadTracks(int type){
        checkViewAttached();
        Log.d("main", "download tracks called ");
        getMvpView().showLoading();
        HashMap<String, String> specs = new HashMap<>();
        if(type == PublicTrackListDisplayFragment.NEW_RELEASE){
            specs.put("type", "new");
            loadPublicTracks(specs);
        }else if(type == PublicTrackListDisplayFragment.TRENDING){
            specs.put("type", "trending");
            loadPublicTracks(specs);
        }else if(type == PublicTrackListDisplayFragment.FAVORITE){
            loadFavoriteTracks();
        }
    }

    public void loadPublicTracks(HashMap<String, String> specs) {
        final String type = specs.get("type");
        mSubscription = mDataManager.getTracks(specs)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Track>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("adapter", "onCompleted from download tracks");
                        getMvpView().hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.getMessage();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<Track> tracks) {
                        if (tracks.isEmpty()) {
                            if(type.equals("new")){
                                getMvpView().showNewTracksEmptyState();
                            }else if(type.equals("trending")){
                                getMvpView().showTrendingTracksEmptyState();
                            }
                        } else {
                            getMvpView().setTrackListAdapter(tracks);
                        }
                    }
                });
    }

    private void loadFavoriteTracks() {
        checkViewAttached();
        mSubscription = mDataManager.loadTracks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Track>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<Track> tracks) {
                        if(tracks.isEmpty()){
                            getMvpView().showFavoriteTracksEmptyState();
                        }else {
                            getMvpView().setTrackListAdapter(tracks);
                        }
                    }
                });
    }

    public void loadMore(int page) {

    }

//    public void getFakeTracks() {
//        List<Track> tracks = new ArrayList<>();
//        tracks.add(Track.newTrack("Father and Son", "Thomas", new Date().toString(), 20, 20, 20, 20, true,
//                "0121deda-7f61-4c6f-ada3-6bb31ac7322e", "2dcb8891-c24c-4582-91d7-442994529947",
//                "https://ia902708.us.archive.org/3/items/count_monte_cristo_0711_librivox/Count_Monte_Cristo_1110.jpg?cnt=0",
////                "https://dl.dropboxusercontent.com/u/25887355/test_song.mp3"
//                "http://cs1.mp3.pm/download/37889938/R1YvRW9naVBEQngvMXdqOGkwRTY4VWVXUzBWb1hUZ3I2TGIxMnRtc08yM0dKYUFiQWt3M3lxU3dtQndHdDY2OHp2d2hvcFdTZ3U5eXFpRlJ6MzBOK0RVNHRuaFBKNEZSYUpKNExSbzJHU0RTRFNLUUoydzFPRnlNTHYvSDF5KzE/Chris_Brown_-_101_Interlude_(mp3.pm).mp3",
//                "mp3", "jpg", 23021, AudioAd.newAd("2dcb8891-c24c-4582-91d7-4429945299d7", "http://www.vansirc.net/load/Ring%20Tones/True%20Tones/Nokia_Tune_Dog_Disguise.mp3"), null));
//        getMvpView().setTrackListAdapter(tracks);
//    }
}
