package primr.apps.eurakacachet.ryme.ryme.ui.view.trackDisplay;

import android.util.Log;

import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.LikedTrack;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.SavedTrack;
import primr.apps.eurakacachet.ryme.ryme.data.model.ActionResponse;
import primr.apps.eurakacachet.ryme.ryme.data.model.AudioAd;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import primr.apps.eurakacachet.ryme.ryme.utils.Config;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;


public class PublicTrackDisplayFragmentPresenter extends BasePresenter<PublicTrackFragmentMvpView>{

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public PublicTrackDisplayFragmentPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(PublicTrackFragmentMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void logPlayTrack(Track track){
        checkViewAttached();
        mDataManager.logPlayTrack(track)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ActionResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ActionResponse actionResponse) {
                        if (actionResponse.code == Config.STATUS_OK) {
                            getMvpView().updateStreamsCount();
                        }
                        Log.d("adapter", actionResponse.toString());
                    }
                });
    }

    public void isLiked(Track track) {
        checkViewAttached();
        LikedTrack likedTrack = makeLikedTrack(track);
        mDataManager.isLiked(likedTrack)
                .take(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean isLiked) {
                        Log.d("adapter", "isLiked called with => " + isLiked);
                        getMvpView().isLiked(isLiked);
                    }
                });
    }

    protected void likeTrack(final Track track){
        checkViewAttached();
        final LikedTrack likedTrack = makeLikedTrack(track);
        mDataManager.likeTrack(track.uuid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ActionResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ActionResponse actionResponse) {
                        if (actionResponse.code == Config.STATUS_OK) {
                            saveLike(likedTrack);
                            getMvpView().showSuccessSnack(1);
                        } else {
                            getMvpView().showErrorSnack("error");
                        }
                    }
                });
    }

    private void saveLike(LikedTrack likedTrack) {
        mDataManager.saveLikedTrack(likedTrack)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PutResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(PutResult putResult) {
                        getMvpView().isLiked(true);
                    }
                });
    }

    protected void dislikeTrack(Track track){
        checkViewAttached();
        final LikedTrack likedTrack = makeLikedTrack(track);
        mSubscription = mDataManager.dislikeTrack(track.uuid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ActionResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ActionResponse actionResponse) {
                        if (actionResponse.code == Config.STATUS_OK) {
                            deleteLike(likedTrack);
                            getMvpView().showSuccessSnack(1);
                        }
                    }
                });
    }

    private void deleteLike(LikedTrack likedTrack) {
        mDataManager.deleteLikedTrack(likedTrack)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DeleteResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(DeleteResult deleteResult) {
                        getMvpView().isLiked(false);
                    }
                });
    }

    public void saveTrack(Track track, String localPath) {
        checkViewAttached();
        SavedTrack savedTrack = prepareTrackData(track, localPath);
        mDataManager.saveDownloadedTrack(savedTrack)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PutResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(PutResult putResult) {
                        Timber.d(String.valueOf(putResult.wasInserted()));
                        Log.d("adapter", "track saved ? " + putResult.wasInserted());
                    }
                });
    }

    public SavedTrack prepareTrackData(Track track, String localPath) {
        return SavedTrack.newTrack(null,
                track.uuid(), track.title(), track.artist_name(), localPath,
                track.duration(), null, "mp3");
    }

    public void updateSavedTrackWithCover(final String cover, String uuid){
        checkViewAttached();
        mSubscription = mDataManager.getSavedTrack(uuid)
                .take(1)
                .subscribe(new Action1<SavedTrack>() {
                    @Override
                    public void call(SavedTrack savedTrack) {
                        SavedTrack track = prepareTrackDataWithCover(savedTrack, cover);
                        Log.d("adapter", "before track " + savedTrack.toString());
                        Log.d("adapter", "after track " + track.toString());
                        mSubscription = mDataManager.updateTrackWithCover(track)
                                .subscribe(new Subscriber<PutResult>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        e.printStackTrace();
                                        getMvpView().enableDownloadButton();
                                        getMvpView().showDownloadFailure();
                                    }

                                    @Override
                                    public void onNext(PutResult putResult) {
                                        getMvpView().showDownloadSuccess();
                                        Timber.d(String.valueOf(putResult.wasUpdated()));
                                        Log.d("adapter", "track updated ? " + putResult.wasUpdated());
                                    }
                                });
                    }
                });
    }

    public SavedTrack prepareTrackDataWithCover(SavedTrack savedTrack, String cover) {
        return SavedTrack.newTrack(savedTrack.id(), savedTrack.uuid(),
                savedTrack.title(), savedTrack.artist(), savedTrack.path(),
                savedTrack.duration(), cover, savedTrack.extention());
    }

    private LikedTrack makeLikedTrack(Track track) {
        return LikedTrack.newTrack(null, track.uuid(), track.artist_name());
    }

    public void isTrackAlreadyDownloaded(String uuid) {
        mDataManager.isThisTrackAlreadySaved(uuid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Boolean isSaved) {
                        getMvpView().trackIsAlreadySaved(isSaved);
                    }
                });
    }

    public void logAudioEventStream(AudioAd audioAd) {
        checkViewAttached();
        mSubscription = mDataManager.logEventStreamed(audioAd.uuid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ActionResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ActionResponse actionResponse) {
                        if (actionResponse.code == Config.STATUS_OK){
                            Log.d("audioAd", "success -> " + actionResponse.toString());
                        }else {
                            Log.d("audioAd", "error -> " + actionResponse.toString());
                        }
                    }
                });
    }
}
