package primr.apps.eurakacachet.ryme.ryme.ui.view.artistRequest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;

import net.grobas.view.PolygonImageView;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.ArtistRequest;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;


public class ArtistRequestViewFragment extends DialogFragment implements ArtistRequestMvpView{

    private static final String ARG_ARTIST_REQUEST = "artist_request";
    private static final String ARG_VIEWER_ID = "viewer_id";
    @Inject ArtistRequestViewPresenter mPresenter;
    private String mViewerId;
    private ArtistRequest mRequest;
    private PolygonImageView mUserAvatar;
    private TextView mUserStage_name;
    private Button mYesButton;
    private Button mNoButton;
    private Picasso mPicasso;
    private Button mLaterButton;
    private RelativeLayout mVouchView;
    private RelativeLayout mInfoView;
    private Button mCloseDialogButton;
    private RelativeTimeTextView mStarted;
    private RelativeTimeTextView mEnding;
    private TextView mYes;
    private TextView mNo;


    public static ArtistRequestViewFragment newInstance(ArtistRequest request, String viewerId) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_ARTIST_REQUEST, request);
        args.putString(ARG_VIEWER_ID, viewerId);
        ArtistRequestViewFragment fragment = new ArtistRequestViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPicasso = Picasso.with(getContext());
        if(getArguments() != null){
            mRequest = getArguments().getParcelable(ARG_ARTIST_REQUEST);
            mViewerId = getArguments().getString(ARG_VIEWER_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.artist_request_fragment_view, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getDialog().setTitle(mRequest.getStage_name());
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        mUserAvatar = (PolygonImageView) view.findViewById(R.id.user_profile_photo_view);
        mPicasso.load(mRequest.getAvatar())
                .placeholder(R.drawable.wallpaper)
                .error(R.drawable.wallpaper)
                .into(mUserAvatar);
        mUserStage_name = (TextView) view.findViewById(R.id.user_stage_name_view);
        mUserStage_name.setText(mRequest.getStage_name());
        mNoButton = (Button) view.findViewById(R.id.no_button);
        mLaterButton = (Button) view.findViewById(R.id.later_button);
        mYesButton = (Button) view.findViewById(R.id.yes_button);

        mYes = (TextView) view.findViewById(R.id.total_yes);
        mYes.setText(Long.toString(mRequest.getYes()));

        mNo = (TextView) view.findViewById(R.id.total_no);
        mNo.setText(Long.toString(mRequest.getNo()));

        mInfoView = (RelativeLayout) view.findViewById(R.id.info_view);
        mVouchView = (RelativeLayout) view.findViewById(R.id.vouch_view);
        mStarted = (RelativeTimeTextView) view.findViewById(R.id.start_date_text_view);
        mStarted.setReferenceTime(mRequest.getStart_date());
        mEnding = (RelativeTimeTextView) view.findViewById(R.id.end_date_text_view);
        mEnding.setReferenceTime(mRequest.getEnd_date());
        mCloseDialogButton = (Button) view.findViewById(R.id.close_dialog_button);
        if(mViewerId.equals(mRequest.getUser_id())){
            switchToInfoView();
        }
        initListeners();
    }

    private void switchToInfoView() {
        mVouchView.setVisibility(View.GONE);
        mInfoView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((BaseActivity)getActivity()).getActivityComponent().inject(this);
        mPresenter.attachView(this);
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();
    }

    private void initListeners() {
        mYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickYes();
            }
        });

        mNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNo();
            }
        });

        mLaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLater();
            }
        });

        mCloseDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLater();
            }
        });
    }

    private void onClickLater() {
        getDialog().dismiss();
    }

    private void onClickNo() {
        mPresenter.noTo(mRequest.getUuid());
    }

    private void onClickYes() {
        mPresenter.yesTo(mRequest.getUuid());
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onVouchDone(boolean success) {
        if(success){
            getDialog().dismiss();
        }else {
            showError();
        }
    }

    private void showError() {

    }
}
