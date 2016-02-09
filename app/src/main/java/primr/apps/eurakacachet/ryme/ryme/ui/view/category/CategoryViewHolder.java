package primr.apps.eurakacachet.ryme.ryme.ui.view.category;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Category;


public class CategoryViewHolder extends RecyclerView.ViewHolder implements CategoryViewHolderMvpView {

    @Inject CategoryViewHolderPresenter mCategoryViewHolderPresenter;
    private Category mCategory;
    TextView mCategoryName;
    Button mFollowButton;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        mCategoryName = (TextView) itemView.findViewById(R.id.category_name_text_view);
        mFollowButton = (Button) itemView.findViewById(R.id.follow_category_button);
        mFollowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFollow();
            }
        });
    }

    public void bindCategory(Category category){
        mCategory = category;
        mCategoryName.setText(category.name.toUpperCase());
        mFollowButton.setText(getText());
    }

    private String getText() {
        if( ! mCategoryViewHolderPresenter.isFollowing(mCategory) ){
            return "follow";
        }else{
            return "unfollow";
        }
    }

    private void toggleFollow() {
        if( ! mCategoryViewHolderPresenter.isFollowing(mCategory) ){
            mCategoryViewHolderPresenter.follow(mCategory);
        }else {
            mCategoryViewHolderPresenter.unFollow(mCategory);
        }
    }

    @Override
    public void enableFollowButton() {
        mFollowButton.setEnabled(true);
    }

    @Override
    public void disableFollowButton() {
        mFollowButton.setEnabled(false);
    }

    @Override
    public void toggleFollowButton() {
        if( ! mCategoryViewHolderPresenter.isFollowing(mCategory) ){
            mFollowButton.setText("follow");
        }else {
            mFollowButton.setText("unfollow");
        }
    }
}
