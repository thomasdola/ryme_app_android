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
import android.view.Menu;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.category.CategoriesFragment;
import primr.apps.eurakacachet.ryme.ryme.ui.view.favorite.FavoritesFragment;
import primr.apps.eurakacachet.ryme.ryme.ui.view.settings.SettingsActivity;

public class MainActivity extends BaseActivity {

    @Bind(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;


    @Bind(R.id.shitstuff)
    NavigationView mNavigationView;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mToolbar.setTitle(mTitle);
        setSupportActionBar(mToolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.containerView);

        if(currentFragment == null){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.containerView, new HomeTabFragment())
                    .commit();
        }

        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });


        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, mToolbar,
                R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    private void selectDrawerItem(MenuItem item) {
        mDrawerLayout.closeDrawers();
        FragmentManager drawerFragmentManager = getSupportFragmentManager();
        FragmentTransaction drawerFragmentTransaction = drawerFragmentManager.beginTransaction();
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.categories:
                drawerFragmentTransaction.replace(R.id.containerView, new CategoriesFragment())
                        .commit();
                break;
            case R.id.home:
                drawerFragmentTransaction.replace(R.id.containerView, new HomeTabFragment())
                        .commit();
                break;
            case R.id.favorites:
                drawerFragmentTransaction.replace(R.id.containerView, new FavoritesFragment())
                        .commit();
                break;
            case R.id.main_settings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            default:
                drawerFragmentTransaction.replace(R.id.containerView, new HomeTabFragment());

        }

        item.setChecked(true);
        setTitle(item.getTitle());
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
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
