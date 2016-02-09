package primr.apps.eurakacachet.ryme.ryme.ui.view.main;

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

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.category.CategoriesFragment;
import primr.apps.eurakacachet.ryme.ryme.ui.view.favorite.FavoritesFragment;
import primr.apps.eurakacachet.ryme.ryme.ui.view.offline.downloads.DownloadsFragment;
import primr.apps.eurakacachet.ryme.ryme.ui.view.settings.SettingsActivity;

public class MainActivity extends BaseActivity {

    public static final String KEY_TITLE = "title";
    public static final String TAG = "main";
    @Inject MainPresenter mMainPresenter;

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    Toolbar mToolbar;
    CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_main);
        if( savedInstanceState != null ){
            Log.d(TAG, "onCreate called with " + savedInstanceState.getString(KEY_TITLE));
            mTitle = savedInstanceState.getCharSequence(KEY_TITLE, "Ryme");
        }
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(mTitle);
        setSupportActionBar(mToolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.containerView);

        if(currentFragment == null){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.containerView, new HomeTabFragment())
                    .commit();
        }

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        selectDrawerItem(item);
                        return true;
                    }
                });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(MainActivity.this,
                mDrawerLayout, mToolbar,R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
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
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.categories:
                drawerFragmentTransaction.replace(R.id.containerView, CategoriesFragment.newInstance())
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
            case R.id.main_settings:
//                drawerFragmentTransaction.replace(R.uuid.containerView, SettingsViewFragment.newInstance())
//                        .commit();
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            default:
                drawerFragmentTransaction.replace(R.id.containerView, HomeTabFragment.newInstance());

        }

        item.setChecked(true);
        mToolbar.setTitle(item.getTitle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();
        switch (menuId){
            case R.id.menu_settings:
                Log.d("menu", "clicked");
                return true;
            default:
                return super.onOptionsItemSelected(item);
            }
    }
}
