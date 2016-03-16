package primr.apps.eurakacachet.ryme.ryme.ui.view.artist.upload;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.AdapterViewItemClickEvent;
import com.jakewharton.rxbinding.widget.RxAutoCompleteTextView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;
import primr.apps.eurakacachet.ryme.ryme.utils.helpers.layout.CustomAutoCompleteTextView;
import primr.apps.eurakacachet.ryme.ryme.utils.helpers.time.DateFormatter;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func5;


public class UploadTrackFragment extends DialogFragment implements UploadTrackFragmentMvpView {

    @Inject UploadTrackFragmentPresenter mPresenter;
    public static final int PICK_TRACK_REQUEST_CODE = 5555;
    public static final int PICK_COVER_REQUEST_CODE = 6666;
    final static String DATE_FORMAT = "dd-MM-yyyy";
    public static final String ARG_CATEGORIES = "categories";
    private static final String DIALOG_DATE = "dialog_date";
    private static final int PICK_DATE_REQUEST_CODE = 15;
    EditText mTrackTitle;
    SwitchCompat mFeatureSwitch;
    EditText mFeatureArtists;
    ImageView mUploadCover;
    SwitchCompat mDownloadableSwitch;
    ImageButton mUploadTrack;
    Button mUploadButton;
    Button mPickDateButton;
    Button mCancelButton;
    String mCoverPath;
    String mTrackPath;
    TextView mTrackTitleDisplay;
    TextView mCoverTextDisplay;
    TextView mReleasedDateText;
    CustomAutoCompleteTextView mCategoriesAutoComplete;
    private ProgressDialog mProgressDialog;
    Picasso mPicasso;
    private AutoCompleteTextView mTrackGenre;
    private String[] mCategories;
    private ArrayAdapter<String> mCategoriesAdapter;

    public UploadTrackFragment(){}

