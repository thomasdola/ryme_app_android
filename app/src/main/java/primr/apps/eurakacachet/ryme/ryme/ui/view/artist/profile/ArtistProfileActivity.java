package primr.apps.eurakacachet.ryme.ryme.ui.view.artist.profile;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Artist;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.artist.allTrack.ArtistAllTracksFragment;
import primr.apps.eurakacachet.ryme.ryme.ui.view.artist.newTrack.ArtistNewTracksFragment;
import primr.apps.eurakacachet.ryme.ryme.ui.view.artist.upload.UploadTrackFragment;
import primr.apps.eurakacachet.ryme.ryme.ui.view.main.MainActivity;
import primr.apps.eurakacachet.ryme.ryme.utils.helpers.number.NumberFormatter;

public class ArtistProfileActivity extends BaseActivity implements ArtistProfileMvpView{

    private static final String UPLOAD_TRACK_TAG = "upload_track";
    @Inject ArtistProfilePresenter mPresenter;

    public static final String EXTRA_ARTIST_ID = "artistId";
    public Artist mArtist;
    public static TabLayout sTabLayout;
    public static ViewPager sViewPager;
    private boolean isArtistBeingFollowed;
    private ImageView mHeader;
    private ImageView mArtistProfileView;
    private FloatingActionButton mUploadTrackFab;
    private TextView mArtistNameView;
    private TextView mFollowersView;
    private CoordinatorLayout mArtistProfileLayout;
    public static String POSITION = "POSITION";
    public static int sItemCount = 2;
    protected String mArtistId;
    private Toolbar mToolbar;
    private Button mFollowArtistButton;
    Picasso mPicasso;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private String[] mCategories;


    public static Intent newIntent(Context packageContext, String artistId){
        Intent intent = new Intent(packageContext, ArtistProfileActivity.class);
        intent.putExtra(EXTRA_ARTIST_ID, artistId);
        return intent;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        retrieveExtras();
        setContentView(R.layout.activity_artist_profile);
        mPicasso = Picasso.with(this);
        mPresenter.attachView(this);
        mPresenter.loadArtist(mArtistId);
        mPresenter.loadCategoriesArray();
        initViews();
        initCollapsingToolbarLayout();
//        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void retrieveExtras() {
        if( getIntent() != null ){
            mArtistId = getIntent().getStringExtra(EXTRA_ARTIST_ID);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, sTabLayout.getSelectedTabPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        sViewPager.setCurrentItem(savedInstanceState.getInt(POSITION));
    }

    private void initListeners() {
        Log.d("artist", "initListeners is called");

        mFollowArtistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mArtist != null){
                    toggleFollowButton();
                }else {
                    Log.d("adapter", "artist is null");
                }
            }
        });
        mUploadTrackFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                UploadTrackFragment uploadTrackFragment = UploadTrackFragment.newInstance(mCategories);
                uploadTrackFragment.show(fragmentManager, UPLOAD_TRACK_TAG);
            }
        });
        mPicasso.load(mArtist.backPic())
                .placeholder(R.drawable.wallpaper)
                .error(R.drawable.wallpaper)
                .into(mHeader);
        mPicasso.load(mArtist.profilPic())
                .placeholder(R.drawable.wallpaper)
                .error(R.drawable.wallpaper)
                .into(mArtistProfileView);
        mArtistNameView.setText(mArtist.stage_name());
        mFollowersView.setText(NumberFormatter.format(mArtist.followers()));
    }


    private void initCollapsingToolbarLayout() {
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.transparent));
        mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
    }

    private void initViewPager() {

        sViewPager.setAdapter(new ArtistProfilePageAdapter(getSupportFragmentManager()));
        sViewPager.setCurrentItem(0);
        sTabLayout.setupWithViewPager(sViewPager);
    }

    private void initViews() {
        Log.d("artist", "initViews is called");
        sViewPager = (ViewPager) findViewById(R.id.artist_profile_view_pager);
        sTabLayout = (TabLayout) findViewById(R.id.artist_profile_tab_layout);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout)
                findViewById(R.id.artist_profile_toolbar_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mHeader = (ImageView) findViewById(R.id.artist_profile_header);
        mArtistProfileLayout = (CoordinatorLayout) findViewById(R.id.artist_profile_coordinator_layout);
        mArtistProfileView = (ImageView) findViewById(R.id.artist_profile_photo_view);
        mArtistNameView = (TextView) findViewById(R.id.artist_profile_name_view);
        mFollowersView = (TextView) findViewById(R.id.followers_count_text_view);
        mFollowArtistButton = (Button) findViewById(R.id.follow_artist_button);
        mUploadTrackFab = (FloatingActionButton) findViewById(R.id.upload_track_fab);
    }


