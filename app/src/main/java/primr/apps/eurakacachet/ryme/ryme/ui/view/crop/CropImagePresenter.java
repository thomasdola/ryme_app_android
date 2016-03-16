package primr.apps.eurakacachet.ryme.ryme.ui.view.crop;

import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.File;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.data.DataManager;
import primr.apps.eurakacachet.ryme.ryme.data.model.ActionResponse;
import primr.apps.eurakacachet.ryme.ryme.data.model.EventImage;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BasePresenter;
import primr.apps.eurakacachet.ryme.ryme.utils.Config;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class CropImagePresenter extends BasePresenter<CropImageMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public CropImagePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(CropImageMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void uploadPicture(final File newImagePath, final String type) {
        checkViewAttached();
        Log.d("crop", "uploadPicture called -> " + type);
        getMvpView().showLoading();
        getMvpView().disableButtons();
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), newImagePath);
        RequestBody imageTypeBody = RequestBody.create(MediaType.parse("text/plain"), type);
        mSubscription = mDataManager.uploadAvatarImage(fileBody, imageTypeBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ActionResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getMvpView().respondWithCroppedImage(null);
                    }

                    @Override
                    public void onNext(ActionResponse actionResponse) {
                        Log.d("upload", actionResponse.message);
                        if (actionResponse.code == Config.STATUS_OK) {
                            mDataManager.setAvatarPath(newImagePath.getAbsolutePath());
                            getMvpView().respondWithCroppedImage(newImagePath);
                            EventImage image = EventImage.newImage(newImagePath.getAbsolutePath(), type);
                            mDataManager.doneSavingImage(image);
                        } else {
                            getMvpView().respondWithCroppedImage(null);
                        }
                    }
                });
    }
}