    public static UploadTrackFragment newInstance(String[] categories) {
        Bundle args = new Bundle();
        args.putStringArray(ARG_CATEGORIES, categories);
        UploadTrackFragment fragment = new UploadTrackFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mCategories = getArguments().getStringArray(ARG_CATEGORIES);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.upload_track_dialog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getDialog().setTitle("Upload New Track");
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        mTrackTitle = (EditText) view.findViewById(R.id.new_track_title_edit);
        mFeatureSwitch = (SwitchCompat) view.findViewById(R.id.featuring_switch);
        mFeatureArtists = (EditText) view.findViewById(R.id.featuring_artists);
        mFeatureArtists.setVisibility(View.GONE);
        mUploadCover = (ImageView) view.findViewById(R.id.new_track_cover_image);
        mDownloadableSwitch = (SwitchCompat) view.findViewById(R.id.downloadable_switch);
        mDownloadableSwitch.setChecked(true);
        mUploadTrack = (ImageButton) view.findViewById(R.id.new_track_upload);
        mUploadButton = (Button) view.findViewById(R.id.confirm_upload_button);
        mPickDateButton = (Button) view.findViewById(R.id.pick_date_button);
//        mUploadButton.setEnabled(false);
        mCancelButton = (Button) view.findViewById(R.id.cancel_upload_button);
        mTrackTitleDisplay = (TextView) view.findViewById(R.id.pick_new_track_file_text);
        mCoverTextDisplay = (TextView) view.findViewById(R.id.new_track_cover_text_view);
        mReleasedDateText = (TextView) view.findViewById(R.id.pick_released_date_text);
        mCategoriesAutoComplete = (CustomAutoCompleteTextView) view.findViewById(R.id.track_genre);
        mCategoriesAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, mCategories);
        mCategoriesAutoComplete.setAdapter(mCategoriesAdapter);
        initListeners();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((BaseActivity)getActivity()).getActivityComponent().inject(this);
        mPicasso = Picasso.with(getContext());
        mPresenter.attachView(this);
    }

    private void initListeners() {

        getDialog().setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                onUploadCancelled();
            }
        });

        mUploadTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPickTrack();
            }
        });

        mUploadCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPickCover();
            }
        });

        mUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUploadTrack();
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUploadCancelled();
            }
        });

        mPickDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getChildFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(new Date());
                dialog.setTargetFragment(UploadTrackFragment.this, PICK_DATE_REQUEST_CODE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mFeatureSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mFeatureArtists.setVisibility(View.VISIBLE);
                }else {
                    mFeatureArtists.setVisibility(View.GONE);
                }
            }
        });

        Observable<Boolean> mFeaturedArtistsObservable = isFeaturedArtistValid()
                .distinctUntilChanged();

        Observable<Boolean> categoryAutoObservable = RxAutoCompleteTextView.itemClickEvents(mCategoriesAutoComplete)
                .map(new Func1<AdapterViewItemClickEvent, Boolean>() {
                    @Override
                    public Boolean call(AdapterViewItemClickEvent adapterViewItemClickEvent) {
                        return mCategoriesAutoComplete.isSelectionFromPopUp();
                    }
                }).distinctUntilChanged();

        Observable<Boolean> releasedDateObservable = RxTextView.textChanges(mReleasedDateText)
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        try {
                            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
                            df.setLenient(false);
                            df.parse(charSequence.toString());
                            return true;
                        } catch (ParseException e) {
                            return false;
                        }
                    }
                });

        Observable<Boolean> trackTitleObserver = RxTextView.textChanges(mTrackTitle)
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        return charSequence.toString().trim().length() > 1;
                    }
                }).distinctUntilChanged();
        Observable<Boolean> trackCoverObserver = RxTextView.textChanges(mCoverTextDisplay)
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        return charSequence.toString().trim().length() > 0;
                    }
                }).distinctUntilChanged();
        Observable<Boolean> trackPathObserver = RxTextView.textChanges(mTrackTitleDisplay)
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        return charSequence.toString().trim().length() > 0;
                    }
                }).distinctUntilChanged();

        Observable.combineLatest(
                categoryAutoObservable,
                trackCoverObserver,
                trackPathObserver,
                trackTitleObserver,
                releasedDateObservable,
                new Func5<Boolean, Boolean, Boolean, Boolean, Boolean, Boolean>() {
                    @Override
                    public Boolean call(Boolean categoryIsValid,
                                        Boolean coverIsValid,
                                        Boolean trackPathIsValid,
                                        Boolean releasedDateIsValid,
                                        Boolean titleIsValid) {
                        return categoryIsValid
                                && coverIsValid
                                && trackPathIsValid
                                && releasedDateIsValid
                                && titleIsValid;
                    }
                }
        ).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean isValid) {
//                mUploadButton.setEnabled(isValid);
            }
        });
    }

    private void onPickCover() {
//        Intent intent = CropImageActivity.newIntent(getContext(), CropImageActivity.REQUEST_CROP_TRACK_COVER);
//        startActivityForResult(intent, PICK_COVER_REQUEST_CODE);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Cover "), PICK_COVER_REQUEST_CODE);
    }

    private void onPickTrack() {
        Intent pickTrackIntent = new Intent();
        pickTrackIntent.setType("audio/*");
        pickTrackIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pickTrackIntent, PICK_TRACK_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == PICK_TRACK_REQUEST_CODE){
                Uri uri = data.getData();
                mTrackPath = uri.getPath();
                File file = new File(mTrackPath);
                mTrackTitleDisplay.setText(file.getName());
                Log.d("upload", "track file exist ? -> " + file.exists() + " => " + mTrackPath);
                Log.d("upload", "track file uri -> " + uri.toString());
            }else if(requestCode == PICK_COVER_REQUEST_CODE){
//                String path = data.getStringExtra(CropImageActivity.EXTRA_CROPPED_FILE_PATH);
//                Log.d("upload", path);
                Uri uri = data.getData();
                Log.d("upload", "cover uri -> " + uri.toString());
                mCoverPath = uri.getPath();
                mCoverTextDisplay.setText(uri.getEncodedPath());
                mPicasso.load(new File(mCoverPath))
                        .into(mUploadCover);
            }else if(requestCode == PICK_DATE_REQUEST_CODE){
                Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
                mReleasedDateText.setText(DateFormatter.formatDate(date));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void onUploadTrack() {
        HashMap<String, String> payload = new HashMap<>();
        payload.put("title", mTrackTitle.getText().toString().trim());
        payload.put("cover", mCoverPath);
        payload.put("path", mTrackPath);
        payload.put("date", mReleasedDateText.getText().toString().trim());
        payload.put("downloadable", String.valueOf(mDownloadableSwitch.isChecked()));
        payload.put("category", mCategoriesAutoComplete.getText().toString().trim());
        if(mFeatureSwitch.isChecked()){
            payload.put("featured", mFeatureArtists.getText().toString().trim());
        }
        mPresenter.uploadTrack(payload);
    }

    private void onUploadCancelled() {
        new AlertDialog.Builder(getContext())
                .setTitle("Are You Sure?")
                .setMessage("No Data will be saved.")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        getDialog().dismiss();
                    }
                }).create().show();
    }

    Observable<Boolean> isFeaturedArtistValid(){
        boolean isValid = false;
        if(mFeatureSwitch.isChecked() && mFeatureArtists.getText()
                .toString().trim().length()>0){
            isValid = true;
        }else if(mFeatureSwitch.isChecked() && mFeatureArtists.getText()
                .toString().trim().length()<0){
            isValid = false;
        }else if(!mFeatureSwitch.isChecked()){
            isValid = true;
        }
        return Observable.just(isValid);
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();
    }

    @Override
    public void showLoading() {
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage(getString(R.string.uploading_track_text));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    @Override
    public void hideLoading(){
        if(mProgressDialog != null){
            if(mProgressDialog.isShowing()){
                mProgressDialog.dismiss();
            }
            mProgressDialog = null;
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(), "Network Error. Please Try Again", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccess() {

    }
}
