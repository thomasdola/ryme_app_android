package primr.apps.eurakacachet.ryme.ryme.ui.view.comment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Comment;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentListFragment extends Fragment implements CommentListMvpView{

    @Inject CommentListPresenter mCommentListPresenter;

    private static final String ARG_TRACK = "track";

    RecyclerView mRecyclerView;
    EditText commentEdit;
    ImageView addCommentButton;

    private Track mTrack;
    private List<Comment> mCommentList;
    CommentListAdapter mCommentListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mTrack = getArguments().getParcelable(ARG_TRACK);
        }


    }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_comment_list, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.comments_list_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<Comment> commentList = new ArrayList<>();
        mCommentListAdapter = new CommentListAdapter(this, commentList);
        mRecyclerView.setAdapter(mCommentListAdapter);

        commentEdit = (EditText) rootView.findViewById(R.id.comment_edit_text_view);
        addCommentButton = (ImageView) rootView.findViewById(R.id.action_send);

        return rootView;
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void disableSendCommentButton() {

    }

    @Override
    public void enableSendCommentButton() {

    }
}
