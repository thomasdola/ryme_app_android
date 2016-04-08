package primr.apps.eurakacachet.ryme.ryme.ui.view.settings;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.util.Log;
import android.widget.Toast;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;


public class SettingsFragment extends PreferenceFragmentCompat implements SettingsFragmentMvpView,
        SharedPreferences.OnSharedPreferenceChangeListener{

    public static final int ARTIST_REQUEST_FORM_REQUEST_CODE = 2005;
    public static final int NO_TOKEN_ERROR = 2;
    public static final int ON_FAILURE = 3;
    @Inject SettingsFragmentPresenter mPresenter;

    SwitchPreferenceCompat mIsArtistSwitch;
    EditTextPreference mUsernamePreference;
    EditTextPreference mStageNamePreference;
    boolean mIsAllowed;
    boolean mIsArtist;
    private String[] mCategories;
    private ProgressDialog mProgressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static SettingsFragment newInstance() {
        Bundle args = new Bundle();
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((BaseActivity)getActivity()).getActivityComponent().inject(this);
        mPresenter.attachView(this);
        mPresenter.loadCategoriesArray();
        init();
        setPreferenceDefaultValue();
    }

    private void setPreferenceDefaultValue() {
        mPresenter.getUsernameDefaultValue();
        if(mIsArtist){
            mPresenter.getArtistStageName();
        }
    }

    private void initPreferences() {
        mIsArtistSwitch = (SwitchPreferenceCompat) findPreference("is_artist");
        mUsernamePreference = (EditTextPreference) findPreference("username");
        mStageNamePreference = (EditTextPreference) findPreference("stage_name");
    }

    private void initPreferenceValue() {
        Log.d("settings", "initPreferences called with => mIsAllowed being " + mIsAllowed);
        Log.d("settings", "initPreferences called with => mIsArtist being "+mIsArtist);
        mIsArtistSwitch.setEnabled(mIsAllowed);
        mIsArtistSwitch.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Log.d("settings", "i was clicked");
                onBeingArtistClicked();
                return true;
            }
        });
        if(!mIsArtist){
            mStageNamePreference.setVisible(false);
        }
    }

    private void onBeingArtistClicked() {
        if(mIsArtistSwitch.isChecked()){
            showForm();
        }
    }

    private void showForm() {
        FragmentManager fm = getFragmentManager();
        BeingArtistFormDialog formDialog = BeingArtistFormDialog.newInstance(mCategories);
        formDialog.setTargetFragment(this, ARTIST_REQUEST_FORM_REQUEST_CODE);
        formDialog.show(fm, "being_musician_request_form");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ARTIST_REQUEST_FORM_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                String stage_name = data.getStringExtra(BeingArtistFormDialog.EXTRA_STAGE_NAME);
                String category = data.getStringExtra(BeingArtistFormDialog.EXTRA_CATEGORY);
                mPresenter.sendRequest(stage_name, category);
                Log.d("settings", "stage name => " + stage_name);
                Log.d("settings", "artist category => " + category);
            }else if(resultCode == Activity.RESULT_CANCELED) {
                mIsArtistSwitch.setChecked(false);
            }
        }
    }

    private void init() {
        mPresenter.getArtistStageName();
        mPresenter.isArtist();
        mPresenter.isAllowedToMakeRequest();
        initPreferenceValue();
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
        initPreferences();
    }

    @Override
    public void setIsArtist(Boolean isArtist) {
        mIsArtist = isArtist;
        mIsArtistSwitch.setChecked(isArtist);
    }

    @Override
    public void setStageName(String stageName) {
        mStageNamePreference.setSummary(stageName);
    }

    @Override
    public void setUsername(String username) {
        mUsernamePreference.setSummary(username);
    }

    @Override
    public void isAllowed(Boolean isAllowed) {
        mIsAllowed = isAllowed;
    }

    @Override
    public void hideArtistRequest() {
        mIsArtistSwitch.setVisible(false);
    }

    @Override
    public void showDialogLoading() {
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage(getString(R.string.please_wait_text));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    @Override
    public void hideDialogLoading() {
        if(mProgressDialog != null){
            if(mProgressDialog.isShowing()){
                mProgressDialog.dismiss();
            }
            mProgressDialog = null;
        }
    }

    @Override
    public void setCategories(String[] categories) {
        mCategories = categories;
    }

    @Override
    public void showError(int code) {
        String message = getErrorMessage(code);
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessDialog() {
        FragmentManager fm = getFragmentManager();
        RequestSuccessDialog formDialog = RequestSuccessDialog.newInstance();
        formDialog.show(fm, "being_musician_request_successful");
    }

    private String getErrorMessage(int code) {
        String message = null;
        switch (code){
            case NO_TOKEN_ERROR:
                message = getString(R.string.no_token_available_text);
                break;
            case ON_FAILURE:
                message = getString(R.string.please_try_again_text);
                break;
        }
        return message;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        updateSharedPreferences(key);
    }

    private void updateSharedPreferences(String key) {
        if(key.equals(mUsernamePreference.getKey())){
            onUsernameChanged();
        }else if(key.equals(mStageNamePreference.getKey())){
            onStageNameChanged();
        }
    }

    private void onBeingArtistChanged() {
        mIsArtistSwitch.setChecked(mIsArtistSwitch.isChecked());
        mPresenter.updateIsArtist(mIsArtistSwitch.isChecked());
    }

    private void onStageNameChanged() {
        mPresenter.updateStageName(mStageNamePreference.getText());
        mStageNamePreference.setSummary(mStageNamePreference.getText());
    }

    private void onUsernameChanged() {
        mPresenter.setUpdateUsername(mUsernamePreference.getText());
        mUsernamePreference.setSummary(mUsernamePreference.getText());
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

}
