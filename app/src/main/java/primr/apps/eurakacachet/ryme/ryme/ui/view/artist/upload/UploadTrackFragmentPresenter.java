package primr.apps.eurakacachet.ryme.ryme.ui.view.artist.upload;

import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.util.HashMap;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import rx.Subscription;


public class UploadTrackFragmentPresenter extends BasePresenter<UploadTrackFragmentMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public UploadTrackFragmentPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(UploadTrackFragmentMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void uploadTrack(HashMap<String, String> payload){
        checkViewAttached();
        HashMap<String , RequestBody> trackPayload = new HashMap<>();
        Log.d("upload", "just call me ");
        RequestBody trackBody = RequestBody.create(MediaType.parse("audio/*"),
                new File(payload.get("path")));
        trackPayload.put("track_file", trackBody);
        RequestBody coverBody = RequestBody.create(MediaType.parse("image/*"),
                new File(payload.get("cover")));
        trackPayload.put("track_cover", coverBody);
        RequestBody titleBody = RequestBody.create(MediaType.parse("text/plain"),
                payload.get("title"));
        trackPayload.put("title", titleBody);
        RequestBody categoryBody = RequestBody.create(MediaType.parse("text/plain"),
                payload.get("category"));
        trackPayload.put("category", categoryBody);
        RequestBody downloadableBody = RequestBody.create(MediaType.parse("text/plain"),
                payload.get("downloadable"));
        trackPayload.put("downloadable", downloadableBody);
        if(payload.containsKey("featured")){
            RequestBody featuredBody = RequestBody.create(MediaType.parse("text/plain"),
                    payload.get("featured"));
            trackPayload.put("featured", featuredBody);
        }
        Log.d("upload", trackPayload.toString());
    }

}
