package primr.apps.eurakacachet.ryme.ryme.ui.view.trackDisplay;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.ui.view.artist.profile.ArtistProfileActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.comment.CommentListFragment;


public class TrackDisplayFragment extends Fragment implements PublicTrackDisplayFragmentMvpView{

    @Inject PublicTrackDisplayFragmentPresenter mPublicTrackDisplayFragmentPresenter;

    private static final String ARG_TRACK = "track";

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Bind(R.id.play_song_fab)
    FloatingActionButton playTrackFab;

    @Bind(R.id.ic_action_track_download)
    ImageView downloadTrackButton;

    @Bind(R.id.info_track_stream_text)
    TextView streamsView;

    @Bind(R.id.info_track_favorite_text)
    TextView likesView;

    @Bind(R.id.info_track_download_text)
    TextView downloadsView;

    @Bind(R.id.info_track_comment_list_text)
    TextView commentsView;

    @Bind(R.id.track_view_toolbar)
    Toolbar toolbar;

//    private CommentListAdapter mCommentListAdapter;
    private Bitmap mOldBitmap;

    @Bind(R.id.header)
    ImageView mHeader;

    private String mTrackTitle;
    private Track mTrack;


    public static TrackDisplayFragment newInstance(Track track) {
        TrackDisplayFragment fragment = new TrackDisplayFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_TRACK, track);
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
            mTrack = getArguments().getParcelable(ARG_TRACK);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_track, container, false);
        ButterKnife.bind(getActivity(), rootView);

        initBlurCollapsingImageHeader();
        initCollapsingToolbarLayout();

        playTrackFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
//        mHeader.setColorFilter(R.color.imageBlurFilter, PorterDuff.Mode.SRC_OVER);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager = getChildFragmentManager();
        Fragment commentListFragment = fragmentManager.findFragmentById(R.id.track_comments_fragment_container);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if ( commentListFragment == null ){
            commentListFragment = CommentListFragment.newInstance(mTrack);
            fragmentTransaction.add(R.id.track_comments_fragment_container, commentListFragment)
                    .commit();
        }


        return rootView;
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
            case R.id.favorite_track_action:
                //call toggleLikeTrack()
//                mPublicTrackDisplayFragmentPresenter.toggleLike(mTrack);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void initCollapsingToolbarLayout() {
        mCollapsingToolbarLayout.setTitle("Track Title");
        mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorPrimary));
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.transparent));
    }

    private void initBlurCollapsingImageHeader() {
        mOldBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.stonebwoy1);
//        Bitmap blurredBitmap = BlurBuilder.blur(getActivity(), mOldBitmap);
        mHeader.setImageBitmap(mOldBitmap);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void togglePayButton() {

    }

    @Override
    public void toggleLikeButton() {

    }

    @Override
    public void disableDownloadButton() {

    }

    @Override
    public void enableDownloadButton() {

    }
}
