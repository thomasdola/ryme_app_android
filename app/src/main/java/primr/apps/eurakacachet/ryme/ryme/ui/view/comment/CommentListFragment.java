package primr.apps.eurakacachet.ryme.ryme.ui.view.comment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Comment;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentListFragment extends Fragment implements CommentListMvpView{

    @Inject CommentListPresenter mCommentListPresenter;
    @Inject CommentListAdapter mAdapter;
    @Inject Bus mBus;

    private static final String ARG_TRACK = "track";

    RecyclerView mRecyclerView;
    private Track mTrack;

    public static CommentListFragment newInstance(Track track) {
        CommentListFragment fragment = new CommentListFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_TRACK, track);
        fragment.setArguments(args);
        return fragment;
    }

    public CommentListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTrack = getArguments().getParcelable(ARG_TRACK);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((BaseActivity)getActivity()).getActivityComponent().inject(this);
        mCommentListPresenter.attachView(this);
        mCommentListPresenter.loadComments(mTrack.uuid());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_comment_list, container, false);
        initViews(rootView);
        return rootView;
    }

    public void initViews(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.comments_list_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setCommentList(List<Comment> commentList) {
        mAdapter.setComments(commentList, this);
    }

    @Override
    public void showNoCommentsYet() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void onPause() {
        super.onPause();
        mBus.unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mBus.register(this);
    }

    @Subscribe
    public void updateAdapter(Comment comment){
        int curSize = mAdapter.getItemCount();
        mAdapter.insertComment(curSize, comment);
        mRecyclerView.scrollToPosition(curSize - 1);
    }

}
