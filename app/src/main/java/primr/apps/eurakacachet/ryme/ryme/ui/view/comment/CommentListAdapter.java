package primr.apps.eurakacachet.ryme.ryme.ui.view.comment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Comment;


public class CommentListAdapter extends RecyclerView.Adapter<CommentViewHolder> {

    private CommentListFragment mTrackDisplayFragment;
    private List<Comment> mComments;

    @Inject
    public CommentListAdapter() {
        mComments = new ArrayList<>();
    }

    public void setComments(List<Comment> comments, CommentListFragment commentListFragment) {
        mTrackDisplayFragment = commentListFragment;
        mComments = comments;
        notifyDataSetChanged();
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mTrackDisplayFragment.getActivity());
        View mCommentView = layoutInflater.inflate(R.layout.comment_view, parent, false);
        return new CommentViewHolder(mCommentView, mTrackDisplayFragment);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        Comment comment = mComments.get(position);
        holder.bindComment(comment);
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    public void insertComment(int curSize, Comment comment) {
        mComments.add(curSize, comment);
        notifyItemInserted(curSize);
    }
}
