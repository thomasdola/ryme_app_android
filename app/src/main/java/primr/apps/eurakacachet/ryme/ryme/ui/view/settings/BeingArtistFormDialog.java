package primr.apps.eurakacachet.ryme.ryme.ui.view.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.AdapterViewItemClickEvent;
import com.jakewharton.rxbinding.widget.RxAutoCompleteTextView;
import com.jakewharton.rxbinding.widget.RxTextView;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.utils.helpers.layout.CustomAutoCompleteTextView;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;


public class BeingArtistFormDialog extends DialogFragment{

    public static final String EXTRA_CATEGORY = "category";
    public static final String EXTRA_STAGE_NAME = "stage_name";
    public static final String ARG_CATEGORIES = "categories";
    EditText mStageNameEditText;
    CustomAutoCompleteTextView mArtistCategory;
    Button mSubmitButton;
    Button mLaterButton;
    private String[] mCategories;
    private ArrayAdapter<String> mCategoriesAdapter;

    public BeingArtistFormDialog(){}

    public static BeingArtistFormDialog newInstance(String[] categories) {
        Bundle args = new Bundle();
        args.putStringArray(ARG_CATEGORIES, categories);
        BeingArtistFormDialog fragment = new BeingArtistFormDialog();
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
        return inflater.inflate(R.layout.preference_being_artist_form_dialog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStageNameEditText = (EditText) view.findViewById(R.id.stage_name_pref);
        mArtistCategory = (CustomAutoCompleteTextView) view.findViewById(R.id.artist_category);
        mLaterButton = (Button) view.findViewById(R.id.later_button);
        mSubmitButton = (Button) view.findViewById(R.id.submit_button);
        mSubmitButton.setEnabled(false);
        getDialog().setTitle(R.string.artist_request_form_title);
        initListeners();
        mCategoriesAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, mCategories);
        mArtistCategory.setAdapter(mCategoriesAdapter);
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();
    }

    private void initListeners() {
        Observable<Boolean> stage_name_observer = RxTextView.textChanges(mStageNameEditText)
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        return charSequence.toString().length() > 5;
                    }
                }).distinctUntilChanged();

        Observable<Boolean> categoryObservable = RxAutoCompleteTextView.itemClickEvents(mArtistCategory)
                .map(new Func1<AdapterViewItemClickEvent, Boolean>() {
                    @Override
                    public Boolean call(AdapterViewItemClickEvent adapterViewItemClickEvent) {
                        return mArtistCategory.isSelectionFromPopUp();
                    }
                }).distinctUntilChanged();

        Observable.combineLatest(stage_name_observer, categoryObservable, new Func2<Boolean, Boolean, Boolean>() {
            @Override
            public Boolean call(Boolean stage_name_isValid, Boolean category_isValid) {
                return stage_name_isValid && category_isValid;
            }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean isValid) {
                mSubmitButton.setEnabled(isValid);
            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("settings", "Agree and submit");
                sendResult(Activity.RESULT_OK, mStageNameEditText.getText().toString().trim(),
                        mArtistCategory.getText().toString().trim());
                getDialog().dismiss();
            }
        });

        mLaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("settings", "Cancel");
                sendResult(Activity.RESULT_CANCELED,
                        null, null);
                getDialog().dismiss();
            }
        });
    }

    private void sendResult(int resultCode , String stageName, String category){
        if(getTargetFragment() == null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_STAGE_NAME, stageName);
        intent.putExtra(EXTRA_CATEGORY, category);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
