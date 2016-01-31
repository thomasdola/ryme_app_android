package primr.apps.eurakacachet.ryme.ryme.ui.view.followCategory;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.category.CategoriesFragment;
import primr.apps.eurakacachet.ryme.ryme.ui.view.main.MainActivity;

public class FollowCategoryActivity extends BaseActivity {

    @Inject FollowCategoryPresenter mFollowCategoryPresenter;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.start_fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_category);
        ButterKnife.bind(this);

        mToolbar.setTitle("Music Categories");
        setSupportActionBar(mToolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(FollowCategoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment = fragmentManager.
                findFragmentById(R.id.music_category_display_fragment_container);
        if(fragment == null){
            fragment = new CategoriesFragment();
            fragmentTransaction.add(R.id.music_category_display_fragment_container,
                    fragment).commit();
        }
    }

}