//    }

    public void toggleFollowButton() {
        Log.d("adapter", "local toggleFollowButton called on => " + isArtistBeingFollowed);
        if ( ! isArtistBeingFollowed ){
            Log.d("adapter", "followArtist");
            mPresenter.followArtist(mArtist);
        }else {
            Log.d("adapter", "unfollowArtist");
            mPresenter.unFollowArtist(mArtist);
        }
    }

    @Override
    public void updateFollowers(int followers) {
        int count = Integer.parseInt(mFollowersView.getText().toString()) + followers;
        mFollowersView.setText(Integer.toString(count));
    }

    @Override
    public void enableFollowButton() {
        mFollowArtistButton.setEnabled(true);
    }

    @Override
    public void disableFollowButton() {
        mFollowArtistButton.setEnabled(false);
    }

    @Override
    public void hideFollowButton() {
        mFollowArtistButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showFollowButton() {
        mFollowArtistButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void setUpArtist(Artist artist) {
        mArtist = artist;
        if(!artist.followed()){
            mFollowArtistButton.setText(getString(R.string.followText));
        }else {
            mFollowArtistButton.setText(getString(R.string.unfollowText));
        }
        mCollapsingToolbarLayout.setTitle(mArtist.stage_name());
        initListeners();
        initViewPager();
    }

    @Override
    public void setArtistBeingFollowed(Boolean isFollowed) {
        Log.d("adapter", "local setArtistBeingFollowed called on => " + isFollowed);
        updateFollowButton(isFollowed);
        isArtistBeingFollowed = isFollowed;
        Log.d("adapter", "local setArtistBeingFollowed updated to => " + isArtistBeingFollowed);
    }

    @Override
    public void showUploadButton() {
        mUploadTrackFab.show(new FloatingActionButton.OnVisibilityChangedListener() {
            @Override
            public void onShown(FloatingActionButton fab) {
                super.onShown(fab);
                Snackbar snackbar = Snackbar.make(mArtistProfileLayout, "ready to upload?",
                        Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }

    @Override
    public void hideUploadButton() {
        mUploadTrackFab.hide();
    }

    @Override
    public void setCategories(String[] categories) {
        mCategories = categories;
    }

    @Override
    public void launchMainActivity() {
        Intent intent = MainActivity.newIntent(this);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }

    public void updateFollowButton(Boolean isFollowed) {
        Log.d("adapter", "local updateFollowButton called on => " + isFollowed);
        if( ! isFollowed){
            mFollowArtistButton.setText(R.string.followText);
        }else {
            mFollowArtistButton.setText(R.string.unfollowText);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemId = item.getItemId();
        switch (menuItemId){
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.activity_back_in,
                        R.anim.activity_back_out);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class ArtistProfilePageAdapter extends FragmentPagerAdapter {

        public ArtistProfilePageAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position){
            switch (position){
                case 0:
                    return ArtistNewTracksFragment.newInstance(mArtistId, mArtist.amTheOne());
                case 1:
                    return ArtistAllTracksFragment.newInstance(mArtistId, mArtist.amTheOne());
            }

            return null;
        }

        @Override
        public int getCount(){
            return sItemCount;
        }

        @Override
        public CharSequence getPageTitle(int position){
            switch (position){
                case 0:
                    return "Latest Releases";
                case 1:
                    return "All Tracks";
            }
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }
}
