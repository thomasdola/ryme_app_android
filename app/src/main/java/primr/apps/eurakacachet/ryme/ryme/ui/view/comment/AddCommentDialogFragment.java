package primr.apps.eurakacachet.ryme.ryme.ui.view.comment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;


public class AddCommentDialogFragment extends DialogFragment implements AddCommentMvpView{

    @Inject AddCommentPresenter mPresenter;
    private static final String ARG_TRACK_ID = "track_id";
    private EditText commentEdit;
    private Button postComment;
    private Button cancelComment;
    private String mTrackId;
    private ProgressDialog mProgressDialog;

    public static AddCommentDialogFragment newInstance(String trackId) {
        Bundle args = new Bundle();
        args.putString(ARG_TRACK_ID, trackId);
        AddCommentDialogFragment fragment = new AddCommentDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mTrackId = getArguments().getString(ARG_TRACK_ID);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((BaseActivity)getActivity()).getActivityComponent().inject(this);
        mPresenter.attachView(this);
        initListeners();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_comment_layout, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
//        getDialog().setTitle(R.string.comment_text);
        commentEdit = (EditText) view.findViewById(R.id.comment_edit_text_view);
        postComment = (Button) view.findViewById(R.id.post_comment_button);
        cancelComment = (Button) view.findViewById(R.id.close_dialog_button);
    }

    public void initListeners(){
        Observable<Boolean> commentEditObservable = RxTextView.textChanges(commentEdit)
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence message) {
                        return message.toString().trim().length() >= 1;
                    }
                }).distinctUntilChanged();

        commentEditObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean isValid) {
                        postComment.setEnabled(isValid);
                    }
                });

        postComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = commentEdit.getText().toString().trim();
                postCommentToServer(message);
            }
        });

        cancelComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
    }

    private void postCommentToServer(String message) {
        mPresenter.comment(mTrackId, message);
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
        mProgressDialog.setMessage(getString(R.string.please_wait_text));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    @Override
    public void dismissDialog() {
        getDialog().dismiss();
    }

    @Override
    public void disableButtons() {
        postComment.setEnabled(false);
        cancelComment.setEnabled(false);
    }

    @Override
    public void enableButtons() {
        postComment.setEnabled(true);
        cancelComment.setEnabled(true);
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(),
                "Network Error. Please Try Again.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        if(mProgressDialog != null){
            if(mProgressDialog.isShowing()){
                mProgressDialog.dismiss();
            }
            mProgressDialog = null;
        }
    }
}
