package primr.apps.eurakacachet.ryme.ryme.ui.view.crop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.lyft.android.scissors.CropView;

import java.io.File;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;
import rx.Observable;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

import static android.graphics.Bitmap.CompressFormat.JPEG;
import static rx.android.schedulers.AndroidSchedulers.mainThread;
import static rx.schedulers.Schedulers.io;

public class CropImageActivity extends BaseActivity implements CropImageMvpView{

    @Inject CropImagePresenter mPresenter;

    CompositeSubscription subscriptions = new CompositeSubscription();
    public static final String EXTRA_CROP_TYPE = "crop_type";
    public static final int REQUEST_CROP_AVATAR = 0;
    public static final int REQUEST_CROP_BACK_IMAGE = 1;
    public static final int REQUEST_CROP_TRACK_COVER = 2;
    //    public static String EXTRA_CURRENT_FILE_PATH = "current_path";
    public static final String EXTRA_CROPPED_FILE_PATH = "cropped_path";
    private static final int PICK_IMAGE_FROM_GALLERY = 10001;
    CropView mCropView;
    FloatingActionButton mMiniPick;
    FloatingActionButton mBigPick;
    FloatingActionButton mCrop;
    ProgressBar mLoadingView;
    private int mCropType;
    private String mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_crop_image);
        mPresenter.attachView(this);
        initViews();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (getIntent() != null) {
            mCropType = getIntent().getIntExtra(EXTRA_CROP_TYPE, 0);
        }

        initListeners();
        toolbar.setTitle(R.string.profile);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        onPickClicked();
    }

    private void initListeners() {
        mMiniPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPickClicked();
            }
        });

        mBigPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPickClicked();
            }
        });

        mCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCropType == REQUEST_CROP_AVATAR) {
                    onCropClicked("avatar");
                } else if (mCropType == REQUEST_CROP_BACK_IMAGE) {
                    onCropClicked("background");
                } else if (mCropType == REQUEST_CROP_TRACK_COVER) {
                    onCropClicked("track_cover");
                }
            }
        });
    }

    private void initViews() {
        mCropView = (CropView) findViewById(R.id.crop_view);
        mMiniPick = (FloatingActionButton) findViewById(R.id.pick_mini_fab);
        mCrop = (FloatingActionButton) findViewById(R.id.crop_fab);
        mBigPick = (FloatingActionButton) findViewById(R.id.pick_fab);
        mLoadingView = (ProgressBar) findViewById(R.id.loading_view);
        mMiniPick.setVisibility(View.INVISIBLE);
        mCrop.setVisibility(View.INVISIBLE);
    }

    public static Intent newIntent(Context context, int cropType) {
        Log.d("crop", "newIntent called -> " + cropType);
        Intent intent = new Intent(context, CropImageActivity.class);
        intent.putExtra(EXTRA_CROP_TYPE, cropType);
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("crop", "onActivityResult called -> " + data.getData());

        if (requestCode == PICK_IMAGE_FROM_GALLERY
                && resultCode == Activity.RESULT_OK) {
            Uri galleryPictureUri = data.getData();

            mCropView.extensions()
                    .load(galleryPictureUri);

            updateButtons();
        }
    }

    private void updateButtons() {
        mBigPick.setVisibility(View.GONE);
        mMiniPick.setVisibility(View.VISIBLE);
        mCrop.setVisibility(View.VISIBLE);
    }

    private void setNewImagePath(File newImagePath) {
        Log.d("crop", "image file -> " + newImagePath.getAbsolutePath());
        if(mCropType == REQUEST_CROP_AVATAR){
            mPresenter.uploadPicture(newImagePath, "avatar");
        }else if(mCropType == REQUEST_CROP_BACK_IMAGE){
            mPresenter.uploadPicture(newImagePath, "background");
        }else if(mCropType == REQUEST_CROP_TRACK_COVER) {
            respondWithCroppedImage(newImagePath);
        }
    }

    public void onPickClicked() {
        mCropView.extensions()
                .pickUsing(this, PICK_IMAGE_FROM_GALLERY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscriptions.unsubscribe();
    }

    public void onCropClicked(String type) {
        final File croppedFile = new File(getCacheDir(), type + "_cropped.jpg");

        Observable<Void> onSave = Observable.from(mCropView.extensions()
                .crop()
                .quality(100)
                .format(JPEG)
                .into(croppedFile))
                .subscribeOn(io())
                .observeOn(mainThread());

        subscriptions.add(onSave
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void nothing) {
                        setNewImagePath(croppedFile);
                        CropImageActivity.this.finish();
                    }
                }));
    }

    @Override
    public void disableButtons() {
        mBigPick.setEnabled(false);
        mMiniPick.setEnabled(false);
        mCrop.setEnabled(false);
    }

    @Override
    public void enableButtons() {
        mBigPick.setEnabled(true);
        mMiniPick.setEnabled(true);
        mCrop.setEnabled(true);
    }

    @Override
    public void showLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mLoadingView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void respondWithCroppedImage(@Nullable File imageFile) {
        Intent data = new Intent();
        if( imageFile != null){
            data.putExtra(EXTRA_CROPPED_FILE_PATH, imageFile.getAbsolutePath());
            if (mCropType == REQUEST_CROP_AVATAR) {
                data.putExtra(EXTRA_CROP_TYPE, REQUEST_CROP_AVATAR);
            } else if (mCropType == REQUEST_CROP_BACK_IMAGE) {
                data.putExtra(EXTRA_CROP_TYPE, REQUEST_CROP_BACK_IMAGE);
            } else if (mCropType == REQUEST_CROP_TRACK_COVER) {
                data.putExtra(EXTRA_CROP_TYPE, REQUEST_CROP_TRACK_COVER);
            }
            setResult(RESULT_OK, data);
        }else {
            setResult(RESULT_CANCELED, data);
        }
    }
}
