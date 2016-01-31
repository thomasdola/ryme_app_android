package primr.apps.eurakacachet.ryme.ryme.ui.view.comment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import primr.apps.eurakacachet.ryme.ryme.R;


public class CommentViewHolder extends RecyclerView.ViewHolder {

    public TextView mUsername;
    public TextView mCommentTime;
    public TextView mCommentText;

    public CommentViewHolder(View itemView) {
        super(itemView);
        mUsername = (TextView) itemView.findViewById(R.id.user_name_text_view);
        mCommentTime = (TextView) itemView.findViewById(R.id.comment_time_text_view);
        mCommentText = (TextView) itemView.findViewById(R.id.comment_text_text_view);

    }
}
