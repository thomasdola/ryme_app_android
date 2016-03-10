package primr.apps.eurakacachet.ryme.ryme.ui.view.followCategory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.category.CategoriesFragment;

public class FollowCategoryActivity extends BaseActivity implements FollowCategoryMvpView{

    @Inject
    FollowCategoryPresenter mPresenter;

    Toolbar mToolbar;
    CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_follow_category);

        initViews();

        mPresenter.attachView(this);

        mToolbar.setTitle("Music Categories");
        setSupportActionBar(mToolbar);
        initFragment();
    }



    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment = fragmentManager.
                findFragmentById(R.id.music_category_display_fragment_container);
        if(fragment == null){
            fragment = CategoriesFragment.newInstance();
            fragmentTransaction.add(R.id.music_category_display_fragment_container,
                    fragment).commit();
        }
    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.follow_category_layout);
    }

    public static Intent newIntent(Context context){
        return new Intent(context, FollowCategoryActivity.class);
    }

    @Override
    public void onBackPressed() {
        Snackbar.make(mCoordinatorLayout,
                R.string.moveForward, Snackbar.LENGTH_LONG)
                .show();
    }
}
