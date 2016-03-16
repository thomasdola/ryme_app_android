package primr.apps.eurakacachet.ryme.ryme.ui.view.main;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import net.grobas.view.PolygonImageView;

import java.io.File;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.EventImage;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.artist.profile.ArtistProfileActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.category.inApp.StoredCategoriesFragment;
import primr.apps.eurakacachet.ryme.ryme.ui.view.crop.CropImageActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.favorite.FavoritesFragment;
import primr.apps.eurakacachet.ryme.ryme.ui.view.offline.downloads.DownloadsFragment;
import primr.apps.eurakacachet.ryme.ryme.ui.view.settings.SettingsActivity;
import primr.apps.eurakacachet.ryme.ryme.utils.helpers.image_effect.LetterImageView;

public class MainActivity extends BaseActivity implements MainMvpView{

    @Inject MainPresenter mMainPresenter;
    @Inject Bus mBus;

    private static final int REQUEST_CROP_CODE = 0;
//    private static final String EXTRA_ = 0;
    public static final String KEY_TITLE = "title";
//    public static final String TAG = "main";
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    Toolbar mToolbar;
    CharSequence mTitle;
    private PolygonImageView mHeaderPhotoAvatar;
    private LetterImageView mHeaderLetterAvatar;
    private TextView mUsernameView;
    private ImageView mBackImage;

    private boolean mIsArtist = false;
    Picasso mPicasso;
    private String mUserId;


    public static Intent newIntent(Context context){
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_main);
        mMainPresenter.attachView(this);
        mMainPresenter.autoLogin(true);
        mPicasso = Picasso.with(this);
        mMainPresenter.isUserAllowedToMakeArtistRequest();
        if( savedInstanceState != null ){
            mTitle = savedInstanceState.getCharSequence(KEY_TITLE, "Ryme");
        }
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(mTitle);
        setSupportActionBar(mToolbar);

        initNavigationView();

        initDrawerLayout();

