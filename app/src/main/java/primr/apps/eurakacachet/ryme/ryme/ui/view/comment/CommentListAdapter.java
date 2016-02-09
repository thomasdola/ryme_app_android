package primr.apps.eurakacachet.ryme.ryme.ui.view.comment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Comment;


public class CommentListAdapter extends RecyclerView.Adapter<CommentViewHolder> {

    private CommentListFragment mCommentListFragment;
    private List<Comment> mComments;

    public CommentListAdapter(CommentListFragment commentListFragment, List<Comment> comments) {
        mCommentListFragment = commentListFragment;
        mComments = comments;
    }


    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mCommentListFragment.getActivity());
        View mCommentView = layoutInflater.inflate(R.layout.comment_view, parent, false);
        return new CommentViewHolder(mCommentView);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        holder.mUsername.setText(mComments.get(position).username);
        holder.mCommentTime.setText((CharSequence) mComments.get(position).time);
        holder.mCommentText.setText(mComments.get(position).body);
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }
}
