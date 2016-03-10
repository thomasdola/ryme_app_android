package primr.apps.eurakacachet.ryme.ryme.ui.view.crop;

import android.support.annotation.Nullable;

import java.io.File;

import primr.apps.eurakacachet.ryme.ryme.ui.base.MvpView;


public interface CropImageMvpView extends MvpView {

    void disableButtons();

    void enableButtons();

    void showLoading();

    void hideLoading();

    void respondWithCroppedImage(@Nullable File imageFile);
}
