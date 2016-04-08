package primr.apps.eurakacachet.ryme.ryme.ui.view.category;

import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.Category;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.category.inApp.StoredCategoriesFragment;


public class CategoryViewHolder extends RecyclerView.ViewHolder implements CategoryViewHolderMvpView {

    public static final String TAG = "adapter";
    private final Fragment mFragment;
    @Inject CategoryViewHolderPresenter mPresenter;

    public boolean mIsFollowed;
    private Category mCategory;
    TextView mCategoryName;
    Button mFollowButton;
    @Nullable
    FloatingActionButton mStartFab;
    @Nullable
    RelativeLayout mStoredCategoriesLayout;

    public CategoryViewHolder(View itemView, Fragment fragment) {
        super(itemView);
        ((BaseActivity) fragment.getActivity()).getActivityComponent().inject(this);

        mFragment = fragment;
        if(fragment instanceof CategoriesFragment){
            mStartFab = (FloatingActionButton) fragment.getView().findViewById(R.id.start_fab);
        }

        if(fragment instanceof StoredCategoriesFragment){
            mStoredCategoriesLayout = (RelativeLayout) fragment.getView().findViewById(R.id.stored_category_layout);
        }

        Log.d(TAG, "CategoryViewHolder called");
        mCategoryName = (TextView) itemView.findViewById(R.id.category_name_text_view);
        mFollowButton = (Button) itemView.findViewById(R.id.follow_category_button);
        mPresenter.attachView(this);
        startWatch();
        mFollowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFollowAction();
            }
        });
    }

    public void startWatch() {
        if(mStartFab != null){
            mPresenter.watching();
        }
    }

    public void toggleFollowAction() {
        Log.d(TAG, "toggleFollowAction called with " + mIsFollowed);
        if(!mIsFollowed){
            mPresenter.follow(mCategory);
        }else {
            if(mFragment instanceof StoredCategoriesFragment){
                mPresenter.attemptUnfollow(mCategory);
            }else {
                mPresenter.unFollow(mCategory);
            }
        }
    }

    public void bindCategory(Category category){
        Log.d(TAG, "bindCategory called");
        mPresenter.isFollowing(category);
        mCategory = category;
        mCategoryName.setText(category.name().toUpperCase());
        mFollowButton.setText(getText());
    }

    private int getText() {
        Log.d(TAG, "getText called with " + mIsFollowed);
        if( !mIsFollowed){
            return R.string.followText;
        }else {
            return R.string.unfollowText;
        }
    }


    @Override
    public void enableFollowButton() {
        Log.d(TAG, "enableFollowButton called");
        mFollowButton.setEnabled(true);
    }

    @Override
    public void disableFollowButton() {
        Log.d(TAG, "disableFollowButton called");
        mFollowButton.setEnabled(false);
    }

    @Override
    public void toggleFollowButton() {
        Log.d(TAG, "toggleFollowButton called with " + mIsFollowed);
        if( ! mIsFollowed ){
            mFollowButton.setText(R.string.followText);
        }else {
            mFollowButton.setText(R.string.unfollowText);
        }
    }

    @Override
    public void beingFollowed(boolean isFollowed) {
        Log.d(TAG, "beingFollowed on currently " + mIsFollowed);
        Log.d(TAG, "beingFollowed called with " + isFollowed);
        mIsFollowed = isFollowed;
        Log.d(TAG, "beingFollowed called change to " + mIsFollowed);
    }

    @Override
    public void showStart() {
        Log.d(TAG, "showStart called");
        if (mStartFab != null) {
            mStartFab.show();
        }
    }

    @Override
    public void hideStart() {
        Log.d(TAG, "hideStart called");
        if (mStartFab != null) {
            mStartFab.hide();
        }
    }

    @Override
    public void showError(String message) {
        if (mStoredCategoriesLayout != null) {
            Snackbar errorSnack = Snackbar.make(mStoredCategoriesLayout, message.toUpperCase()
                    , Snackbar.LENGTH_LONG);
            errorSnack.show();
        }
    }
}