        initialSetup();
    }

    private void initMainFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.containerView);

        if(currentFragment == null){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.containerView, new HomeTabFragment())
                    .commit();
        }
    }

    private void initialSetup() {
        mMainPresenter.setUserType();
        updateMenu();
        mMainPresenter.setBackImage();
        mMainPresenter.setUsername();
        mMainPresenter.setAvatar();
    }

    private void updateMenu() {
        if( ! mIsArtist ) {
            hideUploadMenu();
        }
    }

    private void setDefaultBackImage() {
        mBackImage.setImageResource(R.drawable.wallpaper);
    }

    private void initNavigationView() {
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        View navigationHeader = mNavigationView.inflateHeaderView(R.layout.nav_drawer_header);
        mHeaderPhotoAvatar = (PolygonImageView) navigationHeader.findViewById(R.id.photo_avatar_view);
        mBackImage = (ImageView) navigationHeader.findViewById(R.id.artist_back_image_view);

        mHeaderLetterAvatar = (LetterImageView) navigationHeader.findViewById(R.id.letter_avatar_view);
        mUsernameView = (TextView) navigationHeader.findViewById(R.id.username_view);
        mHeaderLetterAvatar.setOval(true);
        initListeners();
    }

    private void initListeners() {
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        selectDrawerItem(item);
                        return true;
                    }
                });
        mHeaderPhotoAvatar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startCropActivity(CropImageActivity.REQUEST_CROP_AVATAR);
                return true;
            }
        });

        mBackImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!mIsArtist) {
                    return false;
                } else {
                    startCropActivity(CropImageActivity.REQUEST_CROP_BACK_IMAGE);
                    return true;
                }
            }
        });
        mHeaderLetterAvatar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startCropActivity(CropImageActivity.REQUEST_CROP_AVATAR);
                return true;
            }
        });
    }

    private void initDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(MainActivity.this,
                mDrawerLayout, mToolbar,R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    public void startCropActivity(int type) {
        Intent intent = null;
        if( type == CropImageActivity.REQUEST_CROP_AVATAR){
            intent = CropImageActivity.newIntent(MainActivity.this,
                    CropImageActivity.REQUEST_CROP_AVATAR);
        }else if( type == CropImageActivity.REQUEST_CROP_BACK_IMAGE){
            intent = CropImageActivity.newIntent(MainActivity.this,
                    CropImageActivity.REQUEST_CROP_BACK_IMAGE);
        }
        startActivityForResult(intent, REQUEST_CROP_CODE);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        CharSequence title = mToolbar.getTitle();
        savedInstanceState.putCharSequence(KEY_TITLE, title);
    }

    private void selectDrawerItem(MenuItem item) {
        mDrawerLayout.closeDrawers();
        FragmentManager drawerFragmentManager = getSupportFragmentManager();
        FragmentTransaction drawerFragmentTransaction = drawerFragmentManager.beginTransaction();
        drawerFragmentTransaction.setCustomAnimations(R.anim.activity_in, R.anim.activity_out);
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.categories:
                drawerFragmentTransaction.replace(R.id.containerView, StoredCategoriesFragment.newInstance())
                        .commit();
                break;
            case R.id.home:
                drawerFragmentTransaction.replace(R.id.containerView, HomeTabFragment.newInstance())
                        .commit();
                break;
            case R.id.favorites:
                drawerFragmentTransaction.replace(R.id.containerView, FavoritesFragment.newInstance())
                        .commit();
                break;
            case R.id.downloads:
                drawerFragmentTransaction.replace(R.id.containerView, DownloadsFragment.newInstance())
                        .commit();
                break;
            case R.id.upload:
                Intent uploadsIntent = ArtistProfileActivity.newIntent(this, mUserId);
                startActivity(uploadsIntent);
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                break;
            case R.id.main_settings:
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                break;
            default:
                drawerFragmentTransaction.replace(R.id.containerView, HomeTabFragment.newInstance());

        }

        item.setChecked(true);
        if( itemId != R.id.main_settings){
            mToolbar.setTitle(item.getTitle());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        Log.d("search", "onCreateOptionsMenu called");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CROP_CODE && resultCode == Activity.RESULT_OK){
            int cropType = data.getIntExtra(CropImageActivity.EXTRA_CROP_TYPE, 0);
            String filePath = data.getStringExtra(CropImageActivity.EXTRA_CROPPED_FILE_PATH);
            if(cropType == CropImageActivity.REQUEST_CROP_BACK_IMAGE){
                setBackImage(filePath);
            }else if(cropType == CropImageActivity.REQUEST_CROP_AVATAR){
                hideLetterAvatarView();
                setPhotoAvatar(filePath);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBus.unregister(this);
    }

    @Subscribe
    public void onProfileImagesChanged(EventImage image){
        if(image.getType().equalsIgnoreCase("avatar")){
            setPhotoAvatar(image.getPath());
        }else if(image.getType().equalsIgnoreCase("background")){
            setBackImage(image.getPath());
        }
    }

    @Override
    public void setUsername(String username) {
        mUsernameView.setText(username);
    }

    @Override
    public void setPhotoAvatar(String path) {
        Log.d("main", "setPhotoAvatar called with => " + path);
        loadImage(path, mHeaderPhotoAvatar);
    }

    @Override
    public void setBackImage(String path) {
        Log.d("main", "setBackImage called with => " + path);
        if(path.isEmpty()){
            setDefaultBackImage();
        }else {
            loadImage(path, mBackImage);
        }
    }

    public void loadImage(String path, ImageView target) {
        File imageFile = new File(path);
        mPicasso.load(imageFile)
                .placeholder(R.drawable.wallpaper)
                .error(R.drawable.wallpaper)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_CACHE)
                .into(target);
    }

    @Override
    public void setLetterAvatar(String username) {
        mHeaderLetterAvatar.setLetter(username.charAt(0));
    }

    @Override
    public void hidePhotoAvatarView() {
        mHeaderPhotoAvatar.setVisibility(View.GONE);
        mHeaderLetterAvatar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideUploadMenu() {
        MenuItem upload = mNavigationView.getMenu().getItem(4);
        upload.setVisible(false);
    }

    @Override
    public void isArtist(Boolean isArtist) {
        Log.d("main" , "isArtist called with initial of "+ mIsArtist);
        Log.d("main" , "isArtist called with "+ isArtist);
        mIsArtist = isArtist;
        Log.d("main" , "isArtist done with "+ mIsArtist);
    }

    @Override
    public void setUserId(String uuid) {
        mUserId = uuid;
    }

    @Override
    public void initMainView() {
        initMainFragments();
    }

    @Override
    public void hideLetterAvatarView() {
        mHeaderLetterAvatar.setVisibility(View.GONE);
        mHeaderPhotoAvatar.setVisibility(View.VISIBLE);
    }
}
