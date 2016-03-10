package primr.apps.eurakacachet.ryme.ryme.ui.view.comment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Comment;


public class CommentViewHolder extends RecyclerView.ViewHolder {

    CommentListFragment mListFragment;
    Picasso mPicasso;
    public TextView mUsername;
    public RelativeTimeTextView mCommentTime;
    public TextView mCommentText;
    public ImageView userAvatarView;

    public CommentViewHolder(View itemView, CommentListFragment fragment) {
        super(itemView);
        mListFragment = fragment;
        mPicasso = Picasso.with(fragment.getContext());
        mUsername = (TextView) itemView.findViewById(R.id.user_name_text_view);
        mCommentTime = (RelativeTimeTextView) itemView.findViewById(R.id.comment_time_text_view);
        mCommentText = (TextView) itemView.findViewById(R.id.comment_text_text_view);
        userAvatarView = (ImageView) itemView.findViewById(R.id.user_photo_image_view);
    }

    public void bindComment(Comment comment){
        mUsername.setText(comment.username());
        mCommentTime.setReferenceTime(comment.time());
        mCommentText.setText(comment.message());
        mPicasso.load(comment.userAvatar())
                .placeholder(R.drawable.wallpaper)
                .error(R.drawable.wallpaper)
                .into(userAvatarView);
    }
}
