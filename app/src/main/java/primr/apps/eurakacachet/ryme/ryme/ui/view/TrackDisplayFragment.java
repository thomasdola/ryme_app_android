package primr.apps.eurakacachet.ryme.ryme.ui.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Comment;


public class TrackDisplayFragment extends Fragment {

    private static final String ARG_TRACK_TITLE = "trackTitle";
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    private FloatingActionButton favoriteSongFab;
    private TrackDisplayFragment.CommentListAdapter mCommentListAdapter;
    private Bitmap mOldBitmap;
    private ImageView mHeader;
    private RecyclerView mCommentListViewRecycler;
    private String mTrackTitle;

//    private OnFragmentInteractionListener mListener;


    public static TrackDisplayFragment newInstance() {
        TrackDisplayFragment fragment = new TrackDisplayFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_TRACK_TITLE, trackTitle);
//        args.putSerializable(ARG_TRACK_ID, trackId);
        fragment.setArguments(args);
        return fragment;
    }

    public TrackDisplayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();

        if (getArguments() != null) {
//             mTrackId = (UUID) getArguments().getSerializable(ARG_TRACK_ID);
            mTrackTitle = getArguments().getString(ARG_TRACK_TITLE);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_track, container, false);
        initBlurCollapsingImageHeader(rootView);
//        mHeader.setColorFilter(R.color.imageBlurFilter, PorterDuff.Mode.SRC_OVER);
        initCommentRecyclerView(rootView);
        initCommentListAdapter();
        initCollapsingToolbarLayout(rootView);
        initFloatingActionButton(rootView);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.track_view_toolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return rootView;
    }

    private void initFloatingActionButton(View rootView) {
        favoriteSongFab = (FloatingActionButton) rootView.findViewById(R.id.like_song_fab);
        favoriteSongFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
        menuInflater.inflate(R.menu.menu_track, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu){
        menu.findItem(R.id.view_artist_profile).setTitle("StoneBowy" + " profile");
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        int menuItemId = menuItem.getItemId();
        switch (menuItemId){
            case R.id.view_artist_profile:
                Intent intent = ArtistProfileActivity.newIntent(getActivity());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void initCollapsingToolbarLayout(View rootView) {
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
        mCollapsingToolbarLayout.setTitle("Track Title");
        mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorPrimary));
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.transparent));
    }

    private void initCommentListAdapter() {
        mCommentListAdapter = new CommentListAdapter(Comment.getComments());
        mCommentListViewRecycler.setAdapter(mCommentListAdapter);
    }

    private void initCommentRecyclerView(View rootView) {
        mCommentListViewRecycler = (RecyclerView) rootView.findViewById(R.id.comments_list_recycler);
        mCommentListViewRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initBlurCollapsingImageHeader(View rootView) {
        mHeader = (ImageView) rootView.findViewById(R.id.header);
        mOldBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.stonebwoy1);
//        Bitmap blurredBitmap = BlurBuilder.blur(getActivity(), mOldBitmap);
        mHeader.setImageBitmap(mOldBitmap);
    }


    private class CommentViewHolder extends RecyclerView.ViewHolder{

        public TextView mUsername;
        public TextView mCommentTime;
        public TextView mCommentText;

        public CommentViewHolder(View itemView){
            super(itemView);
            mUsername = (TextView) itemView.findViewById(R.id.user_name_text_view);
            mCommentTime = (TextView) itemView.findViewById(R.id.comment_time_text_view);
            mCommentText = (TextView) itemView.findViewById(R.id.comment_text_text_view);

        }
    }

    private class CommentListAdapter extends RecyclerView.Adapter<TrackDisplayFragment.CommentViewHolder>{

        private List<Comment> mComments;

        public CommentListAdapter(List<Comment> comments){
            mComments = comments;
        }


        @Override
        public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View mCommentView = layoutInflater.inflate(R.layout.comment_card_view, parent, false);
            return new CommentViewHolder(mCommentView);
        }

        @Override
        public void onBindViewHolder(CommentViewHolder holder, int position) {
            holder.mUsername.setText(mComments.get(position).getUserName());
            holder.mCommentTime.setText(mComments.get(position).getCommentTime());
            holder.mCommentText.setText(mComments.get(position).getCommentText());
        }

        @Override
        public int getItemCount() {
            return mComments.size();
        }
    }

}
